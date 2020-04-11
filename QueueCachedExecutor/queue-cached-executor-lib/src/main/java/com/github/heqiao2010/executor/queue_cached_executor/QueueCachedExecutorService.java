package com.github.heqiao2010.executor.queue_cached_executor;

import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTask;
import com.github.heqiao2010.executor.queue_cached_executor.model.TaskRunner;
import com.github.heqiao2010.executor.queue_cached_executor.model.TaskType;
import com.github.heqiao2010.executor.queue_cached_executor.service.CacheableTaskService;
import com.github.heqiao2010.executor.queue_cached_executor.utils.NamedThreadFactory;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 能够缓存任务的ExecutorService
 * <br>
 *     <code>QueueLimitedThreadPoolExecutor</code>虽然实现了限制队列大小，但是当任务队列超过这个大小的时候，默认逻辑是将任务丢弃了，
 *     比较暴力，这里采用缓存到mongo的任务表中，避免任务丢失；
 * </br>
 */
@Builder
public class QueueCachedExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(QueueCachedExecutorService.class);

    private final ExecutorService delegatedExecutorService;
    private final LinkedBlockingQueue<Runnable> taskQueue;

    private RunnableConverter runnableConverter;
    private CacheableTaskService cacheableTaskService;
    @Getter
    private int corePoolSize;
    @Getter
    private int maximumPoolSize;
    @Getter
    private long keepAliveTime;
    @Getter
    private int queueLength;
    @Getter
    private String threadPrefix;
    @Getter
    private TaskType type;

    public QueueCachedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueLength,
                                      String threadPrefix, TaskType type, RunnableConverter runnableConverter,
                                      CacheableTaskService cacheableTaskService) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.queueLength = queueLength;
        this.threadPrefix = threadPrefix;
        this.type = type;
        this.runnableConverter = runnableConverter;
        this.cacheableTaskService = cacheableTaskService;

        taskQueue = new LinkedBlockingQueue<>(queueLength);
        int maxPoolSize = maximumPoolSize > 0 ? maximumPoolSize : Runtime.getRuntime().availableProcessors();
        delegatedExecutorService = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MINUTES,
                taskQueue,
                NamedThreadFactory.create(threadPrefix),
                new ReQueueInMongoPolicy(this)
        );
    }

    public int getTaskQueueSize() {
        return taskQueue.size();
    }

    public Class<? extends CacheableTask> getTaskClass(){
        return runnableConverter.taskClass();
    }

    /**
     * 执行任务
     */
    public void execute(CacheableTask cacheableTask) {
        Runnable realRunner = runnableConverter.convert(cacheableTask);
        if (null == realRunner) {
            logger.warn("realRunner is null!");
            return;
        }
        TaskRunner taskRunner = TaskRunner.builder()
                .task(cacheableTask)
                .delegate(realRunner)
                .build();
        delegatedExecutorService.execute(taskRunner);
    }

    private void cacheTask(TaskRunner taskRunner) {
        try {
            cacheableTaskService.save(taskRunner.getTask().toPO());
        } catch (Exception e) {
            logger.error("cacheTask failed!", e);
        }
    }

    /**
     * 拒绝策略
     */
    private static class ReQueueInMongoPolicy implements RejectedExecutionHandler {

        private final QueueCachedExecutorService executorHolder;

        ReQueueInMongoPolicy(QueueCachedExecutorService executorHolder) {
            this.executorHolder = executorHolder;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!(r instanceof TaskRunner)) {
                logger.warn("Task is not a TaskRunner, discard. {}", r);
                return ;
            }
            TaskRunner taskRunner = (TaskRunner)r;
            executorHolder.cacheTask(taskRunner);
        }
    }
}

package com.github.heqiao2010.executor.queue_cached_executor;



import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTask;
import com.github.heqiao2010.executor.queue_cached_executor.model.CacheableTaskPO;
import com.github.heqiao2010.executor.queue_cached_executor.model.TaskType;
import com.github.heqiao2010.executor.queue_cached_executor.service.CacheableTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class QueueCachedExecutorManager {
    private final static Logger log = LoggerFactory.getLogger(QueueCachedExecutorManager.class);

    private final static int FIXED_DELAY = 60;
    // 队列大小小于最大值的80%时，才会将缓存的任务重新入队
    private final static float SLOT = 0.8f;

    private final Map<TaskType, QueueCachedExecutorService> EXECUTOR_MAP =
            new ConcurrentHashMap<>();

    private static final ScheduledExecutorService scheduledExecutorService = Executors
            .newScheduledThreadPool(1, new BasicThreadFactory.Builder()
                    .namingPattern("queue-cached-scheduled-executor")
                    .build());

    private final CacheableTaskService cacheableTaskService;

    public QueueCachedExecutorManager(CacheableTaskService cacheableTaskService) {
        this.cacheableTaskService = cacheableTaskService;
        scheduledExecutorService.scheduleWithFixedDelay(this::doReQueue,
                FIXED_DELAY, FIXED_DELAY, TimeUnit.SECONDS);
    }

    public void register(QueueCachedExecutorService executorService) {
        EXECUTOR_MAP.put(executorService.getType(), executorService);
    }

    private void doReQueue(){
        List<CacheableTaskPO> taskList = cacheableTaskService.queryLatest();
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        Map<String, List<CacheableTaskPO>> taskMap = taskList.stream()
                .collect(Collectors.groupingBy(CacheableTaskPO::getType));
        taskMap.forEach((type, tasks) -> {
            TaskType typeEnum = TaskType.fromName(type);
            if (null == typeEnum) {
                log.warn("Unknown type: {}", type);
                return;
            }
            QueueCachedExecutorService executor = EXECUTOR_MAP.get(typeEnum);
            if (null == executor) {
                log.warn("No executor found by type: {}", type);
                return;
            }
            if (canReQueue(executor)) {
                tasks.stream()
                        .map(task -> CacheableTask.from(task, executor.getTaskClass()))
                        .filter(Objects::nonNull)
                        .forEach(executor::execute);
            }
        });
    }

    private boolean canReQueue(QueueCachedExecutorService executor) {
        return (float)executor.getTaskQueueSize() / executor.getQueueLength() < SLOT;
    }
}

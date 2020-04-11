package com.github.heqiao2010.executor.queue_cached_executor.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.heqiao2010.executor.queue_cached_executor.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 可以缓存的任务数据
 */
public interface CacheableTask {
    Logger logger = LoggerFactory.getLogger(CacheableTask.class);

    /**
     * 任务类型
     */
    String getType();

    /**
     * 转化为可持久化对象
     */
    default CacheableTaskPO toPO() throws JsonProcessingException {
        return CacheableTaskPO.builder()
                .type(getType())
                .createTime(System.currentTimeMillis())
                .content(JsonUtils.writeValueAsString(this))
                .build();
    }

    static <BO extends CacheableTask> BO from(CacheableTaskPO po, Class<BO> clazz){
        try{
            return JsonUtils.readValue(po.getContent(), clazz);
        } catch (Exception e){
            // 忽略格式不正确的通知信息
            logger.error("json convert failed! type: "+ po.getType(), e);
            return null;
        }
    }
}

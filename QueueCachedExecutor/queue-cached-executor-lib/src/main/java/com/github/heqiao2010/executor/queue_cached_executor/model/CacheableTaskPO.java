package com.github.heqiao2010.executor.queue_cached_executor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = CacheableTaskPO.COLLECTION)
public class CacheableTaskPO {
    public static final String COLLECTION = "cacheable_task";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_REQUEUE_TIME = "reQueueTime";
    public static final String FIELD_CREATE_TIME = "createTime";

    @Id
    private String id;

    private String type;

    @Indexed(background = true)
    private Long createTime;

    // 重新加入队列后，过1天失效
    @Indexed(expireAfterSeconds = 86400)
    private Date reQueueTime;

    // 任务内容
    private String content;
}

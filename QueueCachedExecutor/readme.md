# QueueCachedExecutor

很多时候采用线程池去执行一些异步任务，在某些情况下我们需要控制任务队列的大小来避免OOM，
而当队列满了的时候，需要采用一些策略来避免任务丢失。

QueueCachedExecutor可以在队列满时，缓存到mongo的任务表中，然后通过一个独立线程来重新消费，避免任务丢失。
# AopCacheUtil

一个基于AspectJ的缓存工具包,用于方便实现J2EE中经常遇到的缓存逻辑;此工具包包含如下功能:

1. 根据缓存策略拦截方法执行;(已完成)
2. 支持配置缓存时间;(已完成)
3. 支持缓存更新;(已完成)
4. 支持缓存监控;

示例：

在Controller上：
```
    @ResponseBody
    @Cache(name = "random-test", timeUnit = TimeUnit.SECONDS, ttl = 1000, keys = { @Key("#{0}"), @Key("#{1}") })
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public String cacheByName(@RequestParam("name") String name, @RequestParam("age") int age) {
        //...
    }

    @ResponseBody
    @Update(name = "random-test", timeUnit = TimeUnit.SECONDS, ttl = 1000, keys = { @Key("#{0}"), @Key("#{1}") })
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateByName(@RequestParam("name") String name, @RequestParam("age") int age) {
        //...
    }

    @ResponseBody
    @Evict(name = "random-test", keys = { @Key("#{cacheName}"), @Key("#{1}") })
    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    public String evictByName(@RequestParam("name") @Param("cacheName") String name, @RequestParam("age") int age) {
       //...
    }
```
在业务层：
```
	@Override
	@Cache(name = "user", keys = { @Key("#{0}"), @Key("#{1}") }, timeUnit = TimeUnit.MINUTES, ttl = 1)
	public UserDO query(String name, int age) {
		//...
	}

	@Override
	@Update(name = "user", keys = { @Key("#{user.name}"), @Key("#{user.age}") }, timeUnit = TimeUnit.MINUTES, ttl = 1)
	public UserDO update(@Param("user") UserDO u) {
		//...
	}

	@Override
	@Evict(name = "user", keys = { @Key("#{user.name}"), @Key("#{user.age}") })
	public void delete(@Param("user") UserDO u) {
        //...
	}
```

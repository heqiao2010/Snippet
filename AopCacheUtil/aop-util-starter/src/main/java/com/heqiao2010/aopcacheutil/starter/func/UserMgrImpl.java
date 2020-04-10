package com.heqiao2010.aopcacheutil.starter.func;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.heqiao2010.aopcacheutil.core.annotation.Cache;
import com.heqiao2010.aopcacheutil.core.annotation.Evict;
import com.heqiao2010.aopcacheutil.core.annotation.Key;
import com.heqiao2010.aopcacheutil.core.annotation.Param;
import com.heqiao2010.aopcacheutil.core.annotation.Update;
import com.heqiao2010.aopcacheutil.starter.entity.UserDO;
/**
 * 一个userDO例子
 * @author heqiao
 *
 */
@Component
public class UserMgrImpl implements UserMgr {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public void add(UserDO u) {
		log.info(u + " is added.");
	}

	@Override
	@Cache(name = "user", keys = { @Key("#{0}"), @Key("#{1}") }, timeUnit = TimeUnit.MINUTES, ttl = 1)
	public UserDO query(String name, int age) {
		UserDO u = new UserDO();
		u.setName(name);
		u.setAge(age);
		u.setTestRandom(UUID.randomUUID().toString());
		return u;
	}

	@Override
	@Update(name = "user", keys = { @Key("#{user.name}"), @Key("#{user.age}") }, timeUnit = TimeUnit.MINUTES, ttl = 1)
	public UserDO update(@Param("user") UserDO u) {
		return u;
	}

	@Override
	@Evict(name = "user", keys = { @Key("#{user.name}"), @Key("#{user.age}") })
	public void delete(@Param("user") UserDO u) {
	}

}

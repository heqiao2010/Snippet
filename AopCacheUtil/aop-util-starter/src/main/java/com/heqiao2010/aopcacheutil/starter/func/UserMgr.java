package com.heqiao2010.aopcacheutil.starter.func;

import com.heqiao2010.aopcacheutil.starter.entity.UserDO;

public interface UserMgr {

	public void add(UserDO u);

	public UserDO query(String name, int age);

	public UserDO update(UserDO u);

	public void delete(UserDO u);
}

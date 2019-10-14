# -*- coding: utf-8 -*-
"""
对于给定的key前缀,清除其值前后的双引号
"""

import redis
conn = redis.Redis(host='127.0.0.1', port=6379)


# top key
DETECT_DASHBOARD_TOP = "detect_dashboard_top*"


# 找出所有dashboard模块的key
def get_all_dashboard_key():
    dashboard_keys = []
    for key in conn.keys(DETECT_DASHBOARD_TOP):
        key_type = conn.type(key)
        print("key: " + key + " type is: " + key_type)
        if "zset" == key_type:
            dashboard_keys.append(key)
    return dashboard_keys


# 移除zset中字符串值的前后的双引号,如果字符串值前后没有双引号,则不处理
def remove_quotation_in_zset(zset_key):
    # 迭代取出
    for value_score in conn.zscan_iter(zset_key):
        value = value_score[0]
        score = value_score[1]
        if len(value) > 2 and value.startswith("\"") and value.endswith("\""):
            print("process[" + zset_key + "]: " + value)
            new_value = value[1:-1]
            conn.zadd(zset_key, {new_value: score})
            conn.zrem(zset_key, value)


if __name__ == "__main__":
    keys = get_all_dashboard_key()
    for dashboard_key in keys:
        remove_quotation_in_zset(dashboard_key)

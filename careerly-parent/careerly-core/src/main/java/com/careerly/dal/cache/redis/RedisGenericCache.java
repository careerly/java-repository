package com.careerly.dal.cache.redis;

import com.careerly.dal.meta.DATAMeta;

/**
 * 实现描述:redis基本对象
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-2 下午6:46
 */
public class RedisGenericCache<T> implements DATAMeta<T> {

    @Override
    public T get(int id) {
        return null;
    }

    @Override
    public void add(T entity) {

    }

    @Override
    public void remove(T entity) {

    }

    @Override
    public void update(T entity) {

    }
}

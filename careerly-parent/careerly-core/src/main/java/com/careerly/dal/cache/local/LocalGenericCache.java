package com.careerly.dal.cache.local;

import com.careerly.dal.meta.DATAMeta;

/**
 * 实现描述: 本地基本缓存对象
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-2 下午6:45
 */
public class LocalGenericCache<T> implements DATAMeta<T> {

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

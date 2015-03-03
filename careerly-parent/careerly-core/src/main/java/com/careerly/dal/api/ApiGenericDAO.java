package com.careerly.dal.api;

import com.careerly.dal.meta.DATAMeta;

/**
 * 实现描述:接口访问基本信息类
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-2 下午6:48
 */
public class ApiGenericDAO<T> implements DATAMeta<T> {

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

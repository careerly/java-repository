/*
 * Copyright (c) JForum Team. All rights reserved.
 *
 * The software in this package is published under the terms of the LGPL
 * license a copy of which has been included with this distribution in the
 * license.txt file.
 *
 * The JForum Project
 * http://www.jforum.net
 */
package com.careerly.dal.repository;

import com.careerly.dal.meta.DATAMeta;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;

/**
 * 实现描述: hibernate基本信息dao
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-2 下午6:19
 */
public class HibernateGenericDAO<T> implements DATAMeta<T> {
    protected Class<T> persistClass;
    protected final Session session;

    public HibernateGenericDAO(Session session) {
        this.session = session;
        this.persistClass = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void remove(T entity) {
        session.delete(entity);
    }


    @Override
    public T get(int id) {
        return (T) session.get(this.persistClass, id);
    }


    @Override
    public void add(T entity) {
        session.save(entity);
    }


    @Override
    public void update(T entity) {
        session.update(entity);
    }
}

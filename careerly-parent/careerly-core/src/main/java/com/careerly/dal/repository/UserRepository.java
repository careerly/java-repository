package com.careerly.dal.repository;

import com.careerly.dal.meta.DATAMeta;
import com.careerly.dal.entity.User;
import org.hibernate.Session;

/**
 * 实现描述:
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-3 下午4:53
 */
public class UserRepository extends HibernateGenericDAO<User> implements DATAMeta<User> {

    public UserRepository(Session session) {
        super(session);
    }
}

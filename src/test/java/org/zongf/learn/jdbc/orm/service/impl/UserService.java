package org.zongf.learn.jdbc.orm.service.impl;

import org.zongf.learn.jdbc.orm.anno.EnableTranscation;
import org.zongf.learn.jdbc.orm.dao.UserDao;
import org.zongf.learn.jdbc.orm.po.UserPO;
import org.zongf.learn.jdbc.orm.service.api.IUserService;

import java.util.List;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-27 16:23
 * @since 1.0
 */
public class UserService implements IUserService {

    private UserDao userDao = new UserDao();

    @EnableTranscation(openNewTx = true)
    @Override
    public boolean save(UserPO userPO) {
        return this.userDao.save(userPO);
    }

    @Override
    public List<UserPO> queryAll() {
        return this.userDao.queryAll();
    }
}

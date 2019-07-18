package org.zongf.learn.jdbc.orm.test;

import org.junit.Test;
import org.zongf.learn.jdbc.orm.dao.UserDao;
import org.zongf.learn.jdbc.orm.po.UserPO;

import java.util.List;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-27 16:42
 * @since 1.0
 */
public class UserDaoTest {

    private UserDao userDao = new UserDao();

    @Test
    public void queryAll(){
        List<UserPO> userPOList = userDao.queryAll();
        userPOList.forEach(System.out::println);
    }

}

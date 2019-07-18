package org.zongf.learn.jdbc.orm.service.api;

import org.zongf.learn.jdbc.orm.po.UserPO;

import java.util.List;

/**
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public interface IUserService {

    // 保存实体
    boolean save(UserPO userPO);

    // 查询所有实体
    List<UserPO> queryAll();

}

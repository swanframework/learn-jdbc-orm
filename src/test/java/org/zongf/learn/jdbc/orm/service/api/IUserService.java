package org.zongf.learn.jdbc.orm.service.api;


import org.zongf.learn.jdbc.orm.po.UserPO;

import java.util.List;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-27 16:22
 * @since 1.0
 */
public interface IUserService {

    // 保存实体
    boolean save(UserPO userPO);

    // 通过id删除实体
    boolean deleteById(Integer id);

    // 更新实体
    boolean update(UserPO userPO);

    // 通过id查询实体
    UserPO findById(Integer id);

    // 查询所有实体
    List<UserPO> queryAll();


}

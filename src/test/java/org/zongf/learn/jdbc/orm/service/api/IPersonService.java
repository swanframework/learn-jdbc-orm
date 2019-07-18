package org.zongf.learn.jdbc.orm.service.api;

import org.zongf.learn.jdbc.orm.po.PersonPO;

import java.util.List;

/**
 * @Description: t_person API
 * @author: zongf
 * @date: 2019-06-27 16:22
 * @since 1.0
 */
public interface IPersonService {

    // 保存实体
    boolean save(PersonPO personPO);

    // 通过id删除实体
    boolean deleteById(Integer id);

    // 更新实体
    boolean update(PersonPO personPO);

    // 通过id查询实体
    PersonPO findById(Integer id);

    // 查询所有实体
    List<PersonPO> queryAll();
}

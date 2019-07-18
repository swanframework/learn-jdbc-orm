package org.zongf.learn.jdbc.orm.test;

import org.junit.Assert;
import org.junit.Test;
import org.zongf.learn.jdbc.orm.po.UserPO;
import org.zongf.learn.jdbc.orm.util.EntityManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @Description: 测试类
 * @author: zongf
 * @date: 2019-06-27 15:23
 * @since 1.0
 */
public class EntityManagerTest {

    // 测试保存
    @Test
    public void save(){

        UserPO userPO = new UserPO();
        userPO.setName("zhangsan_" + Instant.now().getEpochSecond());
        userPO.setPassword("123456");

        boolean isSuccess = EntityManager.save(userPO);

        Assert.assertEquals(true, isSuccess);
        Assert.assertNotNull(userPO.getId());
    }

    // 测试通过id 删除
    @Test
    public void deleteById() {

        // 保存一个实体
        UserPO userPO = new UserPO();
        userPO.setName("zhangsan_" + Instant.now().getEpochSecond());
        userPO.setPassword("abcdefg");
        EntityManager.save(userPO);

        boolean isSuccess = EntityManager.deleteById(UserPO.class, userPO.getId());
        Assert.assertEquals(true, isSuccess);
    }

    // 测试更新
    @Test
    public void update(){
        // 保存一个实体
        UserPO userPO = new UserPO();
        userPO.setName("zhangsan");
        userPO.setPassword("123456");
        EntityManager.save(userPO);

        // 更新名称
        userPO.setName("lisi");
        userPO.setPassword("123456");
        EntityManager.update(userPO);

        UserPO afterUserPO = EntityManager.findById(UserPO.class, userPO.getId());

        Assert.assertEquals("lisi", afterUserPO.getName());
    }

    // 测试查询
    @Test
    public void findById(){

        UserPO userPO = new UserPO();
        userPO.setName(LocalDateTime.now().toString());
        userPO.setPassword("123456");

        EntityManager.save(userPO);

        UserPO afterUserPO = EntityManager.findById(UserPO.class, userPO.getId());

        Assert.assertNotNull(afterUserPO);
        Assert.assertEquals(userPO.getId(), afterUserPO.getId());
        Assert.assertEquals(userPO.getName(), afterUserPO.getName());

    }

    // 测试查询所有
    @Test
    public void queryAll(){
        List<UserPO> userPOS = EntityManager.queryAll(UserPO.class);
        Assert.assertNotNull(userPOS);
        userPOS.forEach(System.out::println);
    }
}

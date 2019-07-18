package org.zongf.learn.jdbc.orm.service.impl;

import org.zongf.learn.jdbc.orm.anno.EnableTranscation;
import org.zongf.learn.jdbc.orm.factory.ServiceFactory;
import org.zongf.learn.jdbc.orm.po.PersonPO;
import org.zongf.learn.jdbc.orm.po.UserPO;
import org.zongf.learn.jdbc.orm.service.api.IMixService;
import org.zongf.learn.jdbc.orm.service.api.IPersonService;
import org.zongf.learn.jdbc.orm.service.api.IUserService;

import java.util.List;

/**
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class MixService implements IMixService {

    private IUserService userService = ServiceFactory.getService(UserService.class);

    private IPersonService personService = ServiceFactory.getService(PersonService.class);

    @EnableTranscation
    @Override
    public void success() {

        this.userService.save(new UserPO("user-01", "123456"));

        this.personService.save(new PersonPO("person-01", "abcdefg"));

    }

    @EnableTranscation
    @Override
    public void error() {
        this.userService.save(new UserPO("user-01", "123456"));

        this.personService.save(new PersonPO("person-01", "abcdefg"));

        // 模拟异常会馆
        int a = 1/0;
    }

    @Override
    public void show() {
        List<UserPO> userPOS = this.userService.queryAll();
        List<PersonPO> personPOS = this.personService.queryAll();

        System.out.println("\n****** t_user: *****");
        userPOS.forEach(System.out::println);

        System.out.println("\n****** t_person: *****");
        personPOS.forEach(System.out::println);
    }
}

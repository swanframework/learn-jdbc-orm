package org.zongf.learn.jdbc.orm.test;

import org.junit.Test;
import org.zongf.learn.jdbc.orm.factory.ServiceFactory;
import org.zongf.learn.jdbc.orm.service.api.IMixService;
import org.zongf.learn.jdbc.orm.service.impl.MixService;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-27 17:02
 * @since 1.0
 */
public class MixServiceTest {

    private IMixService mixService = ServiceFactory.getService(MixService.class);

    @Test
    public void test(){
        this.mixService.success();
    }

    @Test
    public void test_error(){
        this.mixService.error();
    }

    @Test
    public void test_show(){
        this.mixService.show();
    }
}

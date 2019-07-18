package org.zongf.learn.jdbc.orm.service.impl;

import org.zongf.learn.jdbc.orm.anno.EnableTranscation;
import org.zongf.learn.jdbc.orm.dao.PersonDao;
import org.zongf.learn.jdbc.orm.po.PersonPO;
import org.zongf.learn.jdbc.orm.service.api.IPersonService;

import java.util.List;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-27 16:53
 * @since 1.0
 */
public class PersonService implements IPersonService {

    private PersonDao personDao = new PersonDao();

    @EnableTranscation(openNewTx = false)
    @Override
    public boolean save(PersonPO personPO) {
        return this.personDao.save(personPO);
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.personDao.deleteById(id);
    }

    @Override
    public boolean update(PersonPO personPO) {
        return this.personDao.update(personPO);
    }

    @Override
    public PersonPO findById(Integer id) {
        return this.personDao.findById(id);
    }

    @Override
    public List<PersonPO> queryAll() {
        return this.personDao.queryAll();
    }
}

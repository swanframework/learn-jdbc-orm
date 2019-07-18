package org.zongf.learn.jdbc.orm.po;

import org.zongf.learn.jdbc.orm.anno.AutoIncId;
import org.zongf.learn.jdbc.orm.anno.TableName;

/**
 * @Description: Person 实体
 * @author: zongf
 * @date: 2019-06-26 11:18
 * @since 1.0
 */
@TableName("t_person")
public class PersonPO {

    @AutoIncId("id")
    private Integer id;

    private String name;

    private String password;

    public PersonPO() {
    }

    public PersonPO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package org.zongf.learn.jdbc.orm.po;

import org.zongf.learn.jdbc.orm.anno.AutoIncId;
import org.zongf.learn.jdbc.orm.anno.TableName;

import java.time.LocalDate;

/**
 * @Description:
 * @author: zongf
 * @date: 2019-06-26 11:18
 * @since 1.0
 */
@TableName("t_user")
public class UserPO {

    @AutoIncId("id")
    private Integer id;

    private String name;

    private String password;

	public UserPO() {
        super();
    }

	public UserPO( String name, String password) {
        super();
		this.name = name;
		this.password = password;
    }

    public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

    public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

    public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return this.password;
	}

    public String toString() {
		return super.toString() + ": {id:" + id + ", name:" + name + ", password:" + password  + "}";
	}
}

/**
*Login.java
*
*@author Sagar Shrestha 
**/
package com.spring.elasticsearch.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class User {
 
    private String userName;
    private String password;
    private String userRole;
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
 
}
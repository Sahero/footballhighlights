/**
 *Search.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.model;

public class Search {
	private String usingTags;
	private String usingCategories;
	private String usingAll;
	
	public Search(){}
	
	public Search(String usingTags, String usingCategories, String usingAll) {
		super();
		this.usingTags = usingTags;
		this.usingCategories = usingCategories;
		this.usingAll = usingAll;
	}

	public String getUsingTags() {
		return usingTags;
	}
	public void setUsingTags(String usingTags) {
		this.usingTags = usingTags;
	}
	public String getUsingCategories() {
		return usingCategories;
	}
	public void setUsingCategories(String usingCategories) {
		this.usingCategories = usingCategories;
	}
	public String getUsingAll() {
		return usingAll;
	}
	public void setUsingAll(String usingAll) {
		this.usingAll = usingAll;
	}

	@Override
	public String toString() {
		return "Search [usingTags=" + usingTags + ", usingCategories=" + usingCategories + ", usingAll=" + usingAll
				+ "]";
	}


}

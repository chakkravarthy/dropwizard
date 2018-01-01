package com.demo.core;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -2818024061525099853L;
	
	private String name;
	private Integer id;

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

}

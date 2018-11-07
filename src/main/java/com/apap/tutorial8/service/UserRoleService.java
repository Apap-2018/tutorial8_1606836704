package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	UserRoleModel getUserByUsername(String username);
	UserRoleModel updateUser(String username, String newPass);
	String getUsernameCurrentLoginUser();
	boolean checkPassword(String oldPass, String newPass, String newConfirmPass);
}

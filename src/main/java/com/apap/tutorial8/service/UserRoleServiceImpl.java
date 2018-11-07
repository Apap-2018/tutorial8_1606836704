package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public UserRoleModel getUserByUsername(String username) {
		return userDb.findByUsername(username);
	}

	@Override
	public UserRoleModel updateUser(String username, String newPass) {
		UserRoleModel user = userDb.findByUsername(username);
		String pass = encrypt(newPass);
		user.setPassword(pass);
		System.out.println(user.getRole());
		return userDb.save(user);
	}
	
	@Override
	public String getUsernameCurrentLoginUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username="";
		if (principal instanceof UserDetails) {
		username = ((UserDetails)principal).getUsername();
		} else {
		username = principal.toString();
		}
		
		return username;
	}

	@Override
	public boolean checkPassword(String oldPass, String newPass, String newConfirmPass) {
		String username = getUsernameCurrentLoginUser();
		UserRoleModel user = userDb.findByUsername(username);
		String oldUserPass = user.getPassword();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(newPass);
		System.out.println(newConfirmPass);
		System.out.println(oldPass);
		
		if(!(newPass.equals(newConfirmPass))) {
			return false;
		} 
		if(!(passwordEncoder.matches(oldPass, oldUserPass))) {
			return false;
		}
		return true;
	}
	
}

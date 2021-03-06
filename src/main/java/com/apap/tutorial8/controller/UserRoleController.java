package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePassword(@RequestParam("oldPass") String oldPass, 
			@RequestParam("newPass") String newPass, 
			@RequestParam("confirmNewPass") String confirmNewPass) {
		
		boolean isPassCorrect = userService.checkPassword(oldPass, newPass, confirmNewPass);
		System.out.println(isPassCorrect);
		if(!isPassCorrect) {
			 return "error-pass";
		}
		else {

			String username = userService.getUsernameCurrentLoginUser();
			
			userService.updateUser(username, newPass);
			
			return "home";
		}
		
	}
}

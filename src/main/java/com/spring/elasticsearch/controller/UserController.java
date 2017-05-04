/**
 *LoginController.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.elasticsearch.model.Football;
import com.spring.elasticsearch.model.Search;
import com.spring.elasticsearch.service.HighlightService;


//@Controller
//@RequestMapping("/highlights")
public class UserController {
	@Autowired
	private HighlightService highlightService;

	

}
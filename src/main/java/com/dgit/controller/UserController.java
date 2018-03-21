package com.dgit.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDto;
import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGet(){
		logger.info("login");
	}
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void loginPost(LoginDto dto, Model model){
		logger.info("loginPost");
		logger.info("LoginDto : " +  "ID : " + dto.getUserid() + "PW : " + dto.getUserpw());
		
		try {
			MemberVO vo = service.readWithPW(dto.getUserid(), dto.getUserpw());
			
			if(vo == null){
				logger.info("user 없음 .....");
				logger.info("loginPost return ....."); 
				return;
			}
			
			dto.setUserid(vo.getUserid());
			dto.setUserpw("");
						
			model.addAttribute("loginDTO", dto); // df
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest req){
		
		req.getSession().removeAttribute("login");
		
		
		return "redirect: /ex01/sboard/listPage";
	}
	
}

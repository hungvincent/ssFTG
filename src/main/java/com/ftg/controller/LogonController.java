package com.ftg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ftg.dao.entity.Sysadmin;
import com.ftg.repository.SysadminRepository;

@Controller
public class LogonController {

	@Autowired
	private SysadminRepository sysadminRepository;
	

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request){
		List<Sysadmin> list = sysadminRepository.findAll();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
	    return model;
	}
	
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String login(@RequestParam("acc")String acc, @RequestParam("pwd")String pwd, RedirectAttributes redirectAttributes,
							HttpServletRequest request){
		
//		Sysadmin sa = sysadminRepository.findByAccount(acc);
//		if(sa == null){
//			redirectAttributes.addFlashAttribute("message", "帳號不存在");
//		}else{
//			if(!pwd.equals(sa.getPassword())){
//				redirectAttributes.addFlashAttribute("message", "密碼輸入錯誤");
//			}else{
//				request.getSession().setAttribute("sysuser", sa);
//				return "redirect:/index";
//			}
//		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	} 
}
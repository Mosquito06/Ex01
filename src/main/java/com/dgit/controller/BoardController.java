package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.BoardVO;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("listAll")
	public void listAll(Model model) throws Exception{
		logger.info("listAll");
		
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
		
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGet(){
		logger.info("register GET");
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception{
		logger.info("register POST");
		
		service.regist(vo);	
		
		return "redirect:/board/listAll";
		
	}
	
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public void read(int bno, Model model) throws Exception{
		logger.info("read GET");
		
		BoardVO board = service.read(bno);
		model.addAttribute("board", board);
	}
	
	@RequestMapping(value = "Modify", method = RequestMethod.GET)
	public void modifyGet(int bno, Model model) throws Exception{
		logger.info("Modify GET");
		
		BoardVO board = service.read(bno);
		model.addAttribute("board", board);
		
	}
	
	@RequestMapping(value = "Modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO vo) throws Exception{
		logger.info("Modify POST");
		
		service.modify(vo);;
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value = "Remove", method = RequestMethod.GET)
	public String remove(int bno) throws Exception{
		logger.info("Remove GET");
		service.remove(bno);
		
		return "redirect:/board/listAll";
		
	}
	
	
	
}

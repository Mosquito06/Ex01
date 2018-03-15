package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("listPage")
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("listPage"); 
		PageMaker pageMake = new PageMaker();
		pageMake.setCri(cri);
		pageMake.setTotalCount(service.listSearchCount(cri));
				
		List<BoardVO> list = service.listSearch(cri);
		model.addAttribute("list", list);		
		model.addAttribute("pageMake", pageMake);
	}
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGet() throws Exception{
		logger.info("registerGet"); 
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPost(BoardVO board) throws Exception{
		logger.info("registerPost"); 
		service.regist(board);
		
		return "redirect:listPage";
	}
	
	@RequestMapping("readPage")
	public void readPage(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("readPage");
		BoardVO board = service.read(bno, true);
				
		model.addAttribute("board", board);
	}
	
	@RequestMapping(value = "ModifyPage", method = RequestMethod.GET)
	public void ModifyPageGet(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("ModifyPage GET");
		
		BoardVO board = service.read(bno, false);
		model.addAttribute("board", board);
		
	}
	
	@RequestMapping(value = "ModifyPage", method = RequestMethod.POST)
	public String ModifyPagePost(BoardVO vo, SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("ModifyPage POST");
		
		service.modify(vo);
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect: listPage";
	}
	
	@RequestMapping("RemovePage")
	public String RemovePage(int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("RemovePage GET");
		service.remove(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect: listPage"; 
		
	}
	
}

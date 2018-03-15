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
	
	@RequestMapping("listCri")
	public void listCri(Criteria cri, Model model) throws Exception{
		logger.info("listCri");
		
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);		
	}
	
	@RequestMapping("listPage")
	public void listPage(Criteria cri, Model model) throws Exception{
		logger.info("listPage");
		PageMaker pageMake = new PageMaker();
		pageMake.setCri(cri);
		pageMake.setTotalCount(service.listCountCriteria());
				
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);		
		model.addAttribute("pageMake", pageMake);
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
		
		BoardVO board = service.read(bno, true);
		model.addAttribute("board", board);
	}
	
	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(int bno, boolean count, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("readPage GET");
		logger.info("count" + count);
		BoardVO board = service.read(bno, false);
		
		if(count == true){
			board.setViewcnt(board.getViewcnt()+1);
			service.modify(board);
		}
		
		BoardVO board2 = service.read(bno, false);
		model.addAttribute("board", board2);
		
	}
	
	@RequestMapping(value = "Modify", method = RequestMethod.GET)
	public void modifyGet(int bno, Model model) throws Exception{
		logger.info("Modify GET");
		
		BoardVO board = service.read(bno, false);
		model.addAttribute("board", board);
		
	}
	
	@RequestMapping(value = "Modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO vo) throws Exception{
		logger.info("Modify POST");
		
		service.modify(vo);;
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value = "ModifyPage", method = RequestMethod.GET)
	public void ModifyPageGet(int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("ModifyPage GET");
		
		BoardVO board = service.read(bno, false);
		model.addAttribute("board", board);
		
	}
	
	@RequestMapping(value = "ModifyPage", method = RequestMethod.POST)
	public String ModifyPagePost(BoardVO vo, Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("ModifyPage POST");
		
		service.modify(vo);
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		return "redirect:/board/readPage";
	}
	
	@RequestMapping(value = "Remove", method = RequestMethod.GET)
	public String remove(int bno) throws Exception{
		logger.info("Remove GET");
		service.remove(bno);
		
		return "redirect:/board/listAll";
		
	}
	
	@RequestMapping(value = "RemovePage", method = RequestMethod.GET)
	public String RemovePage(int bno, Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("RemovePage GET");
		service.remove(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		
		return "redirect:/board/listPage";
		
	}
	
	
	
}

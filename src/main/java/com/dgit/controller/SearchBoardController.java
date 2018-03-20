package com.dgit.controller;

import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String outUploadPath;
	
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
	public String registerPost(BoardVO board, List<MultipartFile> imageFiles) throws Exception{
		logger.info("registerPost"); 
		logger.info("outUploadPath : " + outUploadPath );
		
		
		if(imageFiles.get(0).getBytes().length != 0){
			String[] files = new String[imageFiles.size()]; 
			
			for(int i = 0; i < imageFiles.size(); i++){
				String saveFile = UploadFileUtils.uploadFile(outUploadPath, imageFiles.get(i).getOriginalFilename(), imageFiles.get(i).getBytes());
				logger.info("saveFile : " + saveFile ); 
				
				files[i] = outUploadPath + saveFile;
			}
					
			board.setFiles(files);
		}
		
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
	public String ModifyPagePost(BoardVO vo, SearchCriteria cri, RedirectAttributes rttr, List<MultipartFile> imageFiles, String[] delFiles) throws Exception{
		logger.info("ModifyPage POST");
		String[] files = new String[imageFiles.size()];
		
		
		if(delFiles != null){
			logger.info("게시물 번호 : " + vo.getBno());
			for(String s : delFiles){
				UploadFileUtils.deleteImg(s.toString());
				service.delAttachByfullName(vo.getBno(), s.toString());
			}
		}
		
		if(imageFiles.get(0).getBytes().length != 0){
			for(int i = 0; i < imageFiles.size(); i++){
				String saveName = UploadFileUtils.uploadFile(outUploadPath, imageFiles.get(i).getOriginalFilename(), imageFiles.get(i).getBytes());
				files[i] = outUploadPath + saveName;
			}
			
			vo.setFiles(files);
		}
		
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
	
	@ResponseBody
	@RequestMapping(value="displayFile", method = RequestMethod.GET) 
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		logger.info("filename : " + filename);
		FileInputStream in = null;
		
		try{
			String formatName = filename.substring(filename.lastIndexOf(".") + 1); // 파일형식 반환(예: jpg, png)
			
			// jsp file 내 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			// contentType을 지정하기 위해 MediaUtils을 만들고 Type을 지정해줌
			// MediUtils 클래스를 만들지 않아도 되지만, 작업의 편의를 위해 별도의 util 패키지내 클래스로 작성
						
			MediaType type = MediaUtils.getMeditType(formatName); 
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			in = new FileInputStream(filename);
																		// contentType = "text/MediaType.IMAGE_JPEG"으로 설정한 header를 함께 넘김
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
												// fileInputStream으로 읽은 byte배열을 넘김
		
		
		}catch(Exception e){
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}	
		return entity;
	}

}

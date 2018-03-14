package com.dgit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.ReplyVO;
import com.dgit.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {

	@Autowired
	private ReplyService service;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;

		try {
			service.create(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST); // exception 발생 시 400 에러를 보냄
		}

		return entity;

	}
	
	@RequestMapping(value="/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable int bno){
		ResponseEntity<List<ReplyVO>> entity = null;
		
		
		try {
			List<ReplyVO> list = service.list(bno);
			entity =  new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity =  new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}	
		
		return entity;
	}
	
	@RequestMapping(value="/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			HashMap<String, Object> map = new HashMap<>();
			
			Criteria cri = new Criteria();
			cri.setPage(page);
			List<ReplyVO> list = service.listPage(bno, cri);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.countReply(bno));
			
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		} catch (Exception e) {
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
			
	}
	
	@RequestMapping(value="/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable int rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		try {
			vo.setRno(rno);
			service.update(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		
		try {
			service.delete(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

}

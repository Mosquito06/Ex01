package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDao;

@Repository
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDao dao;
	
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		return dao.list(bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		dao.create(vo);

	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		dao.update(vo);

	}

	@Override
	public void delete(int rno) throws Exception {
		dao.delete(rno);

	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		return dao.listPage(bno, cri);
	}

	@Override
	public int countReply(int bno) throws Exception {
		return dao.countReply(bno);
	}

}

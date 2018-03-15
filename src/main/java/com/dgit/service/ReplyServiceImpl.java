package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.BoardDao;
import com.dgit.persistence.ReplyDao;

@Repository
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		return replyDao.list(bno);
	}

	@Override
	@Transactional
	public void create(ReplyVO vo) throws Exception {
		replyDao.create(vo);
		boardDao.updateReplyCount(vo.getBno(), 1);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		replyDao.update(vo);

	}

	@Override
	@Transactional
	public void delete(int rno) throws Exception {
		int bno = replyDao.getBno(rno);
		replyDao.delete(rno);
			
		boardDao.updateReplyCount(bno, -1);

	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		return replyDao.listPage(bno, cri);
	}

	@Override
	public int countReply(int bno) throws Exception {
		return replyDao.countReply(bno);
	}

}

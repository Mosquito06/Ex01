package com.dgit.service;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

public interface ReplyService {
	public List<ReplyVO> list(int bno) throws Exception;
	public void create(ReplyVO vo) throws Exception;
	public void update(ReplyVO vo) throws Exception;
	public void delete(int rno) throws Exception;
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception;
	public int countReply(int bno) throws Exception;
}

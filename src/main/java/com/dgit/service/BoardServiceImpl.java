package com.dgit.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.persistence.BoardDao;

@Repository
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDao dao;
	
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);
	}

	@Override
	public void remove(int bno) throws Exception {
		dao.delete(bno);;
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

}

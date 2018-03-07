package com.dgit.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;

@Repository
public interface BoardService {
	public void regist(BoardVO board) throws Exception;
	public BoardVO read(int bno) throws Exception;
	public void modify(BoardVO board)  throws Exception;
	public void remove(int bno)  throws Exception;
	public List<BoardVO> listAll() throws Exception;
}

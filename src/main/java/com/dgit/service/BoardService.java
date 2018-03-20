package com.dgit.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardService {
	public void regist(BoardVO board) throws Exception;
	public BoardVO read(int bno, boolean isRead) throws Exception;
	public void modify(BoardVO board)  throws Exception;
	public void remove(int bno)  throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	public int listCountCriteria() throws Exception;
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	public int listSearchCount(SearchCriteria cri) throws Exception;
	public void delAttachByfullName(int bno, String fullName) throws Exception;
	
}

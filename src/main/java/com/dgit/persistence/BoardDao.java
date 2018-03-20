package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardDao {
	public void create(BoardVO vo) throws Exception;
	public BoardVO read(Integer bno) throws Exception;
	public void update(BoardVO vo) throws Exception;
	public void delete(Integer bno) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public List<BoardVO> listPage(int page) throws Exception;
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	public int countPaging() throws Exception;
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	public int listSearchCount(SearchCriteria cri) throws Exception;
	public void updateReplyCount(int bno, int amount) throws Exception;
	public void updateViewCount(int bno) throws Exception;
	public void addAttach(String fullName, int bno) throws Exception;
	public List<String> getAttach(int bno) throws Exception;
	public void delAttach(int bno) throws Exception;
	public void delAttachByfullName(int bno, String fullName) throws Exception;
}

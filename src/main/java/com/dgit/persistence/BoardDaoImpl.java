package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDaoImpl implements BoardDao {
	private static final String namespace = "com.dgit.persistence.BoardDao";
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert(namespace + ".create", vo);

	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return sqlSession.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace + ".update", vo);

	}

	@Override
	public void delete(Integer bno) throws Exception {
		sqlSession.delete(namespace + ".delete", bno);

	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return sqlSession.selectList(namespace + ".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		if(page <= 0){
			page = 1;
		}
		
		page = (page - 1) * 10;
				
		return sqlSession.selectList(namespace + ".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".listCriteria", cri);
	}

	@Override
	public int countPaging() throws Exception {
		return sqlSession.selectOne(namespace + ".countPaging");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace + ".listSearchCount", cri);
	}

	@Override
	public void updateReplyCount(int bno, int amount) throws Exception {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
				
		sqlSession.update(namespace + ".updateReplyCount", map);
		
	}

	@Override
	public void updateViewCount(int bno) throws Exception {
		sqlSession.update(namespace + ".updateViewCount", bno);
		
	}

}

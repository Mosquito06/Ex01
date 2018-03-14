package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

@Repository
public class ReplyImpl implements ReplyDao {
	private static final String NAMESPACE = "com.dgit.persistence.ReplyDao";
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".list", bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".create", vo);

	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".update", vo);

	}

	@Override
	public void delete(int rno) throws Exception {
		sqlSession.delete(NAMESPACE + ".delete", rno);

	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("cri", cri);
		
		return sqlSession.selectList(NAMESPACE + ".listPage", map);
	}

	@Override
	public int countReply(int bno) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".count", bno);
	}

}

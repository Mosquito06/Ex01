package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TestBoardDao {
	
	@Autowired
	private BoardDao dao;
	
	//@Test
	public void Testinsert(){
		BoardVO vo = new BoardVO();
		vo.setTitle("테스트2");
		vo.setContent("테스트 확인2입니다.");
		vo.setWriter("배재진");
		try {
			dao.create(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void listAll(){
		
		try {
			dao.listAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void TestRead(){
		BoardVO vo = new BoardVO();
		vo.setBno(1);
		try {
			dao.read(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void TestUpdate(){
		BoardVO vo = new BoardVO();
		vo.setBno(1);
		vo.setTitle("테스트 수정");
		vo.setContent("테스트 수정입니다.");
		try {
			dao.update(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		//@Test
		public void TestDelete(){
			try {
				dao.delete(1);;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Test
		public void TestListPage(){
			try {
				dao.listPage(3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Test
		public void TestListCriteria(){
			Criteria cri = new Criteria();
			cri.setPage(1);
			cri.setPerPageNum(20);
			
			try {
				dao.listCriteria(cri);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}

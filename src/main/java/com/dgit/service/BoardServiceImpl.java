package com.dgit.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDao;
import com.dgit.persistence.ReplyDao;
import com.dgit.util.UploadFileUtils;

@Repository
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDao dao;
	
	@Inject
	private ReplyDao replyDao;
	
	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		
		String[] files = board.getFiles();
		
		if(files == null){
			return;
		}else{
			for(String fileName : files){
				dao.addAttach(fileName, board.getBno());
			}
		}
	}

	@Override
	@Transactional
	public BoardVO read(int bno, boolean isRead) throws Exception {
		if(isRead){
			dao.updateViewCount(bno);
		}
		
		BoardVO vo = dao.read(bno);
		List<String> list = dao.getAttach(bno);
							// 컬렉션을 배열로 반환
		String[] files = list.toArray(new String[list.size()]);
		
		vo.setFiles(files);
			
		return vo;
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		String[] files = board.getFiles();
		
		System.out.println(board.getBno());
		
		if(files != null){
			for(String fileName : files){
				dao.addAttach(fileName, board.getBno());
			}
		}
		
		dao.update(board);
	}

	@Override
	@Transactional
	public void remove(int bno) throws Exception {
		BoardVO vo = dao.read(bno);
		
		List<String> files = dao.getAttach(bno);
		
		if(files.size() != 0){
			System.out.println("그림 삭제 진입?");
			
			for(String file : files){
				UploadFileUtils.deleteImg(file);
			}
			
			dao.delAttach(bno);
		}
		
		int replyCount = replyDao.countReply(bno);
		if(replyCount != 0){

			System.out.println("리플 삭제 진입?");
			replyDao.deleteByBno(bno);
		}
		
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		return dao.countPaging();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Override
	public void delAttachByfullName(int bno, String fullName) throws Exception {
		dao.delAttachByfullName(bno, fullName);
		
	}

	

}

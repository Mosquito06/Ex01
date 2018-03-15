<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
	$(function(){
		$("#searchBtn").click(function(){
			var searchType = $("select[name='searchType']").val();
			var keyword = $("input[name='keyword']").val();
			location.href="listPage${pageMake.makeQuery(1)}&searchType=" + searchType + "&keyword=" + keyword;
		})
		
		$("#newBoard").click(function(){
			location.href="register";
		})
	})

</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">LIST PAGE</h3>
				</div>
				<div class="box-body">
					<select name="searchType">
						<option value="n" ${cri.searchType == 'n'? 'selected' : '' }>Choose</option>
						<option value="t" ${cri.searchType == 't'? 'selected' : '' }>Title</option>
						<option value="c" ${cri.searchType == 'c'? 'selected' : '' }>Content</option>
						<option value="w" ${cri.searchType == 'w'? 'selected' : '' }>Writer</option>
						<option value="tc" ${cri.searchType == 'tc'? 'selected' : '' }>Title or Content</option>
						<option value="cw" ${cri.searchType == 'cw'? 'selected' : '' }>Content or writer</option>
						<option value="tcw" ${cri.searchType == 'tcw'? 'selected' : '' }>Title or Content or writer</option>
					</select>
					<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}">
					<button id="searchBtn">Search</button>
					<button id="newBoard">New Board</button>
				</div>	
				<div class="box-body">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>BNO</th>
								<th>TITLE</th>
								<th>CONTENT</th>
								<th>REGDATE</th>
								<th>VIEWCNT</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="board" items="${list }">
								<tr>
									<td style="width:10px;">${board.bno }</td>
									<td><a href="readPage${pageMake.makeSearch(pageMake.cri.page) }&bno=${board.bno }&count=true">${board.title }<span style="color:black">[${board.replycnt}]</span></a></td>
									<td>${board.content }</td>
									<td>
										<fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd HH:mm"/>
									</td>
									<td style="width:40px;">
										<span class="badge bg-red">${board.viewcnt }</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMake.prev }">
								<li><a href="listPage${pageMake.makeSearch(pageMake.startPage - 1) }">&laquo;</a></li>
							</c:if>
							<c:forEach var="idx" begin="${pageMake.startPage }" end="${pageMake.endPage }">
								<li ${pageMake.cri.page == idx? 'class=active' : '' }><a href="listPage${pageMake.makeSearch(idx) }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMake.next }">
								<li><a href="listPage${pageMake.makeSearch(pageMake.endPage + 1 ) }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>
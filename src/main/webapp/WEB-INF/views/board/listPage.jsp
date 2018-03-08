<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">LIST PAGE</h3>
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
									<td><a href="readPage${pageMake.makeQuery(pageMake.cri.page) }&bno=${board.bno }&count=true">${board.title }</a></td>
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
								<li><a href="listPage?page=${pageMake.startPage - 1 }">&laquo;</a></li>
							</c:if>
							<c:forEach var="idx" begin="${pageMake.startPage }" end="${pageMake.endPage }">
								<li ${pageMake.cri.page == idx? 'class=active' : '' }><a href="listPage?page=${idx }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMake.next }">
								<li><a href="listPage?page=${pageMake.endPage + 1 }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
<script>
	$(function(){
		$("input[type='button']").click(function(){
			var action = $(this).val();
						
			if(action == "Go List"){
				action = "listPage";
			}else{
				action = action + "Page";
			}
			$("#f1").attr("action", action);
			$("#f1").submit();
		})
	})

</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">REGISTER</h3>
				</div>
				<div class="box-body">
					<form method="get" action="" id="f1">
						<input type="hidden" name="bno" value="${board.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
					</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" readonly="readonly" class="form-control" value="${board.title }" placeholder="title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" name="content" readonly="readonly" class="form-control" placeholder="content">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" readonly="readonly" class="form-control" value="${board.writer }" placeholder="writer">
						</div>
						<div class="form-group">
							<input type="button" value="Modify" class="btn btn-warning">
							<input type="button" value="Remove" class="btn btn-danger">
							<input type="button" value="Go List" class="btn btn-primary">
						</div>
				</div>
			</div>
		</div>	
	</div>
	
	<!-- 댓글 영역 -->
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">Add Reply</h3>
				</div>
				<div class="box-body">
					<label for="Writer">Writer</label>
					<input type="text" id="Writer" class="form-control">
					<label for="replytest">Reply Text</label>
					<input type="text" id="replytext" class="form-control">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="replyesDiv">
					<span class="bg-green">Replies List</span>
				</li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin">
					
				
				</ul>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="modifyModal">
	    <div class="modal-dialog modal-dialog-centered">
	      <div class="modal-content">
	      
	        <!-- Modal Header -->
	        <div class="modal-header">
	          <h3 class="modal-title"></h3>
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	        </div>
	        
	        <!-- Modal body -->
	        <div class="modal-body">
	         	<div class="box-body">
					<input type="text" id="updatetext" class="form-control">
				</div>
	        </div>
	        
	        <!-- Modal footer -->
	        <div class="modal-footer">
	       	  <button type="button" class="btn btn-primary" data-dismiss="modal">Modify</button>
	          <button type="button" class="btn btn-danger" data-dismiss="modal">Delete</button>
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        </div>
	        
	      </div>
    	</div>
  	</div>
	
	<script id="template" type="text/x-handlebars-template">
		{{#each.}}
			<li class='replyLi' data-rno={{rno}}>
				<i class="fa fa-comments bg-blue"></i>
				<div class="timeline-item">
					<span class="time">
						<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
					</span>
				<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
				<div class="timeline-body">{{replytext}}</div>
				<div class="timeline-footer">
					<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal">Modify</a>
				</div>
				</div>
			</li>
		{{/each}}
	</script>
	<script>
		Handlebars.registerHelper("prettifyDate", function(value){
			var dateObj = new Date(value);
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth() + 1;
			var date = dateObj.getDate();
			
			return year + "-" + month + "-" + date;
			
		})
	
		var bno = ${board.bno };
		var replyPage = 1;
		var templateFunc = Handlebars.compile($("#template").html());
		
		$(function(){
			$("#replyAddBtn").click(function(){
				
				var replyer = $("#Writer").val();
				var replytext = $("#replytext").val();
				
				var sendDate = {bno : bno, replyer : replyer, replytext : replytext};
				
				$.ajax({
					url: "${pageContext.request.contextPath}/replies/",
					type: "post",
					headers : {"Content-Type" : "application/json"}, //@RequestBody를 사용하기 때문에 Json이라는 형식을 지정해줘야함
					dataType : "text",
					data : JSON.stringify(sendDate), // json 객체를 json string으로 변경해줌
					success : function(result){
						console.log(result);
						getPage(1);
					}
					
				})
			})
			
			$("#replyesDiv").click(function(){
				getPage(1);
			})
			
			/* 수정 버튼 클릭 시 모달 내 데이터 세팅 */
			$(document).on("click" , ".replyLi .timeline-footer a", function(){
				var rno = $(this).parents("li").attr("data-rno");
				var replytext = $(this).parents("li").find(".timeline-body").text();
				
				$(".modal-header h3").html(rno);
				$("#updatetext").val(replytext);
				
			})	
			
			/* 모달 내 댓글 수정 */
			$(".modal-footer button:eq(0)").click(function(){
				var rno = $(this).parents("#modifyModal").find(".modal-title").text();
				var replytext = $(this).parents("#modifyModal").find("input").val();
				
				var sendDate = {replytext : replytext};
				
				$.ajax({
					url : "/ex02/replies/" + rno,
					type : "put",
					dataType : "text",
					data : JSON.stringify(sendDate),
					headers : {"Content-Type" : "application/json"},
					success : function(result){
						console.log(result);
						getPage(1);
					}
				})
			})
			
			/* 모달 내 댓글 삭제 */
			$(".modal-footer button:eq(1)").click(function(){
				var rno = $(this).parents("#modifyModal").find(".modal-title").text();
				
				$.ajax({
					url : "/ex02/replies/" + rno,
					type : "delete",
					dataType : "text",
					success : function(result){
						getPage(1);
					}
					
				})
			})
		})
		
		function getPage(page){
			$.ajax({
				url : "${pageContext.request.contextPath}/replies/" + bno + "/" + page,
				type : "get",
				dataType : "json",
				success : function(data){
					console.log(data);
					
					$("li[class='replyLi']").remove();
					
					$(".timeline").append(templateFunc(data.list));
					printPaging(data.pageMaker);
				}
			})
		}
		
		function printPaging(pageMaker){
			var str = "";
			if(pageMaker.prev){
				str += "<li><a href='" + pageMaker.startPage -1 + "'> << </a></li>"
			}
			
			for(var i = pageMaker.startPage; i <= pageMaker.endPage; i++){
				if(pageMaker.cri.page == i){
					str += "<li class='active'><a href='" + i + "'>"+ i +"</a></li>"
				}else{
					str += "<li><a href='" + i + "'>"+ i +"</a></li>"
				}
			}
					
			if(pageMaker.next){
				str += "<li><a href='" + pageMaker.endPage + 1 + "'> >> </a></li>"
			}
			
			$("#pagination").html(str);
		}
	</script>
</section>
<%@ include file="../include/footer.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/header.jsp" %>
<style>
	div.imgDiv img.newImg{
		max-height: 100px;
	}
	
	div.imgDiv{
		display: inline-block;
		position: relative;
	}
	
	div.imgDiv button{
		position: absolute;
		top: 1px;
		right: 1px;
	}
</style>
<script>
	$(function(){
		$(function(){
			$("input[type='file']").change(function(e){
				var file = document.getElementById("file");
				$(file.files).each(function(i, file){
					var reader = new FileReader();
					reader.onload = function(e){
						var imgDiv = $("<div>").addClass("imgDiv");
						var imgObj = $("<img>").attr("src", e.target.result).addClass("newImg");
						var imgButton = $("<button>").addClass("delFile").attr("data-del", "").text("X");
						imgDiv.append(imgObj).append(imgButton);
												
						$("#previewDiv").append(imgDiv);
					}
					
					reader.readAsDataURL(file);
				})	
			})	
			
			$(document).on("click", ".delFile", function(e){
				e.preventDefault();
				$(this).parents(".imgDiv").css("display", "none");
				
				
				var value = $(this).attr("data-del");

				if(value != ""){
					var inputObj = $("<input>").attr("type", "hidden").attr("name", "delFiles").attr("value", value);
					$("#addInputDiv").append(inputObj);
				}
			})
			
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
					<form method="post" action="ModifyPage" enctype="multipart/form-data">
						<div class="form-group" id="addInputDiv">
							<label>Title</label>
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="hidden" name="viewcnt" value="${board.viewcnt }">
							<input type="hidden" name="page" value="${cri.page }">
							<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
							<input type="text" name="title" class="form-control" value="${board.title }" placeholder="title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" name="content" class="form-control" placeholder="content">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${board.writer }" placeholder="writer">
						</div>
						<div class="form-group">
							<label>Image Files</label>
							<input type="file" id="file" name="imageFiles" multiple="multiple" class="form-control">
						</div>
						<div class="form-group" id="previewDiv">
							<c:if test="${board.files != null}">
								<c:forEach var="file" items="${board.files }">
									<div class="imgDiv">
										<img src="displayFile?filename=${file }">
										<button data-del="${file }" class="delFile">X</button>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<div class="form-group">
							<input type="submit" value="Modify" class="btn btn-primary">
							<input type="submit" value="Cancel" class="btn btn-danger">
						</div>
					</form>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>
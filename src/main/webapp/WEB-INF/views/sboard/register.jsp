<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script>
	$(function(){
		$("input[type='file']").change(function(e){
			$("#preview").empty();
			
			var file = document.getElementById("file");
			$(file.files).each(function(i, file){
				var reader = new FileReader();
				reader.onload = function(e){
					var imgObj = $("<img>").attr("src", e.target.result);
					$("#preview").append(imgObj);
				}
				
				reader.readAsDataURL(file);
			})	
		})	
	})

</script>
<style>
	div#preview{
		height: 200px;
	}
	
	div#preview img{
		width: 100px;
		margin: 1%;
	}

</style>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">REGISTER</h3>
				</div>
				<div class="box-body">
					<form method="post" action="register" enctype="multipart/form-data">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" name="content" class="form-control" placeholder="content"></textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="writer">
						</div>
						<div class="form-group">
							<label>Image File</label>
							<input type="file" id="file" name="imageFiles" class="form-control" multiple="multiple">
						</div>
						<div id="preview" class="form-group">
						</div>
						<div class="form-group">
							<input type="submit" value="submit" class="btn btn-primary">
						</div>
					</form>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>
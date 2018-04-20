<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="_css/styles.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Image Processing for eBay Searching</title>
</head>
<body>
	<!-- Navigation bar -->
	<div class="navigation-bar">
		<div id="navigation-container">
			<img id="uni_logo" src="_images/dundeeuni_logo.png">
			<ul>
				<li><a href="upload.jsp">Upload Photo</a></li>
				<li><a href="results.jsp">Results</a></li>
			</ul>
			<img id="ebay_logo" src="_images/ebay_logo.png">
		</div>
	</div>

	<!-- Upload image form -->
	<div class="upload-form">
		<form id="image-upload" method="POST" action="Upload" encType="multipart/form-data">
			<h2>Upload image to search</h2>
			<input type="file" name="image" accept="image/*"value="Select image..."> 
			<input type="submit" value="Upload Image">
		</form>
		<form id="url-upload" method="POST" action="Upload">
			<h2>Search image URL</h2>
			<input type="text" name="image" accept="image/*"value="Paste image URL..."> 
			<input type="submit" value="Upload Image">
		</form>
	</div>
	
	<script>
	$("#image-upload").submit(function(){
		$.ajax({
			complete: function(){
				window.location.href = "loadingPage.jsp";
			}
		});
	});
	
	$("#url-upload").submit(function(){
		$.ajax({
			complete: function(){
				window.location.href = "loadingPage.jsp";
			}
		});
	});
	</script>
</body>
</html>


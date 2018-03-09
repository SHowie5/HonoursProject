<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="_css/styles.css">
<title>Image Processing for eBay Searching</title>
</head>
<body>
	<h1>Image Processing for eBay Searching</h1>
	<div>
		<h2>Upload image to search</h2>
		<form method="POST" action="Upload" encType="multipart/form-data">
			<input type="file" name="image" accept="image/*" value="Select image..."> <input
				type="submit" value="Upload Image">
		</form>
		 	
		<form method="POST" action="Upload">
		<h2>Search image URL</h2>
			<input type="text" name="image" accept="image/*" value="Select image...">
			<input type="submit" value="Upload Image">
		</form>		
	</div>
</body>
</html>

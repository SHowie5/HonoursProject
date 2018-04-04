<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="stores.*"%>
<%@ page import="java.util.*"%>
<%@ page import="Upload.*"%>
<%@ page import="Upload.imageHistogram"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="_css/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Complete</title>
</head>
<body>
	<h3>Your file has been uploaded!</h3>
	<%
		UploadStore us = (UploadStore) session.getAttribute("UploadStore");
		String filePath = us.getFilePath();
		String keyword = us.getKeyword();
		ArrayList<ebayResults> results = us.getResults();
	%>
	<!-- Display Image uploaded to search -->
	<div class="uploadedImage">
		<p>Keyword Searched: <%=keyword%></p>
		<img src="<%=filePath%>" height="200px" width="200px"><br>
	</div>
	<!-- Display eBay results -->
	<%
		for (int i = 0; i < results.size(); i++) {
	%>
	<div class="ebay_results">
		<img src="<%=results.get(i).getGalleryUrl()%>" border=2><br>
		<a href="<%=results.get(i).getItemUrl()%>" target=_blank><%=results.get(i).getTitle()%></a>
		<p>
			Score: <%=results.get(i).getScore()%> <br>
			Price: $<%=results.get(i).getCurrentPrice()%> <br>
			Item ID: <%=results.get(i).getItemId()%>
		</p>
	</div>
	<%
		}
	%>
	
	
</body>
</html>
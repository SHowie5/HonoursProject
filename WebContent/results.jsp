<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="stores.*"%>
<%@ page import="java.util.*"%>
<%@ page import="Upload.*"%>
<%@ page import="Upload.imageHistogram"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="_css/styles.css">
<title>Upload Complete</title>
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
	
	<!-- Uploaded image -->
	<%
		UploadStore us = (UploadStore) session.getAttribute("UploadStore");
		String filePath = us.getFilePath();
		String keyword = us.getKeyword();
		String filename = us.getFilename();
		ArrayList<ebayResults> results = us.getResults();
		int resultSize = results.size();
		if(resultSize > 10) {
			resultSize = 10;
		}
		
	%>
	<!-- Display Image uploaded to search -->
	<div class="uploadedImage">
		<p>Your uploaded image: </p>
		<img src="/HonoursProject/data/<%=filename%>"/>
	</div>
	<!-- Display eBay results -->
	<%
		for (int i = 0; i < resultSize; i++) {
	%>
	<div class="ebay_results">
		<p>
		<a href="<%=results.get(i).getItemUrl()%>" target=_blank><img src="<%=results.get(i).getGalleryUrl()%>"><br></a>
		<a href="<%=results.get(i).getItemUrl()%>" target=_blank><%=results.get(i).getTitle()%></a><br><br>
			Match Score: <%=results.get(i).getScore()%> <br>
			Price: $<%=results.get(i).getCurrentPrice()%> <br>
			Item ID: <%=results.get(i).getItemId()%><br><br>
			<a href="<%=results.get(i).getItemUrl()%>" target=_blank class="button">Buy Now!</a>
		</p>
	</div>
	<%
		}
	%>
	
	
</body>
</html>
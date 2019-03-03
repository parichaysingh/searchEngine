<!DOCTYPE script PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.List"%>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
</head>
<body>
<center style="margin-left: 100px; padding-top: 10px;">
	<div class="container"
		style="background-color: #fbfbfb; min-height: 100%; min-width: 100%">
		<div class="logo" style="display: flex; justify-content: center;">
			<img src="download.png" width="250" height="250">
		</div>

		

<br/>
			<form action="InteractionHandler" method="post">
				<input type="hidden" value="index" name="action" /><input type="text" name="keyword"
					id="keyword" placeholder="Enter a keyword to search"><br/><br/> <input
					class="btn btn-primary" type="submit" value="Search Keyword" />

			</form>
			
				<a href="phonenumber.jsp">Phone Number</a>
				<a href="pincodes.jsp">Search Pincodes</a>
				
			<br /><br />
			<%
				if (request.getAttribute("files") != null
						&& ((List<Entry<String, Integer>>) request.getAttribute("files")).size() != 0) {

					List<Entry<String, Integer>> files = (List<Entry<String, Integer>>) request.getAttribute("files");
					if (files.size() != 0) {
						out.write("<br/><table class='table'>");
						out.write("<tr>");
						out.write("<th class='tableChildClass'>Name of the file</th>");
						out.write("<th class='tableChildClass'>Word Count</th>");
						out.write("</tr>");
						for (Entry<String, Integer> entry : files) {
							out.write("<tr>");
							out.write("<td class='tableChildClass'>" + entry.getKey() + "</td>");
							out.write("<td class='tableChildClass'>" + entry.getValue() + "</td>");
							out.write("</tr>");
						}

						out.write("</table>");
					}

				} else if (request.getAttribute("suggest") != null) {

					String suggest = (String) request.getAttribute("suggest");
					if (suggest != null) {
						System.out.print("Suggestions: " + suggest);
			%>


			<b>No file found with the keyword specified</b> <br /> <i><%=suggest%></i>

			<%
				}
				}
			%>

		</center>
	</div>
</body>
</html>
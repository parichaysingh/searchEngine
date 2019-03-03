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
				<input type="hidden" value="phonenumber" name="action" /> <input
					type="text" name="phonenumber"
					id="phonenumber" placeholder="Enter phone number"><br/> <br/><input
					class="btn btn-primary" type="submit" value="Search Phone Number" /><br/>	
			</form>
				<a href="index.jsp">Search Keyword</a>
				<a href="pincodes.jsp">Search Pincodes</a>
			<br /><br/>
			<%
				if (request.getAttribute("valid") != null) {
			%>
			<table class='table'>
				<%
					out.write("");
						String valid = (String) request.getAttribute("valid");
						System.out.print("Phone is " + valid);
						if (valid.equalsIgnoreCase("valid")) {
							System.out.print("Phone number is valid ");
				%><b>THE GIVEN PHONE NUMBER IS VALID</b>
				<br />
				<%
					if (request.getAttribute("files") != null) {
								List<String> files = (List<String>) request.getAttribute("files");
								if (files.size() != 0) {
				%>

				<br />
				<br />
				<tr>
					<th class='tableChildClass'>Name of the file</th>
				</tr>
				<%
					for (String str : files) {
				%>
				<tr>

					<td class='tableChildClass'><%=str%></td>
					<%
						}
									} else {
					%>
					<b>No files contain the provided phone number</b>

					<%
						
					%>
				
			</table>
			<%
				}

						}
					} else {
						System.out.print("Phone number is Invalid ");
			%>
			<b>THE GIVEN PHONE NUMBER IS INVALID</b>
			<%
				}
			%>


			<%
				}
			%>
		
	</center>
	</div>
</body>
</html>
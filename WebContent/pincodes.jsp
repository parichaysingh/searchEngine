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
			<br />

			<form action="InteractionHandler" method="post">
				<input type="hidden" value="pincode" name="action" /> <input
					type="text" name="city" id="city" placeholder="Enter City"> <br />
				<br />
				<input class="btn btn-primary" type="submit" value="Get Pincodes" />
			</form>
				<a href="index.jsp">Search Keyword</a>
				<a href="phonenumber.jsp">Phone Number</a>
			<br />
			<br />
			<%
				if (request.getAttribute("found") != null) {
			%>


			<table class='table'>

				<%
					String found = (String) request.getAttribute("found");
						System.out.print("Pincodes " + found);
						if (request.getAttribute("pincodes") != null && found.equalsIgnoreCase("found")) {

							String[] pincodes = (String[]) request.getAttribute("pincodes");
				%>
				<tr>
					<th class='tableChildClass'>List of Pincodes</th>

				</tr>
				<%
					for (String str : pincodes) {
				%>
				<tr>
					<td class='tableChildClass'><%=str%></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				} else {
			%>
			<b>No Pincodes found for the entered city</b>
			<%
				}
			%>

			<%
				}
			%>
		
	
	</div>
	</center>
</body>
</html>
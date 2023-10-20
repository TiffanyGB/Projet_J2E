<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search students</title>
</head>
<body>
	<% 
	String error = (String)request.getSession().getAttribute("error");
	if(error != null && !error.equals("")) { %>
	<div style="color:red">${error}</div>
	<% } %>
	<form action="/td1/students" method="POST">
		<label>Student groups:</label>
		<select name="group">
		   		<option value="none" selected>Choose a value</option>
		<% List<String> groups = (List<String>)request.getAttribute("groups"); 
		   for (String group : groups ) { %>
				<option value="<%= group %>"><%= group %></option>
		<% } %>
		</select>
		<input type="submit" value="Search">
	</form>
</body>
</html>
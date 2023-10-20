<%@page import="java.util.List"%>
<%@page import="td1.servlets.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
Students list
</title>
</head>
<body>
	<h1>Students:</h1>
	<h2>Méthod 1 : Java blocks</h2>
	<ul>
	<% for (Student student : (List<Student>) request.getAttribute("students")) { %>
		<li><%=student.getFirstName()%> <%=student.getLastName()%></li>
	<% } %>
	</ul>
	<h2>Méthod 2 : JSTL taglib</h2>
	<ul>
	<c:forEach items="${students}" var="student">
		<li>${student.firstName} ${student.lastName}</li>
	</c:forEach>
	</ul>
	<f:formatNumber maxFractionDigits="3" value="3.6544226"></f:formatNumber>
</body>
</html>
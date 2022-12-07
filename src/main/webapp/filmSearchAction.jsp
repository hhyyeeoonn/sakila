<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>

<%
	String[] rating = request.getParameterValues("rating");
		System.out.println("filmSearchAction:rating:" + rating);
	if(rating != null) {
		System.out.println("filmSearchAction:rating.length:" + rating.length);
	}
	String from = request.getParameter("fromMinute");
	String to = request.getParameter("toMinute");
	
	if(from.equals("") || to.equals("")) {
		
	}
	
	
	
	int fromMinute = Integer.parseInt(request.getParameter("fromMinute"));
	int toMinute = Integer.parseInt(request.getParameter("toMinute"));
	String searchTitle = request.getParameter("searchTitle");
	
	
	
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.filmSearchList(fromMinute, toMinute, rating, searchTitle);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>filmListAction2</title>
</head>
<body>
	<h1>영화검색결과</h1>
	<table border = "1">
		<tr>
			<th>제목</th>
			<th>상영등급</th>
			<th>상영시간</th>
		</tr>
		<%
			for(Film f : list) {
		%>
			<tr>
				<td><%=f.getTitle()%></td>
				<td><%=f.getRating()%></td>
				<td><%=f.getLength()%></td>
			</tr>
		<%
			}
		%>
	</table>
</body>
</html>
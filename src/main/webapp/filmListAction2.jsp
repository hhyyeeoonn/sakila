<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	// 1) rating
	String[] rating = request.getParameterValues("rating");
	System.out.println(rating);
	// 디버깅
	if(rating != null) {
		System.out.println(rating.length + " <-- rating.length");
	}
	
	String searchTitle = request.getParameter("searchTitle");
	
	// 2) fromMinute & toMinute & releaseYear
	int fromMinute = 0;
	int toMinute = 0;
	int releaseYear = 0;
	
	// releaseYear에 공백값이 넘어왔을 때
	String release = request.getParameter("releaseYear");
	if(release.equals("")) {
		release = "0";
	}
	releaseYear = Integer.parseInt(release);
	
	// 둘다 공백값이 아니면 -> 정상적인 숫자값이 넘겨져 왔다면
	if(!request.getParameter("fromMinute").equals("") && !request.getParameter("toMinute").equals("")) {
		fromMinute = Integer.parseInt(request.getParameter("fromMinute"));
		toMinute = Integer.parseInt(request.getParameter("toMinute"));
	}
	// 디버깅
	System.out.println("filmListAction:"+fromMinute+"/"+toMinute+"/"+releaseYear);
	
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmList2(releaseYear, searchTitle, rating, fromMinute, toMinute);
	
	System.out.println("filmListAction:끝");
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
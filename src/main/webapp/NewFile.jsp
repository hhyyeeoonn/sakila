<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>
<%
	// 1. 요청처리
	// 1) 등급
	String[] rating = request.getParameterValues("rating");
	System.out.println("rating : " + rating);
	if (rating != null) {
		System.out.println("rating.length : " + rating.length);
	}
	
	// 2) 상영시간
	int fromMinute = 0;
	int toMinute = 0;
	// 둘 다 공백이 아니면 값 대입
	if(request.getParameter("fromMinute") == null || request.getParameter("toMinute") == null) {
		fromMinute = 0;
		toMinute = 0;
		
	} else if(!request.getParameter("fromMinute").equals("") && !request.getParameter("toMinute").equals("")) {
		fromMinute = Integer.parseInt(request.getParameter("fromMinute"));
		toMinute = Integer.parseInt(request.getParameter("toMinute"));
	}
	System.out.println("fromMinute : " + fromMinute + " / toMinute : " + toMinute);
	
	// 3) 제목
	String searchTitle = "";
	if(request.getParameter("searchTitle")==null){
		searchTitle ="";
	}
	System.out.println("searchTitle : " + searchTitle);
	
	// 4) 출시년도
	String releaseYear = "";
	if(request.getParameter("releaseYear") == null) {
		releaseYear = "";
	} else if(!request.getParameter("releaseYear").equals("")){
		releaseYear = request.getParameter("releaseYear");
	}
	System.out.println("releaseYear : " + releaseYear);
	
	// 5) 대여로
	String rentalRate = "";
	if(request.getParameter("rentalRate") == null) {
		rentalRate = "";
	} else if(!request.getParameter("rentalRate").equals("")) {
		rentalRate = request.getParameter("rentalRate");	
	}
	System.out.println("rentalRate : " + rentalRate);
	
	// 6) 정렬
	String col = "title";
	String sort = "ASC";
	
	if(request.getParameter("col") != null) {
		col = request.getParameter("col");
	}
	if(request.getParameter("sort") != null) {
		sort = request.getParameter("sort");
	}
	
	// 제목 클릭시 넘겨질 sort값(sort의 반대값)
	String paramSort = "ASC";
	if(sort.equals("ASC")) { 
		paramSort = "DESC";
	}
	
	// 2. 모델호출
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmList2(col, sort, rentalRate, releaseYear, rating, fromMinute, toMinute, searchTitle);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>검색결과</title>
	</head>
	
	<body>
		<h1>검색 결과</h1>
		<a href="<%=request.getContextPath()%>/filmListForm2.jsp">홈으로</a>
		<form method="get" action="<%=request.getContextPath()%>/filmListAction2.jsp">
			<table border="1">
				<tr>
					<th>
					<a href="<%=request.getContextPath()%>/filmListAction2.jsp?col=title&sort=<%=paramSort%>">
						제목
					</a>
					</th>
					<th>
						<a href="<%=request.getContextPath()%>/filmListAction2.jsp?col=rating&sort=<%=paramSort%>">
							등급
						</a>
					</th>
					<th>
						<a href="<%=request.getContextPath()%>/filmListAction2.jsp?col=length&sort=<%=paramSort%>">
							상영시간
						</a>
					</th>
					<th>
						<a href="<%=request.getContextPath()%>/filmListAction2.jsp?col=release_year&sort=<%=paramSort%>">
							출시년도
						</a>
					</th>
					<th>
						<a href="<%=request.getContextPath()%>/filmListAction2.jsp?col=rental_rate&sort=<%=paramSort%>">
							대여료
						</a>
					</th>
				</tr>
				<%
					for(Film f : list){
						%>
						<tr>
							<td><%=f.getTitle() %></td>
							<td><%=f.getRating() %></td>
							<td><%=f.getLength() %></td>
							<td><%=f.getReleaseYear()%></td>
							<td><%=f.getRentalRate()%></td>
						</tr>
						<%
					}
				%>
			</table>
		</form>
	</body>
</html>
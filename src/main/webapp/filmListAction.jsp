<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	// 1) Controller (요청처리 & 모델(Model)호출(dao)) 
	String col = "film_id";
	String sort = "asc";
	
	if(request.getParameter("col") != null) {
		col =request.getParameter("col");
	}
	if(request.getParameter("sort") != null) {
		sort = request.getParameter("sort");	
	}
	
	String paramSort = "asc"; // 제목 클릭시 넘겨질 sort값(sort변수의 반대값)
	if(sort.equals("asc")) {
		paramSort = "desc";
	}
	
	String searchWord = request.getParameter("searchWord");
	String searchCol = request.getParameter("searchCol");
	
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmListBySearch(col, sort, searchWord, searchCol);
	

	// 2) View
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>필름 목록(검색 : 동적쿼리)</h1>
	<hr>
	<form action="<%=request.getContextPath()%>/filmListAction.jsp" method="get">
		<select name="searchCol">
			<option value="title">title</option>
			<option value="description">description</option>
			<option value="titleAndDescription">title + description</option>
		</select>
		<input type="text" name="searchWord">
		<button type="submit">검색</button>
	</form>
	
	<table border=1>
		<tr>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=film_id&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					filmId
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=title&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					title
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=description&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					description
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=release_year&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					release_year
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=language_id&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					language_id
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=original_language_id&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					original_language_id
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_duration&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					rental_duration
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_rate&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					rental_rate
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=length&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					length
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=replacement_cost&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					replacement_cost
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rating&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					rating
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=special_features&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					special_features
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=last_update&sort=<%=paramSort%>&searchWord=<%=searchWord%>&searchCol=<%=searchCol%>">
					last_update
				</a>
			</th>
		</tr>
		<%
			for(Film f : list) {
		%>
				<tr>
					<td><%=f.getFilmId()%></td>
					<td><%=f.getTitle()%></td>
					<td><%=f.getDescription()%></td>
					<td><%=f.getReleaseYear()%></td>
					<td><%=f.getLanguageId()%></td>
					<td><%=f.getOriginalLanguageId()%></td>
					<td><%=f.getRentalDuration()%></td>
					<td><%=f.getRentalRate()%></td>
					<td><%=f.getLength()%></td>
					<td><%=f.getReplacementCost()%></td>
					<td><%=f.getRating()%></td>
					<td><%=f.getSpecialFeatures()%></td>
					<td><%=f.getLastUpdate()%></td>
				</tr>
		<%		
			}
		%>
	</table>
</body>
</html>
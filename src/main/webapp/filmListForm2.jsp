<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	FilmDao filmDao = new FilmDao();
	int minYear = filmDao.selectMinReleaseYear(); // 2006
	Calendar today = Calendar.getInstance(); // WHY? new Calendar() -> Calendar는 추상클래스
	int todayYear = today.get(Calendar.YEAR); // 오늘 날짜의 연도 2022
	
	System.out.println("filmListForm:"+minYear+"/"+todayYear);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>filmListForm2</title>
</head>
<body>
	<form action = "<%=request.getContextPath()%>/filmListAction2.jsp">
		<table border = "1">
			<tr>
				<th>출시 연도</th>
				<td>
					<select name = "releaseYear">
						<option value = "">출시 연도를 선택하세요</option>
						<%
							for(int i = minYear; i <= todayYear; ++i) {
						%>
							<option value = "<%=i%>"><%=i%>년</option>
						<%
							}
						%>
					</select>
				</td>
			</tr>

			<tr>
				<th>상영시간(분)</th>
				<td>
					<input type = "number" name = "fromMinute"> <!-- 입력하지 않으면 null이 아닌 "(공백)"값이 넘어감 -->
					~
					<input type = "number" name = "toMinute">
				</td>
			</tr>
			<tr>
				<th>등급</th>
				<td>
					<input type = "checkbox" name = "rating" value = "G">G
					<input type = "checkbox" name = "rating" value = "PG">PG
					<input type = "checkbox" name = "rating" value = "PG-13">PG-13
					<input type = "checkbox" name = "rating" value = "R">R
					<input type = "checkbox" name = "rating" value = "NC-17">NC-17
				</td>
			</tr>
			<tr>
				<th>검색어(제목)</th>
				<td>
					<input type = "text" name = "searchTitle">
				</td>
			</tr>
		</table>
		<button type = "submit">검색</button>
	</form>
</body>
</html>
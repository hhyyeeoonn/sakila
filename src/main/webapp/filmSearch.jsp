<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>filmListForm2</title>
</head>
<body>
	<form action = "<%=request.getContextPath()%>/filmSearchAction.jsp">
		<table border = "1">
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
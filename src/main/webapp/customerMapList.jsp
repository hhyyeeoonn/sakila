<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.HashMap" %>
<%
	CustomerDao customerDao = new CustomerDao();
	
	int currentPage=1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10;
	int beginRow = (currentPage-1) * rowPerPage;
	int lastPage = customerDao.selectCount(rowPerPage);
	
	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>(); 
	list = customerDao.selectCustomerMapList(beginRow, rowPerPage);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>customerMapList</title>
</head>
<body>
	<!-- Map 타입 사용 -->
	<table>
		<tr>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Address</th>
			<th>district</th>
			<th>City</th>
			<th>Country</th>
		</tr>
		<%
			for(HashMap<String, Object> m  : list) {
		%>
			<tr>
				<td><%=(String)(m.get("firstName"))%></td>
				<td><%=(String)(m.get("lastName"))%></td>
				<td><%=(String)(m.get("address"))%></td>
				<td><%=(String)(m.get("district"))%></td>
				<td><%=(String)(m.get("city"))%></td>
				<td><%=(String)(m.get("country"))%></td>			
			</tr>
		<%
			}
		%>
	</table>
	
	<!-- paging -->
	<span>
		<%
			if(currentPage > 1) {
		%>
				<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=1">처음</a>
				<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage-1%>">이전</a>
		<%
			}
		%>
	</span>
	<span><%=currentPage%></span>
	<span>
		<%
			if(currentPage < lastPage) { 
		%>
				<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage+1%>">다음</a>
		<%
			}
		%>		
	</span>
	<span>
		<%
			if(currentPage < lastPage) {
		%>
			<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=lastPage%>">마지막</a>
		<%
			}
		%>
	</span>
</body>
</html>
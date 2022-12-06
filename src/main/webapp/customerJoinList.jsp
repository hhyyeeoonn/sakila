<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.ArrayList" %>
<%	
	CustomerDao customerDao = new CustomerDao();

	int currentPage=1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10;
	int beginRow = (currentPage-1) * rowPerPage;
	int lastPage = customerDao.selectCount(rowPerPage);
	
	ArrayList<CustomerAddressCityCountry> list = customerDao.selectCustomerJoinList(beginRow, rowPerPage);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>customerJoinList</title>
</head>
<body>
	<!-- Domain 타입 사용 -->
	<table>
		<tr>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Address</th>
			<th>District</th>
			<th>City</th>
			<th>Country</th>
		</tr>
		<%
			for(CustomerAddressCityCountry cacc : list) {
		%>
			<tr>
				<td><%=cacc.getCustomer().getFirstName()%></td>
				<td><%=cacc.getCustomer().getLastName()%></td>
				<td><%=cacc.getAddress().getAddress()%></td>
				<td><%=cacc.getAddress().getDistrict()%></td>
				<td><%=cacc.getCity().getCity()%></td>
				<td><%=cacc.getCountry().getCountry()%></td>
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
				<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=1">처음</a>
				<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=currentPage-1%>">이전</a>
		<%
			}
		%>
	</span>
	<span><%=currentPage%></span>
	<span>
		<%
			if(currentPage < lastPage) { 
		%>
				<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=currentPage+1%>">다음</a>
		<%
			}
		%>		
	</span>
	<span>
		<%
			if(currentPage < lastPage) {
		%>
			<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=lastPage%>">마지막</a>
		<%
			}
		%>
	</span>
</body>
</html>
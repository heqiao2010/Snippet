<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>自定义用户登录</title>
</head>
<body>
	<h3>用户登录</h3>
	<form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" name="j_username" /></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" name="j_password"> <br></td>
		</tr>
		<tr>
			<td><input type="submit" value="登录"/> </td>
			<td><input type="reset" name="" value="重置"></td>
		</tr>
	</table>
	</form>
	<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
		<div id="loginerror">
			    <p> 登陆失败:&nbsp;${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</p>
    	</div>
	</c:if>
</body>
</html>
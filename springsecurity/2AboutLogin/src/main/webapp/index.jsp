<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>首页</title>
</head>
<body>
	<p>
		这是首页，欢迎
		<sec:authentication property="name" />
		！
	</p>
	<a href="admin.jsp">管理员页面</a>
	<br>
	<hr>
	<p>当前Session 内容：</p>
	<p>${sessionScope.SPRING_SECURITY_CONTEXT}</p>
</body>
</html>


<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 07.10.2020
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <title><fmt:message key="success"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
</head>
<body>
<h2 class="success">
    <fmt:message key="success"/>
</h2>
</body>
</html>

<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 25.09.2020
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
<link rel="shortcut icon" href="resources/img/xrp_small.png">
<html>
<head>
    <title><fmt:message key="main.menu"/></title>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>

    <%@ include file="parts/header.jspf" %>
</head>
<body>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 30.09.2020
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="finalproject"%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${param.lang}">
<head>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <link rel="shortcut icon" href="resources/img/xrp_small.png">

    <title><fmt:message key="documents"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/analytics_menu.jsp" %>

</head>
    <body>
    <script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/analytics.js"></script>
    </body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>
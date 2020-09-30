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
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" media="screen"/>
<link rel="shortcut icon" href="resources/img/xrp_small.png">
<html>
<head>
    <title><fmt:message key="main.menu"/></title>

    <%
        out.print("<br>name: " + session.getAttribute("user"));
        User user = (User) session.getAttribute("user");
        if(user.getRole() == 2) request.setAttribute("isManager", true);
        if(user.getRole() == 1) request.setAttribute("isHeightCashier", true);
        if(user.getRole() == 0) request.setAttribute("isCashier", true);
        out.print(request.getAttribute("isManager"));
        out.print(request.getAttribute("isHeightCashier"));
        out.print(request.getAttribute("isCashier"));
    %>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
<%--        <form id="logout_form" action="controller" method="post" class="well">--%>

<%--            <p>Введите длину в сантиметрах:--%>
<%--            </p>--%>
<%--            <input type="hidden" name="command" value="logout"/>--%>
<%--            <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="login.submit"/>"/>--%>
<%--        </form>--%>


    <%@ include file="/payments.jsp" %>
    <%@ include file="/documents.jsp" %>

    <%@ include file="parts/header.jspf" %>
</head>
<body>

</body>
</html>

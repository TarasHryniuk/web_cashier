<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 29.09.2020
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" media="screen"/>
<html>
<head>
    <title><fmt:message key="users"/></title>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>

    <c:choose>
        <c:when test="${MANAGER}">
            You got Gold
        </c:when>

        <c:when test="${isCustomer}">
            You got Silver
        </c:when>

        <c:when test="${isProducer}">
            You got Bronze
        </c:when>

        <c:otherwise>
            Better luck next time
        </c:otherwise>
    </c:choose>
</head>
<body>

</body>
</html>

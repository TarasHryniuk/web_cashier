<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 01.10.2020
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="refactor.user"/></title>
</head>
<body>

</body>
</html>

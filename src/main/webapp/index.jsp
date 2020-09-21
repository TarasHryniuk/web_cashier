<a href="login.jsp">login</a>
<a href="logout.jsp">logout</a>
<a href="profile.jsp">profile</a>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <title>PhraseApp - i18n</title>
</head>
<body>
<h2>
    <fmt:message key="gateway.id" />
</h2>
</body>
</html>
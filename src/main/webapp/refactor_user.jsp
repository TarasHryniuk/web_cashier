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
<html lang="${param.lang}">
<head>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" media="screen"/>
    <link rel="shortcut icon" href="resources/img/xrp_small.png">

    <title><fmt:message key="create.user"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/user_menu.jsp" %>

</head>
<body>
<div class="col-xs-12 col-sm-6">
    <form id="refactor_user" action="controller" method="post" class="tab-content">
        <input type="hidden" name="command" value="refactor_user"/>
        <input type="hidden" name="refactor_user_login" value="<c:out value="${param.refactor_user_login}"/>"/>
        <input name="password" type="password" placeholder=<fmt:message key="password"/> class="form-control"/>
        <input name="terminal.id" type="terminal.id" value="<c:out value="${param.refactor_user_terminal_id}"/>" placeholder=<fmt:message key="terminal.id"/> class="form-control"/>
        <input name="full.name" type="full.name" value="<c:out value="${param.refactor_user_full_name}"/>" required="required" placeholder=<fmt:message key="full.name"/> class="form-control"/>
        <select name="role" class="form-control">
            <c:if test="${sessionScope.userRole=='MANAGER'}">
                <option selected = "selected" value="2"><fmt:message key="manager"/></option>
            </c:if>
            <c:if test="${sessionScope.userRole=='HIGH_CASHIER'}">
                <option selected = "selected" value="1"><fmt:message key="height.cashier"/></option>
            </c:if>

            <c:if test="${sessionScope.userRole=='CASHIER'}">
                <option selected = "selected" value="0"><fmt:message key="cashier"/></option>
            </c:if>

            <option value="0"><fmt:message key="cashier"/></option>
            <option value="1"><fmt:message key="height.cashier"/></option>
            <option value="2"><fmt:message key="manager"/></option>
        </select>

        <select name="active" class="form-control">
            <c:if test="${sessionScope.refactor_user_active=='true'}">
                <option selected = "selected" value="true"><fmt:message key="active"/></option>
            </c:if>
            <c:if test="${sessionScope.refactor_user_active=='false'}">
                <option selected = "selected" value="false"><fmt:message key="non.active"/></option>
            </c:if>
            <option value="true"><fmt:message key="active"/></option>
            <option value="false"><fmt:message key="non.active"/></option>
        </select>
        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="save"/>"/>
    </form>
</div>
</div>

</body>
</html>
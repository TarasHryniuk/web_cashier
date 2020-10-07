<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 30.09.2020
  Time: 17:37
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

    <title><fmt:message key="all.user"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/user_menu.jsp" %>

</head>
<body>
<div class="table-responsive">
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="login"/></th>
            <th><fmt:message key="active"/></th>
            <th><fmt:message key="full.name"/></th>
            <th><fmt:message key="role"/></th>
            <th><fmt:message key="config"/></th>
            <th><fmt:message key="delete"/></th>
        </tr>
        <c:forEach items="${requestScope.users}" var="element" varStatus="loop">
            <tr>
                <td>${element.login}</td>

                <c:if test="${element.active=='true'}">
                <td>
                    <button type="button" class="icon-ok text-success"></button>
                </td>
                </c:if>
                <c:if test="${element.active=='false'}">
                    <td>
                        <button type="button" class="icon-remove text-danger"></button>
                    </td>
                </c:if>
                <td>${element.fullName}</td>
                <td>${element.roleName}</td>
                <td>
                    <form id="cashier" action="refactor_user.jsp">
                        <input type="hidden" name="command" value="refactor_user"/>
                        <input type="hidden" name="refactor_user_active" value=${element.active}>
                        <input type="hidden" name="refactor_user_full_name" value=${element.fullName}>
                        <c:out value="${param.refactor_user_full_name}"/>
                        <input type="hidden" name="refactor_user_role" value=${element.role}>
                        <input type="hidden" name="refactor_user_terminal_id" value=${element.terminalId}>
                        <input type="hidden" name="refactor_user_login" value=${element.login}>
                        <input type="submit" value="<fmt:message key="refactor.user"/>">
                    </form>
                </td>
                <td>
                    <form id="delete_user" action="controller" method="post">
                        <input type="hidden" name="command" value="delete_user"/>
                        <input type="hidden" name="refactor_user_login" value=${element.login}>
                        <input type="submit" value="<fmt:message key="delete"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <table align="center">
        <c:forEach items="${requestScope.count}" var="element1" varStatus="loop">
            <th width="40">
                <form id="pagination" action="controller" method="post">
                    <input type="hidden" name="command" value="all_users"/>
                    <input type="hidden" name="page" value=${element1}>
                    <input type="submit" value=${element1}>
                </form>
            </th>
        </c:forEach>
    </table>

</div>
</div>

</body>
</html>
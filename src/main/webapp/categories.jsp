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

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <link rel="shortcut icon" href="resources/img/xrp_small.png">

    <title><fmt:message key="categories"/></title>

</head>
<%--<body>--%>
<%--<div class="table-responsive"--%>
<%--     data-bind="flash: {error: state.error, success: state.success}, hover_loader: state.loading">--%>
<%--    <table class="table table-bordered table-hover table-striped table-condensed">--%>
<%--        <tr>--%>
<%--            <th><fmt:message key="login"/></th>--%>
<%--            <th><fmt:message key="active"/></th>--%>
<%--            <th><fmt:message key="full.name"/></th>--%>
<%--            <th><fmt:message key="role"/></th>--%>
<%--            <th><fmt:message key="config"/></th>--%>
<%--        </tr>--%>
<%--        <c:forEach items="${requestScope.users}" var="element" varStatus="loop">--%>
<%--            <tr>--%>
<%--                <td>${element.login}</td>--%>
<%--                <td>${element.active}</td>--%>
<%--                <td>${element.fullName}</td>--%>
<%--                <td>${element.roleName}</td>--%>
<%--                <td>--%>
<%--                    <form id="cashier" action="refactor_user.jsp">--%>
<%--                        <input type="hidden" name="command" value="refactor_user"/>--%>
<%--                        <input type="hidden" name="refactor_user_active" value=${element.active}/>--%>
<%--                        <input type="hidden" name="refactor_user_full_name" value=${element.fullName}/>--%>
<%--                        <input type="hidden" name="refactor_user_role" value=${element.role}/>--%>
<%--                        <input type="hidden" name="refactor_user_terminal_id" value=${element.terminalId}/>--%>
<%--                        <input type="hidden" name="refactor_user" value=${element}/>--%>
<%--                        <input type="submit" value="<fmt:message key="refactor.user"/>"/>--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>

<%--    <table align="center">--%>
<%--        <c:forEach items="${requestScope.count}" var="element1" varStatus="loop">--%>
<%--            <th width="40">--%>
<%--                <form id="pagination" action="controller" method="post">--%>
<%--                    <input type="hidden" name="command" value="all_users"/>--%>
<%--                    <input type="hidden" name="page" value=${element1}>--%>
<%--                    <input type="submit" value=${element1}>--%>
<%--                </form>--%>
<%--            </th>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>

<%--</div>--%>
<%--</div>--%>

<%--</body>--%>
</html>
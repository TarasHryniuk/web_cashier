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
    <link rel="shortcut icon" href="resources/img/xrp_small.png">

    <title><fmt:message key="all.user"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%--    <%@ include file="/WEB-INF/views/parts/user_menu.jsp" %>--%>

</head>
<body>
<div class="table-responsive"
     data-bind="flash: {error: state.error, success: state.success}, hover_loader: state.loading">
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="id"/></th>
            <th><fmt:message key="cashier"/></th>
            <th><fmt:message key="goods"/></th>
            <th><fmt:message key="total.price"/></th>
            <th><fmt:message key="processing.time"/></th>
            <th><fmt:message key="cancel"/></th>
        </tr>
<%--        <c:forEach items="${requestScope.payments}" var="element" varStatus="loop">--%>
<%--            <tr>--%>
<%--                <td>${element.id}</td>--%>
<%--                <td>${element.user}</td>--%>
<%--                <td>${element.fullName}</td>--%>
<%--                <td>${element.roleName}</td>--%>

<%--                <c:if test="${sessionScope.userRole = MANAGER}">--%>
<%--                    <td>--%>
<%--                        <form id="cashier" action="refactor_user.jsp">--%>
<%--                            <input type="hidden" name="command" value="refactor_user"/>--%>
<%--                            <input type="hidden" name="refactor_user_active" value=${element.active}/>--%>
<%--                            <input type="hidden" name="refactor_user_full_name" value=${element.fullName}/>--%>
<%--                            <input type="hidden" name="refactor_user_role" value=${element.role}/>--%>
<%--                            <input type="hidden" name="refactor_user_terminal_id" value=${element.terminalId}/>--%>
<%--                            <input type="hidden" name="refactor_user" value=${element}/>--%>
<%--                            <input type="submit" value="<fmt:message key="cancel"/>"/>--%>
<%--                        </form>--%>
<%--                    </td>--%>
<%--                </c:if>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
    </table>

    <table align="center">
        <c:forEach items="${requestScope.count}" var="element1" varStatus="loop">
            <th width="40">
                <form id="pagination" action="controller" method="post">
                    <input type="hidden" name="command" value="payments"/>
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
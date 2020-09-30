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
<div class="table-responsive"
     data-bind="flash: {error: state.error, success: state.success}, hover_loader: state.loading">
<%--    <form id="create_user" action="controller" method="post" class="well">--%>
<%--        <input type="hidden" name="command" value="create_user"/>--%>
<%--        <input name="login" type="login" required="required" placeholder=<fmt:message key="login"/> class="form-control"/>--%>
<%--        <input name="password" type="password" required="required" placeholder=<fmt:message key="password"/> class="form-control"/>--%>
<%--        <input name="terminal.id" type="terminal.id" placeholder=<fmt:message key="terminal.id"/> class="form-control"/>--%>
<%--        <input name="full.name" type="full.name" required="required" placeholder=<fmt:message key="full.name"/> class="form-control"/>--%>
<%--        <select name="role">--%>
<%--            <option value="0"><fmt:message key="cashier"/></option>--%>
<%--            <option value="1"><fmt:message key="height.cashier"/></option>--%>
<%--            <option value="2"><fmt:message key="manager"/></option>--%>
<%--        </select>--%>
<%--        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="save"/>"/>--%>
<%--    </form>--%>
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="login"/></th>
            <th><fmt:message key="active"/></th>
            <th><fmt:message key="full.name"/></th>
            <th><fmt:message key="role"/></th>
        </tr>
        <tr>
<%--            &lt;%&ndash;%>
<%--                String[] cartItems = (String[]) request.getSession().getAttribute("cartList");--%>
<%--                for (int i = 0; i < cartItems.length; i++) {--%>
<%--            %>--%>
<%--            <td>--%>
<%--                <input type="text" id="itemPrice" maxlength="5" size="5" style="border:none;" value="$<%= cartItems[3]%>" />--%>
<%--            </td>--%>
<%--            <% } %>--%>
        </tr>
    </table>
</div>
</div>

</body>
</html>
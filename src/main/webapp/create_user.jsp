<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 30.09.2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="finalproject"%>
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

    <title><fmt:message key="create.user"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/user_menu.jsp" %>

</head>
<body>
<c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">
<script type="text/javascript">

    function validate(evt) {
        var theEvent = evt || window.event;

        // Handle paste
        if (theEvent.type === 'paste') {
            key = event.clipboardData.getData('text/plain');
        } else {
            // Handle key press
            var key = theEvent.keyCode || theEvent.which;
            key = String.fromCharCode(key);
        }
        var regex = /[A-Za-z0-9№;$%^!.,&*|~₴]|\./;
        if (!regex.test(key)) {
            theEvent.returnValue = false;
            if (theEvent.preventDefault) theEvent.preventDefault();
        }
    }
</script>

<div class="col-xs-12 col-sm-6">
    <form id="create_user" action="controller" method="post" class="tab-content">

        <label><fmt:message key="login"/></label>
        <input name="login" type="text" required="required" onkeypress='validate(event)' placeholder=<fmt:message key="login" /> class="form-control"/>

        <label><fmt:message key="password"/></label>
        <input name="password" type="password" required="required" onkeypress='validate(event)' placeholder=<fmt:message key="password"/> class="form-control"/>

        <label><fmt:message key="full.name"/></label>
        <input name="full.name" type="text" required="required" placeholder=<fmt:message key="full.name"/> class="form-control"/>

        <label><fmt:message key="role"/></label>
        <select name="role" class="form-control">
            <option value="0"><fmt:message key="cashier"/></option>
            <option value="1"><fmt:message key="height.cashier"/></option>
            <option value="2"><fmt:message key="manager"/></option>
        </select>
        <input type="hidden" name="command" value="create_user"/>
        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="save"/>"/>
    </form>
</div>
</div>

<script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/create_user.js"></script>
</c:if>
</body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>


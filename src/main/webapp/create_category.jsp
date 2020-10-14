<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 30.09.2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="finalproject" %>
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

    <title><fmt:message key="category.create"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/payments_menu.jsp" %>

</head>
<body>

<c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">

    <div class="col-xs-12 col-sm-6">
        <form id="create_category" action="controller" method="post" class="tab-content">
            <input type="hidden" name="command" value="create_category"/>
            <label><fmt:message key="name"/></label>
            <input name="name" type="text" required="required" placeholder=
                <fmt:message key="name"/> class="form-control"/>

            <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="save"/>"/>
        </form>
    </div>
    </div>

    <script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/create_category.js"></script>
</c:if>
</body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>


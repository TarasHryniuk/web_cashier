<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 07.10.2020
  Time: 14:23
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

    <title><fmt:message key="manage.product"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/payments_menu.jsp" %>

</head>
<body>
<c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-6">
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
                    var regex = /[0-9]|\./;
                    if (!regex.test(key)) {
                        theEvent.returnValue = false;
                        if (theEvent.preventDefault) theEvent.preventDefault();
                    }
                }
            </script>

            <form id="refactor_product" action="controller" method="post" class="tab-content">
                <b><fmt:message key="goods"/></b>
                <select name='goods_id' class="form-control">
                    <c:forEach items="${sessionScope.goods}" var="goods">
                        <option value="${goods.id}">${goods.name} ${goods.price / 100.0} uah</option>
                    </c:forEach>
                </select>

                <b><fmt:message key="count"/></b>
                <input name="count" onkeypress='validate(event)' type="text" min="0" required="required" placeholder=
                <fmt:message key="count"/> class="form-control"/>

                <b><fmt:message key="price"/></b>
                <input name="price" onkeypress='validate(event)' type="text" min="0" required="required" placeholder=
                <fmt:message key="price"/> class="form-control"/>
                <input type="hidden" name="command" value="refactor_product"/>
                <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="add"/>"/>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/edit_products.js"></script>
</c:if>
</body>

<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>
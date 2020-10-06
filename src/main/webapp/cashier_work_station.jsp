<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 04.10.2020
  Time: 19:07
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

    <title><fmt:message key="cashier.window"/></title>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/payments_menu.jsp" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

</head>
<body>
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

    <form>
<%--        <b><fmt:message key="good_id"/></b>--%>
<%--        <select name='goods_by_id' class="form-control">--%>
<%--            <c:forEach items="${requestScope.goods}" var="goods">--%>
<%--                <option value="${goods.id}">${goods.id}</option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>

        <b><fmt:message key="goods"/></b>

        <select id="opt_ph" name='goods_id' class="form-control">
            <c:forEach items="${requestScope.goods}" var="goods">
                <option value="${goods.id}">${goods.name}</option>
            </c:forEach>
        </select>

        <b><fmt:message key="count"/></b>
        <input name="count" onkeypress='validate(event)' type="text" required="required" placeholder=<fmt:message key="count"/> class="form-control"/>

        <b><fmt:message key="price"/></b>
        <input id="price" name="price" maxlength="30" value="" autocomplete="off" class="form-control"/>

        <input type="hidden" name="command" value="add_to_basket"/>

        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="add"/>"/>
    </form>
</div>

<div class="col-xs-12 col-sm-6">
    <b><fmt:message key="summary"/></b>
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="goods"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="count"/></th>
            <th><fmt:message key="cancel"/></th>
        </tr>

        <c:forEach items="${sessionScope.products}" var="products" varStatus="loop">
            <tr>
                <td>${products.name}</td>
                <td>${products.price}</td>
                <td>${products.count}</td>
<%--                <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">--%>
                <td>
                    <form>
                        <input type="hidden" name="command" value="remove_from_basket"/>
                        <input type="hidden" name="cancel_product_id" value="${products.id}"/>
                        <input type="hidden" name="cancel_product_count" value="${products.count}"/>
                        <input type="submit" value="<fmt:message key="cancel"/>">
                    </form>
                </td>
<%--                </c:if>--%>
            </tr>
        </c:forEach>
    </table>
    <b><fmt:message key="total.price"/></b>
    <input readonly="readonly" name="total.price" type="text" required="required" value="${sessionScope.total_price}" class="form-control"/>
    <form id="pay_basket" action="controller" method="post" class="well">
        <input type="hidden" name="command" value="pay_basket"/>
        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="pay"/>"/>
    </form>
    <form id="clear_basket" action="controller" method="post" class="well">
        <input type="hidden" name="command" value="clear_basket"/>
        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="clear"/>"/>
    </form>
</div>
</body>
</html>
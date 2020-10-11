<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 11.10.2020
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <link rel="shortcut icon" href="resources/img/xrp_small.png">

    <title><fmt:message key="edit"/></title>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
</head>
<body>
<div class="col-xs-12 col-sm-6 col-md-12">
    <b><fmt:message key="summary"/></b>
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="goods"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="count"/></th>
            <th><fmt:message key="cancel"/></th>
        </tr>

        <c:forEach items="${requestScope.receipt_for_editing}" var="receipt_for_editing" varStatus="loop">
            <tr>
                <td>${receipt_for_editing.productName}</td>
                <td>${receipt_for_editing.price / 100.0}</td>
                <td>${receipt_for_editing.count}</td>
                <td>
                    <form>
                        <input type="hidden" name="command" value="cancel_product_from_receipt"/>
                        <input type="hidden" name="cancel_receipt_id" value="${receipt_for_editing.id}"/>
                        <input type="hidden" name="cancel_product_name" value="${receipt_for_editing.productName}"/>
                        <input type="hidden" name="cancel_product_id" value="${receipt_for_editing.id}"/>
                        <input type="hidden" name="cancel_product_count" value="${receipt_for_editing.count}"/>
                        <input type="submit" value="<fmt:message key="cancel"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>

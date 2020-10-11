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

    <title><fmt:message key="all.user"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>

</head>
<body>
<div class="container">
    <div class="row">
<div class="table-responsive">
    <table class="table table-bordered table-hover table-striped table-condensed">
        <tr>
            <th><fmt:message key="id"/></th>
            <th><fmt:message key="total.price"/></th>
            <th><fmt:message key="cashier"/></th>
            <th><fmt:message key="processing.time"/></th>
            <th><fmt:message key="edit"/></th>
            <th><fmt:message key="cancel"/></th>
            <th><fmt:message key="print.receipt"/></th>
        </tr>
        <c:forEach items="${requestScope.total_receipts}" var="element" varStatus="loop">
            <tr>
                <td>${element.id}</td>
                <td>${element.totalAmount}</td>
                <td>${element.login}</td>
                <td>${element.date}</td>

                <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">
                    <td>
                        <form id="edit_receipt" action="controller" method="post">
                            <input type="hidden" name="command" value="edit_receipt"/>
<%--                            <input type="hidden" name="edit_receipt" value=${element.id}>--%>
<%--                            <input type="hidden" name="edit_receipt_name" value=${element.name}>--%>
<%--                            <input type="hidden" name="edit_receipt_count" value=${element.count}>--%>
                            <input type="submit" class="btn btn-link"  value="<fmt:message key="edit"/>">
                        </form>
                    </td>

                    <td>
                        <form id="cancel_receipt" action="controller" method="post">
                            <input type="hidden" name="command" value="cancel_receipt"/>
                            <input type="hidden" name="cancel_receipt_id" value=${element.id}>
                            <input type="submit" class="btn btn-link" value="<fmt:message key="cancel"/>">
                        </form>
                    </td>
                </c:if>

                <td>
                    <form id="print_receipt" action="controller" method="post">
                        <input type="hidden" name="command" value="print_receipt"/>
                        <input type="hidden" name="print_bill" value=${element.id}>
                        <input type="submit" class="btn btn-link" value="<fmt:message key="print.receipt"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <table align="center">
        <c:forEach items="${requestScope.count}" var="element1" varStatus="loop">
            <th width="40">
                <form id="pagination" action="controller" method="post">
                    <input type="hidden" name="command" value="payments"/>
                    <input type="hidden" name="page" value=${element1}>
                    <input type="submit" class="btn btn-link"  value=${element1}>
                </form>
            </th>
        </c:forEach>
    </table>

</div>
</div>
</div>
</div>

<script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/payments.js"></script>

</body>
</html>
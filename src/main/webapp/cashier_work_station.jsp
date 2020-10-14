<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 04.10.2020
  Time: 19:07
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

    <title><fmt:message key="cashier.window"/></title>

    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/payments_menu.jsp" %>

</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-6">

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
                <input type="hidden" name="command" value="add_to_basket"/>
                <input type="submit" class="hided" />
                <div class="form-group">
                    <div class="dropdown-container">
                        <label><fmt:message key="categories"/></label>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" id="dropdown-categories" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <fmt:message key="categories"/>
                            </button>
                            <div id="dropdown-categories-menu" class="dropdown-menu" aria-labelledby="dropdown-categories"></div>
                        </div>
                    </div>
                </div>

                <table id="products" class="table table-bordered table-hover table-striped table-condensed hided">
                    <thead>
                        <tr>
                            <th><fmt:message key="goods"/></th>
                            <th><fmt:message key="price"/></th>
                            <th><fmt:message key="weight"/></th>
                            <th><fmt:message key="count"/></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>

                <label><fmt:message key="find.product.by.id"/></label>
                <input id="find_by_product_id" name="find_by_product_id" type="number" onkeypress='validate(event)' type="text" min="1" class="form-control" placeholder="<fmt:message key="find.product.by.id"/>">


                <div id="product-count-group" class="form-group">
                    <label><fmt:message key="count"/></label>
                    <input name="count" type="number" onkeypress='validate(event)' type="text" min="1" required="required" class="form-control" id="product-count" placeholder="<fmt:message key="count"/>">
                </div>


                <div id="countAlertError" class="alert alert-danger hided" role="alert">
                    <h4 class="alert-heading"><fmt:message key="error"/></h4>
                    <p><fmt:message key="error.count.product"/></p>
                </div>

                <div id="productNotFoundAlertError" class="alert alert-danger hided" role="alert">
                    <h4 class="alert-heading"><fmt:message key="error"/></h4>
                    <p><fmt:message key="product.not.found"/></p>
                </div>

                <input type="hidden" name="product_id" value=""/>
                <input type="hidden" name="categories.id" value=""/>
                <input type="hidden" name="command" value="add_to_basket"/>
                <input type="submit" class="hided" />

                <div class="text-right">
                    <button id="addProduct" style="width: 100px;" type="button" class="btn btn-secondary hided"><fmt:message key="add"/></button>
                </div>
            </form>

        </div>

        <div class="col-xs-12 col-sm-6 col-md-6">
            <b><fmt:message key="summary"/></b>
            <table class="table table-bordered table-hover table-striped table-condensed">
                <tr>
                    <th><fmt:message key="goods"/></th>
                    <th><fmt:message key="price"/></th>
                    <th><fmt:message key="count"/></th>
                    <th><fmt:message key="cancel"/></th>
                </tr>

                <c:forEach items="${sessionScope.basket}" var="basket" varStatus="loop">
                    <tr>
                        <td>${basket.productName}</td>
                        <td>${basket.price / 100.0}</td>
                        <td>${basket.count}</td>
                        <c:if test="${not empty sessionScope.basket}">
                            <td>
                                <form>
                                    <input type="hidden" name="command" value="remove_from_basket"/>
                                    <input type="hidden" name="cancel_product_name" value="${basket.productName}"/>
                                    <input type="hidden" name="cancel_product_count" value="${basket.count}"/>
                                    <input type="submit" value="<fmt:message key="cancel"/>">
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>

            <c:if test="${not empty sessionScope.basket}">
                <b><fmt:message key="total.price"/></b>

                <input readonly="readonly" name="total.price" type="text" required="required"
                       value="${sessionScope.total_price}" class="form-control"/>
                <form action="controller" method="post" class="well">
                    <input type="hidden" name="command" value="pay_basket"/>
                    <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="pay"/>"/>
                </form>
                <form action="controller" method="post" class="well">
                    <input type="hidden" name="command" value="clear_basket"/>
                    <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="clear"/>"/>
                </form>
            </c:if>

        </div>
    </div>
</div>

<script type="text/javascript" src="resources/js/jquery-3.5.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/cashier_work_station.js"></script>

</body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>
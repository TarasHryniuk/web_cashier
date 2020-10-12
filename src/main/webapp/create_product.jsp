<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 07.10.2020
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="finalproject"%>
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

    <title><fmt:message key="create.product"/></title>
    <%@ include file="/WEB-INF/views/parts/menu.jsp" %>
    <%@ include file="/WEB-INF/views/parts/payments_menu.jsp" %>

</head>
<body>
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

<div class="col-xs-12 col-sm-6">
    <form id="create_product" action="controller" method="post" class="tab-content">
        <input type="hidden" name="command" value="create_product"/>

        <label><fmt:message key="categories"/></label>
        <select name='category_id' class="form-control">
            <c:forEach items="${sessionScope.categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>

        <label><fmt:message key="name"/></label>
        <input name="name" type="text" required="required" placeholder=<fmt:message key="name"/> class="form-control"/>

        <label><fmt:message key="price"/></label>
        <input name="price" onkeypress='validate(event)' type="text" required="required" placeholder=<fmt:message key="price"/> class="form-control"/>

        <label><fmt:message key="count"/></label>
        <input name="count" onkeypress='validate(event)' type="text" required="required" placeholder=<fmt:message key="count"/> class="form-control"/>

        <label><fmt:message key="weight"/></label>
        <input name="weight" onkeypress='validate(event)' type="text" required="required" placeholder=<fmt:message key="weight"/> class="form-control"/>

        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="save"/>"/>
    </form>
</div>
</body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>

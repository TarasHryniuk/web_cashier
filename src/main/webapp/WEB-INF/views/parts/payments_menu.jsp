<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 05.10.2020
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <c:if test="${sessionScope.userRole=='MANAGER'}">
                    <ul class="nav navbar-nav navbar-left">
                        <form action="manage_products.jsp">
                            <input type="submit" value=<fmt:message key="edit.products"/>>
                        </form>
                    </ul>
                    <ul class="nav navbar-nav navbar-left">
                        <form action="create_category.jsp">
                            <input type="submit" value=<fmt:message key="category.create"/>>
                        </form>
                    </ul>
                    <ul class="nav navbar-nav navbar-left">
                        <form action="create_product.jsp">
                            <input type="submit" value=<fmt:message key="create.product"/>>
                        </form>
                    </ul>
                </c:if>
            </ul>
        </div>
    </div>
</head>
<body>

</body>
</html>

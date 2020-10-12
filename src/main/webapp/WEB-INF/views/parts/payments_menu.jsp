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
    <c:if test="${sessionScope.userRole=='MANAGER'}">
        <div class="nav justify-content-center">
            <div id="navigation" class="nav-item">
                <ul id="main-nav" class="nav-item">
                    <ul class="nav-item">
                        <form action="edit_products.jsp">
                            <input class="btn btn-link" type="submit" value="<fmt:message key="edit.products"/>"/>
                        </form>
                    </ul>
                    <ul class="nav-item">
                        <form action="create_category.jsp">
                            <input class="btn btn-link" type="submit" value="<fmt:message key="category.create"/>"/>
                        </form>
                    </ul>
                    <ul class="nav-item">
                        <form action="create_product.jsp">
                            <input class="btn btn-link" type="submit" value="<fmt:message key="create.product"/>"/>
                        </form>
                    </ul>
                </ul>
            </div>
        </div>
    </c:if>
</head>
<body>
</body>
</html>

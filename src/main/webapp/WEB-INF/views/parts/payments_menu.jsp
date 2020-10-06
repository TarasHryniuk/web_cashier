<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 05.10.2020
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <div>
        <div class="collapse navbar-collapse">
            <ul id="main-nav" class="nav navbar-nav">
                <ul class="nav navbar-nav navbar-left">
                    <form id="cashier" action="controller" method="post">
                        <input type="hidden" name="command" value="all_users"/>
                        <input type="submit" value="<fmt:message key="all.user"/>"/>
                    </form>
                    <c:if test="${sessionScope.userRole=='MANAGER'}">
                        <ul class="nav navbar-nav navbar-left">
                            <form action="create_user.jsp">
                                <input type="submit" value=<fmt:message key="create.user"/>/>
                            </form>
                        </ul>
                    </c:if>
                </ul>
            </ul>
        </div>
    </div>
</head>
<body>

</body>
</html>

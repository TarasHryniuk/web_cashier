<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <ul id="main-nav" class="nav-item">
            <c:if test="${sessionScope.userRole=='MANAGER'}">
                <ul class="nav navbar-nav navbar-left">
                    <form id="cashier" action="controller" method="post">
                        <input type="hidden" name="command" value="all_users"/>
                        <input class="btn btn-link" type="submit" value="<fmt:message key="all.user"/>"/>
                    </form>
                </ul>

                <ul class="nav navbar-nav navbar-left">
                    <form action="create_user.jsp">
                        <input class="btn btn-link" type="submit" value="<fmt:message key="create.user"/>"/>
                    </form>
                </ul>
            </c:if>
        </ul>
    </div>
</div>
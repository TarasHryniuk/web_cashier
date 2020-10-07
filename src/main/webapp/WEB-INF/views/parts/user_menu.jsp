<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div>
    <div class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <c:if test="${sessionScope.userRole=='MANAGER'}">
                <ul class="nav navbar-nav navbar-left">
                    <form id="cashier" action="controller" method="post">
                        <input type="hidden" name="command" value="all_users"/>
                        <input type="submit" value="<fmt:message key="all.user"/>"/>
                    </form>
                </ul>

                <ul class="nav navbar-nav navbar-left">
                    <form action="create_user.jsp">
                        <input type="submit" value=<fmt:message key="create.user"/>/>
                    </form>
                </ul>
            </c:if>
        </ul>
    </div>
</div>
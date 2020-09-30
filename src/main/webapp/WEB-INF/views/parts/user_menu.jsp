<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div>
    <div class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <ul class="nav navbar-nav navbar-left">
                <form action="all_users.jsp">
                    <input type="submit" value=<fmt:message key="all.user"/> />
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form action="create_user.jsp">
                    <input type="submit" value=<fmt:message key="create.user"/> />
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form action="refactor_user.jsp">
                    <input type="submit" value=<fmt:message key="refactor.user"/> />
                </form>
            </ul>

    </div>
</div>
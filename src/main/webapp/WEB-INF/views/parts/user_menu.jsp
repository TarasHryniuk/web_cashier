<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div>
    <div class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <ul class="nav navbar-nav navbar-left">
                <form id="cashier" action="controller" method="post">
                    <input type="hidden" name="command" value="all_users"/>
                    <input type="submit" value="<fmt:message key="all.user"/>"/>
                </form>
            </ul>
                <%
                    out.print(session.getAttribute("userRole"));
                    if("MANAGER" == session.getAttribute("userRole")){

                    }
                %>
            <ul class="nav navbar-nav navbar-left">
                <form action="create_user.jsp">
                    <input type="submit" value=
                    <fmt:message key="create.user"/>/>
                </form>
            </ul>
    </div>
</div>
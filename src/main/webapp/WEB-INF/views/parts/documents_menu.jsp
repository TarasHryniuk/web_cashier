<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div>
    <div class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <ul class="nav navbar-nav navbar-left">
                <form id="x_report" action="controller" method="post">
                    <input type="hidden" name="command" value="x.report"/>
                    <input type="submit" value="<fmt:message key="x.report"/>"/>
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form id="z_report" action="controller" method="post">
                    <input type="hidden" name="command" value="z.report"/>
                    <input type="submit" value="<fmt:message key="z.report"/>"/>
                </form>
            </ul>
    </div>
</div>
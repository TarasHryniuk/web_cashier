<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div>
    <div class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">
                <ul class="nav navbar-nav navbar-left">
                    <form id="x_report" action="controller" method="post">
                        <input type="hidden" name="command" value="x_report"/>
                        <input type="submit" value="<fmt:message key="x.report"/>"/>
                    </form>
                </ul>
                <ul class="nav navbar-nav navbar-left">
                    <form id="z_report" action="controller" method="post">
                        <input type="hidden" name="command" value="z_report"/>
                        <input type="submit" value="<fmt:message key="z.report"/>"/>
                    </form>
                </ul>
            </c:if>
        </ul>
    </div>
</div>
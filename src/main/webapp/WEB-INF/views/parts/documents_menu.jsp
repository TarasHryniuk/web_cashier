<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">

            <ul id="main-nav" class="nav-item">
                <ul>
                    <form id="x_report" action="controller" method="post">
                        <input type="hidden" name="command" value="x_report"/>
                        <input class="btn btn-link" type="submit" value="<fmt:message key="x.report"/>"/>
                    </form>
                </ul>
                <ul>
                    <form id="z_report" action="controller" method="post">
                        <input type="hidden" name="command" value="z_report"/>
                        <input class="btn btn-link" type="submit" value="<fmt:message key="z.report"/>"/>
                    </form>
                </ul>
            </ul>
        </c:if>
    </div>
</div>
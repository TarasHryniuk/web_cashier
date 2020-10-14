<%@ page import="cashier.dao.entity.User" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">

            <ul id="main-nav" class="nav-item">
                <ul>
                    <form id="all_products_analytics" action="controller" method="post">
                        <input type="hidden" name="command" value="all_products_analytics"/>
                        <input class="btn btn-link" type="submit" value="<fmt:message key="all.products.in.warehouse"/>"/>
                    </form>
                </ul>
                <ul>
                    <form id="analytics_by_cashier_per_day" action="controller" method="post">
                        <input type="hidden" name="command" value="all_products_analytics"/>
                        <input class="btn btn-link" type="submit" value="<fmt:message key="analytics_by_cashier_per_day"/>"/>
                    </form>
                </ul>
            </ul>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <ul id="main-nav" class="nav-item">
            <ul class="nav-item">
                <form id="initialize.work.station" action="controller" method="post">
                    <input type="hidden" name="command" value="initialize.work.station"/>
                    <input id="cashier-window-tab" class="btn btn-link" type="submit" value="<fmt:message key="cashier.window"/>"/>
                </form>
            </ul>
            <ul class="nav-item">
                <form id="payments" action="controller" method="post">
                    <input type="hidden" name="command" value="payments"/>
                    <input id="payments-window-tab" class="btn btn-link" type="submit" value="<fmt:message key="payments"/>"/>
                </form>
            </ul>
            <c:if test="${sessionScope.userRole=='MANAGER' || sessionScope.userRole=='HIGH_CASHIER'}">
                <ul class="nav-item">
                    <form action="documents.jsp">
                        <input id="documents-window-tab" class="btn btn-link" type="submit" value=<fmt:message key="documents"/>>
                    </form>
                </ul>
            </c:if>
            <ul class="nav-item">
                <form id="users" action="controller" method="post">
                    <input type="hidden" name="command" value="all_users"/>
                    <input id="users-window-tab" class="btn btn-link" type="submit" value="<fmt:message key="users"/>"/>
                </form>
            </ul>

            <ul class="nav-item">
                <form id="logout_form" action="controller" method="post">
                    <input type="hidden" name="command" value="logout"/>
                    <input class="btn btn-link" type="submit" value="<fmt:message key="logout"/>"/>
                </form>
            </ul>
        </ul>
    </div>
</div>

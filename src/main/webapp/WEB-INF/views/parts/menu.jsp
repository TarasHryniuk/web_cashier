<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<div role="navigation" class="navbar navbar-inverse">
    <div id="navigation" class="collapse navbar-collapse">
        <ul id="main-nav" class="nav navbar-nav">
            <ul class="nav navbar-nav navbar-left">
                <form id="initialize.work.station" action="controller" method="post">
                    <input type="hidden" name="command" value="initialize.work.station"/>
                    <input type="submit" value="<fmt:message key="cashier.window"/>"/>
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form id="payments" action="controller" method="post">
                    <input type="hidden" name="command" value="payments"/>
                    <input type="submit" value="<fmt:message key="payments"/>"/>
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form action="documents.jsp">
                    <input type="submit" value=<fmt:message key="documents"/>>
                </form>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <form id="users" action="controller" method="post">
                    <input type="hidden" name="command" value="all_users"/>
                    <input type="submit" value="<fmt:message key="users"/>"/>
                </form>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <form id="logout_form" action="controller" method="post">
                    <input type="hidden" name="command" value="logout"/>
                    <input type="submit" value="<fmt:message key="logout"/>"/>
                </form>
            </ul>
    </div>
</div>
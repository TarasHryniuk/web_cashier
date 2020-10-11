<%--
  Created by IntelliJ IDEA.
  User: tarashryniuk
  Date: 11.10.2020
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/custom.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="resources/css/datepicker.css" media="screen"/>

</head>
<body>
<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <ul id="main-nav" class="nav-item">
            <ul class="nav-item">
                <form id="initialize.work.station" action="controller" method="post">
                    <input type="hidden" name="command" value="initialize.work.station"/>
                    <input name="date" type="date" class="form-control">
                    <input class="btn btn-link" type="submit" value="<fmt:message key="search"/>"/>
                </form>
            </ul>
        </ul>
    </div>
</div>
</body>
</html>


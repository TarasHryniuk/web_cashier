<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<html lang="${param.lang}">
<head>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" media="screen"/>

    <title>Auth page</title>
    <%@ include file="parts/header.jspf" %>


    <style type="text/css" media="screen">
        .container {
            padding: 150px 0 0;
            width: 500px;
        }

        .form-control {
            font-size: 16px;
            height: 44px;
            border-radius: 0;
            margin-bottom: 10px;
            padding: 6px 12px;
        }

        .btn {
            padding: 6px 12px;
        }

        .well {
            padding: 40px;
            border: none;
        }

        @media (max-width: 480px) {
            .container {
                padding-top: 10px;
            }
        }
    </style>
</head>
<body>
<div class="container">
        <form id="login_form" action="controller" method="post" class="well">
            <div style="display: inline-flex;">
                <h2 style="color: #DEA230;margin-right: 124px;margin-bottom: 6px;"><fmt:message key="label"/></h2>
                <div class="btn-group pull-left">
                    <select name="language" class="form-control">
                        <option value="en"><fmt:message key="lang.en"/></option>
                        <option value="ru"><fmt:message key="lang.ru"/></option>
                        <option value="ua"><fmt:message key="lang.ua"/></option>
                    </select>
                </div>
            </div>

            <input type="hidden" name="command" value="login"/>
            <input name="login" type="login" required="required" placeholder=<fmt:message key="login"/> class="form-control"/>
            <input name="password" type="password" required="required" placeholder=<fmt:message key="password"/> class="form-control"/>
            <input name="terminal.id" type="terminal.id" placeholder=<fmt:message key="terminal.id"/> class="form-control"/>

            <div class="alert alert-danger" style="display: none;"><strong><fmt:message key="login.error"/></strong>
            </div>

            <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="login.submit"/>"/>
        </form>
    </div>
</div>
<%@ include file="parts/footer.jspf" %>
</body>
</html>


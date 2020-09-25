<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>

<html lang="${param.lang}">
<head>

    <link rel="shortcut icon" href="resources/img/xrp_small.png">
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" media="screen"/>

    <title>Auth page</title>
    <%@ include file="parts/header.jspf" %>


    <style type="text/css" media="screen">
        .container {
            padding: 150px 0 0;
            width: 380px;
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
    <div class="pages">
        <%
            String profile_msg = (String) request.getAttribute("profile_msg");
            if (profile_msg != null) {
                out.print(profile_msg);
            }
            String login_msg = (String) request.getAttribute("login_msg");
            if (login_msg != null) {
                out.print(login_msg);
            }
        %>
        <br/>

        <form action="error_page.jsp" method="post" class="well">
            <div style="display: inline-flex;">
                <h2 style="color: #DEA230;margin-right: 124px;margin-bottom: 6px;"><fmt:message key="label"/></h2>
<%--                <div class="btn-group pull-right">--%>
<%--                    <a data-toggle="dropdown" class="btn btn-default dropdown-toggle">--%>
<%--                        <span><c:out value="${pageContext.response.locale}"/></span>--%>
<%--                        <span class="caret"></span>--%>
<%--                    </a>--%>
<%--                    <ul class="dropdown-menu">--%>
<%--                        <li><a><fmt:message key="lang.en"/></a></li>--%>
<%--                        <li><a><fmt:message key="lang.ru"/></a></li>--%>
<%--                        <li><a><fmt:message key="lang.ua"/></a></li>--%>
<%--                    </ul>--%>
<%--                </div>--%>

                <form id="settings_form" action="controller" method="get">
                    <input type="hidden" name="command" value="updateSettings" />

                    <div>
                        <p>

                        </p>
                        <select name="localeToSet">
                            <c:choose>
                                <c:when test="${not empty defaultLocale}">
                                    <option value="">${defaultLocale}[Default]</option>
                                </c:when>
                                <c:otherwise>
                                    <option value=""/>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="localeName" items="${locales}">
                                <option value="${localeName}">${localeName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <input class="btn btn-primary btn-block" type="submit" value='<fmt:message key="login.submit"/>'><br/>
                </form>

            </div>

            <input type="hidden" name="command" value="login"/>
            <label><fmt:message key="login"/></label>
            <input name="username" type="text" required="required" placeholder=<fmt:message key="login"/> class="form-control"/>

            <label><fmt:message key="password"/></label>
            <input name="password" type="password" required="required" placeholder=<fmt:message key="password"/> class="form-control"/>

            <div class="alert alert-danger" style="display: none;"><strong><fmt:message key="login.error"/></strong>
            </div>

            <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="login.submit"/>"/>
        </form>
    </div>
</div>
<%@ include file="parts/footer.jspf" %>
</body>
</html>


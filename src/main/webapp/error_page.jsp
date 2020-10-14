<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="finalproject" %>
<%@ include file="/WEB-INF/views/parts/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${param.lang}">
<head>
</head>
<body>
<div class="nav justify-content-center">
    <div id="navigation" class="nav-item">
        <table align="center" class="table table-bordered table-hover table-striped table-condensed" id="main-container">
            <%@ include file="parts/header.jspf" %>
            <tr>
                <td class="content">
                    <h2 class="error">
                        The following error occurred
                    </h2>

                    <%-- this way we get the error information (error 404)--%>
                    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
                    <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

                    <%-- this way we get the exception --%>
                    <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

                    <c:if test="${not empty code}">
                        <h3>Error code: ${code}</h3>
                    </c:if>

                    <c:if test="${not empty message}">
                        <h3>Message: ${message}</h3>
                    </c:if>

                    <%-- if get this page using forward --%>
                    <c:if test="${not empty errorMessage and empty exception and empty code}">
                        <h3>Error message: ${errorMessage}</h3>
                    </c:if>

                    <%-- this way we print exception stack trace --%>
                    <c:if test="${not empty exception}">
                        <hr/>
                        <h3>Stack trace:</h3>
                        <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
                            ${stackTraceElement}
                        </c:forEach>
                    </c:if>

                    <%-- CONTENT --%>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
<footer>
    <p align="center"><finalproject:footer/><p>
</footer>
</html>
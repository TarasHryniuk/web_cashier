<%--<%@ include file="index.jsp" %>--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Auth page</title>
    <%@ include file="parts/header.jsp" %>
</head>
<body>
<div class="view">
    <div class="pages">
        <%
            String profile_msg=(String)request.getAttribute("profile_msg");
            if(profile_msg!=null){
                out.print(profile_msg);
            }
            String login_msg=(String)request.getAttribute("login_msg");
            if(login_msg!=null){
                out.print(login_msg);
            }
        %>
        <br/>

        <form action="loginprocess.jsp" method="post">
            Email:<input type="text" name="email"/><br/><br/>
            Password:<input type="password" name="password"/><br/><br/>
            <input type="submit" value="login"/>
        </form>
    </div>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>
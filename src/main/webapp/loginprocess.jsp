<%@page import="cashier.service.AuthService"%>
<jsp:useBean id="obj" class="cashier.service.AuthService"/>

<jsp:setProperty property="*" name="obj"/>

<%
    out.print("obj");
    AuthService authService = new AuthService();
    int status=authService.doAuth(obj);
    if(status){
        out.println("You r successfully logged in");
        session.setAttribute("session","TRUE");
    } else {
        out.print("Sorry, email or password error");
%>
<jsp:include page="index.jsp"></jsp:include>
<%
    }
%>
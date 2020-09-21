<%@ include file="/WEB-INF/views/parts/include.jsp" %>

<h2><spring:message code="error.internal.server.error"/></h2>

<sec:authorize access="hasRole('ADMIN')">
    <pre>
    <%
        try {
            // The Servlet spec guarantees this attribute will be available
            Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

            if (exception != null) {
                if (exception instanceof ServletException) {
                    // It's a ServletException: we should extract the root cause
                    ServletException sex = (ServletException) exception;
                    Throwable rootCause = sex.getRootCause();
                    if (rootCause == null)
                        rootCause = sex;
                    out.println("** Root cause is: "+ rootCause.getMessage());
                    rootCause.printStackTrace(new java.io.PrintWriter(out));
                }
                else {
                    // It's not a ServletException, so we'll just show it
                    exception.printStackTrace(new java.io.PrintWriter(out));
                }
            }
            else  {
                out.println("No error information available");
            }

            // Display cookies
            out.println("\nCookies:\n");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    out.println(cooky.getName() + "=[" + cooky.getValue() + "]");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(new java.io.PrintWriter(out));
        }
    %>
    </pre>
</sec:authorize>
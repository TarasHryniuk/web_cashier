package cashier.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Taras Hryniuk, created on  22.09.2020
 * email : hryniuk.t@gmail.com
 */
public class SessionLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}

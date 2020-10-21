package cashier.servlet;

import cashier.Path;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.util.StringHelpers;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Security filter. Disabled by default. Uncomment Security filter
 * section in web.xml to enable.
 *
 * @author Taras Hryniuk, created on  19.09.2020
 * email : hryniuk.t@gmail.com
 */
public class CommandAccessFilter implements Filter {

    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

    // commands access
    private static List<String> access = new ArrayList<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter starts");

        if (accessAllowed(request)) {
            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";

            request.setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);

            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        System.out.println(commandName);
        if (StringHelpers.isNullOrEmpty(commandName)) return false;

        System.out.println("outOfControl: " + outOfControl);

        if (outOfControl.contains(commandName)) return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = Role.getRole((User) session.getAttribute("user"));
        if (userRole == null) return false;

        return access.contains(commandName) || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) {
        log.debug("Filter initialization starts");
        access.add("create_user");
        access.add("delete_user");
        access.add("x_report");
        access.add("z_report");
        access.add("create_category");
        access.add("create_product");
        access.add("edit_product");
        access.add("cancel_receipt");
        access.add("edit_receipt");
        access.add("cancel_product_from_receipt");
        access.add("all_products_analytics");
        access.add("analytics_by_cashier_per_day");

        commons.add("payments");
        commons.add("all_users");
        commons.add("refactor_user");
        commons.add("initialize_work_station");
        commons.add("add_to_basket");
        commons.add("remove_from_basket");
        commons.add("clear_basket");
        commons.add("pay_basket");
        commons.add("pay_basket");
        commons.add("print_receipt");

        outOfControl.add("login");
        outOfControl.add("logout");
        log.debug("Filter initialization finished");
    }

}
package cashier.servlet;

import cashier.Path;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  30.09.2020
 * email : hryniuk.t@gmail.com
 */
public class GetAllUsersCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(GetAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        Role userRole = Role.getRole((User) session.getAttribute("user"));
        LOGGER.trace("userRole --> " + userRole);

        if (userRole == Role.CASHIER || userRole == Role.HIGH_CASHIER) {
            errorMessage = "User don't have permissions";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        Integer page = null;
        if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
            page = 0;
        else
            page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

        request.setAttribute("users", new UserDaoImpl().getAllUsers(page));
        Integer count = new UserDaoImpl().getAllUsersCount() / 20;

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= count + 1; i++) {
            list.add(i);
        }

        System.out.println(list.toString());
        request.setAttribute("count", list);

//        if (userRole == Role.MANAGER)
        forward = Path.PAGE_ALL_USERS;


        LOGGER.debug("Command finished");
        return forward;
    }

}

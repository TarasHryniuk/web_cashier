package cashier.servlet;

import cashier.Path;
import cashier.dao.UserDaoImpl;
import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.util.StringHelpers;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Taras Hryniuk, created on  30.09.2020
 * email : hryniuk.t@gmail.com
 */
public class CreateUserCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(CreateUserCommand.class);

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

        User user = new User();

        user.setLogin(request.getParameter("login"));
        user.setAuthCode(StringHelpers.digest(request.getParameter("login") + request.getParameter("password")));
        user.setRole(Integer.parseInt(request.getParameter("role")));
        user.setFullName(request.getParameter("full.name"));
        user.setTerminalId(Integer.parseInt(request.getParameter("terminal.id")));

        if(!new UserDaoImpl().insertUser(user)){
            errorMessage = "Something went wrong....";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        forward = Path.PAGE_USER;


        LOGGER.debug("Command finished");
        return forward;
    }

}

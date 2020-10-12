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
public class EditUserCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(EditUserCommand.class);
    private UserDaoImpl userDao;

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

        userDao = new UserDaoImpl();

        String login = request.getParameter("refactor_user_login");
        String password = request.getParameter("password");
        User user = userDao.getUserByLogin(login);

        if (!StringHelpers.isNullOrEmpty(password)) {
            user.setAuthCode(StringHelpers.digest(login + password));
            try {
                userDao.changePasswordUserByLogin(user);
            } catch (Exception e) {
                errorMessage = "Something went wrong....";
                request.setAttribute("errorMessage", errorMessage);
                LOGGER.error("errorMessage --> " + errorMessage);
                return forward;
            }
        }

        try {
            if (request.getParameter("command").equals("refactor_user")) {
                user.setActive(Boolean.parseBoolean(request.getParameter("active")));
                user.setRole(Integer.parseInt(request.getParameter("role")));
                user.setFullName(request.getParameter("full.name"));

                if (!userDao.updateUserByLogin(user)) {
                    errorMessage = "Something went wrong....";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }

            } else if (request.getParameter("command").equals("delete_user")) {

                if (!userDao.deleteUserByLogin(user)) {
                    errorMessage = "Something went wrong....";
                    request.setAttribute("errorMessage", errorMessage);
                    LOGGER.error("errorMessage --> " + errorMessage);
                    return forward;
                }

            }
        } catch (Exception e) {
            errorMessage = "Something went wrong....";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }


        forward = Path.SUCCESS;


        LOGGER.debug("Command finished");
        return forward;
    }

}

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
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Login command.
 *
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public class LoginCommand extends Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = new UserDaoImpl().getUserByLogin(login);
        log.trace("Found in DB: user --> " + user);

        if (user == null) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        if (!user.isActive()) {
            errorMessage = "User isn't active";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(login).append(password);
        if (!user.getAuthCode().equals(StringHelpers.digest(sb.toString()))) {
            errorMessage = "Auth failed";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        Role userRole = Role.getRole(user);
        log.trace("userRole --> " + userRole);

        String terminalId = request.getParameter("terminal.id");
        if (userRole == Role.CASHIER && null == terminalId){
            errorMessage = "Cashier must have terminal id";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        if (userRole == Role.CASHIER)
            forward = Path.PAGE_CASHIER_MENU;

        if (userRole == Role.HIGH_CASHIER)
            forward = Path.PAGE_HEIGHT_CASHIER_MENU;

        if (userRole == Role.MANAGER)
            forward = Path.PAGE_MANAGER_MENU;

        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);

        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

//         work with i18n
			String userLocaleName = user.getLocaleName();
			log.trace("userLocalName --> " + userLocaleName);

			if (userLocaleName != null && !userLocaleName.isEmpty()) {
				Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

				session.setAttribute("defaultLocale", userLocaleName);
				log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

				log.info("Locale for user: defaultLocale --> " + userLocaleName);
			}


        log.debug("Command finished");
        return forward;
    }

}
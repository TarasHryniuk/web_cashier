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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Hryniuk, created on  30.09.2020
 * email : hryniuk.t@gmail.com
 */
public class GetAllUsersCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(GetAllUsersCommand.class);
    private UserDaoImpl userDao;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();
        String forward;

        Role userRole = Role.getRole((User) session.getAttribute("user"));
        LOGGER.trace("userRole --> " + userRole);

        userDao = new UserDaoImpl();

        if (userRole == Role.CASHIER || userRole == Role.HIGH_CASHIER) {
            List<User> listUsers = new ArrayList<>();
            listUsers.add(userDao.getUserByLogin(((User) session.getAttribute("user")).getLogin()));

            request.setAttribute("users", listUsers);

            List<Integer> list = new ArrayList<>();
            list.add(1);
            request.setAttribute("count", list);
        } else {
            Integer page = null;
            if (null == request.getParameter("page") || 1 == Integer.parseInt(request.getParameter("page")))
                page = 0;
            else
                page = (Integer.parseInt(request.getParameter("page")) - 1) * 20;

            request.setAttribute("users", userDao.getAllUsers(page));
            Integer count = userDao.getAllUsersCount() / 20;

            List<Integer> list = new ArrayList<>();

            for (int i = 0; i <= count; i++) {
                list.add(i + 1);
            }

            request.setAttribute("count", list);

        }

        forward = Path.PAGE_ALL_USERS;

        LOGGER.debug("Command finished");
        return forward;
    }

}

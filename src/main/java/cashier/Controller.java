package cashier;

import cashier.servlet.Command;
import cashier.servlet.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 * 
 * @author D.Kolesnikov
 * 
 */
public class Controller extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		LOGGER.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter("command");
		LOGGER.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOGGER.trace("Obtained command --> " + command);

		// execute command and get forward address
		String forward = command.execute(request, response);
		LOGGER.trace("Forward address --> " + forward);

		LOGGER.debug("Controller finished, now go to forward address --> " + forward);

		// if the forward address is not null go to the address
		if (forward != null) {
			RequestDispatcher disp = request.getRequestDispatcher(forward);
			disp.forward(request, response);
		}
	}

}
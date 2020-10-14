package cashier.servlet;

import cashier.Path;
import cashier.dao.entity.Role;
import cashier.util.StringHelpers;
import org.apache.log4j.Logger;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Disabled by default. Uncomment Security filter
 *  section in web.xml to enable.
 * @author Taras Hryniuk, created on  19.09.2020
 * email : hryniuk.t@gmail.com
 */
public class CommandAccessFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

	// commands access	
	private static Map<Role, List<String>> accessMap = new HashMap<>();
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
			String errorMessasge = "You do not have permission to access the requested resource";
			
			request.setAttribute("errorMessage", errorMessasge);
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
					.forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (StringHelpers.isNullOrEmpty(commandName)) return false;
		
		if (outOfControl.contains(commandName)) return true;
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) 
			return false;
		
		Role userRole = (Role)session.getAttribute("userRole");
		if (userRole == null) return false;
		
		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.debug("Filter initialization starts");
		
		// roles
		accessMap.put(Role.CASHIER, asList(fConfig.getInitParameter("cashier")));
		accessMap.put(Role.HIGH_CASHIER, asList(fConfig.getInitParameter("high_cashier")));
		accessMap.put(Role.MANAGER, asList(fConfig.getInitParameter("manager")));
		log.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);
		
		log.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;		
	}
	
}
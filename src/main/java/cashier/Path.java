package cashier;

/**
 * Path holder (jsp pages, controller commands).
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public final class Path {
	
	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ERROR_PAGE = "/error_page.jsp";
	public static final String PAGE_MENU = "/main_menu.jsp";
	public static final String PAGE_ALL_USERS = "/all_users.jsp";
	public static final String PAGE_PAYMENTS = "/payments.jsp";
	public static final String PAGE_DOCUMENTS = "/WEB-INF/views/parts/doc.jspf";

	// commands
	public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";

}
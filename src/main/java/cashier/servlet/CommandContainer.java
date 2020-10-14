package cashier.servlet;

import cashier.servlet.cashierCommands.InitializeCashierWorkStationCommand;
import cashier.servlet.cashierCommands.ProcessingReceiptCommand;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 * @author Taras Hryniuk, created on  21.09.2020
 * email : hryniuk.t@gmail.com
 */
public class CommandContainer {
	
	private static final Logger LOGGER = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("payments", new PaymentsCommand());
		commands.put("users", new UsersCommand());
		commands.put("create_user", new CreateUserCommand());
		commands.put("all_users", new GetAllUsersCommand());
		commands.put("refactor_user", new EditUserCommand());
		commands.put("delete_user", new EditUserCommand());
		commands.put("x_report", new DocumentsCommand());
		commands.put("z_report", new DocumentsCommand());
		commands.put("initialize.work.station", new InitializeCashierWorkStationCommand());
		commands.put("add_to_basket", new InitializeCashierWorkStationCommand());
		commands.put("remove_from_basket", new InitializeCashierWorkStationCommand());
		commands.put("clear_basket", new InitializeCashierWorkStationCommand());
		commands.put("create_category", new InitializeCashierWorkStationCommand());
		commands.put("create_product", new InitializeCashierWorkStationCommand());
		commands.put("refactor_product", new InitializeCashierWorkStationCommand());
		commands.put("pay_basket", new ProcessingReceiptCommand());
		commands.put("cancel_receipt", new ProcessingReceiptCommand());
		commands.put("edit_receipt", new ProcessingReceiptCommand());
		commands.put("cancel_product_from_receipt", new ProcessingReceiptCommand());
		commands.put("print_receipt", new PrintBillCommand());
		commands.put("all_products_analytics", new PrintAnalyticsCommand());
		commands.put("analytics_by_cashier_per_day", new PrintAnalyticsCommand());

		LOGGER.debug("Command container was successfully initialized");
		LOGGER.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOGGER.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}

		return commands.get(commandName);
	}
	
}
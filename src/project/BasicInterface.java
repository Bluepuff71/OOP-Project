package project;

import runner.Runner;

import java.util.InputMismatchException;

/**
 * Base class that all other UI interfaces can use
 */
public abstract class BasicInterface {

    /**
     * The login manager to be used by the interface
     *
     * @see LoginManager
     */
    protected LoginManager loginManager;

    /**
     * The storefront that the customer will browse
     * @see InventoryManager
     */
    protected InventoryManager inventoryManager;

    /**
     * The super constructor for all interfaces
     *
     * @param loginManager     the login manager to use
     * @param inventoryManager the inventory manager to use
     * @see LoginManager
     * @see InventoryManager
     */
    public BasicInterface(LoginManager loginManager, InventoryManager inventoryManager) {
        this.loginManager = loginManager;
        this.inventoryManager = inventoryManager;
    }

    /**
     * The main menu for all interfaces
     */
    public abstract void mainInterface();

    /**
     * The menu that allows a user to create an account
     *
     * @return the account that was created
     */
    public abstract Account createAccount();

    /**
     * Prints a dialog with yes or no options
     *
     * @param text the yes/no question to ask
     * @return true if yes, false if no
     */
    public static boolean yesNoDialog(String text) {
        while (true) {
            System.out.print(text);
            String selection = Runner.scanner.nextLine();
            if (selection.toLowerCase().equals("y")) {
                return true;
            } else if (selection.toLowerCase().equals("n")) {
                return false;
            } else {
                System.out.println("That is not an option");
            }
        }
    }
}


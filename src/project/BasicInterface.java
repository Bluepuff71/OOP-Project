package project;

import runner.Runner;

import java.util.InputMismatchException;

public abstract class BasicInterface {

    protected LoginManager loginManager;

    /**
     * The storefront that the customer will browse
     */
    protected InventoryManager inventoryManager;

    public BasicInterface(LoginManager loginManager, InventoryManager inventoryManager) {
        this.loginManager = loginManager;
        this.inventoryManager = inventoryManager;
    }

    protected void logout() {
        loginManager.logOutCurrentUser();
    }

    public abstract void mainInterface();

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


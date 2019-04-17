package project;

import runner.Runner;

import java.util.InputMismatchException;

public final class SupplierInterface extends BasicInterface {

    //TODO finish implementation

    private Supplier currentSupplier;

    public SupplierInterface(LoginManager loginManager) {
        super(loginManager);
        currentSupplier = (Supplier) super.currentAccount;
    }

    @Override
    protected void mainInterface() {
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Process Orders");
            System.out.println("[2] Logout");
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine(); //catches the \n not caught by nextInt()
            switch (selection) {
                case 1:
                    //Process Order
                    break;
                case 5:
                    logout();
                    System.out.println("You have been logged out.");
                    return;
                default:
                    System.out.println("That is not an option.\nPlease try again.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("That is not an option.\nPlease try again.");
            Runner.scanner.nextLine();
        }
        mainInterface();
    }

    @Override
    protected Supplier createAccount() {
        String username = "", plainText = "", error;
        while (true) {
            if (username.equals("") || plainText.equals("")) {
                error = "Error: One or more fields have not been completed.\n";
            } else if (super.loginManager.usernameTaken(username)) {
                error = "Error: That username is taken\n";
            } else if (username.contains(" ")) {
                error = "Error: Username contains invalid characters\n";
            } else if (plainText.contains(" ")) {
                error = "Error: Password contains invalid characters\n";
            } else {
                error = "";
            }
            System.out.println("---- New Supplier Account ----");
            System.out.printf("[1] Username [%s]\n", username);
            System.out.printf("[2] Password [%s]\n", plainText);
            if (error.equals("")) {
                System.out.println("[3] Create Account");
            }
            System.out.println("[4] Cancel");
            System.out.print(error);
            System.out.print("Please choose an option: ");
            try {
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                switch (selection) {
                    case 1:
                        System.out.print("Enter Username: ");
                        username = Runner.scanner.nextLine();
                        break;
                    case 2:
                        System.out.print("Enter Password: ");
                        plainText = Runner.scanner.nextLine();
                        break;
                    case 3:
                        if (error.equals("")) {
                            return new Supplier(username, plainText);
                        } else {
                            System.out.println("Account cannot be created while errors exist.");
                            break;
                        }
                    case 4:
                        return null;
                    default:
                        System.out.println("That is not an option.\nPlease try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option.\nPlease try again.");
                Runner.scanner.nextLine();
            }
        }
    }

    //wait off until you are sure what to do with this
    private void processDeliveryOrders() {

    }
}
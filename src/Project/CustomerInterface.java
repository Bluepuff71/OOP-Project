package Project;

import runner.Runner;

public final class CustomerInterface {

    /**
     * The current logged in customer
     */
    private Customer currentCustomer;

    /**
     * The login manager...
     */
    private LoginManager loginManager;

    /**
     * Creates a CustomerInterface with a specified loginManager
     *
     * @param loginManager the loginManager to use
     */
    public CustomerInterface(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    /**
     * The account step of the customer interface
     */
    public void initalMenu() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Login");
        System.out.println("[2] Create Account");
        System.out.println("[3] Go back");
        System.out.print("Please choose an option: ");
        switch (Runner.scanner.nextInt()) {
            case 1: //login
                this.login();
                this.mainInterface();
                break;
            case 2: //create account
                currentCustomer = this.createAccount("", "", "", "", "", "");
                //if the user cancelled account creation
                if (currentCustomer == null) {
                    initalMenu();
                } else {
                    System.out.println("Account has been created.");
                    this.mainInterface();
                }
                break;
            case 3:
                return;
            default:
                System.out.println("That is not an option.\nPlease try again");
        }
        initalMenu();
    }

    /**
     * The login menu
     */
    private void login() {
        try {
            System.out.println("---- Login ----");
            System.out.print("Username: ");
            String username = Runner.scanner.nextLine();
            System.out.print("Password: ");
            String plainText = Runner.scanner.nextLine();
            this.currentCustomer = loginManager.getCustomerAccount(username, plainText);
            return;
        } catch (NoAccountFoundException e) {
            System.out.println("An account with that username doesn't exist.");
        } catch (InvalidLoginException | InvalidAccountTypeException e) {
            System.out.println("That username/password combination is not correct.");
        }
        System.out.println("Would you like to try again? (Y,N): ");
        while (true) {
            if (Runner.scanner.nextLine().toLowerCase().equals("y")) {
                login();
            } else if (Runner.scanner.nextLine().toLowerCase().equals("n")) {
                return;
            } else {
                System.out.println("That is not an option\nPlease try again");
            }
        }
    }

    /**
     * Logs the current customer out
     */
    private void logout() {
        this.currentCustomer = null;
    }

    /**
     * The main menu of the customer interface
     */
    private void mainInterface() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Browse Catalog");
        System.out.println("[2] View Orders");
        System.out.println("[3] Logout");
        System.out.print("Please choose an option: ");
        switch (Runner.scanner.nextInt()) {

        }
    }

    /**
     * Creates a new customer account and logs it in (Defaults should be "")
     *
     * @param username    the default username
     * @param plainText   the default plaintext password
     * @param phoneNumber the default phone number
     * @param address     the default address
     * @param cardNumber  the default card number
     * @param error       the default error
     * @return the new customer or null if the user backs out
     */
    private Customer createAccount(String username, String plainText, String phoneNumber, String address, String cardNumber, String error) {
        if (username.equals("") || plainText.equals("") || phoneNumber.equals("") || address.equals("") || cardNumber.equals("")) {
            error = "Error: One or more fields have not been completed.\n";
        } else {
            error = "";
        }
        System.out.println("---- New Customer Account ----");
        System.out.printf("[1] Name [%s]\n", username);
        System.out.printf("[2] Password [%s]\n", plainText);
        System.out.printf("[3] Phone # [%s]\n", phoneNumber);
        System.out.printf("[4] Address [%s]\n", address);
        System.out.printf("[5] Card # [%s]\n", cardNumber);
        if (error.equals("")) {
            System.out.println("[6] Create Account");
        } else {
            System.out.println(error);
            System.out.println("All errors must be fixed before account can be created.");
        }
        System.out.println("[7] Cancel");
        System.out.println("Please choose an option.");
        try {
            switch (Runner.scanner.nextInt()) {
                case 1:
                    System.out.print("Enter Name: ");
                    username = Runner.scanner.nextLine();
                    break;
                case 2:
                    System.out.print("Enter Password: ");
                    plainText = Runner.scanner.nextLine();
                    break;
                case 3:
                    while (true) {
                        System.out.print("Enter Phone #: ");
                        phoneNumber = Runner.scanner.nextLine();
                        if (phoneNumber.length() != 10) {
                            System.out.println("Phone numbers can only be 10 characters long.\nPlease Try Again.");
                        } else {
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter Address: ");
                    address = Runner.scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter Card Number: ");
                    cardNumber = Runner.scanner.nextLine();
                    break;
                case 6:
                    if (error.equals("")) {
                        return new Customer(username, plainText, phoneNumber, address, new Card(cardNumber, 1000));
                    }
                case 7:
                    return null;
                default:
                    System.out.println("That is not an option.\nPlease try again.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("There was an error.\nPlease try again.");
        }
        return createAccount(username, plainText, phoneNumber, address, cardNumber, error);
    }
}

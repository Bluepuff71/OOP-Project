package project;

import runner.Runner;

import java.util.InputMismatchException;

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
     * The storefront that the customer will browse
     */
    private InventoryManager inventoryManager;

    /**
     * Creates a CustomerInterface with a specified loginManager
     *
     * @param loginManager the loginManager to use
     */
    public CustomerInterface(LoginManager loginManager, InventoryManager inventoryManager) {
        this.loginManager = loginManager;
        this.inventoryManager = inventoryManager;
    }

    /**
     * The account step of the customer interface
     */
    public void initialMenu() {
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Login");
            System.out.println("[2] Create Account");
            System.out.println("[3] Go back");
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            switch (selection) {
                case 1: //login
                    login();
                    break;
                case 2: //create account
                    currentCustomer = createAccount("", "", "", "", "");
                    //if the user didn't cancel account creation
                    if (currentCustomer != null) {
                        loginManager.addCustomerAccount(currentCustomer);
                        System.out.println("Account has been created.");
                        mainInterface();
                    } else {
                        System.out.println("Account creation has been cancelled.\nReturning to menu.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("That is not an option.\nPlease try again");
                    break;
            }
        } catch (UsernameTakenException e) {
            System.out.println("THIS ERROR SHOULDN'T BE POSSIBLE");
        } catch (InputMismatchException e) {
            System.out.println("That is not an option.\nPlease try again");
        }
        initialMenu();
    }

    /**
     * The login menu
     */
    private void login() {
        try {
            System.out.println("---- Login ----");
            System.out.print("Username: ");
            String username = Runner.scanner.nextLine();//grab input
            System.out.print("Password: ");
            String plainText = Runner.scanner.nextLine(); //grab input
            currentCustomer = loginManager.getCustomerAccount(username, plainText); //get the customer from the login manager
            mainInterface();
        } catch (NoAccountFoundException e) {
            System.out.println("Account doesn't exist");
            System.out.println("Would you like to try again? (Y,N): ");
            if (yesNoDialog()) {
                this.login();
            } else {
                currentCustomer = null;
            }
        } catch (InvalidLoginException | InvalidAccountTypeException e) {
            System.out.println("That username/password combination is not correct.");
            System.out.println("Would you like to try again? (Y,N): ");
            if (yesNoDialog()) {
                this.login();
            } else {
                currentCustomer = null;
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
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Browse Catalog");
            System.out.println("[2] Checkout");
            System.out.println("[3] View Cart");
            System.out.println("[4] View Orders");
            System.out.println("[5] Logout");
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine(); //catches the \n not caught by nextInt()
            switch (selection) {
                case 1:
                    selectItemInterface();
                    break;
                case 2:
                    if (currentCustomer.getCart().isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        checkOutInterface();
                    }
                    break;
                case 3:
                    if (currentCustomer.getCart().isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        printCart();
                    }
                    break;
                case 4:
                    if (currentCustomer.getOrders().isEmpty()) {
                        System.out.println("You have not placed any orders.");
                    } else {
                        printOrdersInterface();
                    }
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
        }
        mainInterface();
    }

    /**
     * The interface for doing checkout on a cart
     */
    private void checkOutInterface() {
        System.out.println("----------[ Checkout ]-----------");
        printCart();
        System.out.println("---------------------------------");
        System.out.printf("Total Cost: %.2f\n", currentCustomer.getCartTotalCost());
        System.out.println("-----[ Payment ]-----");
        System.out.printf("Card #: XXXX XXXX XXXX %s\n", currentCustomer.getCard().getLastFourDigits());
        System.out.println("Confirm Payment? (Y,N) :");
        if (yesNoDialog()) {
            //confirm
            while (true) {
                try {
                    System.out.println("Verifying Payment Method...");
                    currentCustomer.addOrder(inventoryManager.createOrderRequest(currentCustomer));
                    break;
                } catch (InvalidCardException e) {
                    System.out.println("Your card number is invalid.");
                } catch (CreditLimitException e) {
                    System.out.println("There is not enough credit remaining on the specified card.");
                }
                System.out.print("Would you like to input a different card? (Y,N): ");
                if (yesNoDialog()) {
                    System.out.print("Enter new Card Number: ");
                    String cardNumber = Runner.scanner.nextLine();
                    currentCustomer.getCard().setNumber(cardNumber);
                }
            }
        } else {
            //cancel
            System.out.println("Order Cancelled");
        }
    }

    /**
     * Interface for users to select items from the store
     */
    private void selectItemInterface() {
        System.out.println("----------[ StoreFront ]----------");
        System.out.print(this.inventoryManager.toString());
        System.out.printf("[%d] Go Back\n", this.inventoryManager.getInventorySize() + 1);
        System.out.print("Type the item you want to add to your cart: ");
        int selectedItemID = Runner.scanner.nextInt();
        Runner.scanner.nextLine();
        if (selectedItemID == this.inventoryManager.getInventorySize() + 1) {
            return;
        }
        Shipment selectedShipment = this.inventoryManager.getShipmentFromInventory(selectedItemID - 1);
        int amount = 1;
        do {
            if (amount < 1) {
                System.out.println("Amount to add must be greater than 0");
            }
            System.out.printf("-----[ Selected Item: %s ]-----\n", selectedShipment.getItem().getName());
            System.out.print("How many would you like to add to your cart?: ");
            amount = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
        } while (amount < 1);
        currentCustomer.addToCart(selectedShipment.getItem(), amount);
        System.out.printf("%d %s added to cart.\n", amount, selectedShipment.getItem().getName());
        selectItemInterface();
    }

    /**
     * Creates a new customer account and logs it in (Defaults should be "")
     *
     * @param username    the default username
     * @param plainText   the default plaintext password
     * @param phoneNumber the default phone number
     * @param address     the default address
     * @param cardNumber  the default card number
     * @return the new customer or null if the user backs out
     */
    private Customer createAccount(String username, String plainText, String phoneNumber, String address, String cardNumber) {
        String error;
        if (username.equals("") || plainText.equals("") || phoneNumber.equals("") || address.equals("") || cardNumber.equals("")) {
            error = "Error: One or more fields have not been completed.\n";
        } else if (cardNumber.length() != 16) {
            error = "Error: Card number must be exactly 16 numbers long\n";
        } else if (cardNumber.matches("[^0-9]")) {
            error = "Error: Card number may only contain numbers\n";
        } else if (phoneNumber.length() != 10) {
            error = "Error: Phone numbers can only be 10 characters long.\n";
        } else if (phoneNumber.matches("[^0-9]")) {
            error = "Error: Phone number may only contain numbers\n";
        } else if (loginManager.usernameTaken(username)) {
            error = "Error: That username is taken\n";
        } else if (username.contains(" ")) {
            error = "Error: Username contains invalid characters\n";
        } else {
            error = "";
        }
        System.out.println("---- New Customer Account ----");
        System.out.printf("[1] Username [%s]\n", username);
        System.out.printf("[2] Password [%s]\n", plainText);
        System.out.printf("[3] Phone # [%s]\n", phoneNumber);
        System.out.printf("[4] Address [%s]\n", address);
        System.out.printf("[5] Card # [%s]\n", cardNumber);
        if (error.equals("")) {
            System.out.println("[6] Create Account");
        }
        System.out.println("[7] Cancel");
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
                    System.out.print("Enter Phone #: ");
                    phoneNumber = Runner.scanner.nextLine();
                    phoneNumber = phoneNumber.replace(" ", "");
                    phoneNumber = phoneNumber.replace("-", "");
                    break;
                case 4:
                    System.out.print("Enter Address: ");
                    address = Runner.scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter Card Number: ");
                    cardNumber = Runner.scanner.nextLine();
                    cardNumber = cardNumber.replace(" ", "");
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
        return createAccount(username, plainText, phoneNumber, address, cardNumber);
    }

    /**
     * Prints a dialog with yes or no options
     *
     * @return true if yes, false if no
     */
    private boolean yesNoDialog() {
        while (true) {
            String selection = Runner.scanner.nextLine();
            if (selection.toLowerCase().equals("y")) {
                return true;
            } else if (selection.toLowerCase().equals("n")) {
                return false;
            } else {
                System.out.println("That is not an option\nPlease try again");
            }
        }
    }

    /**
     * Prints the customer's cart
     */
    private void printCart() {
        System.out.println("----------[ Cart ]----------");
        for (Shipment shipment : currentCustomer.getCart()) {
            System.out.printf("%d %s - $%.2f\n", shipment.getAmount(), shipment.getItem().getName(), shipment.getItem().getPrice());
        }
        System.out.println("----------------------------");
    }

    /**
     * Prints the customer's orders to the screen
     */
    private void printOrdersInterface() {
        while (true) {
            System.out.println("----------[ Orders ]----------");
            int i = 1;
            for (Order order : currentCustomer.getOrders()) {
                System.out.printf("[%d] Order %d - %s\n", i, i, order.getOrderStatus().toString());
                i++;
            }
            System.out.printf("[%d] Go back\n", i);
            System.out.print("Which order would you like to view?: ");
            if (Runner.scanner.hasNextInt()) {
                int selection = Runner.scanner.nextInt();
                //If they selected go back
                if (selection == i) {
                    return;
                } else if (selection > i || selection < 1) {
                    System.out.println("That is not an option");
                } else {
                    i = 1;
                    System.out.printf("----------[ Order %d ]----------\n", selection);
                    Order selectedOrder = currentCustomer.getOrders().get(selection);
                    for (Shipment shipment : selectedOrder.getOrderedItems()) {
                        System.out.printf("Item %d: %s - Amount: %d\n", i, shipment.getItem().getName(), shipment.getAmount());
                    }
                    System.out.printf("\nOrder Status: %s", selectedOrder.getOrderStatus().toString());
                    System.out.println("--------------------------------");
                    System.out.print("Press enter when you are done viewing.");
                    Runner.scanner.nextLine();
                }
            } else {
                System.out.println("That is not an option");
            }
        }
    }
}

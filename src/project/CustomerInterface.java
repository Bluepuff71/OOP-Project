package project;

import runner.Runner;

import java.util.InputMismatchException;

public final class CustomerInterface extends BasicInterface {

    //TODO add lambda functions for any search


    private Customer currentCustomer;


    public CustomerInterface(LoginManager loginManager, InventoryManager inventoryManager) {
        super(loginManager, inventoryManager);
        this.currentCustomer = (Customer) super.currentAccount;
    }

    /**
     * The main menu of the customer interface
     */
    @Override
    protected void mainInterface() {
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Browse Catalog");
            System.out.println("[2] Checkout");
            System.out.println("[3] View/Edit Cart");
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
                        editCartInterface();
                    }
                    break;
                case 4:
                    if (currentCustomer.getOrders().isEmpty()) {
                        System.out.println("You have not placed any orders.");
                    } else {
                        ordersInterface();
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
            Runner.scanner.nextLine();
        }
        mainInterface();
    }

    /**
     * The interface for doing checkout on a cart
     */
    private void checkOutInterface() {
        System.out.println("----------[ Checkout ]-----------");
        printCart();
        System.out.printf("Total Cost: $%.2f\n", ((Customer) super.currentAccount).getCartTotalCost());
        System.out.println("-----[ Payment ]-----");
        System.out.printf("Card #: XXXX XXXX XXXX %s\n", currentCustomer.getCard().getLastFourDigits());
        if (yesNoDialog("Confirm Payment? (Y,N) : ")) {
            //confirm
            while (true) {
                try {
                    System.out.println("Verifying Payment Method...");
                    currentCustomer.addOrder(inventoryManager.createOrderRequest(currentCustomer));
                    System.out.println("Payment Completed");
                    currentCustomer.getCart().clear();
                    break;
                } catch (InvalidCardException e) {
                    System.out.println("Your card number is invalid.");
                } catch (CreditLimitException e) {
                    System.out.println("There is not enough credit remaining on the specified card.");
                }
                if (yesNoDialog("Would you like to input a different card? (Y,N): ")) {
                    System.out.print("Enter new card number: ");
                    String cardNumber = Runner.scanner.nextLine();
                    currentCustomer.getCard().setNumber(cardNumber);
                }
            }
        } else {
            //cancel
            System.out.println("Order Cancelled");
        }
        System.out.println("---------------------------------");
    }

    /**
     * Interface for users to select items from the store
     */
    private void selectItemInterface() {
        try {
            System.out.println("----------[ StoreFront ]----------");
            System.out.print(this.inventoryManager.toString());
            System.out.println("----------------------------------");
            System.out.printf("[%d] Go Back\n", this.inventoryManager.getInventorySize() + 1);
            System.out.print("Please type your selection: ");
            int selectedItemID = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            if (selectedItemID == this.inventoryManager.getInventorySize() + 1) {
                System.out.println("----------------------------------");
                return;
            }
            Shipment selectedShipment = this.inventoryManager.getShipmentFromInventory(selectedItemID - 1);
            boolean amountSet = false;
            int amount = 1;
            do {
                System.out.printf("-----[ Selected Item: %s ]-----\n", selectedShipment.getItem().getName());
                System.out.print("How many would you like to add to your cart?: ");
                try {
                    amount = Runner.scanner.nextInt();
                    Runner.scanner.nextLine();
                    if (amount < 1) {
                        System.out.println("Amount to add must be greater than 0");
                    } else {
                        amountSet = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("That is not an option.");
                    Runner.scanner.nextLine();
                }
            } while (amount < 1 || !amountSet);
            currentCustomer.addToCart(selectedShipment.getItem(), amount);
            System.out.printf("%d %s added to cart.\n", amount, selectedShipment.getItem().getName());
        } catch (InputMismatchException e) {
            System.out.println("That is not an option.\nPlease try again.");
            Runner.scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("That is not an option.\nPlease try again.");
        }
        selectItemInterface();
    }


    /**
     * Creates a new customer account and logs it in (Defaults should be "")
     *
     * @return the new customer or null if the user backs out
     */
    @Override
    protected Customer createAccount() {
        String username = "", plainText = "", phoneNumber = "", address = "", cardNumber = "", formattedPhoneNumber = "", formattedCardNumber = "", error;
        while (true) {
            if (username.equals("") || plainText.equals("") || phoneNumber.equals("") || address.equals("") || cardNumber.equals("")) {
                error = "Error: One or more fields have not been completed.\n";
            } else if (super.loginManager.usernameTaken(username)) {
                error = "Error: That username is taken\n";
            } else if (username.contains(" ")) {
                error = "Error: Username contains invalid characters\n";
            } else if (plainText.contains(" ")) {
                error = "Error: Password contains invalid characters\n";
            } else if (phoneNumber.matches(".*\\D.*")) {
                error = "Error: Phone number may only contain numbers\n";
            } else if (phoneNumber.length() != 10) {
                error = "Error: Phone numbers can only be 10 characters long.\n";
            } else if (cardNumber.matches(".*\\D.*")) {
                error = "Error: Card number may only contain numbers\n";
            } else if (cardNumber.length() != 16) {
                error = "Error: Card number must be exactly 16 numbers long\n";
            } else {
                error = "";
            }
            System.out.println("---- New Customer Account ----");
            System.out.printf("[1] Username [%s]\n", username);
            System.out.printf("[2] Password [%s]\n", plainText);
            System.out.printf("[3] Phone # [%s]\n", formattedPhoneNumber);
            System.out.printf("[4] Address [%s]\n", address);
            System.out.printf("[5] Card # [%s]\n", formattedCardNumber);
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
                        if (phoneNumber.length() != 10) {
                            formattedPhoneNumber = phoneNumber;
                            break;
                        } else {
                            formattedPhoneNumber = "";
                            phoneNumber = phoneNumber.replace(" ", "");
                            phoneNumber = phoneNumber.replace("-", "");
                            for (int i = 0; i < 2; i++) {
                                formattedPhoneNumber += phoneNumber.substring(i * 3, (i * 3) + 3) + "-";
                            }
                            formattedPhoneNumber += phoneNumber.substring(6);
                            break;
                        }
                    case 4:
                        System.out.print("Enter Address: ");
                        address = Runner.scanner.nextLine();
                        break;
                    case 5:
                        System.out.print("Enter Card Number: ");
                        cardNumber = Runner.scanner.nextLine();
                        if (cardNumber.length() != 16) {
                            formattedCardNumber = cardNumber;
                            break;
                        } else {
                            formattedCardNumber = "";
                            cardNumber = cardNumber.replace(" ", "");
                            for (int i = 0; i < 3; i++) {
                                formattedCardNumber += cardNumber.substring(i * 4, (i * 4) + 4) + " ";
                            }
                            formattedCardNumber += cardNumber.substring(12);
                            break;
                        }
                    case 6:
                        if (error.equals("")) {
                            return new Customer(username, plainText, phoneNumber, address, new Card(cardNumber, 1000));
                        } else {
                            System.out.println("Account cannot be created while errors exist.");
                            break;
                        }

                    case 7:
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

    /**
     * Prints the customer's cart
     */
    private void editCartInterface() {
        try {
            printCart();
            System.out.println("Cart Options:");
            System.out.println("[1] Remove Item");
            System.out.println("[2] Empty Cart");
            System.out.println("[3] Go Back");
            System.out.println("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            switch (selection) {
                case 1:
                    removeFromCartInterface();
                    if (currentCustomer.getCart().isEmpty()) {
                        System.out.println("The cart is now empty returning to menu");
                        return;
                    }
                    break;
                case 2:
                    currentCustomer.getCart().clear();
                    return;
                case 3:
                    return;
                default:
                    System.out.println("That is not an option.\nPlease try again.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("That is not an option.\nPlease try again.");
            Runner.scanner.nextLine();
        }
        editCartInterface();
    }

    /**
     * Interface for removing from the cart
     */
    private void removeFromCartInterface() {
        System.out.println("----[ Remove From Cart ]----");
        int i = 1;
        for (Shipment cartItem : currentCustomer.getCart()) {
            System.out.printf("[%d] %d %s - $%.2f\n", i, cartItem.getAmount(), cartItem.getItem().getName(), cartItem.getItem().getPrice());
            i++;
        }
        System.out.println("----------------------------");
        System.out.printf("[%d] Go Back\n", currentCustomer.getCart().size() + 1);
        System.out.println("Please choose an option: ");
        try {
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            if (selection != currentCustomer.getCart().size() + 1) {
                Shipment selectedCartItem = currentCustomer.getCart().get(selection - 1);
                while (true) {
                    System.out.printf("-----[ Selected Item: %s ]-----\n", selectedCartItem.getItem().getName());
                    System.out.println("[1] Remove Amount");
                    System.out.println("[2] Remove All");
                    System.out.println("[3] Go Back");
                    try {
                        int choice = Runner.scanner.nextInt();
                        Runner.scanner.nextLine();
                        switch (choice) {
                            case 1:
                                while (true) {
                                    try {
                                        System.out.printf("-----[ Selected Item: %s - Amount: %d ]-----\n", selectedCartItem.getItem().getName(), selectedCartItem.getAmount());
                                        System.out.print("How many would you like to remove: ");
                                        int amountToRemove = Runner.scanner.nextInt();
                                        Runner.scanner.nextLine();
                                        if (amountToRemove < 1) {
                                            System.out.println("The amount to remove must be greater than 0.\nPlease try again.");
                                        } else if (amountToRemove > selectedCartItem.getAmount()) {
                                            System.out.println("The amount to remove can't be larger than the amount in the cart.\nPlease try again.");
                                        } else {
                                            System.out.printf("%d %s removed from cart\n", amountToRemove, selectedCartItem.getItem().getName());
                                            currentCustomer.removeFromCart(selectedCartItem.getItem(), amountToRemove);
                                            break;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("That is not a valid amount.\nPlease try again.");
                                        Runner.scanner.nextLine();
                                    }
                                }
                                break;
                            case 2:
                                System.out.printf("%s removed from cart\n", selectedCartItem.getItem().getName());
                                currentCustomer.removeFromCart(selectedCartItem.getItem());
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("That is not an option.\nPlease try again.");
                                continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("That is not an option.\nPlease try again.");
                        Runner.scanner.nextLine();
                        continue;
                    }
                    break;
                }
                if (currentCustomer.getCart().isEmpty()) {
                    return;
                }
            } else {
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("That is not an option.\nPlease try again.");
            Runner.scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("That is not an option.\nPlease try again.");
        }
        removeFromCartInterface();
    }

    /**
     * Prints the cart to the screen
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
    private void ordersInterface() {
        while (true) {
            System.out.println("----------[ Orders ]----------");
            int i = 1;
            for (Order order : currentCustomer.getOrders()) {
                System.out.printf("[%d] Order %d - %s\n", i, i, order.getOrderStatus().toString());
                i++;
            }
            System.out.printf("[%d] Go back\n", i);
            System.out.print("Which order would you like to view?: ");
            try {
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                //If they selected go back
                if (selection == i) {
                    return;
                } else if (selection > i || selection < 1) {
                    System.out.println("That is not an option");
                } else {
                    System.out.printf("----------[ Order %d ]----------\n", selection);
                    System.out.println(currentCustomer.getOrders().get(selection - 1));
                    System.out.println("--------------------------------");
                    System.out.println("Press enter when you are done viewing.");
                    Runner.scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option");
                Runner.scanner.nextLine();
            }
        }
    }
}

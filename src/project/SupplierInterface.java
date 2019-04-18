package project;

import runner.Runner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public final class SupplierInterface extends BasicInterface {

    public SupplierInterface(LoginManager loginManager, InventoryManager inventoryManager) {
        super(loginManager, inventoryManager);
    }

    @Override
    protected void mainInterface() {
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Process Orders");
            System.out.println("[2] Confirm Shipments");
            System.out.println("[3] Create Inventory Order");
            System.out.println("[4] View all Orders");
            System.out.println("[5] Logout");
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine(); //catches the \n not caught by nextInt()
            switch (selection) {
                case 1:
                    processOrdersInterface();
                    break;
                case 2:
                    confirmShipmentsInterface();
                    break;
                case 3:
                    inventoryOrderInterface();
                    break;
                case 4:
                    if (!Supplier.getInventoryOrderList().isEmpty()) {
                        System.out.println("----------[ All Orders ]----------");
                        if (!getOrdersByStatus(Order.OrderStatus.Ordered).isEmpty()) {
                            System.out.println("------[Status: Ordered]------");
                            getOrdersByStatus(Order.OrderStatus.Ordered).forEach(order -> System.out.printf("User: %s - Number of Items: %d", order.getCustomerUsername(), order.getOrderedItems().size()));
                        }
                        if (!getOrdersByStatus(Order.OrderStatus.Ready).isEmpty()) {
                            System.out.println("------[Status: Ready]------");
                            getOrdersByStatus(Order.OrderStatus.Ready).forEach(order -> System.out.printf("User: %s - Number of Items: %d", order.getCustomerUsername(), order.getOrderedItems().size()));
                        }
                        if (!getOrdersByStatus(Order.OrderStatus.Shipped).isEmpty()) {
                            System.out.println("------[Status: Shipped]------");
                            getOrdersByStatus(Order.OrderStatus.Shipped).forEach(order -> System.out.printf("User: %s - Number of Items: %d", order.getCustomerUsername(), order.getOrderedItems().size()));
                        }
                        System.out.println("----------------------------------");
                        System.out.println("Press enter when you are finished");
                        Runner.scanner.nextLine();
                    } else {
                        System.out.println("No orders were found.");
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

    private void processOrdersInterface() {
        while (true) {
            List<Order> orders = getOrdersByStatus(Order.OrderStatus.Ordered);
            if (orders.isEmpty()) {
                System.out.println("There are no orders awaiting processing.");
                return;
            }
            System.out.println("----------[ Current Orders]----------");
            printOrders(orders);
            try {
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                //If they selected go back
                if (selection == orders.size()) {
                    return;
                } else if (selection > orders.size() || selection < 1) {
                    System.out.println("That is not an option");
                } else {
                    while (true) {
                        Order selectedOrder = orders.get(selection - 1);
                        printOrderSelection(selectedOrder, selection);
                        selection = Runner.scanner.nextInt(); //reusing selection from earlier
                        Runner.scanner.nextLine();
                        try {
                            switch (selection) {
                                case 1:
                                    StringBuilder ordersRequired = new StringBuilder();
                                    System.out.println("Performing inventory check...");
                                    for (Shipment shipment : selectedOrder.getOrderedItems()) {
                                        //if the item is not in stock
                                        if (!inventoryManager.itemInStock(shipment.getItem(), shipment.getAmount())) {
                                            ordersRequired.append(String.format("Name: %s - Amount Requested: %d\n", shipment.getItem().getName(), shipment.getAmount()));
                                            Supplier.addToInventoryOrderList(shipment);
                                        }
                                    }
                                    //If an inventory order is required
                                    if (!ordersRequired.toString().isEmpty()) {
                                        System.out.println("An inventory order is needed for the following items:");
                                        System.out.print(ordersRequired.toString());
                                        System.out.println("Press enter to go back.");
                                        Runner.scanner.nextLine();
                                    } else {
                                        System.out.println("Inventory check successful!");
                                        System.out.println("Reserving inventory...");
                                        for (Shipment shipment : selectedOrder.getOrderedItems()) {
                                            inventoryManager.removeFromInventory(shipment.getItem(), shipment.getAmount());
                                        }
                                        selectedOrder.setOrderStatus(Order.OrderStatus.Ready);
                                        System.out.println("Inventory reserved successfully!");
                                        // No orders left
                                        if (Supplier.getDeliveryOrderList().isEmpty()) {
                                            return; //Go back to the main menu
                                        } else {
                                            break; //Go back
                                        }
                                    }
                                    continue;
                                case 2:
                                    break; //Go back
                                default:
                                    System.out.println("That is not an option");
                                    continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("That is not an option");
                            Runner.scanner.nextLine();
                        } catch (OutOfStockException | ItemNotFoundException e) {
                            System.out.println(e);
                        }
                        break; //Break out of the while loop in order to go back
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option");
                Runner.scanner.nextLine();
            }
        }
    }

    private List<Order> getOrdersByStatus(Order.OrderStatus requestedStatus) {
        //Super fancy lambda function
        return Supplier.getDeliveryOrderList().stream().filter(
                order -> order.getOrderStatus() == requestedStatus
        ).collect(Collectors.toList());
    }

    private void confirmShipmentsInterface() {
        while (true) {
            List<Order> orders = getOrdersByStatus(Order.OrderStatus.Ready);
            if (orders.isEmpty()) {
                System.out.println("There are no orders awaiting shipping.");
                return;
            }
            System.out.println("----------[ Ready Orders]----------");
            printOrders(orders);
            try {
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                //If they selected go back
                if (selection == orders.size()) {
                    return;
                } else if (selection > orders.size() || selection < 1) {
                    System.out.println("That is not an option");
                } else {
                    while (true) {
                        Order selectedOrder = orders.get(selection - 1);
                        printOrderSelection(selectedOrder, selection);
                        selection = Runner.scanner.nextInt(); //reusing selection from earlier
                        Runner.scanner.nextLine();
                        try {
                            switch (selection) {
                                case 1:
                                    selectedOrder.setOrderStatus(Order.OrderStatus.Shipped);
                                    System.out.println("Successfully confirmed shipment!");
                                    continue;
                                case 2:
                                    break; //Go back
                                default:
                                    System.out.println("That is not an option");
                                    continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("That is not an option");
                            Runner.scanner.nextLine();
                        }
                        break; //Break out of the while loop in order to go back
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option");
                Runner.scanner.nextLine();
            }
        }
    }

    private void printOrders(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            System.out.printf("[%d] Order %d\n", i, i);
        }
        System.out.println("-------------------------------------");
        System.out.printf("[%d] Go back\n", orders.size());
        System.out.print("Which order would you like to view?: ");
    }

    private void printOrderSelection(Order order, int selectionID) {
        System.out.printf("----------[ Order %d ]----------\n", selectionID);
        System.out.println(order.toString());
        System.out.println("--------------------------------");
        System.out.println("[1] Confirm Shipment");
        System.out.println("[2] Go Back");
        System.out.print("Please choose an option: ");
    }

    private void inventoryOrderInterface() {
        while (true) {
            if (Supplier.getInventoryOrderList().isEmpty()) {
                System.out.println("-----[ Inventory Order ]-----");
                System.out.println("[1] Create Custom Order");
                System.out.println("[2] Go Back");
            } else {
                System.out.println("-----[ Auto-Generated Order ]-----");
                Supplier.getInventoryOrderList().forEach(shipment -> System.out.printf("Name: %s - Amount Needed: %d\n", shipment.getItem().getName(), shipment.getAmount()));
                System.out.println("---------------------------");
                System.out.println("[1] Request Auto-Generated Order");
                System.out.println("[2] Create Custom Order");
                System.out.println("[3] Go Back");
            }
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            if (Supplier.getInventoryOrderList().isEmpty()) {
                selection++;
            }
            if (Supplier.getInventoryOrderList().isEmpty() && selection == 1) {
                System.out.println("That is not an option");
                continue;
            }
            try {
                switch (selection) {
                    case 1:
                        Supplier.getInventoryOrderList().forEach(shipment -> inventoryManager.createInventoryOrder(shipment.getItem(), shipment.getAmount()));
                        System.out.println("Inventory order requested!");
                        continue;
                    case 2:
                        customInventoryOrder();
                        System.out.println("Custom inventory order requested!");
                        continue;
                    case 3:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option");
                Runner.scanner.nextLine();
                continue;
            }
            break;
        }
    }

    private void customInventoryOrder() {
        String itemName = "", error;
        int amount = -1;
        double price = -1;
        while (true) {
            if (itemName.equals("") || amount == -1 || price == -1) {
                error = "Error: One or more fields have not been completed.\n";
            } else if (inventoryManager.itemInStock(new Item(itemName, price), 1)) {
                error = "Error: An item with that name is already in the store\n";
            } else {
                error = "";
            }
            System.out.println("---- Custom Inventory Order ----");
            System.out.printf("[1] Item Name [%s]\n", itemName);
            System.out.printf("[2] Item Price [$%.2f]\n", price);
            System.out.printf("[3] Amount to Add [%d]\n", amount);
            if (error.equals("")) {
                System.out.println("[4] Create Custom Inventory Order");
            }
            System.out.println("[5] Cancel");
            System.out.print(error);
            System.out.print("Please choose an option: ");
            try {
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                switch (selection) {
                    case 1:
                        System.out.print("Enter Item Name: ");
                        itemName = Runner.scanner.nextLine();
                        break;
                    case 2:
                        while (true) {
                            try {
                                System.out.print("Enter Item Price: ");
                                price = Runner.scanner.nextDouble();
                                if (price <= 0) {
                                    System.out.println("The price cannot be lower than 0.");
                                } else {
                                    break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("That input is not correct.");
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            try {
                                System.out.print("Enter Amount to Add: ");
                                amount = Runner.scanner.nextInt();
                                if (amount <= 0) {
                                    System.out.println("The amount to add cannot be lower than 0.");
                                } else {
                                    break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("That input is not correct.");
                            }
                        }
                        break;
                    case 4:
                        if (error.equals("")) {
                            inventoryManager.createInventoryOrder(new Item(itemName, price), amount);
                        } else {
                            System.out.println("The inventory order cannot be created while errors exist.");
                            break;
                        }
                    case 5:
                        return;
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
}
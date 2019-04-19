package runner;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import fileStuff.Serializer;
import project.*;

public class Runner {


    //Global scanner
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryManager inventoryManager;
        LoginManager loginManager;
        BasicInterface basicInterface;
        try {
            inventoryManager = Serializer.readObject("Inventory.dat");
        } catch (IOException e) {
            inventoryManager = new InventoryManager();
            /*inventoryManager.createInventoryOrder(new Item("Black Shirt", 30), 12);
            inventoryManager.createInventoryOrder(new Item("Cool Socks", 6.36), 17);
            inventoryManager.createInventoryOrder(new Item("Extraordinarily Large Drum", 830), 1);
            inventoryManager.createInventoryOrder(new Item("Broken Chair", 15.67), 2);
            inventoryManager.createInventoryOrder(new Item("Extremely Broken Chair", 8.54), 1);
            inventoryManager.createInventoryOrder(new Item("Pile of saw dust", .54), 400);
            inventoryManager.createInventoryOrder(new Item("Quantum Entanglement Device", 860000), 15);
            inventoryManager.createInventoryOrder(new Item("sICK Skateboard", 24), 32);
            inventoryManager.createInventoryOrder(new Item("Interesting goo", 12.25), 46);
            inventoryManager.createInventoryOrder(new Item("Bag of lukewarm milk", 3.38), 5);*/
        } catch (ClassNotFoundException e) {
            System.out.println("No class found. This is a problem.");
            return;
        }
        try {
            loginManager = Serializer.readObject("Accounts.dat");
        } catch (IOException e) {
            loginManager = new LoginManager();
        } catch (ClassNotFoundException e) {
            System.out.println("No class found. This is a problem.");
            return;
        }
        while (true){
            try {
                System.out.println("What would you like to do?");
                System.out.println("[1] Login");
                System.out.println("[2] Create Account");
                System.out.println("[3] Quit");
                System.out.print("Please choose an option: ");
                int selection = Runner.scanner.nextInt();
                Runner.scanner.nextLine();
                switch (selection) {
                    case 1: //login
                        while (true){
                            try {
                                System.out.println("---- Login ----");
                                System.out.print("Username: ");
                                String username = Runner.scanner.nextLine();//grab input
                                System.out.print("Password: ");
                                String plainText = Runner.scanner.nextLine(); //grab input
                                loginManager.setCurrentUser(username, plainText);
                                if (loginManager.getAccount(username, plainText) instanceof Customer){
                                    new CustomerInterface(loginManager, inventoryManager).mainInterface();
                                } else {
                                    new SupplierInterface(loginManager, inventoryManager).mainInterface();
                                }
                            } catch (NoAccountFoundException e) {
                                System.out.println("Account doesn't exist");
                                if (BasicInterface.yesNoDialog("Would you like to try again? (Y,N): ")) {
                                    continue;
                                }
                            } catch (InvalidLoginException | InvalidAccountTypeException e) {
                                System.out.println("That username/password combination is not correct.");
                                if (BasicInterface.yesNoDialog("Would you like to try again? (Y,N): ")) {
                                    continue;
                                }
                            }
                            break;
                        }
                        break;
                    case 2: //create account
                        while (true) {
                            Account newAccount = null;
                            basicInterface = null;
                            System.out.println("Which account type would you like to create?");
                            System.out.println("[1] Customer");
                            System.out.println("[2] Supplier");
                            System.out.println("[3] Go back");
                            System.out.print("Please choose an option: ");
                            try {
                                selection = scanner.nextInt();
                                scanner.nextLine();
                                switch (selection) {
                                    case 1: //customer interface
                                        basicInterface = new CustomerInterface(loginManager, inventoryManager);
                                        newAccount = basicInterface.createAccount();
                                        break;
                                    case 2: //supplier interface
                                        basicInterface = new SupplierInterface(loginManager, inventoryManager);
                                        newAccount = basicInterface.createAccount();
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        System.out.println("That is not an option.\nPlease try again");
                                        continue;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("That is not an option.\nPlease try again");
                                scanner.nextLine();
                                continue;
                            }
                            //if the user didn't cancel account creation
                            if (newAccount != null) {
                                loginManager.addAccount(newAccount);
                                loginManager.setCurrentUser(newAccount);
                                System.out.println("Account has been created.");
                                basicInterface.mainInterface();
                            } else {
                                System.out.println("Account creation has been cancelled.\nReturning to menu.");
                            }
                            break;
                        }
                        break;
                    case 3:
                        Serializer.writeObject(inventoryManager, "Inventory.dat");
                        Serializer.writeObject(loginManager, "Accounts.dat");
                        Serializer.writeObject(Supplier.getInventoryOrderList(), "InvOrder.dat");
                        Serializer.writeObject(Supplier.getDeliveryOrderList(), "DelOrder.dat");
                        scanner.close();
                        return;
                    default:
                        System.out.println("That is not an option.\nPlease try again");
                        break;
                }
            } catch (UsernameTakenException e) {
                System.out.println("THIS ERROR SHOULDN'T BE POSSIBLE");
            } catch (InputMismatchException e) {
                System.out.println("That is not an option.\nPlease try again");
                Runner.scanner.nextLine();
            }
        }
    }
}

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

        try {
            System.out.println("[INFO]: Getting inventory file");
            inventoryManager = Serializer.readObject("Inventory.dat");
            System.out.println("[INFO]: Inventory successfully loaded");
        } catch (IOException e) {
            System.out.println("[ERROR]: No Inventory file found");
            System.out.println("[INFO]: Creating new inventory manager");
            inventoryManager = new InventoryManager();
            System.out.println("[INFO]: Inventory manager created successfully");
            System.out.println("[INFO]: Filling inventory with default items");
            inventoryManager.createInventoryOrder(new Item("Black Shirt", 30), 12);
            inventoryManager.createInventoryOrder(new Item("Cool Socks", 6.36), 17);
            inventoryManager.createInventoryOrder(new Item("Extraordinarily Large Drum", 830), 1);
            inventoryManager.createInventoryOrder(new Item("Broken Chair", 15.67), 2);
            inventoryManager.createInventoryOrder(new Item("Extremely Broken Chair", 8.54), 1);
            inventoryManager.createInventoryOrder(new Item("Pile of saw dust", .54), 400);
            inventoryManager.createInventoryOrder(new Item("Quantum Entanglement Device", 860000), 15);
            inventoryManager.createInventoryOrder(new Item("sICK Skateboard", 24), 32);
            inventoryManager.createInventoryOrder(new Item("Interesting goo", 12.25), 46);
            inventoryManager.createInventoryOrder(new Item("Bag of lukewarm milk", 3.38), 5);
            System.out.println("[INFO]: Items added successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("No class found. This is a problem.");
            return;
        }
        try {
            System.out.println("[INFO]: Getting accounts file");
            loginManager = Serializer.readObject("Accounts.dat");
            System.out.println("[INFO]: Accounts successfully loaded");
        } catch (IOException e) {
            System.out.println("[ERROR]: No Accounts file found");
            System.out.println("[INFO]: Creating new login manager");
            loginManager = new LoginManager();
            System.out.println("[INFO]: Login manager created successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("No class found. This is a problem.");
            return;
        }

        while (true) {
            System.out.println("Which user are you?");
            System.out.println("[1] Customer");
            System.out.println("[2] Supplier");
            System.out.println("[3] Quit");
            System.out.print("Please choose an option: ");
            try {
                int selection = scanner.nextInt();
                scanner.nextLine();
                switch (selection) {
                    case 1: //customer interface
                        new CustomerInterface(loginManager, inventoryManager).initialMenu();
                        break;
                    case 2: //supplier interface
                        new SupplierInterface(loginManager);
                        break;
                    case 3:
                        Serializer.writeObject(inventoryManager, "Inventory.dat");
                        Serializer.writeObject(loginManager, "Accounts.dat");
                        scanner.close();
                        return;
                    default:
                        System.out.println("That is not an option.\nPlease try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option.\nPlease try again");
                scanner.nextLine();
            }
        }
    }


}

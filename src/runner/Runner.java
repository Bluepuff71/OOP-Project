package runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
            inventoryManager = Serializer.readObject("Inventory.dat");
        } catch (IOException e) {
            inventoryManager = new InventoryManager();
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
                        new SupplierInterface(loginManager, inventoryManager).initialMenu();
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
                }
            } catch (InputMismatchException e) {
                System.out.println("That is not an option.\nPlease try again");
                scanner.nextLine();
            }
        }
    }


}

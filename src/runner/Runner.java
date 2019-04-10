package runner;

import java.util.Scanner;
import Project.*;

public class Runner {


    //Global scanner
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager("Inventory.dat");
        LoginManager loginManager = new LoginManager("Accounts.dat");
        while (true) {
            System.out.println("Which user are you?");
            System.out.println("[1] Customer");
            System.out.println("[2] Supplier");
            System.out.println("[3] Quit");
            System.out.print("Please choose an option: ");
            try {
                switch (scanner.nextInt()) {
                    case 1: //customer interface
                        new CustomerInterface(loginManager, inventoryManager).initalMenu();
                        break;
                    case 2: //supplier interface

                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("That is not an option.\nPlease try again");
                }
            } catch (Exception e) {
                System.out.println("There was an error.\nPlease try again");
            }
        }

    }
}

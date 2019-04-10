package runner;

import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;

import FileStuff.Serializer;
import Project.*;

public class Runner {
    private static Storefront store;


    //Global scanner
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            store = Serializer.readObject("Storefront.dat");
        } catch (IOException e) {
            store = new Storefront();
        } catch (Exception e) {
            System.out.println("There was an error.");
        }
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
                        new CustomerInterface(loginManager).initalMenu();
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

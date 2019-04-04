package Testing;

import FileStuff.Serializer;

import java.io.IOException;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args){
        System.out.println("This class is for testing.\n" +
                "Warning: No inputs are validated; crashes regarding incorrect inputs don't count as a valid crash.");
        Scanner scanner = new Scanner(System.in);
        String name;
        String password;
        int age;
        System.out.println("Looking for previous testObject.");
        TestObject testObject;
        try{
            testObject = Serializer.readObject("testObject.dat");
            System.out.println("TestObject found!\n");
        } catch (IOException e){
            System.out.println("File not found.\nTestObject creation required.\n\nBeginning TestObject Creation:");
            System.out.print("Please type your name: ");
            name = scanner.nextLine();
            System.out.print("Please type your password: ");
            password = scanner.nextLine();
            System.out.print("Please type your age: ");
            age = scanner.nextInt();
            testObject = new TestObject(name, password, age);
            int i;
            System.out.print("How many items would you like in your cart?: ");
            i = scanner.nextInt();
            for(; i > 0; i--){
                String itemName;
                double itemPrice;
                System.out.print("What is the item's name: ");
                scanner.nextLine();
                itemName = scanner.nextLine();
                System.out.print("What is the item's price: ");
                itemPrice = scanner.nextDouble();
                testObject.addToCart(itemName, itemPrice);
                System.out.println("Item added to cart.");
            }
            System.out.println("TestObject created. Saving as TestObject.dat.");
            Serializer.writeObject(testObject, "TestObject.dat");
            System.out.println("The program will now close.\nPress enter to continue.");
            scanner.nextLine();
            scanner.nextLine();
            scanner.close();
            return;
        } catch (ClassNotFoundException e){
            System.out.println("Class doesn't exist.");
            scanner.close();
            return;
        }
        System.out.println("Testing will now begin:");
        boolean hasTriedPassword = false;
        do{
            if(hasTriedPassword){
                System.out.println("That password is incorrect please try again.");
            }
            System.out.println("Verify Password.");
            System.out.print("Please type your name: ");
            name = scanner.nextLine();
            System.out.print("Please type your password: ");
            password = scanner.nextLine();
            System.out.println("Verifying password.");
            hasTriedPassword = true;
        }
        while (!testObject.testPassword(name, password));

        System.out.println("That password is correct!\n" +
                "Here is the information on the testObject:");
        System.out.println(testObject.toString());
        testObject.printCart();
        System.out.print("Testing completed.\n" +
                "Press enter to stop the program.");
        scanner.nextLine();
        scanner.close();
    }
}

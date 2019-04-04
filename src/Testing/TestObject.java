package Testing;

import Security.Hash;

import java.util.ArrayList;
import java.util.Arrays;

public class TestObject implements java.io.Serializable {

    private String name;
    private byte[] password;
    private int age;
    private ArrayList<Item> cart;


    public TestObject(String name, String password, int age) {
        this.name = name;
        this.password = Hash.CreateHash(password, name);
        this.age = age;
        this.cart = new ArrayList<Item>();
    }

    public void addToCart(String name, double price){
        cart.add(new Item(name, price));
    }

    public void printCart(){
        if (cart.isEmpty()){
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Here is your cart:");
            for (Item item: cart) {
                System.out.println(item.toString());
            }
        }
    }

    public boolean testPassword(String name, String plaintext){
        return Arrays.equals(this.password, Hash.CreateHash(plaintext, name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("TestObject Properties:\n" +
                "Name: %s\n" +
                "Password: %s\n" +
                "Age: %d\n" +
                "# of items in cart: %d", this.name, this.password.toString(), this.age, this.cart.size());
    }
}

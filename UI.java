package ui;
import java.util.Scanner;

public class UI {
	Scanner scan;
	String Account_name;
	String Account_pass;
	Item item[];

	public UI() {
		this.scan = new Scanner(System.in);
		this.item = new Item[10];
		Item banana = new Item("Banana", 10,2);
		this.item[0] = banana;
	}

	public void display() {

		this.sign_in();

	}

	public void sign_in() {

		System.out.println("Welcome to Store");
		System.out.print("Username: ");
		this.Account_name = scan.nextLine();
		System.out.print("Password: ");
		this.Account_pass = scan.nextLine();
		// checkuseraccount(Account_name,Account_Pass);
		// if fail ask user for new account name/pass and what type of account
		this.supplier_ui();
		

	}

	public void user_ui() {
		System.out.println("Processing store front, please wait:");
		for (int j = 0; j < this.item.length / 5; j = j + 5) {
			System.out.println("***********************************************************************");
			System.out.printf("%d:%s	%d:%s	%d:%s	%d:%s	%d:%s\n", j+1,this.item[j].name, j+2,this.item[j].name,
					j+3,this.item[j].name,j+4, this.item[j].name,j+5, this.item[j].name);
			System.out.printf("%d		%d		%d		%d		%d\n", this.item[j].ammount, this.item[j].ammount,
					this.item[j].ammount, this.item[j].ammount, this.item[j].ammount);
			System.out.printf("%d		%d		%d		%d		%d", this.item[j].price, this.item[j].price,
					this.item[j].price, this.item[j].price, this.item[j].price);
			System.out.println();
		}
		System.out.println("***********************************************************************\n");
		System.out.println("Type name of item to add to cart or exit to review order/exit store - ex: 1, exit");
		String item = scan.nextLine().toUpperCase();
		
		String choice2;
		if(item.equals("EXIT")) {
			
		}
		else {
			System.out.printf("How many of %s ",this.item[Integer.parseInt(item)-1].name);
			int ammount_order = scan.nextInt();
			scan.nextLine();
			System.out.printf("Selection of %s of ammount %d will cost %d \n",this.item[Integer.parseInt(item)-1].name,ammount_order,ammount_order*this.item[Integer.parseInt(item)-1].price);
			System.out.println("Would you like to add to cart? Yes or No");
			String choice = scan.nextLine().toUpperCase();
			
			if(choice.equals("YES")) {
				System.out.println("Adding to cart");
			}
			else if(choice.equals("NO")){
				System.out.println("Sorry to see that");
			}
			else {
				System.out.println("Invalid choice - Try either Yes or No");
				choice = scan.next();
				if(choice.equals("YES")) {
					System.out.println("Adding to cart");
				}
				else if(choice.equals("NO")){
					System.out.println("Sorry to see that");
				}
			}
			this.user_ui();
		}
		
		System.out.println("Would you like to review your cart, or exit: Review,Exit");
		choice2 = scan.next();
		choice2 =choice2.toUpperCase();
		if(choice2.equals("REVIEW")) {
			//calls review cart
		}
		else if(choice2.equals("EXIT")) {
			//saves everything, closes program
		}
		
	}

	public void supplier_ui() {
		
	}

}







package project;

import runner.Runner;

import java.util.InputMismatchException;

public abstract class StandardInterface {

    protected Account currentAccount;

    protected LoginManager loginManager;

    public StandardInterface(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    protected void login() {
        try {
            System.out.println("---- Login ----");
            System.out.print("Username: ");
            String username = Runner.scanner.nextLine();//grab input
            System.out.print("Password: ");
            String plainText = Runner.scanner.nextLine(); //grab input
            currentAccount = loginManager.getAccount(username, plainText); //get the customer from the login manager
            mainInterface();
        } catch (NoAccountFoundException e) {
            System.out.println("Account doesn't exist");
            if (yesNoDialog("Would you like to try again? (Y,N): ")) {
                this.login();
            } else {
                currentAccount = null;
            }
        } catch (InvalidLoginException | InvalidAccountTypeException e) {
            System.out.println("That username/password combination is not correct.");
            if (yesNoDialog("Would you like to try again? (Y,N): ")) {
                this.login();
            } else {
                currentAccount = null;
            }
        }
    }

    protected void logout() {
        this.currentAccount = null;
    }

    protected abstract void mainInterface();

    protected abstract Account createAccount();

    public void initialMenu() {
        try {
            System.out.println("What would you like to do?");
            System.out.println("[1] Login");
            System.out.println("[2] Create Account");
            System.out.println("[3] Go back");
            System.out.print("Please choose an option: ");
            int selection = Runner.scanner.nextInt();
            Runner.scanner.nextLine();
            switch (selection) {
                case 1: //login
                    login();
                    break;
                case 2: //create account
                    currentAccount = createAccount();
                    //if the user didn't cancel account creation
                    if (currentAccount != null) {
                        loginManager.addAccount(currentAccount);
                        System.out.println("Account has been created.");
                        mainInterface();
                    } else {
                        System.out.println("Account creation has been cancelled.\nReturning to menu.");
                    }
                    break;
                case 3:
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
        initialMenu();
    }

    /**
     * Prints a dialog with yes or no options
     *
     * @param text the yes/no question to ask
     * @return true if yes, false if no
     */
    protected boolean yesNoDialog(String text) {
        while (true) {
            System.out.print(text);
            String selection = Runner.scanner.nextLine();
            if (selection.toLowerCase().equals("y")) {
                return true;
            } else if (selection.toLowerCase().equals("n")) {
                return false;
            } else {
                System.out.println("That is not an option");
            }
        }
    }
}


package Project;

import FileStuff.Serializer;

import java.io.IOException;
import java.util.HashMap;

public final class LoginManager {
    public HashMap<String, Account> accountList;


    public LoginManager(String filePath) {
        try {
            accountList = Serializer.readObject("Accounts.dat");
        } catch (IOException e) {
            accountList = new HashMap<>();
        } catch (Exception e) {
            System.out.println("There was an error.");
        }
    }

    public final Customer getCustomerAccount(String username, String plainText) throws NoAccountFoundException, InvalidLoginException, InvalidAccountTypeException {
        //if account exists
        if (accountList.containsKey(username)) {
            //Check password
            if (accountList.get(username).verifyCredentials(username, plainText)) {
                try {
                    return (Customer) accountList.get(username);
                } catch (ClassCastException e) {
                    throw new InvalidAccountTypeException();
                }
            } else {
                throw new InvalidLoginException();
            }
        } else {
            throw new NoAccountFoundException();
        }
    }
}

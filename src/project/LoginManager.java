package project;

import java.util.HashMap;

public final class LoginManager {
    private HashMap<String, Account> accountList;


    public LoginManager() {
        accountList = new HashMap<>();
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

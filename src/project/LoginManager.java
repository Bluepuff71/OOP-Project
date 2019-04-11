package project;

import java.util.HashMap;

public final class LoginManager implements java.io.Serializable {

    /**
     * The list of accounts
     */
    private HashMap<String, Account> accountList;


    /**
     * Creates a new loginManager
     */
    public LoginManager() {
        accountList = new HashMap<>();
    }

    /**
     * Gets the customer account of a specified user
     *
     * @param username  the username of the user
     * @param plainText the plaintext password of the user
     * @return the specified user
     * @throws NoAccountFoundException     if no account exists with the specified credentials
     * @throws InvalidLoginException       if the credentials didn't work
     * @throws InvalidAccountTypeException if the account type is not a customer account
     */
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

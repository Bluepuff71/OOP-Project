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
     * Checks if the specified username is already taken
     *
     * @param username the username to check
     * @return true if the username is taken, false otherwise
     */
    public boolean usernameTaken(String username) {
        return accountList.containsKey(username.toLowerCase());
    }

    /**
     * Adds a new customer account to the account list
     *
     * @param account the account to add
     * @throws UsernameTakenException if the username is already taken
     */
    public final <T extends Account> void addAccount(T account) throws UsernameTakenException {
        if (usernameTaken(account.getUsername())) {
            throw new UsernameTakenException();
        } else {
            accountList.put(account.getUsername().toLowerCase(), account);
        }

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
    public final <T extends Account> T getAccount(String username, String plainText) throws NoAccountFoundException, InvalidLoginException, InvalidAccountTypeException {
        //if account exists
        if (accountList.containsKey(username.toLowerCase())) {
            //Check password
            if (accountList.get(username.toLowerCase()).verifyCredentials(username, plainText)) {
                try {
                    return (T) accountList.get(username.toLowerCase());
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

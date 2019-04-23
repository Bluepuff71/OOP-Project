package project;

import java.util.HashMap;

/**
 * Contains all the methods for interacting with the account list
 */
public final class LoginManager implements java.io.Serializable {

    /**
     * The list of accounts
     */
    private HashMap<String, Account> accountList;

    /**
     * The current account that is signed in
     */
    private Account currentUser;


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
     * @return {@code true} if the username is taken, {@code false} otherwise
     */
    public boolean usernameTaken(String username) {
        return accountList.containsKey(username.toLowerCase());
    }

    /**
     * Adds a new account to the account list
     * @param account the account to add
     * @param <T> the type of account
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
     * Gets the account of the specified type and of the specified user
     *
     * @param username  the username of the user
     * @param plainText the plaintext password of the user
     * @param <T> the type of account
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
                currentUser = null;
                throw new InvalidLoginException();
            }
        } else {
            currentUser = null;
            throw new NoAccountFoundException();
        }
    }

    /**
     * Gets the current user that is logged in
     *
     * @return the current user that is logged in
     * @see #currentUser
     */
    public Account getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user that is signed in
     * @param account the account to set
     * @see #currentUser
     */
    public void setCurrentUser(Account account) {
        currentUser = account;
    }

    /**
     * Sets the current user to the user with the specified credentials
     * @param username the username of the account
     * @param plainText the plaintext password of the account
     * @throws NoAccountFoundException if no account with the specified credentials exists
     * @throws InvalidLoginException if the username or password was incorrect
     * @throws InvalidAccountTypeException if the account type of the user is not correct
     * @see #currentUser
     */
    public void setCurrentUser(String username, String plainText) throws NoAccountFoundException, InvalidLoginException, InvalidAccountTypeException {
        currentUser = getAccount(username, plainText);
    }

    /**
     * Logs out the current user
     * @see #currentUser
     */
    public void logOutCurrentUser(){
        currentUser = null;
    }
}

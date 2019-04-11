package project;

import security.Hash;

import java.util.Arrays;

/**
 * The general base class for all accounts
 */
public abstract class Account implements java.io.Serializable {

    /**
     * The username of the account holder.
     */
    private String username;

    /**
     * The users hashed password, NOT PLAINTEXT
     */
    private byte[] password;

    public Account(String username, String plainText) {
        this.username = username;
        this.password = Hash.CreateHash(plainText, username);
    }

    public String getUsername() {
        return username;
    }

    /**
     * Changes the username of this user. (Validation Required)
     * @param newUsername     the username to change to
     * @param currentUsername the current username
     * @param plainText       the plaintext password
     * @throws UnauthorizedException if the username or password is incorrect
     */
    public final void setUsername(String newUsername, String currentUsername, String plainText) throws UnauthorizedException {
        if (verifyCredentials(currentUsername, plainText)) {
            this.password = Hash.CreateHash(plainText, newUsername);
            this.username = newUsername;
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Changes the password of the user. (Validation Required)
     * @param newPlainText     the plaintext of the new password
     * @param username         the username of the user
     * @param currentPlaintext the current plaintext password of the user
     * @throws UnauthorizedException if the username or password is incorrect
     */
    public final void setPassword(String newPlainText, String username, String currentPlaintext) throws UnauthorizedException {
        if (verifyCredentials(username, currentPlaintext)) {
            this.password = Hash.CreateHash(newPlainText, username);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Used to verify user credentials
     * @param username  the username of the user
     * @param plainText the plaintext password of the user
     * @return true if the username/password combination is valid, false otherwise
     */
    public final boolean verifyCredentials(String username, String plainText) {
        return Arrays.equals(this.password, Hash.CreateHash(plainText, username));
    }
}

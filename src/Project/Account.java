package Project;

import Security.Hash;

import java.util.Arrays;

public abstract class Account {

    //TODO Some kind of token that verifies that the user is signed in before changing stuff like passwords

    /**
     * The username of the account holder.
     */
    private String username;

    /**
     * The users hashed password, NOT PLAINTEXT
     */
    private byte[] password;

    private boolean isLoggedIn;


    public Account(String username, String plainText) {
        this.username = username;
        this.password = Hash.CreateHash(plainText, username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws UnauthorizedException {
        if (isLoggedIn) {
            this.username = username;
        } else {
            throw new UnauthorizedException();
        }
    }

    public void setPassword(String plainText) throws UnauthorizedException {
        if (isLoggedIn) {
            this.password = Hash.CreateHash(plainText, this.username);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Used to verify user credentials
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return
     */
    public boolean verifyCredentials(String username, String password) {
        return Arrays.equals(this.password, Hash.CreateHash(password, username));
    }

}

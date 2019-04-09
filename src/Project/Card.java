package Project;

/**
 * All the information for a debit/credit card
 */
public class Card implements java.io.Serializable {

    /**
     * The main numbers on the front of the card
     */
    private String number;

    /**
     * The first and last name of the holder of the card
     */
    private String name;

    /**
     * The 2-digit expiration month on the card
     */
    private String expirationMonth;

    /**
     * The 2-digit expiration year on the card
     */
    private String expirationYear;

    /**
     * The 3-4 digit code on the back of the card
     */
    private String securityCode;

    /**
     * The amount of money the card is allowed to spend
     */
    private double creditLimit;

    /**
     * Creates a new card
     *
     * @param number          the main numbers on the front of the card
     * @param name            the first and last name of the holder of the card
     * @param expirationMonth the 2-digit expiration month on the card
     * @param expirationYear  the 2-digit expiration year on the card
     * @param securityCode    the 3-4 digit code on the back of the
     * @param creditLimit     the amount of money the card is allowed to spend
     */
    public Card(String number, String name, String expirationMonth, String expirationYear, String securityCode, int creditLimit) {
        this.number = number;
        this.name = name;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.securityCode = securityCode;
        this.creditLimit = creditLimit;
    }

    /**
     * Gets the last 4 digits of the card
     *
     * @return the last 4 digits of the card
     */
    public String getLastFourDigits() {
        return number.substring(number.length() - 4);
    }


    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }
}

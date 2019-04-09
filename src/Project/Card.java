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
     * Creates a new card
     *
     * @param number          the main numbers on the front of the card
     * @param name            the first and last name of the holder of the card
     * @param expirationMonth the 2-digit expiration month on the card
     * @param expirationYear  the 2-digit expiration year on the card
     * @param securityCode    the 3-4 digit code on the back of the card
     */
    public Card(String number, String name, String expirationMonth, String expirationYear, String securityCode) {
        this.number = number;
        this.name = name;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.securityCode = securityCode;
    }
}

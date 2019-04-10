package Project;

import java.util.Random;

public final class Bank {
    /**
     * Generates a authorization number
     *
     * @param card      the debit/credit card of the user
     * @param totalCost the total cost of the order
     * @return the authorization number
     * @throws InvalidCardException if the card is invalid
     * @throws CreditLimitException if the credit limit is exceeded by the transaction
     */
    public static int getPurchaseAuthorizationNumber(Card card, double totalCost) throws InvalidCardException, CreditLimitException {
        if (!(card.getLastFourDigits().equals("1337"))) {
            throw new InvalidCardException();
        } else if ((card.getCreditLimit() - totalCost) < 0) {
            throw new CreditLimitException();
        } else {
            return new Random().nextInt();
        }
    }
}

package project;

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
        if (!card.isValid()) {
            throw new InvalidCardException("Card is invalid");
        } else if ((card.getCreditLimit() - totalCost) < 0) {
            throw new CreditLimitException();
        } else {
            card.setCreditLimit(card.getCreditLimit() - totalCost);
            return new Random().nextInt();
        }
    }
}

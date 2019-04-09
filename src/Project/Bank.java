package Project;

import java.util.Random;

public class Bank {
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

package financials;

import java.time.LocalDate;

public class Visa extends MoneyTransferPlatform {
    private String cardNumber;

    public Visa(double balance, String cardNumber) {
        super(balance);
        setCardNumber(cardNumber);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    @Override
    public String getPaymentDetails(double amount, boolean transferred) {
        return "Visa card number: " + cardNumber
                + (transferred ? " transferred $"+amount : ", received")
                + (transferred ? " to " : (" on " + LocalDate.now()));
    }

    @Override
    public String toString() {
        return super.toString()+
                ", cardNumber:" + cardNumber +
                '}';

    }
}
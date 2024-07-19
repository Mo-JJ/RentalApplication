package financials;

import java.time.LocalDate;

public class PayPal extends MoneyTransferPlatform {
    private String email;

    public PayPal(double balance, String email) {
        super(balance);
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPaymentDetails(double amount, boolean transferred) {
        return "PayPal Account: " + email
                +(transferred?" transferred $":" received $") + amount
                +(transferred?" to ":(" on "+ LocalDate.now()));
    }

    @Override
    public String toString() {
        return super.toString()+", email:" + email +
                '}';
    }
}

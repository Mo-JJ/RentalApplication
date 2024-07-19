package financials;

public abstract class MoneyTransferPlatform {
    private double balance;

    public MoneyTransferPlatform(double balance){
        setBalance(balance);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        if (balance<=0)
            System.out.println("Warning, your balance just hit the ground");
    }

    public abstract String getPaymentDetails(double amount, boolean transferred);
    private boolean isPaymentAllowed(double amount) {
        if (amount >= 0 && this.balance >= amount)
            return true;

        System.out.println("Transaction Failed: Not enough money in the sender's balance."
                +"\namount required: "+amount +" but current balance is: "+this.balance);
        return false;
    }

    public void processPayment(double amount, MoneyTransferPlatform receiverPlatform) {
        System.out.println("Trying to Process the Transaction...");
        if (isPaymentAllowed(amount)){
            this.balance -= amount;
            receiverPlatform.balance += amount;

            System.out.println("Transaction was Successful.");
            System.out.println(
                    this.getPaymentDetails(amount, true)
                    +receiverPlatform.getPaymentDetails(amount, false));
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "balance:" + balance;
    }
}

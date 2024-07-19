package individuals;

import financials.MoneyTransferPlatform;
import residentials.RentalLease;

import java.util.HashMap;
import java.util.Map;

public class Tenant extends Citizen{

    private Map<String, RentalLease> rentalHistory;

    public Tenant(String name, MoneyTransferPlatform moneyTransferChannel) {
        super(name, moneyTransferChannel);
        setRentalHistory(new HashMap<>());
    }

    public Map<String, RentalLease> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(Map<String, RentalLease> rentalHistory) {
        if (rentalHistory ==null)
            this.rentalHistory = new HashMap<>();
        else
            this.rentalHistory = rentalHistory;
    }
    public void addNewRentLease(RentalLease lease){
        if (rentalHistory == null || lease==null)
            System.out.println("Couldn't add this residence to the tenants archive.");
        else
            rentalHistory.put(lease.getResidenceId(), lease);
    }

    @Override
    public String toString() {
        return super.toString()
                +", rentalHistory:" + rentalHistory +
                '}';
    }
}

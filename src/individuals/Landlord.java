package individuals;

import financials.MoneyTransferPlatform;
import residentials.Residence;
import java.util.HashMap;
import java.util.Map;

public class Landlord extends Citizen{

    private String contact;
    private Map<String, Residence> rentableRealEstates;

    public Landlord(String name, MoneyTransferPlatform moneyTransferChannel, String contact) {
        super(name, moneyTransferChannel);
        setContact(contact);
        setRentableRealEstates(new HashMap<>());
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Map<String, Residence> getRentableRealEstates() {
        return rentableRealEstates;
    }

    public void setRentableRealEstates(Map<String, Residence> rentableRealEstates) {
        if (rentableRealEstates==null)
            this.rentableRealEstates = new HashMap<>();
        else
            this.rentableRealEstates = rentableRealEstates;
    }

    public void addRealEstate(Residence residence){
        if (residence!=null)
            if (!rentableRealEstates.containsKey(residence.getResidenceId())) {
                rentableRealEstates.put(residence.getResidenceId(), residence);
                Residence.addResidence(residence);
            }
    }

    @Override
    public String toString() {
        return  super.toString()+ ", contact:" + contact +
                ", rentableRealEstates:" + rentableRealEstates +
                '}';
    }
}

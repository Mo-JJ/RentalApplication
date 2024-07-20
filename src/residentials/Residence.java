package residentials;

import financials.MoneyTransferPlatform;
import individuals.Citizen;
import individuals.Landlord;
import individuals.Tenant;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Residence implements Rentable{
    private String residenceId;
    private double area;
    private short numberOfRooms;
    private String address;
    private double dailyPrice;
    private int landlordId;
    private static Map<String, Residence> residences = new HashMap<>();

    public Residence(String residenceId, double area, short numberOfRooms,
                     String address, double dailyPrice, int landlordId) {
        setResidenceId(residenceId);
        setArea(area);
        setNumberOfRooms(numberOfRooms);
        setAddress(address);
        setDailyPrice(dailyPrice);
        setLandlordId(landlordId);

        Landlord landlord = (Landlord) Citizen.getCitizens().getOrDefault(landlordId, null);
        if (landlord!=null) {
            landlord.addRealEstate(this);
        }
    }

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        if (residenceId!=null && !residences.containsKey(this.residenceId))
            this.residenceId = residenceId;
        else
            System.out.println("Invalid residenceId: "+residenceId);
    }

    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        if (area>0)
            this.area = area;
        else
            System.out.println("Invalid residence area: "+area);
    }

    public short getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(short numberOfRooms) {
        if (numberOfRooms>0)
            this.numberOfRooms = numberOfRooms;
        else
            System.out.println("Invalid number of rooms: "+numberOfRooms);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        if (dailyPrice>0)
            this.dailyPrice = dailyPrice;
        else
            System.out.println("Invalid daily renting price: "+dailyPrice);
    }

    public int getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(int landlordId) {
        if (landlordId>0 && Citizen.getCitizens().containsKey(landlordId))
            this.landlordId = landlordId;
        else
            System.out.println("Invalid landlordId: "+landlordId);
    }

    public static Map<String, Residence> getResidences() {
        return residences;
    }

    public static void setResidences(Map<String, Residence> residences) {
        Residence.residences = residences;
    }
    public static void addResidence(Residence residence){
        if (residence!=null)
            if (!residences.containsKey(residence.getResidenceId()))
                residences.put(residence.getResidenceId(), residence);
            else
                System.out.println("A residence already exist with the given id: "+residence.getResidenceId());
        else
            System.out.println("Can't add the given residence.");
    }

    @Override
    public void rent(int tenantId, LocalDate startDate, LocalDate endDate) {
        if (isAvailable(startDate, endDate)) {
            RentalLease lease = new RentalLease(tenantId, landlordId, residenceId, startDate, endDate);
            if (lease.getTenantId() == -1 || lease.getLandlordId() == -1)
                System.out.println(Rentable.UNAVAILABLE_MESSAGE_INFO);
            else{
                double rentingPrice = this.calculateRentingPrice(startDate, endDate);
                Citizen tenant =  Citizen.getCitizens().get(tenantId);
                Citizen landlord =  Citizen.getCitizens().get(landlordId);
                if (tenant instanceof Tenant) {
                    MoneyTransferPlatform tenantPayChannel = tenant.getMoneyTransferChannel();
                    tenantPayChannel.processPayment(rentingPrice, landlord.getMoneyTransferChannel());
                    ((Tenant)tenant).addNewRentLease(lease);
                    this.printRentingDetails(startDate, endDate);
                    System.out.println("------ Renting process is finished ------\n");
                }else
                    System.out.println("the provided id belongs to an owner, an owner can't rent and be a tenant.");
            }
        }
    }
    @Override
    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        List<RentalLease> leases = RentalLease.getLeases();

        for (RentalLease lease: leases){
            if (lease!=null && lease.getResidenceId().equals(residenceId))
                if (
                    (startDate.isAfter(lease.getStart()) && startDate.isBefore(lease.getEnd()))
                        || (endDate.isAfter(lease.getStart()) && endDate.isBefore(lease.getEnd()))
                        || (startDate.isBefore(lease.getStart()) && endDate.isAfter(lease.getEnd()))) {
                    System.out.println(Rentable.UNAVAILABLE_MESSAGE_DATES);
                    return false;
                }
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+":{" +
                "residenceId:" + residenceId +
                ", area:" + area +
                ", dailyPrice:" + dailyPrice +
                ", numberOfRooms:" + numberOfRooms +
                ", address:" + address;
    }
}

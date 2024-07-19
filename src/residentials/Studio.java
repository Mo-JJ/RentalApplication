package residentials;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Studio extends Residence{
    private short floorNumber;
    private int unitNumber;
    private final static double STUDIO_TAX = 1.20;

    public Studio(String residenceId, double area, short numberOfRooms, String address,
                  double dailyPrice, short floorNumber, int unitNumber, int landlordId) {
        super(residenceId+"-"+unitNumber, area, numberOfRooms, address, dailyPrice, landlordId);
        setFloorNumber(floorNumber);
        setUnitNumber(unitNumber);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(short floorNumber) {
        if (floorNumber>0)
            this.floorNumber = floorNumber;
        else
            System.out.println("Invalid floor number: "+floorNumber);
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        if (unitNumber>0)
            this.unitNumber = unitNumber;
        else
            System.out.println("Invalid unit number: "+unitNumber);
    }

    @Override
    public double calculateRentingPrice(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return getDailyPrice() * daysBetween *  STUDIO_TAX;
    }

    @Override
    public String toString() {
        return  super.toString()+", floorNumber:" + floorNumber +
                ", unitNumber:" + unitNumber +
                '}';
    }
}

package residentials;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Villa extends Residence{
    private boolean hasPool;
    private double gardenSize;
    private final static double VILLA_TAX = 1.35;

    public Villa(String residenceId, double area, short numberOfRooms, String address,
                 double dailyPrice, boolean hasPool, double gardenSize, int landlordId) {
        super(residenceId, area, numberOfRooms, address, dailyPrice, landlordId);
        setHasPool(hasPool);
        setGardenSize(gardenSize);
    }

    public boolean getHasPool() {
        return hasPool;
    }

    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    public double getGardenSize() {
        return gardenSize;
    }

    public void setGardenSize(double gardenSize) {
        if (gardenSize>0)
            this.gardenSize = gardenSize;
        else
            System.out.println("Invalid garden size: "+gardenSize);
    }

    @Override
    public double calculateRentingPrice(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return getDailyPrice() * daysBetween * VILLA_TAX;
    }

    @Override
    public void printRentingDetails(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        System.out.println("Renting Details:\nrenting price per day: $"+getDailyPrice()
                +"\nnumber of renting days: "+daysBetween
                +"\nstudio tax: %"+(VILLA_TAX*100%100)
                +"\ntotal: $"+(getDailyPrice() * daysBetween *  VILLA_TAX));
    }
    @Override
    public String toString() {
        return super.toString()+ ", hasPool:" + hasPool +
                ", gardenSize:" + gardenSize +
                '}';
    }
}


package residentials;

import individuals.Citizen;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RentalLease {

    private int tenantId;
    private int landlordId;
    private String residenceId;
    private LocalDate start;
    private LocalDate end;
    private static List<RentalLease> leases = new LinkedList<>();

    public RentalLease(int tenantId, int landlordId, String residenceId, LocalDate start, LocalDate end){
        setTenantId(tenantId);
        setLandlordId(landlordId);
        setResidenceId(residenceId);
        setStart(start);
        setEnd(end);
        if (tenantId!=-1 && landlordId !=-1 && end!=null)
            leases.add(this);
        else
            System.out.println("This lease isn't legal yet.");

    }
    private boolean findCitizen(int citizenId){
        Map<Integer, Citizen> citizens = Citizen.getCitizens();
        return citizens != null && citizens.containsKey(citizenId);
    }
    private boolean findResidence(String residenceId){
        Map<String, Residence> residences = Residence.getResidences();
        if (residences!=null && residences.containsKey(residenceId)) {
            Residence residence = residences.get(residenceId);
            if (residence!=null && residence.getLandlordId()!=-1 && residence.getLandlordId() == landlordId)
                return true;
            else
                System.out.println("No residence with ID: " +residenceId +" is owned by landlordId: "+ landlordId);
        }else
            System.out.println("No residenceId exists for the given ID: "+residenceId);

        return false;
    }

    public static List<RentalLease> getLeases() {
        return leases;
    }

    public static void setLeases(List<RentalLease> leases) {
        RentalLease.leases = leases;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        if (findCitizen(tenantId))
            this.tenantId = tenantId;
        else {
            this.tenantId = -1;
            System.out.println("No tenantId exists for the given ID: " + tenantId);
        }
    }

    public int getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(int landlordId) {
        if (findCitizen(landlordId))
            this.landlordId = landlordId;
        else {
            this.landlordId = -1;
            System.out.println("No landlordId exists for the given ID: " + landlordId);
        }
    }

    public String getResidenceId() {
        return residenceId;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        if (start!=null && end.isAfter(start))
            this.end = end;
        else
            System.out.println("End date can't be before start date.");
    }

    public void setResidenceId(String residenceId) {
        if (findResidence(residenceId))
            this.residenceId = residenceId;
    }

    @Override
    public String toString() {
        return "RentalLease{" +
                "tenantId:" + tenantId +
                ", landlordId:" + landlordId +
                ", residenceId:" + residenceId +
                ", start:" + start +
                ", end:" + end +
                '}';
    }
}

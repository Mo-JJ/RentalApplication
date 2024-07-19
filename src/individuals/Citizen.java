package individuals;

import financials.MoneyTransferPlatform;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Citizen {

    private int idNumber;
    private String name;
    private MoneyTransferPlatform moneyTransferChannel;
    private static int idCounter=0;
    private static Map<Integer, Citizen> citizens = new HashMap<>();

    public Citizen(String name, MoneyTransferPlatform moneyTransferChannel) {
        setIdNumber(++idCounter);
        setName(name);
        setMoneyTransferChannel(moneyTransferChannel);
        addCitizen(this);
    }


    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        if (idNumber>0)
            this.idNumber = idNumber;
        else
            System.out.println("Invalid id number: "+idNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Integer, Citizen> getCitizens() {
        return citizens;
    }

    public static void setCitizens(Map<Integer, Citizen> citizens) {
        Citizen.citizens = citizens;
        Optional<Integer> max = citizens.keySet().stream().max(Integer::compareTo);
        max.ifPresent(key -> idCounter=key);
    }
    public static void addCitizen(Citizen citizen){
        if (citizen!=null)
            if (!citizens.containsKey(citizen.getIdNumber()))
                citizens.put(citizen.idNumber, citizen);
            else
                System.out.println("A citizen already exist with the given id number: "+citizen.getIdNumber());
        else
            System.out.println("Can't add the given citizen.");
    }

    public MoneyTransferPlatform getMoneyTransferChannel() {
        return moneyTransferChannel;
    }

    public void setMoneyTransferChannel(MoneyTransferPlatform moneyTransferChannel) {
        this.moneyTransferChannel = moneyTransferChannel;
    }
    @Override
    public String toString() {
        return "Citizen-"+getClass().getSimpleName()+"{" +
                "idNumber:" + idNumber +
                ", name:" + name+
                ", moneyTransferChannel:" + moneyTransferChannel;
    }
}

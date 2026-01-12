package kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class OwnerDirectory {

    private final Map<String, OwnerInfo> ownersByPlatePrefix = new HashMap<>();

    public OwnerDirectory() {
        ownersByPlatePrefix.put("AA", template("John Doe", "Denver, CO", "555-0100"));
        ownersByPlatePrefix.put("BB", template("Diana Prince", "Colorado Springs, CO", "555-0111"));
        ownersByPlatePrefix.put("CC", template("Sarah Connor", "Lakewood, CO", "555-0122"));
        ownersByPlatePrefix.put("EE", template("Michael Knight", "Boulder, CO", "555-0133"));
        ownersByPlatePrefix.put("FF", template("Amy Santiago", "Aurora, CO", "555-0144"));
        ownersByPlatePrefix.put("GA", template("Carlos Diaz", "Castle Rock, CO", "555-0155"));
        ownersByPlatePrefix.put("FB", template("Lena Oxton", "Fort Collins, CO", "555-0166"));
        ownersByPlatePrefix.put("FA", template("Jim Kirk", "Golden, CO", "555-0177"));
        ownersByPlatePrefix.put("FG", template("T'Challa", "Thornton, CO", "555-0188"));
        ownersByPlatePrefix.put("AS", template("Peter Parker", "Littleton, CO", "555-0199"));
        ownersByPlatePrefix.put("BS", template("Natasha Romanoff", "Brighton, CO", "555-0200"));
        ownersByPlatePrefix.put("CS", template("Maria Hill", "Loveland, CO", "555-0211"));
        ownersByPlatePrefix.put("ES", template("Pepper Potts", "Erie, CO", "555-0222"));
        ownersByPlatePrefix.put("FS", template("Sam Wilson", "Arvada, CO", "555-0233"));
        ownersByPlatePrefix.put("GS", template("Bruce Wayne", "Parker, CO", "555-0244"));
        ownersByPlatePrefix.put("FBS", template("Shuri Udaku", "Longmont, CO", "555-0255"));
        ownersByPlatePrefix.put("FAS", template("Nick Fury", "Highlands Ranch, CO", "555-0266"));
        ownersByPlatePrefix.put("FGS", template("Peggy Carter", "Greeley, CO", "555-0277"));
    }

    public OwnerInfo findOwner(String licencePlate) {
        if (licencePlate == null || licencePlate.isEmpty()) {
            return new OwnerInfo("UNKNOWN", "Unknown Owner", "Unknown City", "N/A");
        }
        String prefix = extractPrefix(licencePlate);
        OwnerInfo template = ownersByPlatePrefix.get(prefix);
        if (template == null) {
            template = template("State DMV", "Records Office", "555-02" + ThreadLocalRandom.current().nextInt(10, 99));
        }
        return new OwnerInfo(licencePlate, template.getOwnerName(), template.getCity(), template.getPhoneNumber());
    }

    private String extractPrefix(String licencePlate) {
        if (licencePlate.length() >= 3 && ownersByPlatePrefix.containsKey(licencePlate.substring(0, 3))) {
            return licencePlate.substring(0, 3);
        }
        if (licencePlate.length() >= 2) {
            return licencePlate.substring(0, 2);
        }
        return licencePlate.substring(0, 1);
    }

    private OwnerInfo template(String name, String city, String phone) {
        return new OwnerInfo("", name, city, phone);
    }
}

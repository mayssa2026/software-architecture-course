package kafka;

public class OwnerInfo {
    private String licencePlate;
    private String ownerName;
    private String city;
    private String phoneNumber;

    public OwnerInfo() {
    }

    public OwnerInfo(String licencePlate, String ownerName, String city, String phoneNumber) {
        this.licencePlate = licencePlate;
        this.ownerName = ownerName;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return ownerName + " (" + city + ", " + phoneNumber + ")";
    }
}

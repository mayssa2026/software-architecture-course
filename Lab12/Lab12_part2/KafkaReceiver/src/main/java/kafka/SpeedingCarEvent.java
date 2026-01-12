package kafka;

public class SpeedingCarEvent {
    private String licencePlate;
    private double speedMph;
    private int entryMinute;
    private int entrySecond;
    private int exitMinute;
    private int exitSecond;

    public SpeedingCarEvent() {
    }

    public SpeedingCarEvent(String licencePlate, double speedMph,
                            int entryMinute, int entrySecond,
                            int exitMinute, int exitSecond) {
        this.licencePlate = licencePlate;
        this.speedMph = speedMph;
        this.entryMinute = entryMinute;
        this.entrySecond = entrySecond;
        this.exitMinute = exitMinute;
        this.exitSecond = exitSecond;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public double getSpeedMph() {
        return speedMph;
    }

    public void setSpeedMph(double speedMph) {
        this.speedMph = speedMph;
    }

    public int getEntryMinute() {
        return entryMinute;
    }

    public void setEntryMinute(int entryMinute) {
        this.entryMinute = entryMinute;
    }

    public int getEntrySecond() {
        return entrySecond;
    }

    public void setEntrySecond(int entrySecond) {
        this.entrySecond = entrySecond;
    }

    public int getExitMinute() {
        return exitMinute;
    }

    public void setExitMinute(int exitMinute) {
        this.exitMinute = exitMinute;
    }

    public int getExitSecond() {
        return exitSecond;
    }

    public void setExitSecond(int exitSecond) {
        this.exitSecond = exitSecond;
    }

    @Override
    public String toString() {
        return "SpeedingCarEvent{" +
                "licencePlate='" + licencePlate + '\'' +
                ", speedMph=" + speedMph +
                ", entry=" + entryMinute + ":" + entrySecond +
                ", exit=" + exitMinute + ":" + exitSecond +
                '}';
    }
}

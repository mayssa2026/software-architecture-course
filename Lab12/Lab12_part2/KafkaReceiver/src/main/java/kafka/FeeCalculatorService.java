package kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FeeCalculatorService {

    private final OwnerDirectory ownerDirectory;

    public FeeCalculatorService(OwnerDirectory ownerDirectory) {
        this.ownerDirectory = ownerDirectory;
    }

    @KafkaListener(topics = "${app.topic.tofasttopic}", groupId = "fee-calculator")
    public void calculateFee(SpeedingCarEvent event) {
        OwnerInfo owner = ownerDirectory.findOwner(event.getLicencePlate());
        double fee = determineFee(event.getSpeedMph());
        System.out.printf("FeeCalculatorService - %s | %s | %.2f mph | fee $%.2f%n",
                event.getLicencePlate(), owner, event.getSpeedMph(), fee);
    }

    private double determineFee(double speed) {
        if (speed <= 77) {
            return 25;
        } else if (speed <= 82) {
            return 45;
        } else if (speed <= 90) {
            return 80;
        }
        return 125;
    }
}

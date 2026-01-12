package kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private final OwnerDirectory ownerDirectory;

    public OwnerService(OwnerDirectory ownerDirectory) {
        this.ownerDirectory = ownerDirectory;
    }

    @KafkaListener(topics = "${app.topic.tofasttopic}", groupId = "owner-service")
    public void reportOwner(SpeedingCarEvent event) {
        OwnerInfo owner = ownerDirectory.findOwner(event.getLicencePlate());
        System.out.printf("OwnerService - %s belongs to %s%n",
                event.getLicencePlate(), owner);
    }
}

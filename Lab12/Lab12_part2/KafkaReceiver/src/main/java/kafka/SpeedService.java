package kafka;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpeedService {

    private static final double SPEED_LIMIT = 72d;

    private final Map<String, SensorRecord> camera1Reads = new ConcurrentHashMap<>();
    private final Map<String, SensorRecord> camera2Reads = new ConcurrentHashMap<>();
    private final KafkaTemplate<String, SpeedingCarEvent> kafkaTemplate;
    private final String fastTopic;

    public SpeedService(KafkaTemplate<String, SpeedingCarEvent> kafkaTemplate,
                        @Value("${app.topic.tofasttopic}") String fastTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.fastTopic = fastTopic.trim();
    }

    @KafkaListener(topics = {"cameratopic1", "cameratopic2"}, groupId = "speed-service")
    public void handleSensorEvent(SensorRecord sensorRecord) {
        if (sensorRecord.getCameraId() == 1) {
            camera1Reads.put(sensorRecord.getLicencePlate(), sensorRecord);
        } else if (sensorRecord.getCameraId() == 2) {
            camera2Reads.put(sensorRecord.getLicencePlate(), sensorRecord);
        } else {
            // ignore other cameras for this lab
            return;
        }
        tryMatch(sensorRecord.getLicencePlate());
    }

    private void tryMatch(String licencePlate) {
        SensorRecord start = camera1Reads.get(licencePlate);
        SensorRecord end = camera2Reads.get(licencePlate);
        if (start == null || end == null) {
            return;
        }

        camera1Reads.remove(licencePlate);
        camera2Reads.remove(licencePlate);

        double speedMph = calculateSpeedMph(start, end);
        System.out.printf("SpeedService - %s drove %.2f mph (camera1 %d:%d -> camera2 %d:%d)%n",
                licencePlate, speedMph, start.getMinute(), start.getSecond(), end.getMinute(), end.getSecond());

        if (speedMph > SPEED_LIMIT) {
            SpeedingCarEvent event = new SpeedingCarEvent(
                    licencePlate, speedMph,
                    start.getMinute(), start.getSecond(),
                    end.getMinute(), end.getSecond());
            kafkaTemplate.send(fastTopic, licencePlate, event);
            System.out.printf("SpeedService - published speeding event for %s at %.2f mph%n",
                    licencePlate, speedMph);
        }
    }

    private double calculateSpeedMph(SensorRecord start, SensorRecord end) {
        long startSeconds = toSeconds(start);
        long endSeconds = toSeconds(end);
        long deltaSeconds = endSeconds - startSeconds;
        if (deltaSeconds <= 0) {
            deltaSeconds += 60;
        }
        return 0.5d / deltaSeconds * 3600d;
    }

    private long toSeconds(SensorRecord record) {
        return record.getMinute() * 60L + record.getSecond();
    }
}

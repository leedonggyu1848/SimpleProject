package my.project.simple.repository;

import lombok.RequiredArgsConstructor;
import my.project.simple.domain.Trash;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, Trash> kafkaTemplate;

	public void sendMessage(String topic, Trash trash) {
		kafkaTemplate.send(topic, trash)
				.whenComplete((result, ex) -> {
					if (ex == null) {
						System.out.println("Message sent successfully to topic: " + topic + " with message: " + trash);
						System.out.println("Offset: " + result.getRecordMetadata().offset());
					} else {
						System.err.println("Failed to send message: " + trash + " due to: " + ex.getMessage());
					}
				});
	}
}

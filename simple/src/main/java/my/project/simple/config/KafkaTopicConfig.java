package my.project.simple.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	public static final String TRASH = "trash"; // 토픽 이름
	@Bean
	public NewTopic trash() {
		return TopicBuilder.name(TRASH) // 토픽 이름
				.partitions(1) // 파티션 수
				.replicas(1)   // 복제본 수
				.build();
	}
}

package my.project.simple.service;

import lombok.RequiredArgsConstructor;
import my.project.simple.config.KafkaTopicConfig;
import my.project.simple.domain.Trash;
import my.project.simple.repository.KafkaProducer;
import my.project.simple.repository.TrashRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashService {
	private final TrashRepository trashRepository;
	private final KafkaProducer kafkaProducer;

	public Trash addTrash(String content) {
		Trash trash = new Trash();
		trash.setContent(content);
		trash = trashRepository.save(trash);
		kafkaProducer.sendMessage(KafkaTopicConfig.TRASH, trash);
		return trash;
	}

	public long countTrash() {
		return trashRepository.count();
	}
}

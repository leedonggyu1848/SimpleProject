package my.project.simple.service;

import lombok.RequiredArgsConstructor;
import my.project.simple.domain.Trash;
import my.project.simple.domain.TrashRead;
import my.project.simple.repository.TrashReadRepository;
import my.project.simple.repository.TrashRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashService {
	private final TrashRepository trashRepository;
	private final TrashReadRepository trashReadRepository;

	public Trash addTrash(String content) {
		Trash trash = new Trash();
		trash.setContent(content);
		trash = trashRepository.save(trash);
		trashReadRepository.save(new TrashRead(trash.getId(), trash.getContent()));
		return trash;
	}

	public long countTrash() {
		return trashRepository.count();
	}

	public long countRead() {
		return trashReadRepository.count();
	}
}

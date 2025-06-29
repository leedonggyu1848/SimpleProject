package my.project.simple.controller;

import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import my.project.simple.domain.Trash;
import my.project.simple.service.TrashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trash")
public class TrashController {
	private final TrashService trashService;
	private final Random random = new Random();

	@GetMapping("/random")
	public ResponseEntity<Object> random() {
		int randomNumber = random.nextInt(1000);
		Trash newTrash = new Trash();
		newTrash.setContent(String.valueOf(randomNumber));
		trashService.addTrash(newTrash.getContent());
		return ResponseEntity.ok()
				.body(Map.of( "value", randomNumber));
	}

	@GetMapping("/add")
	public ResponseEntity<Object> add() {
		int randomNumber = random.nextInt(1000);
		Trash newTrash = new Trash();
		newTrash.setContent(String.valueOf(randomNumber));
		newTrash = trashService.addTrash(newTrash.getContent());
		return ResponseEntity.ok()
				.body(Map.of("value", newTrash,
						"DB count", trashService.countTrash()));
	}

	@GetMapping("/count")
	public ResponseEntity<Object> count() {
		long count = trashService.countTrash();
		return ResponseEntity.ok()
				.body(Map.of("count", count));
	}
}

package my.project.simple.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import my.project.simple.domain.Trash;
import my.project.simple.service.TrashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class PingController {
	private final Random random = new Random();
	private final TrashService trashService;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

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
}

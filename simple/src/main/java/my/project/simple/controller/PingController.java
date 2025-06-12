package my.project.simple.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class PingController {
	private final Random random = new Random();
	private final List<Trash> trash = new ArrayList<>();
	@Data
	static public class Trash {
		private String value;
	}

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("/random")
	public ResponseEntity<Object> random() {
		int randomNumber = random.nextInt(1000);
		return ResponseEntity.ok()
				.body(Map.of( "value", randomNumber));
	}

	@GetMapping("/add")
	public ResponseEntity<Object> add() {
		int randomNumber = random.nextInt(1000);
		Trash newTrash = new Trash();
		newTrash.setValue(String.valueOf(randomNumber));
		trash.add(newTrash);
		return ResponseEntity.ok()
				.body(Map.of("value", newTrash,
						"count", trash.size()));
	}
}

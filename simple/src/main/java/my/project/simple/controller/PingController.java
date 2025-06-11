package my.project.simple.controller;

import java.util.Map;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class PingController {
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("random")
	public ResponseEntity<Map<String, Object>> random() {
		Random random = new Random();
		int randomNumber = random.nextInt(1000);
		return ResponseEntity.ok()
				.body(Map.of( "value", randomNumber));
	}
}

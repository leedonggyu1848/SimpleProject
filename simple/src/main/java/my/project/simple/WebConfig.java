package my.project.simple;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")                 // 모든 경로에 대해
				.allowedOrigins("http://localhost:5173")  // 허용할 도메인들
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")      // 허용할 HTTP 메서드
				.allowedHeaders("*")                // 허용할 헤더
				.allowCredentials(true)             // 인증정보 허용 (쿠키 등)
				.maxAge(3600);                     // preflight 요청 캐시 시간 (초)
	}
}

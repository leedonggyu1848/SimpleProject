package my.project.simple;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.allowed}")
	private String allowedOrigins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")                 // 모든 경로에 대해
				.allowedOrigins(allowedOrigins)  // 허용할 도메인들
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")      // 허용할 HTTP 메서드
				.allowedHeaders("*")                // 허용할 헤더
				.allowCredentials(true)             // 인증정보 허용 (쿠키 등)
				.maxAge(3600);                     // preflight 요청 캐시 시간 (초)
	}
}

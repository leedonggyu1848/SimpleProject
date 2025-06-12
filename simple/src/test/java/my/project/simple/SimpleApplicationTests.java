package my.project.simple;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class SimpleApplicationTests {

	@Test
	void contextLoads() {
		String target = "abc";
		assertThat(target).isEqualTo("abc");
	}

}

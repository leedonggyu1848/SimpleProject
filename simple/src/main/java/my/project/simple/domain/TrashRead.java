package my.project.simple.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TrashRead {
	private Long id;
	private String content;

	public TrashRead() {
	}

	public TrashRead(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

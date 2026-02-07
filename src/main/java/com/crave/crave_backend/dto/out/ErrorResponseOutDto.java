package com.crave.crave_backend.dto.out;

import java.time.Instant;
import java.util.List;

public class ErrorResponseOutDto {
	
	private List<String> message;

	private String timestamp;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ErrorResponseOutDto(List<String> message) {
		super();
		this.message = message;
		this.timestamp = Instant.now().toString();
	}

	public ErrorResponseOutDto() {
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponseOutDto [message=" + message + ", timestamp=" + timestamp + "]";
	}
}

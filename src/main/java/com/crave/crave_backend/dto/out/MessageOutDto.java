package com.crave.crave_backend.dto.out;

public class MessageOutDto {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageOutDto(String message) {
		super();
		this.message = message;
	}

	public MessageOutDto() {
	}

	@Override
	public String toString() {
		return "MessageOutDto [message=" + message + "]";
	}
}

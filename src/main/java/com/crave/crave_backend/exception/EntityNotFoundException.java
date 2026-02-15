package com.crave.crave_backend.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String logEvent;
	
	private String entity;
	
	private Object id;
	
	private String message;

	public EntityNotFoundException(String logEvent, String entity, Object id, String message) {
		super();
		this.logEvent = logEvent;
		this.entity = entity;
		this.id = id;
		this.message = message;
	}

	public String getLogEvent() {
		return logEvent;
	}

	public void setLogEvent(String logEvent) {
		this.logEvent = logEvent;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

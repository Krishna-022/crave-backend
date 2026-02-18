package com.crave.crave_backend.exception;

import java.util.List;

public class EntityConflictException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private List<String> messageList;

	private List<String> conflictingFieldsList;

	private String logMessage;

	public EntityConflictException(List<String> messageList, List<String> conflictingFieldsList, String logMessage) {
		this.messageList = messageList;
		this.conflictingFieldsList = conflictingFieldsList;
		this.logMessage = logMessage;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public List<String> getConflictingFieldsList() {
		return conflictingFieldsList;
	}

	public void setConflictingFieldsList(List<String> ConflictingFieldsList) {
		this.conflictingFieldsList = ConflictingFieldsList;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
}

package com.crave.crave_backend.exception.exceptionTranslator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import com.crave.crave_backend.constant.DatabaseConstraintNames;
import com.crave.crave_backend.constant.EntityConflictLogConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.constant.LogEventConstants;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.EntityConflictException;

public final class PersistenceExceptionTranslator {
	
	public static EntityConflictException translateUserDataIntegrityViolation(DataIntegrityViolationException dataIntegrityViolationException, User user) {
		String info = dataIntegrityViolationException.getMostSpecificCause().toString().toLowerCase();
		String entity = user.getClass().getSimpleName();
		List<String> messageList = new ArrayList<>();
		List<String> conflictingFieldsList = new ArrayList<String>();

		if (info.contains(DatabaseConstraintNames.UNIQUE_CONTACT_NUMBER)) {
			messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.CONTACT_NUMBER, user.getContactNumber()));
			conflictingFieldsList.add(EntityConflictLogConstants.CONTACT_NUMBER);
		} 
		else if (info.contains(DatabaseConstraintNames.UNIQUE_EMAIL)) {
			messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.EMAIL, user.getEmail()));
			conflictingFieldsList.add(EntityConflictLogConstants.EMAIL);
		} 
		else {
			messageList.add(ErrorMessageConstants.DATA_INTEGRITY_VIOLATION);
		}
		return new EntityConflictException(messageList, conflictingFieldsList, LogEventConstants.REGISTRATION_FAILED);
	}
	
	public static EntityConflictException translateRestaurantDataIntegrityViolation(DataIntegrityViolationException ex, Restaurant restaurant) {
		String info = ex.getMostSpecificCause().toString().toLowerCase();
		String entity = restaurant.getClass().getSimpleName();
		List<String> messageList = new ArrayList<>();
		List<String> conflictingFieldsList = new ArrayList<String>();
		
		if (info.contains(DatabaseConstraintNames.UNIQUE_CONTACT_NUMBER)) {
			messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.CONTACT_NUMBER, restaurant.getContactNumber()));
			conflictingFieldsList.add(EntityConflictLogConstants.CONTACT_NUMBER);
		} else if (info.contains(DatabaseConstraintNames.UNIQUE_EMAIL)) {
			messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.EMAIL, restaurant.getEmail()));
			conflictingFieldsList.add(EntityConflictLogConstants.EMAIL);
		} else if (info.contains(DatabaseConstraintNames.UNIQUE_NAME)) {
			messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.NAME, restaurant.getName()));
			conflictingFieldsList.add(EntityConflictLogConstants.NAME);
		} else {
			messageList.add(ErrorMessageConstants.DATA_INTEGRITY_VIOLATION);
		}
		return new EntityConflictException(messageList, conflictingFieldsList, LogEventConstants.REGISTRATION_FAILED);
	}
}

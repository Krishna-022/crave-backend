package com.crave.crave_backend.validation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.constant.EntityConflictLogConstants;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.EntityConflictException;
import com.crave.crave_backend.repository.UserRepository;

@Component
public class UserValidation {
	
	private final UserRepository userRepository;
	
	public void validateRegistrationContactNumberAndEmail (String contactNumber, String email) {
		List<User> userList = userRepository.findByContactNumberOrEmail(contactNumber, email);
		
		if (userList.size() > 0) {
			List<String> messageList = new ArrayList<>();
			List<String> conflictingFieldsList = new ArrayList<>();
			String entity = User.class.getSimpleName();
			
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				if (contactNumber.equals(user.getContactNumber())) {
		            messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.CONTACT_NUMBER, contactNumber));
		            conflictingFieldsList.add(EntityConflictLogConstants.CONTACT_NUMBER);
		        }
				if (email.equals(user.getEmail())) {
		            messageList.add(String.format(ErrorMessageConstants.ENTITY_CONFLICT, entity, EntityConflictLogConstants.EMAIL, email));
		            conflictingFieldsList.add(EntityConflictLogConstants.EMAIL);
		        }
			}
			throw new EntityConflictException(messageList, conflictingFieldsList, EntityConflictLogConstants.USER);
		}
	}

	public UserValidation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}	
}

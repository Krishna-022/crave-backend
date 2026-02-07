package com.crave.crave_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByContactNumberOrEmail(String contactNumber, String Email);
}

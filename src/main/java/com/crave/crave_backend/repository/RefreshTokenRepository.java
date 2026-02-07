package com.crave.crave_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.crave.crave_backend.entity.RefreshToken;
import jakarta.persistence.LockModeType;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<RefreshToken> findByRefreshTokenHash(String refreshTokenHash);

	Integer deleteByUserId(Long userId);

	@Modifying
	@Query("""
			    update RefreshToken r
			    set r.refreshTokenHash = :newHash
			    where r.userId = :userId
			      and r.refreshTokenHash = :oldHash
			""")
	int rotateToken(Long userId, String oldHash, String newHash);
}

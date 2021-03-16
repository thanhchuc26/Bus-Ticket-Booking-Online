package com.softech.tripnow.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softech.tripnow.models.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long>{
	Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);	
}

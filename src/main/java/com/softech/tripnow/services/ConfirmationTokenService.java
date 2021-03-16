package com.softech.tripnow.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.softech.tripnow.models.ConfirmationToken;
import com.softech.tripnow.repositories.ConfirmationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
	
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	
	void deleteConfirmationToken(Long id) {
		confirmationTokenRepository.deleteById(id);
	}
	
	public Optional<ConfirmationToken> findConfirmationTokenByToken(String token){
		return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
	}
	
}

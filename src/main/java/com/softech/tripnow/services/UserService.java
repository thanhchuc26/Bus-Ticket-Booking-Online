package com.softech.tripnow.services;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.softech.tripnow.models.ConfirmationToken;
import com.softech.tripnow.models.User;
import com.softech.tripnow.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private ConfirmationTokenService confirmationTokenService;

	private EmailSenderService emailSenderService;

	void sendConfirmationMail(String userMail, String token) {

		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userMail);
		mailMessage.setSubject("Confirm TripNow Account Registration!");
		mailMessage.setFrom("<tripnowcompany@gmail.com>");
		mailMessage.setText("Thank you for registering for TripNow. Please click on the below link to activate your account."
				+ "http://localhost:8989/sign-up/confirm?token=" + token);

		emailSenderService.sendEmail(mailMessage);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Optional<User> optional = userRepository.findByEmail(email);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
		}
	}

	public void signUpUser(User user) {
		final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encryptedPassword);
		;

		final User createdUser = userRepository.save(user);

		ConfirmationToken token = new ConfirmationToken(user);

		confirmationTokenService.saveConfirmationToken(token);
		;

	}

	public void confirmUser(ConfirmationToken confirmationToken) {
		User user = confirmationToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		confirmationTokenService.deleteConfirmationToken(user.getId());

	}
}

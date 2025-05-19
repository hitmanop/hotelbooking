package com.springboot.hotelbooking.security;

import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.dto.LoginDto;
import com.springboot.hotelbooking.dto.SignUpDto;
import com.springboot.hotelbooking.dto.UserDto;
import com.springboot.hotelbooking.entity.User;
import com.springboot.hotelbooking.entity.enums.Role;
import com.springboot.hotelbooking.exception.ResourceNotFoundException;
import com.springboot.hotelbooking.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;

	public UserDto signUp(SignUpDto signUpRequest) {

		Optional<User> user = userRepository.findByEmail(signUpRequest.getEmail());
		if (user.isPresent()) {
			throw new RuntimeException("User is already present with same email id");
		}

		User newUser = modelMapper.map(signUpRequest, User.class);
		newUser.setRoles(Set.of(Role.GUEST));
		newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		newUser = userRepository.save(newUser);

		return modelMapper.map(newUser, UserDto.class);

	}

	public String[] login(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		User user = (User) authentication.getPrincipal();
		String[] arr = new String[2];
		arr[0] = jwtService.generateAccessToken(user);
		arr[1] = jwtService.generateRefreshToken(user);
		return arr;

	}

	public String refreshToken(String refreshToken) {
		Long id = jwtService.getUserIdFromToken(refreshToken);

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with         id: " + id));
		return jwtService.generateAccessToken(user);
	}

}

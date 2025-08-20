package jp.levtech.rookie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.levtech.rookie.service.LoginService;
import jp.levtech.rookie.user.User;
import jp.levtech.rookie.user.UserRepository;

@Service
public class LoginServiceImple implements LoginService, UserDetailsService {

	@Autowired
	UserRepository repository;
	
	@Override
	public User login(String id) {
		return repository.findByUsername(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + id));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Attempting to load user by username: " + username);
		User user = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		System.out.println("Successfully loaded user details for: " + username);
		return user;
	}

}

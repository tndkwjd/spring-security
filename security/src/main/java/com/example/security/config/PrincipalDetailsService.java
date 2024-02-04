package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.auth.PrincipalDetails;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository UserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	System.out.println("@@@@@@@@@@@@@@@@@@@@2"+username);
		User userEntity = UserRepository.findByUsername(username);

		if (userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}

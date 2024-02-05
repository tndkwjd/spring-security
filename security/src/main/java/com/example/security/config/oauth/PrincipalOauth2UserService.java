package com.example.security.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
		System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oauth2User = super.loadUser(userRequest);

		System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());
		
		String provider = userRequest.getClientRegistration().getClientId();
		String providerId = oauth2User.getAttribute("sub");
		String username = provider+"_"+providerId; 
		String password = bCryptPasswordEncoder.encode("일이삼사");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			System.out.println("구글 로그인이 최초입니다.");
			userEntity = User.builder()
			.username(username)
			.password(password)
			.email(email)
			.role(role)
			.provider(provider)
			.providerId(providerId)
			.build();
			userRepository.save(userEntity);
		} else { 
			System.out.println("구글 로그인을 이미 한 적이 있습니다.");
		}
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}

}

package com.kcs.attendancesystem.config;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.User;
import com.kcs.attendancesystem.dto.JwtUser;
import com.kcs.attendancesystem.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findOneByUsername(username);
		
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return new JwtUser(user.getId(), user.getUsername(), user.getName(), user.getPassword(), null, true, StringUtils.EMPTY);
		}
		
		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));    	    
	}
}
package com.kcs.attendancesystem.config;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (Objects.nonNull(object) && object instanceof UserDetails) {
			String username = ((UserDetails) object).getUsername();
			return Optional.of(username);
		}
		
		return Optional.of("");
	}

}

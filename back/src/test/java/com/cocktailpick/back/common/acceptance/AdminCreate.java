package com.cocktailpick.back.common.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;

@Service
@Profile("local")
public class AdminCreate {
    public static final String ADMIN_NAME = "두강";
    public static final String ADMIN_EMAIL = "cocktailpick@gamil.com";
    public static final String ADMIN_PASSWORD = "Password1!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Transactional
	public void execute() {
		User user = User.builder()
			.name(ADMIN_NAME)
			.email(ADMIN_EMAIL)
			.provider(AuthProvider.local)
			.password(passwordEncoder.encode(ADMIN_PASSWORD))
			.role(Role.ROLE_ADMIN)
			.build();

        userRepository.save(user);
    }
}

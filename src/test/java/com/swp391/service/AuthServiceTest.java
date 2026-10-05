package com.swp391.service;

import com.swp391.model.User;
import com.swp391.repository.UserRepository;
import com.swp391.security.PasswordHasher;
import com.swp391.security.TokenService;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private TokenService tokenService;
    private AuthService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        tokenService = mock(TokenService.class);
        service = new AuthService(userRepository, passwordHasher, tokenService);
    }

    @Test
    void login_validCredentials_returnsToken() {
        User user = new User(1, "user@test.com", "hash", true);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("secret", "hash")).thenReturn(true);
        when(tokenService.generate(1)).thenReturn("token-123");

        assertEquals("token-123", service.login("user@test.com", "secret"));
        verify(tokenService).generate(1);
    }

    @Test
    void login_missingEmail_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.login("", "secret"));
        verifyNoInteractions(userRepository);
    }

    @Test
    void login_missingPassword_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.login("user@test.com", ""));
        verifyNoInteractions(userRepository);
    }

    @Test
    void login_unknownUser_throwsException() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> service.login("unknown@test.com", "secret"));
    }

    @Test
    void login_inactiveUser_throwsException() {
        User user = new User(2, "inactive@test.com", "hash", false);
        when(userRepository.findByEmail("inactive@test.com")).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class,
                () -> service.login("inactive@test.com", "secret"));
        verifyNoInteractions(passwordHasher);
    }

    @Test
    void login_wrongPassword_throwsException() {
        User user = new User(3, "user@test.com", "hash", true);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("wrong", "hash")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> service.login("user@test.com", "wrong"));
        verifyNoInteractions(tokenService);
    }

@Test
void login_nullEmail_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> service.login(null, "secret"));
    verifyNoInteractions(userRepository);
}

@Test
void login_nullPassword_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> service.login("user@test.com", null));
    verifyNoInteractions(userRepository);
}

}

package vitalitus.springtestproject.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitalitus.springtestproject.dto.CreateUserRequestDto;
import vitalitus.springtestproject.dto.UserDto;
import vitalitus.springtestproject.dto.UserLoginRequestDto;
import vitalitus.springtestproject.dto.UserLoginResponseDto;
import vitalitus.springtestproject.exception.RegistrationException;
import vitalitus.springtestproject.mapper.UserMapper;
import vitalitus.springtestproject.model.Role;
import vitalitus.springtestproject.model.User;
import vitalitus.springtestproject.repository.cart.ShoppingCartRepository;
import vitalitus.springtestproject.repository.role.RoleRepository;
import vitalitus.springtestproject.repository.user.UserRepository;
import vitalitus.springtestproject.service.AuthenticationService;
import vitalitus.springtestproject.service.ShoppingCartService;
import vitalitus.springtestproject.util.JwtUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserDto registrationUser(CreateUserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RegistrationException("This email "
                    + userRequestDto.getEmail() + " is already registered, try to login");
        }
        User user = userMapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        shoppingCartService.registerNewShoppingCart(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(),
                        userLoginRequestDto.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}

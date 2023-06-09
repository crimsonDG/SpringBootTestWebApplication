package com.security.service;

import com.core.client.AuthFeignClient;
import com.core.domain.Role;
import com.core.domain.User;
import com.core.exception.NotFoundException;
import com.core.model.UserDto;
import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import com.core.repository.RoleRepository;
import com.core.repository.UserRepository;
import com.security.redis.CustomPage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userCache")
public class UserService {

    private final AuthFeignClient authFeignClient;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Cacheable(cacheNames = "user")
    public boolean saveUser(UserDto userDto) {
        Optional<User> userFromDB = userRepository.findByLogin(userDto.getLogin());

        if (userFromDB.isPresent()) {
            return false;
        }

        User user = modelMapper.map(userDto, User.class);

        String roleUser = "USER";
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(roleUser)
                .orElseThrow(() -> new NotFoundException("Error: Role is not found.")); //RoleException
        roles.add(userRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @CachePut(cacheNames = "user", key = "#id")
    public boolean updateUser(UserDto user, long id) {

        UserDto currentUser = findUserById(id);

        if (currentUser == null) {
            return false;
        }

        currentUser.setLogin(user.getLogin());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setRoles(user.getRoles());

        userRepository.save(modelMapper.map(currentUser, User.class));

        return true;
    }

    @CacheEvict(cacheNames = "user", key = "#id")
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid user Id:" + id)); //UserException

        Role userRole = roleRepository.findByName(user.getRoles().iterator().next().getName())
                .orElseThrow(() -> new NotFoundException("Role is not found.")); //RoleException

        user.removeRole(userRole);
        userRepository.delete(user);
    }

    @Cacheable(cacheNames = "users")
    public CustomPage<UserDto> findAllUsers(int page, int size) {
        return new CustomPage<>(userConverter(userRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "users")
    public CustomPage<UserDto> sortAllUsersByAsc(int page, int size) {
        return new CustomPage<>(userConverter(userRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "users")
    public CustomPage<UserDto> sortAllUsersByDesc(int page, int size) {
        return new CustomPage<>(userConverter(userRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "user", key = "#value")
    public UserDto findUserByLogin(String value) {
        return modelMapper.map(userRepository.findByLogin(value)
                .orElseThrow(() -> new NotFoundException(value + " user is not found!")), UserDto.class);
    }

    @Cacheable(cacheNames = "user", key = "#id")
    public UserDto findUserById(long id) {
        return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)), UserDto.class);
    }

    private Page<UserDto> userConverter(Page<User> usersToDto) {
        return usersToDto.map(u -> modelMapper.map(u, UserDto.class));
    }

    public UserTokenDto authUser(UserAccessDto userAccessDto) {
        return authFeignClient.auth(userAccessDto);
    }
}

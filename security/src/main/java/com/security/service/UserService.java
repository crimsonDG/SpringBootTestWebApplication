package com.security.service;

import com.core.client.AuthFeingClient;
import com.core.model.UserDto;
import com.core.domain.Role;
import com.core.domain.User;
import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import com.core.repository.RoleRepository;
import com.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthFeingClient authFeingClient;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " user is not found!"));
        return UserDetailsImpl.build(user);
    }

    public boolean saveUser(UserDto userDto) {
        Optional<User> userFromDB = userRepository.findByLogin(userDto.getLogin());

        if (userFromDB.isPresent()) {
            return false;
        }

        User user = modelMapper.map(userDto, User.class);

        String roleUser = "USER";
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(roleUser)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

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

    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        Role userRole = roleRepository.findByName(user.getRoles().iterator().next().getName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.removeRole(userRole);
        userRepository.delete(user);
    }

    public Page<UserDto> findAllUsers(int page, int size) {
        return userConverter(userRepository.findAll(PageRequest.of(page, size)));
    }

    public Page<UserDto> sortAllUsersByAsc(int page, int size) {
        return userConverter(userRepository.ascSorted(PageRequest.of(page, size)));
    }

    public Page<UserDto> sortAllUsersByDesc(int page, int size) {
        return userConverter(userRepository.descSorted(PageRequest.of(page, size)));
    }

    public UserDto findUserByLogin(String value) throws UsernameNotFoundException {
        return modelMapper.map(userRepository.findByLogin(value)
                        .orElseThrow(() -> new UsernameNotFoundException(value + " user is not found!")),
                UserDto.class);
    }

    public UserDto findUserById(long id) {
        return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)), UserDto.class);
    }

    private Page<UserDto> userConverter(Page<User> usersToDto) {
        return usersToDto.map(u -> modelMapper.map(u, UserDto.class));
    }

    public UserTokenDto authUser(UserAccessDto userAccessDto){
        return authFeingClient.auth(userAccessDto);
    }
}

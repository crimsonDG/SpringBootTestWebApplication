package com.security.service;


import com.core.model.UserDto;
import com.core.domain.Role;
import com.core.domain.User;
import com.core.repository.RoleRepository;
import com.core.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired//(required = false)
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        return UserDetailsImpl.build(user);
    }

    public boolean saveUser(UserDto userDto) {
        User userFromDB = userRepository.findByLogin(userDto.getLogin());

        if (userFromDB != null) {
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

        if (currentUser == null)
            return false;

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

    public List<UserDto> findAllUsers() {
        return userConverter((List<User>) userRepository.findAll());
    }

    public List<UserDto> sortAllUsersByAsc() {
        return userConverter((List<User>) userRepository.ascSorted());
    }

    public List<UserDto> sortAllUsersByDesc() {
        return userConverter((List<User>) userRepository.descSorted());
    }

    public UserDto findUserByLogin(String value) {
        return modelMapper.map(userRepository.findByLogin(value), UserDto.class);
    }

    public UserDto findUserById(long id) {
        return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)), UserDto.class);
    }

    private List<UserDto> userConverter(List<User> usersToDto){
        return usersToDto.stream().map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }
}

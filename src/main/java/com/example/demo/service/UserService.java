package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
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

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        return UserDetailsImpl.build(user);
    }

    public boolean saveUser(User user, boolean update) {
        User userFromDB = userRepository.findByLogin(user.getLogin());

        if (userFromDB != null) {
            return false;
        }

        if (!update) {
            String roleUser = "USER";
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(roleUser)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> sortAllUsersByAsc() {
        return (List<User>) userRepository.ascSorted();
    }

    public List<User> sortAllUsersByDesc() {
        return (List<User>) userRepository.descSorted();
    }

    public User findUserByLogin(String value) {
        return userRepository.findByLogin(value);
    }

    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }
}

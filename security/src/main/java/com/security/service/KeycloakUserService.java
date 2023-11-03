package com.security.service;

import com.core.client.AuthFeignClient;
import com.core.domain.KeycloakCredential;
import com.core.domain.KeycloakEntity;
import com.core.domain.KeycloakRole;
import com.core.exception.ErrorCode;
import com.core.exception.MainException;
import com.core.model.BaseUserDto;
import com.core.model.KeycloakCredentialDto;
import com.core.model.KeycloakEntityDto;
import com.core.model.KeycloakRoleDto;
import com.core.model.template.UserAccess;
import com.core.model.template.UserToken;
import com.core.repository.KeycloakCredentialRepository;
import com.core.repository.KeycloakEntityRepository;
import com.core.repository.KeycloakRoleRepository;
import com.redis.config.CustomPage;
import com.security.common.KeycloakValues;
import com.security.encoder.KeycloakCustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userCache")
public class KeycloakUserService {

    private final AuthFeignClient authFeignClient;

    private final ModelMapper modelMapper;

    private final KeycloakEntityRepository keycloakEntityRepository;

    private final KeycloakRoleRepository keycloakRoleRepository;

    private final KeycloakCredentialRepository keycloakCredentialRepository;

    private final KeycloakValues keycloakValues;

    @Cacheable(cacheNames = "user")
    public boolean saveUser(BaseUserDto baseUserDto) {
        Optional<KeycloakEntity> userFromDB = keycloakEntityRepository.findByUsername(baseUserDto.getUsername());
        if (userFromDB.isPresent()) {
            return false;
        }

        KeycloakEntity currentUser = modelMapper.map(buildUser(baseUserDto), KeycloakEntity.class);
        keycloakEntityRepository.save(currentUser);

        KeycloakCredential userCredential = modelMapper.map(currentUser.getCredentials().iterator().next(), KeycloakCredential.class);
        userCredential.setUser(currentUser);
        keycloakCredentialRepository.save(userCredential);

        return true;
    }

    @CacheEvict(cacheNames = "user", key = "#id")
    public void deleteUser(String id) {
        KeycloakEntity user = keycloakEntityRepository.findById(id)
                .orElseThrow(() -> new MainException(ErrorCode.DEMO_USER_NOT_FOUND));

        KeycloakRole userRole = keycloakRoleRepository.findByName(user.getRoles().iterator().next().getName())
                .orElseThrow(() -> new MainException(ErrorCode.DEMO_ROLE_NOT_FOUND));

        KeycloakCredential userCredential = keycloakCredentialRepository.findByUserId(user.getId())
                .orElseThrow(() -> new MainException(ErrorCode.DEMO_CREDENTIAL_NOT_FOUND));

        user.removeCredential(userCredential);
        keycloakCredentialRepository.delete(userCredential);

        user.removeRole(userRole);

        keycloakEntityRepository.delete(user);
    }

    @CachePut(cacheNames = "user", key = "#id")
    public boolean updateUser(BaseUserDto user, String id) {

        KeycloakEntityDto currentUser = findUserById(id);

        if (currentUser == null) {
            return false;
        }

        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setRoles(getRole(user.getRole()));
        currentUser.getCredentials().iterator().next().setSecretData(initSecretData(user.getPassword()));

        keycloakEntityRepository.save(modelMapper.map(currentUser, KeycloakEntity.class));

        return true;
    }

    @Cacheable(cacheNames = "user", key = "#value")
    public KeycloakEntityDto findUserByUsername(String value) {
        Optional<KeycloakEntity> userFromDB = keycloakEntityRepository.findByUsername(value);
        return userFromDB.map(keycloakEntity -> modelMapper.map(keycloakEntity, KeycloakEntityDto.class)).orElse(null);
    }

    @Cacheable(cacheNames = "user", key = "#value")
    public BaseUserDto getBaseUserDto(String value) {
        Optional<KeycloakEntity> userFromDB = keycloakEntityRepository.findByUsername(value);
        BaseUserDto baseUserDto = userFromDB.map(keycloakEntity -> modelMapper.map(keycloakEntity, BaseUserDto.class)).orElse(null);
        if (baseUserDto != null) {
            baseUserDto.setPassword(userFromDB.get().getCredentials().iterator().next().getSecretData());
        }
        return baseUserDto;
    }

    @Cacheable(cacheNames = "users")
    public List<KeycloakEntityDto> getUserList() {
        return keycloakEntityRepository.findAll()
                .stream()
                .map(list -> modelMapper.map(list, KeycloakEntityDto.class))
                .collect(Collectors.toList());
    }


    @Cacheable(cacheNames = "users")
    public CustomPage<KeycloakEntityDto> findAllUsers(int page, int size) {
        return new CustomPage<>(userConverter(keycloakEntityRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "users")
    public CustomPage<KeycloakEntityDto> sortAllUsersByAsc(int page, int size) {
        return new CustomPage<>(userConverter(keycloakEntityRepository.ascSorted(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "users")
    public CustomPage<KeycloakEntityDto> sortAllUsersByDesc(int page, int size) {
        return new CustomPage<>(userConverter(keycloakEntityRepository.descSorted(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "user", key = "#id")
    public KeycloakEntityDto findUserById(String id) {
        return modelMapper.map(keycloakEntityRepository.findById(id)
                .orElseThrow(() -> new MainException(ErrorCode.DEMO_USER_NOT_FOUND)), KeycloakEntityDto.class);
    }

    private Page<KeycloakEntityDto> userConverter(Page<KeycloakEntity> usersToDto) {
        return usersToDto.map(u -> modelMapper.map(u, KeycloakEntityDto.class));
    }

    private KeycloakEntityDto buildUser(BaseUserDto baseUserDto) {

        long timestamp = System.currentTimeMillis();

        //Build User
        KeycloakEntityDto currentUserDto = KeycloakEntityDto.builder()
                .id(UUID.randomUUID().toString())
                .enabled(true)
                .username(baseUserDto.getUsername())
                .email(baseUserDto.getEmail())
                .realmId(keycloakValues.realmId())
                .emailConstraint(baseUserDto.getEmail())
                .emailVerified(false)
                .createdTimestamp(timestamp)
                .build();

        //Build Credential
        KeycloakCredentialDto currentCredentialDto = KeycloakCredentialDto.builder()
                .id(UUID.randomUUID().toString())
                .type(keycloakValues.credentialType())
                .createdDate(currentUserDto.getCreatedTimestamp())
                .userLabel(keycloakValues.userLabel())
                .secretData(initSecretData(baseUserDto.getPassword()))
                .credentialData(keycloakValues.credentialData())
                .priority(10)
                .build();

        //Set Role for User
        currentUserDto.setRoles(getRole("USER"));

        currentUserDto.setCredentials(Collections.singletonList(currentCredentialDto));

        return currentUserDto;
    }

    private Set<KeycloakRoleDto> getRole(String roleName) {
        Set<KeycloakRoleDto> roles = new HashSet<>();
        KeycloakRoleDto userRole = modelMapper.map(keycloakRoleRepository.findByName(roleName)
                .orElseThrow(() -> new MainException(ErrorCode.DEMO_ROLE_NOT_FOUND)), KeycloakRoleDto.class);
        roles.add(userRole);

        return roles;
    }

    private String initSecretData(String rawPassword) {
        KeycloakCustomPasswordEncoder pbkdf2Encoder = new KeycloakCustomPasswordEncoder(rawPassword, 27500, 256);

        return String.format("{\"value\":\"%s\",\"salt\":\"%s\",\"additionalParameters\":{}}",
                pbkdf2Encoder.getHashedPassword(), pbkdf2Encoder.getSalt());
    }

    public String getTokenUrl() {
        return keycloakValues.tokenUrl();
    }

    public UserToken authUser(@RequestBody UserAccess userAccess) {
        return authFeignClient.auth(userAccess);
    }
}

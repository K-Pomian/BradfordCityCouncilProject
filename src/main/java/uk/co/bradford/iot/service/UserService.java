package uk.co.bradford.iot.service;

import uk.co.bradford.iot.model.Role;
import uk.co.bradford.iot.model.User;
import uk.co.bradford.iot.repository.RoleRepository;
import uk.co.bradford.iot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        String[] rolesArray = user.getRole().split(",");
        ArrayList<Role> userRoles = new ArrayList<>();
        for (String singleRole : rolesArray) {
            userRoles.add(roleRepository.findByRole(singleRole));
        }
        user.setRoles(new HashSet<>(userRoles));
        return userRepository.save(user);
    }

}
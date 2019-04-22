package com.delta.fly.service;

import com.delta.fly.enumeration.RoleName;
import com.delta.fly.model.Role;
import com.delta.fly.model.SystemAdmin;
import com.delta.fly.repository.SystemAdminRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppLoader implements ApplicationRunner {

    private SystemAdminRepository systemAdminRepository;

    public AppLoader(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (systemAdminRepository.findAll().size() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Role role = new Role();
            role.setRoleName(RoleName.ROLE_SYSTEMADMIN);
            List<Role> roles = new ArrayList<>();
            roles.add(role);

            SystemAdmin admin = new SystemAdmin();
            admin.setUsername("admin@admin.adm");
            admin.setPassword(encoder.encode("deltaforce"));
            admin.setCity("Everywhere");
            admin.setFirstName("Alpha");
            admin.setLastName("Omega");
            admin.setPhoneNumber("+000 00 000 0000");
            admin.setRoles(roles);
            admin.setDeleted(false);
            admin.setActivated(false);

            systemAdminRepository.save(admin);
            System.out.println("Admin added.");

        }
    }
}

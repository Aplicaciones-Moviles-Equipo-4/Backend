package com.eventify.platform.iam.application.internal.eventhandlers;

import com.eventify.platform.iam.domain.model.commands.SeedRolesCommand;
import com.eventify.platform.iam.domain.model.aggregates.User;
import com.eventify.platform.iam.domain.model.valueobjects.Roles;
import com.eventify.platform.iam.domain.services.RoleCommandService;
import com.eventify.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.eventify.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.eventify.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * ApplicationReadyEventHandler class
 * This class is used to handle the ApplicationReadyEvent
 */
@Service
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptHashingService hashingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
    private static final String DEMO_PASSWORD = "prueba123";

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService, RoleRepository roleRepository, UserRepository userRepository, BCryptHashingService hashingService) {
        this.roleCommandService = roleCommandService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    /**
     * Handle the ApplicationReadyEvent
     * This method is used to seed the roles
     * @param event the ApplicationReadyEvent the event to handle
     */
    @EventListener
    @Transactional
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if roles seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        LOGGER.info("Roles seeding verification finished for {} at {}", applicationName, currentTimestamp());
        seedDemoUsers();
    }

    private void seedDemoUsers() {
        seedDemoUser("cliente_pro", Roles.ROLE_USER);
        seedDemoUser("organizador_vip", Roles.ROLE_USER);
        seedDemoUser("admin_eventify", Roles.ROLE_ADMIN);
        LOGGER.info("Demo users seeding verification finished at {}", currentTimestamp());
    }

    private void seedDemoUser(String username, Roles roleName) {
        var role = roleRepository.findFirstByNameOrderByIdAsc(roleName)
                .orElseThrow(() -> new IllegalStateException("Role not found while seeding demo users: " + roleName));
        var encodedPassword = hashingService.encode(DEMO_PASSWORD);
        var user = userRepository.findByUsername(username)
                .orElseGet(() -> new User(username, encodedPassword, List.of(role)));

        user.setPassword(encodedPassword);
        user.addRole(role);
        userRepository.save(user);
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}

package com.example.springbootrest.managers;


import com.example.springbootrest.exceptions.LoginInUseException;
import com.example.springbootrest.model.Administrator;
import com.example.springbootrest.model.Client;
import com.example.springbootrest.model.ResourceAdministrator;
import com.example.springbootrest.model.User;
import com.example.springbootrest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private Object lock = new Object();


    public List<User> readAllUser() {
        synchronized(lock) {
            return userRepository.readAll();
        }
    }

    public User addUser(String accessLevel, String login, String password ) throws LoginInUseException {
        synchronized(lock) {
            if (accessLevel.equals("ResourceAdministrator"))
                return userRepository.create(new ResourceAdministrator(UUID.randomUUID(), login, password, true, "ResourceAdministrator"));
            if (accessLevel.equals("Administrator"))
                return userRepository.create(new Administrator(UUID.randomUUID(), login, password, true, "Administrator"));
            if (accessLevel.equals("Client"))
                return userRepository.create(new Client(UUID.randomUUID(), login, password, true, "Client"));
            return null;
        }
    }

    public User updateUser(UUID uuid, String login, String password) throws LoginInUseException {
        synchronized(lock) {
            User user = userRepository.readById(uuid);
            String accessLevel = user.getClass().getSimpleName();
            if (accessLevel.equals("ResourceAdministrator"))
                return userRepository.update(new ResourceAdministrator(uuid, login, password, user.getActive(), "ResourceAdministrator"));
            if (accessLevel.equals("Administrator"))
                return userRepository.update(new Administrator(uuid, login, password, user.getActive(), "Administrator"));
            if (accessLevel.equals("Client"))
                return userRepository.update(new Client(uuid, login, password, user.getActive(), "Client"));
            return null;
        }
    }


    public User readOneUser(UUID uuid ) {
        synchronized(lock) {
            return userRepository.readById(uuid);
        }
    }

    public List<User> readManyUser(String login) {
        synchronized(lock) {
            return userRepository.readManyByLogin(login);
        }
    }


    public User deactivateUser(UUID uuid) {
        synchronized(lock) {
            return userRepository.deactivate(uuid);
        }
    }

    public User activateUser(UUID uuid) {
        synchronized(lock) {
            return userRepository.activate(uuid);
        }
    }


}

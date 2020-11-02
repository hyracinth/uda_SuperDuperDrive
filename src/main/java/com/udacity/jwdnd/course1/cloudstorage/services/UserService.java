package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * This service handles logic regarding User object
 */
@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final HashService hashService;
    private final UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    /**
     * This method returns a user from database given username
     *
     * @param username username to bring back from db
     * @return user object
     */
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    /**
     * This method checks if username is available in database
     *
     * @param username value to be checked in db
     * @return true if available; false if not available
     */
    public boolean isUserAvailable(String username) {
        try {
            return userMapper.getUser(username) == null;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * This method creates a user
     *
     * @param user user to be added to database
     * @return number of rows affected
     */
    public Boolean createUser(User user) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
            Integer result = userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
            return result > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}

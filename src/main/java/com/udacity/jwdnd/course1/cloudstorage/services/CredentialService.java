package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * This service handles logic regarding Credential object
 */
@Service
public class CredentialService {
    private final Logger logger = LoggerFactory.getLogger(CredentialService.class);
    private final CredentialMapper _credentialMapper;
    private final EncryptionService _encryptionService;

    public CredentialService(CredentialMapper _credentialMapper, EncryptionService _encryptionService) {
        this._credentialMapper = _credentialMapper;
        this._encryptionService = _encryptionService;
    }

    /**
     * This method returns a list of Credentials given a username
     *
     * @param username username to search for credentials
     * @return a list of notes of credentials
     */
    public List<Credential> getCredentials(String username) {
        return this._credentialMapper.getCredentials(username);
    }

    /**
     * The method handles creating and updating credentials
     * If the credentials exist, it will update, else it will insert
     *
     * @param credIn credential to be updated or inserted
     * @return returns true if inserted / updated
     */
    public Boolean createUpdateCredential(Credential credIn) {
        try {
            Integer result = 0;
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = _encryptionService.encryptValue(credIn.getPassword(), encodedKey);

            credIn.setKey(encodedKey);
            credIn.setPassword(encryptedPassword);

            if (credIn.getCredentialId() != null) {
                result = this._credentialMapper.updateCredential(credIn);
            } else {
                result = this._credentialMapper.insertCredential(credIn);
            }
            return result > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * This method deletes a credential given an noteId
     *
     * @param credId credential to be deleted
     * @return returns true if delete successful
     */
    public Boolean deleteCredential(Integer credId) {
        try {
            _credentialMapper.deleteCredential(credId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}

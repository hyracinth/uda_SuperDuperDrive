package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper _credentialMapper;
    private EncryptionService _encryptionService;

    public CredentialService(CredentialMapper _credentialMapper, EncryptionService _encryptionService) {
        this._credentialMapper = _credentialMapper;
        this._encryptionService = _encryptionService;
    }

    public List<Credential> getCredentials (String username) {
        List<Credential> listCreds = this._credentialMapper.getCredentials(username);
        for(Credential currCred : listCreds) {
            String decryptedPassword = _encryptionService.decryptValue(currCred.getPassword(), currCred.getKey());
            currCred.setPassword(decryptedPassword);
        }
        return listCreds;
    }

    public Boolean deleteCredential(Integer credId) {
        try {
            _credentialMapper.deleteCredential(credId);
            return true;
        } catch(Exception e) {
            //TODO Log?
            System.out.println("Delete failed");
            return false;
        }
    }

    public Boolean createUpdateCredential(Credential credIn) {
        Integer result = 0;
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = _encryptionService.encryptValue(credIn.getPassword(), encodedKey);

        credIn.setKey(encodedKey);
        credIn.setPassword(encryptedPassword);

        if(credIn.getCredentialId() != null) {
            result = this._credentialMapper.updateCredential(credIn);
        }
        else {
            result = this._credentialMapper.insertCredential(credIn);
        }

        return result > 0;
    }
}

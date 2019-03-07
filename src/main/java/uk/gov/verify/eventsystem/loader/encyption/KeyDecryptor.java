package uk.gov.verify.eventsystem.loader.encyption;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;

import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.util.Base64;

public class KeyDecryptor {

    private AWSKMS kmsClient;

    @Inject
    public KeyDecryptor(AWSKMS kmsClient){
        this.kmsClient = kmsClient;
    }

    public byte[] decryptEncryptionKey(String encryptedKey){
        DecryptRequest req = new DecryptRequest().withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(encryptedKey)));
        ByteBuffer plainText = kmsClient.decrypt(req).getPlaintext();
        return plainText.array();
    }
}

package uk.gov.verify.eventsystem.loader.encyption;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;

import java.nio.ByteBuffer;
import java.util.Base64;

public class KeyDecryptor {

    public byte[] decryptEncryptionKey(String encryptedKey){
        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();
        DecryptRequest req = new DecryptRequest().withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(encryptedKey)));
        ByteBuffer plainText = kmsClient.decrypt(req).getPlaintext();
        return plainText.array();
    }
}

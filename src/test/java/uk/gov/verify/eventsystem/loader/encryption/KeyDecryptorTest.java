package uk.gov.verify.eventsystem.loader.encryption;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.verify.eventsystem.loader.encyption.KeyDecryptor;

import java.nio.ByteBuffer;
import java.util.Base64;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class KeyDecryptorTest {

    private static final String CIPHERTEXT = "some-ciphertext-blob";
    private static final String PLAINTEXT = "the-plain-text";
    private static final String KEY = "valid-encryption-key";
    private static final String ENCODED_CIPHERTEXT = Base64.getEncoder().encodeToString(CIPHERTEXT.getBytes());

    @Mock
    AWSKMS awskms;

    KeyDecryptor keyDecryptor;

    @Before
    public void setup(){
        keyDecryptor = new KeyDecryptor(awskms);
    }

    @Test
    public void testKeyDecryption() {
        when(awskms.decrypt(mockDecryptionRequest())).thenReturn(mockDecryptionResult());

        byte[] decrypted = keyDecryptor.decryptEncryptionKey(ENCODED_CIPHERTEXT);
        assertThat(decrypted).isEqualTo(PLAINTEXT.getBytes());
    }

    private DecryptResult mockDecryptionResult() {
        return new DecryptResult().withKeyId(KEY).withPlaintext(ByteBuffer.wrap(PLAINTEXT.getBytes()));
    }

    private DecryptRequest mockDecryptionRequest() {
        return new DecryptRequest().withCiphertextBlob(ByteBuffer.wrap(CIPHERTEXT.getBytes()));
    }

}

package uk.gov.verify.eventsystem.loader.configuration;

import com.amazonaws.regions.Regions;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.ida.eventemitter.Configuration;
import uk.gov.verify.eventsystem.loader.encyption.KeyDecryptor;

import javax.validation.Valid;
import java.net.URI;

public class EventEmitterConfiguration implements Configuration {
    @Valid
    @JsonProperty
    private boolean enabled;

    @Valid
    @JsonProperty
    private String accessKeyId;

    @Valid
    @JsonProperty
    private String secretAccessKey;

    @Valid
    @JsonProperty
    private Regions region;

    @Valid
    @JsonProperty
    private String encryptionKey;

    @Valid
    @JsonProperty
    private URI apiGatewayUrl;

    private EventEmitterConfiguration() { }

    @Override
    public boolean isEnabled() { return enabled; }

    @Override
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @Override
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    @Override
    public Regions getRegion() {
        return region;
    }

    @Override
    public byte[] getEncryptionKey() {
        KeyDecryptor encryptor = new KeyDecryptor();
        return encryptor.decryptEncryptionKey(encryptionKey);
    }

    @Override
    public URI getApiGatewayUrl() { return apiGatewayUrl; }
}


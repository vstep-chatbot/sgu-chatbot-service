package com.sguchatbot.backend.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@ConfigurationProperties("storage")
@Getter
@Setter
@NoArgsConstructor
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
}


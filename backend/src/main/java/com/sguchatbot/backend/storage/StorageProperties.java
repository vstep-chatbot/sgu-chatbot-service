package com.sguchatbot.backend.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
}


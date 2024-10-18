package com.sguchatbot.backend.storage;

import com.sguchatbot.backend.dto.UploadedFilesDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file, String folder);

    Stream<Path> loadAll();

    List<UploadedFilesDto> listUploadedFiles();

    void deleteFile(String filename, String type);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
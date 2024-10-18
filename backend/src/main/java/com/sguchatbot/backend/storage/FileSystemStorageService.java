package com.sguchatbot.backend.storage;

import com.sguchatbot.backend.dto.UploadedFilesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {

        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());

        if (!Files.exists(this.rootLocation)) {
            try {
                Files.createDirectories(this.rootLocation);
                log.info("Upload folder not exists, creating folder " + this.rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }

        log.info("File upload location: " + this.rootLocation);
        log.info(String.valueOf(this.rootLocation.toAbsolutePath()));
    }

    @Override
    public void store(MultipartFile file, String folder) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // Create the path to the folder
            Path rootLocationwithFolder = this.rootLocation.resolve(folder);

            // Create the folder if it does not exist
            if (!Files.exists(rootLocationwithFolder)) {
                Files.createDirectories(rootLocationwithFolder);
            }

            // Create the path to the file
            Path destinationFile = rootLocationwithFolder.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(rootLocationwithFolder.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            // if the file already exists, throw an exception
            if (Files.exists(destinationFile)) {
                throw new StorageException("File " + file.getOriginalFilename() + " đã tồn tại, hãy xoá file cũ trước khi tải lên.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Boolean checkFileExist(String filename, String folder) {
        Path fileToCheck = this.rootLocation.resolve(folder).resolve(filename);
        return Files.exists(fileToCheck);
    }

    public void deleteFile(String filename, String folder) {
        log.info("Deleting file from disk: " + filename);
        try {
            Path fileToDelete = this.rootLocation.resolve(folder).resolve(filename);
            Files.delete(fileToDelete);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public List<UploadedFilesDto> listUploadedFiles() {
        List<UploadedFilesDto> uploadedFiles = new LinkedList<>();

        try (Stream<Path> paths = Files.walk(this.rootLocation, 2)
                .filter(path -> path.getFileName().toString().contains("."))) {

            paths.forEachOrdered(path -> {
                log.debug("Walking" + path.toString());

                UploadedFilesDto temp;

                try {
                    temp = new UploadedFilesDto(this.rootLocation.relativize(path).toString(), LocalDateTime.parse(Files.getLastModifiedTime(path).toString(), DateTimeFormatter.ISO_DATE_TIME));
                } catch (IOException e) {
                    log.error("Error reading file creation time: " + e.getMessage());
                    temp = new UploadedFilesDto(this.rootLocation.relativize(path).toString(), LocalDateTime.MIN);
                }

                uploadedFiles.add(temp);
            });

        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }


        return uploadedFiles;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
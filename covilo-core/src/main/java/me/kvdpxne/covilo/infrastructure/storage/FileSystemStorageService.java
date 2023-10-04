package me.kvdpxne.covilo.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public final class FileSystemStorageService
  implements StorageService {

  private final StorageConfiguration storageConfiguration;

  /**
   *
   */
  private final Path parentDirectory;

  @Autowired
  public FileSystemStorageService(
    final StorageConfiguration storageConfiguration
  ) {
    this.storageConfiguration = storageConfiguration;
    this.parentDirectory = Path.of(storageConfiguration.getParent());
    this.createDirectories(this.parentDirectory);
  }

  private Path createDirectories(final Path path) {
    Path directory = path;
    try {
      directory = Files.createDirectories(path);

      return directory;
    } catch (final FileAlreadyExistsException ignore) {
      // If the directory we want to create already, exists, there is no need
      // for any additional actions to be performed.
    } catch (final IOException exception) {
      throw new StorageException(
        "Could not initialize storage.",
        exception
      );
    }
    return directory;
  }

  private Path resolve(final Path parent, final String... children) {
    Path result = this.parentDirectory.resolve(parent);
    this.createDirectories(result);
    for (final String child : children) result = result.resolve(child);
    return result.normalize().toAbsolutePath();
  }

  private void store(
    final Path path,
    final String name,
    final MultipartFile file
  ) {
    if (file.isEmpty()) {
      throw new StorageException("Failed to store empty file.");
    }

    final String fileName = file.getOriginalFilename();
    final String fileExtension = fileName.substring(fileName.indexOf('.'));

    try {
      //
      //
      final Path destination = this.resolve(
        path,
        name.concat(fileExtension)
      );

      try (final InputStream input = file.getInputStream()) {
        Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (final IOException exception) {
      throw new StorageException(
        "Failed to store file.",
        exception
      );
    }
  }

  @Override
  public void storeUserAvatar(final String identity, final MultipartFile file) {
    this.store(Path.of(this.storageConfiguration.getAvatars()), identity, file);
  }

  @Override
  public Path load(final String fileName) {
    return this.parentDirectory.resolve(fileName);
  }

  @Override
  public Resource loadAsResource(final String fileName) {
    try {
      Path file = load(fileName);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new StorageFileNotFoundException(
          "Could not read file: " + fileName);

      }
    }
    catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
    }
  }

  @Override
  public byte[] loadAsByteArray(final String fileName) throws StorageFileNotFoundException {
    try {
      return this.loadAsResource(fileName).getContentAsByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

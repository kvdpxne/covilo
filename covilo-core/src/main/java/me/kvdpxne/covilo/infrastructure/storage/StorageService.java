package me.kvdpxne.covilo.infrastructure.storage;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void storeUserAvatar(final String identity, final MultipartFile file);

  Path load(final String fileName);

  Resource loadAsResource(final String fileName) throws StorageFileNotFoundException;

  byte[] loadAsByteArray(final String fileName) throws StorageFileNotFoundException;
}

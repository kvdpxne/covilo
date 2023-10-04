package me.kvdpxne.covilo.infrastructure.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public final class ImageConverterService {

  public void convert(final File file) {
    try {
      final FileInputStream input = new FileInputStream("dice.png");
      final BufferedImage image = ImageIO.read(input);

      //
      //
      input.close(); // ImageIO.read does not close the input stream

      final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

      final FileOutputStream fileOutputStream = new FileOutputStream("dice-test.jpg");
      final boolean canWrite = ImageIO.write(convertedImage, "jpg", fileOutputStream);
      fileOutputStream.close(); // ImageIO.write does not close the output stream

      if (!canWrite) {
        throw new IllegalStateException("Failed to write image.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

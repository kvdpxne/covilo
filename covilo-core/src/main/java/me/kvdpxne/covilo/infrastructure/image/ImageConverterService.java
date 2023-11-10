package me.kvdpxne.covilo.infrastructure.image;

import com.luciad.imageio.webp.WebPWriteParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.stereotype.Service;

@Service
public final class ImageConverterService {

  private ImageWriter getImageWriter(final ImageType imageType) {
    Objects.requireNonNull(imageType, "The given parameter cannot be null");
    final String mimeType = imageType.getMimeType();
    final Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType(
      mimeType);
    if (!writers.hasNext()) {
      throw new IllegalStateException(String.format(
        "No image writer is assigned to the \"%s\" type.", mimeType));
    }
    ImageWriter writer = null;
    while (writers.hasNext()) {
      if (null != writer) {
        throw new IllegalStateException(String.format(
          "More than 1 image writer is assigned for the \"%s\" mime type.",
          mimeType));
      }
      writer = writers.next();
    }
    return writer;
  }

  private ImageWriteParam configureParameter(final Locale locale) {
    final WebPWriteParam parameter = new WebPWriteParam(locale);
    parameter.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    parameter.setCompressionType(parameter.getCompressionTypes()[
      WebPWriteParam.LOSSLESS_COMPRESSION]);
    return parameter;
  }

  public void convertImage(
    final InputStream inputStream,
    final Object output
  ) {
    final BufferedImage image;
    try (inputStream) {
      image = ImageIO.read(inputStream);
    } catch (final IOException cause) {
      throw new ImageException(
        "Failed to read the given image stream.",
        cause
      );
    }
    final ImageWriter writer = this.getImageWriter(ImageType.WEBP);
    final ImageWriteParam parameter = this.configureParameter(writer.getLocale());
    try (final ImageOutputStream outputStream = ImageIO.createImageOutputStream(
      output)) {
      writer.setOutput(outputStream);
      writer.write(null, new IIOImage(image, null, null), parameter);
    } catch (final IOException cause) {
      throw new ImageException(
        "Failed to convert the image.",
        cause
      );
    } finally {
      writer.dispose();
    }
  }
}

package me.kvdpxne.covilo.presentation;

import java.util.Collection;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.application.dto.ClassificationDto;
import me.kvdpxne.covilo.application.mapper.CategoryMapper;
import me.kvdpxne.covilo.application.mapper.ClassificationMapper;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import me.kvdpxne.covilo.domain.persistence.ClassificationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/0.1.0/search")
@RequiredArgsConstructor
public final class SearchController {

  private final ClassificationRepository classificationRepository;
  private final CategoryRepository categoryRepository;
  private final ClassificationMapper classificationMapper;
  private final CategoryMapper categoryMapper;

  @GetMapping("classifications")
  public Stream<ClassificationDto> getClassifications() {
    return this.classificationRepository.getAll()
      .map(this.classificationMapper::toDto);
  }

  @GetMapping("categories")
  public Collection<CategoryDto> getCategories() {
    return this.categoryRepository.getAll()
      .stream()
      .map(this.categoryMapper::toDto)
      .toList();
  }
}

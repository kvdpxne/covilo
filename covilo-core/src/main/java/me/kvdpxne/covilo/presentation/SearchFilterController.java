package me.kvdpxne.covilo.presentation;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.application.dto.CategoryDto;
import me.kvdpxne.covilo.application.mapper.ICategoryMapper;
import me.kvdpxne.covilo.application.mapper.ICrimeMapper;
import me.kvdpxne.covilo.domain.persistence.ICategoryRepository;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ICategoryJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/0.1.0/search")
@RequiredArgsConstructor
public final class SearchFilterController {

  private final ICategoryRepository categoryRepository;
  private final ICategoryMapper categoryMapper;

  @GetMapping("categories")
  public ResponseEntity<Collection<CategoryDto>> getCategories() {
    return ResponseEntity.ok(
      this.categoryRepository.findCategories()
        .stream()
        .map(this.categoryMapper::toCategoryDto)
        .toList()
    );
  }
}

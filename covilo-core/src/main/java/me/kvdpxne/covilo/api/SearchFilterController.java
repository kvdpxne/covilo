package me.kvdpxne.covilo.api;

import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.persistence.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/0.1.0/search")
@RequiredArgsConstructor
public final class SearchFilterController {

  private final CategoryRepository categoryRepository;
  private final CrimeMapper crimeMapper;

  @GetMapping("categories")
  public Collection<CategoryDto> getCategories() {
    return categoryRepository.findAll()
      .stream()
      .map(crimeMapper::toResponse)
      .toList();
  }
}

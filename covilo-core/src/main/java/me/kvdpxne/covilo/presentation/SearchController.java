package me.kvdpxne.covilo.presentation;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.service.SystematizationService;
import me.kvdpxne.covilo.infrastructure.swagger.HiddenParameter;
import me.kvdpxne.covilo.infrastructure.swagger.PagingAsQueryParameter;
import me.kvdpxne.covilo.presentation.dto.CategoryDto;
import me.kvdpxne.covilo.presentation.dto.ClassificationDto;
import me.kvdpxne.covilo.presentation.paging.PageDto;
import me.kvdpxne.covilo.presentation.paging.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("api/0.1.0/search")
@RestController
public final class SearchController {

  private final SystematizationService systematizationService;

  @PagingAsQueryParameter
  @GetMapping("classifications")
  public PageDto<ClassificationDto> classifications(
    @HiddenParameter
    final PageRequest pageRequest
  ) {
    return PageDto.of(
      this.systematizationService.getClassifications(
        pageRequest.getPage()
      ),
      classification -> new ClassificationDto(
        classification.getIdentifier(),
        classification.getName()
      )
    );
  }

  @PagingAsQueryParameter
  @GetMapping("categories")
  public PageDto<CategoryDto> categories(
    @HiddenParameter
    final PageRequest pageRequest
  ) {
    return PageDto.of(
      this.systematizationService.getCategories(
        pageRequest.getPage()
      ),
      category -> new CategoryDto(
        category.getIdentifier(),
        category.getName()
      )
    );
  }

  /**
   * Deletes a classification by its unique identifier.
   *
   * @param identifier The unique identifier of the classification to delete.
   * @return {@code true} if the deletion was successful, otherwise
   * {@code false}.
   */
  @DeleteMapping("classification/{identifier}")
  public boolean deleteClassificationByIdentifier(
    @PathVariable
    final UUID identifier
  ) {
    return this.systematizationService.deleteClassificationByIdentifier(
      identifier
    );
  }

  /**
   * Deletes a category by its unique identifier.
   *
   * @param identifier The unique identifier of the category to delete.
   * @return {@code true} if the deletion was successful, otherwise
   * {@code false}.
   */
  @DeleteMapping("category/{identifier}")
  public boolean deleteCategoryByIdentifier(
    @PathVariable
    final UUID identifier
  ) {
    return this.systematizationService.deleteCategoryByIdentifier(
      identifier
    );
  }
}

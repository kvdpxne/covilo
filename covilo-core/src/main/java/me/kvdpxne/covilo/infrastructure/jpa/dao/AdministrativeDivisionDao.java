package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.AdministrativeDivision;
import me.kvdpxne.covilo.domain.persistence.AdministrativeDivisionRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.AdministrativeDivisionPersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaAdministrativeDivisionRepository;
import me.kvdpxne.covilo.shared.BookcaseRequestPage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class AdministrativeDivisionDao
  implements AdministrativeDivisionRepository {

  private final JpaAdministrativeDivisionRepository jpa;
  private final AdministrativeDivisionPersistenceMapper mapper;

  @Override
  public List<AdministrativeDivision> getAll() {
    return this.jpa.findAll()
      .stream()
      .map(this.mapper::toAdministrativeDivision)
      .toList();
  }

  @Override
  public Book<AdministrativeDivision> getAll(
    final BookAttributes attributes
  ) {
    return Book.boxed(attributes, it -> {
      final Pageable request = BookcaseRequestPage.request(attributes);

      this.jpa.findAll(request)
        .map(this.mapper::toAdministrativeDivision)
        .forEach(it::put);
    });
  }

  @Override
  public Optional<AdministrativeDivision> findByIdentifier(
    final UUID identifier
  ) {
    return this.jpa.findById(identifier)
      .map(this.mapper::toAdministrativeDivision);
  }

  @Override
  public Optional<AdministrativeDivision> findByName(
    final String name
  ) {
    return this.jpa.findByName(name)
      .map(this.mapper::toAdministrativeDivision);
  }

  @Override
  public void insertAll(
    final Collection<AdministrativeDivision> administrativeDivisions
  ) {
    administrativeDivisions.stream()
      .map(this.mapper::toAdministrativeDivisionEntity)
      .forEach(this.jpa::save);
  }

  @Override
  public void insert(
    final AdministrativeDivision administrativeDivision
  ) {
    final var all = List.of(administrativeDivision);
    this.insertAll(all);
  }


}

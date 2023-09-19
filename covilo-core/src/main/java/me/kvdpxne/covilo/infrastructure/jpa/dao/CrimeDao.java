package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.aggregation.Book;
import me.kvdpxne.covilo.domain.aggregation.BookAttributes;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.ICrimeRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ICrimePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ICrimeJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CrimeDao implements ICrimeRepository {

  private final ICrimeJpaRepository repository;
  private final ICrimePersistenceMapper mapper;

  @Override
  public Book<Crime> findCrimes(final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.repository.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCrime)
        .forEach(book::put)
    );
  }

  @Override
  public Book<Crime> findCrimesByCityIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.repository.findCrimeEntityByPlace_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCrime)
        .forEach(book::put)
    );
  }

  @Override
  public Crime findCrimeByIdentifierOrNull(final UUID identifier) {
    return this.repository.findById(identifier).map(this.mapper::toCrime).orElse(null);
  }

  @Override
  public Crime insertCrime(final Crime crime) {
    return this.mapper.toCrime(
      this.repository.save(
        this.mapper.toCrimeEntity(crime)
      )
    );
  }

  @Override
  public boolean existsCrimeByIdentifier(final UUID identifier) {
    return this.repository.existsById(identifier);
  }
}

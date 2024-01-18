package me.kvdpxne.covilo.infrastructure.jpa.dao;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.shared.Book;
import me.kvdpxne.covilo.shared.BookAttributes;
import me.kvdpxne.covilo.domain.model.Crime;
import me.kvdpxne.covilo.domain.persistence.CrimeRepository;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.CrimePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.JpaCrimeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class CrimeDao implements CrimeRepository {

  private final JpaCrimeRepository jpa;
  private final CrimePersistenceMapper mapper;

  @Override
  public Book<Crime> findCrimes(final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.jpa.findAll(
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCrime)
        .forEach(book::put)
    );
  }

  @Override
  public Book<Crime> findCrimesByCityIdentifier(final UUID identifier, final BookAttributes attributes) {
    return Book.boxed(attributes, book ->
      this.jpa.findCrimeEntityByPlace_Identifier(
          identifier,
          PageRequest.of(attributes.page(), attributes.size())
        )
        .map(this.mapper::toCrime)
        .forEach(book::put)
    );
  }

  @Override
  public Crime findCrimeByIdentifierOrNull(final UUID identifier) {
    return this.jpa.findById(identifier).map(this.mapper::toCrime).orElse(null);
  }

  @Override
  public Crime insertCrime(final Crime crime) {
    return this.mapper.toCrime(
      this.jpa.save(
        this.mapper.toCrimeEntity(crime)
      )
    );
  }

  @Override
  public boolean existsCrimeByIdentifier(final UUID identifier) {
    return this.jpa.existsById(identifier);
  }
}

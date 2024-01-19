package me.kvdpxne.covilo.infrastructure.jpa;

import me.kvdpxne.covilo.application.Mapper;

public interface PersistenceMapper<A, B>
  extends Mapper<A, B> {

  /**
   *
   */
  B toDao(final A target);

  /**
   *
   */
  A toDomain(final B target);
}

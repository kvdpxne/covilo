package me.kvdpxne.covilo.shared;

public interface MapStructPersistenceMapper<A, B>
  extends MapStructMapper<A, B> {

  /**
   *
   */
  B toDao(final A target);

  /**
   *
   */
  A toDomain(final B target);
}

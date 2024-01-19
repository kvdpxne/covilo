package me.kvdpxne.covilo.application;

public interface PresentationMapper<A, B>
  extends Mapper<A, B> {

  /**
   *
   */
  B toDto(
    final A target
  );
}

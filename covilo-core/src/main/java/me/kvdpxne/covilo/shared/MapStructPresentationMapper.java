package me.kvdpxne.covilo.shared;

public interface MapStructPresentationMapper<A, B>
  extends MapStructMapper<A, B> {

  /**
   *
   */
  B toDto(
    final A target
  );
}

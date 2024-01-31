package me.kvdpxne.covilo.shared;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

interface MapStructMapper<A, B> {

  /**
   *
   */
  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  B partialUpdate(
    @MappingTarget
    final B target,
    final A source
  );
}

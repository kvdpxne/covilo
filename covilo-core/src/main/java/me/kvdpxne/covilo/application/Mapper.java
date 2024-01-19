package me.kvdpxne.covilo.application;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface Mapper<A, B> {

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

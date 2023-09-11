package me.kvdpxne.covilo.application;

import java.util.UUID;
import me.kvdpxne.covilo.domain.exception.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.CrimeNotFoundException;
import me.kvdpxne.covilo.infrastructure.jpa.entity.CrimeEntity;

public interface CrimeLifecycleUseCase {

  /**
   *
   */
  CrimeEntity getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException;

  /**
   *
   */
  CrimeEntity createCrime(final CrimeEntity crime) throws CrimeAlreadyExistsException;
}

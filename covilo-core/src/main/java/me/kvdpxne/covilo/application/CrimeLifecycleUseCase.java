package me.kvdpxne.covilo.application;

import java.util.UUID;
import me.kvdpxne.covilo.domain.exception.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.domain.exception.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;

public interface CrimeLifecycleUseCase {

  /**
   *
   */
  Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException;

  /**
   *
   */
  Crime createCrime(final Crime crime) throws CrimeAlreadyExistsException;
}

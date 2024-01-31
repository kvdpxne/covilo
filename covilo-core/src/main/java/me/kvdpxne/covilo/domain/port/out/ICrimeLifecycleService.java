package me.kvdpxne.covilo.domain.port.out;

import java.util.UUID;
import me.kvdpxne.covilo.common.exceptions.CrimeAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.CrimeNotFoundException;
import me.kvdpxne.covilo.domain.model.Crime;

public interface ICrimeLifecycleService {

  /**
   * @throws CrimeNotFoundException
   */
  Crime getCrimeByIdentifier(final UUID identifier) throws CrimeNotFoundException;

  /**
   *
   */
  Crime createCrime(final Crime crime) throws CrimeAlreadyExistsException;
}

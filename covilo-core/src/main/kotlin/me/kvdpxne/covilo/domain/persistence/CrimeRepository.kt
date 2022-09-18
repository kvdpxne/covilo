package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.domain.model.Crimes
import java.util.UUID

/**
 * @see Crime
 */
interface CrimeRepository {

  fun findByIdentifier(identifier: UUID): Crime?

  fun findAll(): Crimes

  fun insert(crime: Crime)

  fun update(crime: Crime)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}

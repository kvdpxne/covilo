package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.CrimePerpetrator
import me.kvdpxne.covilo.domain.model.CrimePerpetrators
import java.util.UUID

/**
 * @see CrimePerpetrator
 */
interface CrimePerpetratorRepository {

  fun findByIdentifier(identifier: UUID): CrimePerpetrator?
  fun findAll(): CrimePerpetrators

  fun insert(perpetrator: CrimePerpetrator)
  fun update(perpetrator: CrimePerpetrator)

  fun delete(identifier: UUID)
  fun deleteAll()

  fun count(): Long
}
package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.domain.model.CrimeClassifications
import java.util.UUID

/**
 * @see CrimeClassification
 */
interface CrimeClassificationRepository {

  fun findByIdentifier(identifier: UUID): CrimeClassification?
  fun findAll(): CrimeClassifications

  fun insert(classification: CrimeClassification)
  fun update(classification: CrimeClassification)

  fun delete(identifier: UUID)
  fun deleteAll()

  fun count(): Long
}

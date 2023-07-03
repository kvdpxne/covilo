package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.CrimeClassification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrimeClassificationRepository
  extends JpaRepository<CrimeClassification, UUID> {
}
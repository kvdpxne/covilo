package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassificationRepository
  extends JpaRepository<Classification, UUID> {
}

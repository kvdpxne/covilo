package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Crime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrimeRepository
  extends JpaRepository<Crime, UUID> {

  Page<Crime> findByPlace_Identifier(UUID identifier, Pageable pageable);
}
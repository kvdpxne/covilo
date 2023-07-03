package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
}
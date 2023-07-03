package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}
package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvinceRepository extends JpaRepository<Province, UUID> {
}
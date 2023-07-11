package me.kvdpxne.covilo.domain.persistence;

import me.kvdpxne.covilo.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository
  extends JpaRepository<Category, UUID> {
}
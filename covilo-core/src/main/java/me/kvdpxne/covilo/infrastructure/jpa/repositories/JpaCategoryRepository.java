package me.kvdpxne.covilo.infrastructure.jpa.repositories;

import java.util.Optional;
import me.kvdpxne.covilo.infrastructure.jpa.JpaIdentifiableRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository
  extends JpaIdentifiableRepository<CategoryEntity> {

  @Query("select c from CategoryEntity c where upper(c.name) = upper(?1)")
  Optional<CategoryEntity> findByName(final String name);

//  @Query("select c from CategoryEntity c where upper(c.name) = upper(:name)")
//  @Nullable
//  CategoryEntity findByName(@Param("name") @NonNull String name);



}
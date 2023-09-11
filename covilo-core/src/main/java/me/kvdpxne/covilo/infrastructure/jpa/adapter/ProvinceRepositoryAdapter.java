package me.kvdpxne.covilo.infrastructure.jpa.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.kvdpxne.covilo.domain.model.Province;
import me.kvdpxne.covilo.domain.persistence.ProvinceRepository;
import me.kvdpxne.covilo.infrastructure.jpa.entity.ProvinceEntity;
import me.kvdpxne.covilo.infrastructure.jpa.mapper.ProvincePersistenceMapper;
import me.kvdpxne.covilo.infrastructure.jpa.repository.ProvinceDao;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProvinceRepositoryAdapter implements ProvinceRepository {

  private final ProvinceDao provinceDao;
  private final ProvincePersistenceMapper provincePersistenceMapper;

  private Province toProvinceOrNull(final Optional<ProvinceEntity> source) {
    return source.map(this.provincePersistenceMapper::toProvince).orElse(null);
  }

  @Override
  public Province findProvinceByIdentifierOrNull(final UUID identifier) {
    final var entity = this.provinceDao.findById(identifier);
    return toProvinceOrNull(entity);
  }

  @Override
  public Province findProvinceByNameOrNull(final String name) {
    return null;
  }
}

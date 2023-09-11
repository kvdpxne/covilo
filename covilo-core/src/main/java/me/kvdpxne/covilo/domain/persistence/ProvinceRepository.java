package me.kvdpxne.covilo.domain.persistence;

import java.util.UUID;
import me.kvdpxne.covilo.domain.model.Province;

public interface ProvinceRepository {

  Province findProvinceByIdentifierOrNull(final UUID identifier);

  Province findProvinceByNameOrNull(final String name);
}

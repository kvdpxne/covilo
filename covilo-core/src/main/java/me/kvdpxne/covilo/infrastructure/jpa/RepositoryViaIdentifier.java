package me.kvdpxne.covilo.infrastructure.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryViaIdentifier<T> extends JpaRepository<T, UUID> {
}

package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.domain.models.Account
import org.springframework.jdbc.core.RowMapper

interface AccountRowMapper<T : Account> : RowMapper<T>
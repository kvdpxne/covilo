package me.kvdpxne.covilo.infrastructure.security.authentication.dto

import me.kvdpxne.covilo.domain.models.LoginCredentials

/**
 *
 */
data class LoginCredentialsDto(
  override val email: String,
  override val password: String
) : LoginCredentials
package me.kvdpxne.covilo.domain.models

class AccountAlreadyExistsException(email: String) : RuntimeException(
  "A Account with the same email already exists: $email."
)
package me.kvdpxne.covilo.domain.repositories

import me.kvdpxne.covilo.domain.models.Account

interface AccountRepository {

  fun findByEmail(email: String): Account?
}
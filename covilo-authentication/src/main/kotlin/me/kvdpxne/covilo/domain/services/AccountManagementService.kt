package me.kvdpxne.covilo.domain.services

import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.models.AccountAlreadyExistsException

interface AccountManagementService {

  fun isExists(email: String): Boolean

  @Throws(AccountAlreadyExistsException::class)
  fun create(account: Account): Account

  fun find(email: String): Account
}
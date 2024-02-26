package me.kvdpxne.covilo.domain.port.out;

import me.kvdpxne.covilo.presentation.dto.TokenDto;
import me.kvdpxne.covilo.common.exceptions.UserInvalidPasswordException;
import me.kvdpxne.covilo.common.exceptions.TokenExpiredException;
import me.kvdpxne.covilo.common.exceptions.TokenSignatureException;
import me.kvdpxne.covilo.common.exceptions.UserAlreadyExistsException;
import me.kvdpxne.covilo.common.exceptions.UserNotFoundException;
import me.kvdpxne.covilo.presentation.payloads.LoginRequest;
import me.kvdpxne.covilo.presentation.payloads.SignupRequest;
import me.kvdpxne.covilo.domain.model.Token;

public interface UserAuthenticationServicePort {

  Token createAuthentication(
    final SignupRequest request
  ) throws UserAlreadyExistsException, UserInvalidPasswordException;

  TokenDto authenticate(final LoginRequest request) throws UserNotFoundException;

  Token refreshAuthentication(final String token) throws UserNotFoundException, TokenExpiredException,
    TokenSignatureException;
}

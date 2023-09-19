package me.kvdpxne.covilo.application;

import me.kvdpxne.covilo.application.exception.InvalidPasswordException;
import me.kvdpxne.covilo.application.exception.TokenExpiredException;
import me.kvdpxne.covilo.application.exception.TokenSignatureException;
import me.kvdpxne.covilo.application.exception.UserAlreadyExistsException;
import me.kvdpxne.covilo.application.exception.UserNotFoundException;
import me.kvdpxne.covilo.application.payload.LoginRequest;
import me.kvdpxne.covilo.application.payload.SignupRequest;
import me.kvdpxne.covilo.domain.model.Token;

public interface IUserAuthenticationService {

  Token createAuthentication(
    final SignupRequest request
  ) throws UserAlreadyExistsException, InvalidPasswordException;

  Token authenticate(final LoginRequest request) throws UserNotFoundException;

  Token refreshAuthentication(final String token) throws UserNotFoundException, TokenExpiredException,
    TokenSignatureException;
}

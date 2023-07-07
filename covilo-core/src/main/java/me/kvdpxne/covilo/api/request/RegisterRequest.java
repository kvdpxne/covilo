package me.kvdpxne.covilo.api.request;

import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private Role role;
  private Gender gender;
  private LocalDate birthDate;
}

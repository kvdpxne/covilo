package me.kvdpxne.covilo.application.payload;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kvdpxne.covilo.domain.model.Gender;
import me.kvdpxne.covilo.domain.model.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String confirmPassword;
  private boolean privacyPolicy;
  private Role role;
  private Gender gender;
  private LocalDate birthDate;
}

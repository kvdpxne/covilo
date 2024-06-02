package me.kvdpxne.covilo.infrastructure.security.jwt;

import me.kvdpxne.covilo.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JjwtServiceTest {

  @Autowired
  public JjwtServiceExtension jjwtService;

  @Test
  void test() {
    final User user = User.builder()
      .withRandomIdentifier()
      .withEmail("support@covilo.com")
      .withCreatedDate()
      .build();

    final String jws = this.jjwtService.createAccessJws(user);
    final String refreshJws = this.jjwtService.createRefreshJws(user);

    System.out.printf("Access: %s%nRefresh: %s%n", jws, refreshJws);

    final String jwe = this.jjwtService.createAccessJwe(user);
    final String refreshJwe = this.jjwtService.createRefreshJwe(user);

    System.out.printf("Access: %s%nRefresh: %s%n", jwe, refreshJwe);
  }
}

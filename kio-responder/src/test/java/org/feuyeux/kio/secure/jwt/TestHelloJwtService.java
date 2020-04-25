package org.feuyeux.kio.secure.jwt;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.secure.HelloToken;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.feuyeux.kio.pojo.secure.HelloRole.USER;

/**
 * @author feuyeux@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestHelloJwtService {
    @Autowired
    private HelloJwtService helloJwtService;

    @Test
    public void testRefreshToken() {
        HelloUser setup = HelloUser.builder().userId("0000").password("Zero4").role(USER).build();
        HelloToken helloToken = helloJwtService.signToken(setup);
        String refreshToken = helloToken.getRefreshToken();
        HelloUser result = helloJwtService.authenticate(refreshToken).block();
        log.info("result={}", result);
    }
}

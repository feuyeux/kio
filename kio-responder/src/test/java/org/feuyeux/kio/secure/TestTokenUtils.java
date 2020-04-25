package org.feuyeux.kio.secure;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.pojo.secure.UserToken;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
public class TestTokenUtils {
    @org.junit.Test
    public void generateSetupToken() throws IOException {
        HelloUser setup = HelloUser.builder().userId("88888888").password("868").role("SETUP").build();
        UserToken token = TokenUtils.generateAccessToken(setup);
        UserToken refreshToken = TokenUtils.generateRefreshToken(setup);
        saveToken(token, "access", TokenUtils.getAccessTokenDecoder());
        saveToken(refreshToken, "refresh", TokenUtils.getRefreshTokenDecoder());
    }

    private void saveToken(UserToken token, String tokenName, ReactiveJwtDecoder decoder) throws IOException {
        String tokenValue;
        if (token != null) {
            tokenValue = token.getToken();
            System.out.println(tokenValue);
            decoder.decode(tokenValue).subscribe(jwt -> {
                String scope = jwt.getClaim("scope");
                log.info("Verify {} token role:{}", tokenName, scope);
            });
            Path dir = Paths.get("/tmp/kio");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Path path = Paths.get("/tmp/kio/", tokenName + ".token");
            Files.write(path, tokenValue.getBytes());
        }
    }
}

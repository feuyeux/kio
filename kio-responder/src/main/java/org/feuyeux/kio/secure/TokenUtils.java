package org.feuyeux.kio.secure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.pojo.secure.UserToken;
import org.feuyeux.kio.secure.jwt.HelloJwtDecoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
public class TokenUtils {
    public static final long ACCESS_EXPIRE = 15;
    public static final long REFRESH_EXPIRE = 7;

    /*
     * Use base64 to generate secret string.
     * The HS256 secret key of length, which should be at least 256 bits.
     * As 1 character = 8 bits, the characters should be more than 32.
     *
     * ▶ input=jwt_token-based_openapi_for_rsocket_access_token
     * ▶ secret=$(echo -n $input | openssl base64)
     * ▶ echo $secret
     * and0X3Rva2VuLWJhc2VkX29wZW5hcGlfZm9yX3Jzb2NrZXRfYWNjZXNzX3Rva2Vu
     * ▶ echo $secret | openssl base64 -d
     * jwt_token-based_openapi_for_rsocket_access_token
     *
     * ▶ input=jwt_token-based_openapi_4_rsocket_refresh_token
     * ▶ echo $secret
     * and0X3Rva2VuLWJhc2VkX29wZW5hcGlfNF9yc29ja2V0X3JlZnJlc2hfdG9rZW4=
     */
    private static final String ACCESS_SECRET_KEY = "and0X3Rva2VuLWJhc2VkX29wZW5hcGlfZm9yX3Jzb2NrZXRfYWNjZXNzX3Rva2Vu";
    private static final String REFRESH_SECRET_KEY = "and0X3Rva2VuLWJhc2VkX29wZW5hcGlfNF9yc29ja2V0X3JlZnJlc2hfdG9rZW4=";
    private static final MacAlgorithm MAC_ALGORITHM = MacAlgorithm.HS256;
    private static final String HMAC_SHA_256 = "HmacSHA256";

    public static UserToken generateAccessToken(HelloUser user) {
        Algorithm ACCESS_ALGORITHM = Algorithm.HMAC256(ACCESS_SECRET_KEY);
        return generateToken(user, ACCESS_ALGORITHM, ACCESS_EXPIRE, ChronoUnit.MINUTES);
    }

    public static ReactiveJwtDecoder getAccessTokenDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(ACCESS_SECRET_KEY.getBytes(), HMAC_SHA_256);
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MAC_ALGORITHM)
                .build();
    }

    public static ReactiveJwtDecoder jwtAccessTokenDecoder() {
        return new HelloJwtDecoder(getAccessTokenDecoder());
    }

    public static UserToken generateRefreshToken(HelloUser user) {
        Algorithm REFRESH_ALGORITHM = Algorithm.HMAC256(REFRESH_SECRET_KEY);
        return generateToken(user, REFRESH_ALGORITHM, REFRESH_EXPIRE, ChronoUnit.DAYS);
    }

    public static ReactiveJwtDecoder getRefreshTokenDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(REFRESH_SECRET_KEY.getBytes(), HMAC_SHA_256);
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MAC_ALGORITHM)
                .build();
    }

    private static UserToken generateToken(HelloUser user, Algorithm algorithm, long expire, ChronoUnit unit) {
        String tokenId = UUID.randomUUID().toString();
        Instant instant;
        Instant now = Instant.now();
        if (now.isSupported(unit)) {
            instant = now.plus(expire, unit);
        } else {
            log.error("unit param is not supported");
            return null;
        }
        String token = JWT.create()
                .withJWTId(tokenId)
                .withSubject(user.getUserId())
                .withClaim("scope", user.getRole())
                .withExpiresAt(Date.from(instant))
                .sign(algorithm);
        return UserToken.builder().tokenId(tokenId).token(token).user(user).build();
    }
}

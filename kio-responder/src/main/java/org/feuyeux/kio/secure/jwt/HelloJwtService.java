package org.feuyeux.kio.secure.jwt;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.secure.HelloToken;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.pojo.secure.UserToken;
import org.feuyeux.kio.secure.TokenUtils;
import org.feuyeux.kio.secure.db.HelloTokenRepository;
import org.feuyeux.kio.secure.db.HelloUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author feuyeux@gmail.com
 */
@Service
@Slf4j
public class HelloJwtService {
    @Autowired
    private HelloUserRepository userRepository;
    @Autowired
    private HelloTokenRepository tokenRepository;
    @Setter
    private String tokenId;

    public HelloUser authenticate(String principal, String credential) {
        try {
            HelloUser user = userRepository.retrieve(principal);
            if (user.getPassword().equals(credential)) {
                return user;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Mono<HelloUser> authenticate(String refreshToken) {
        log.info("authenticate refreshToken: {}", refreshToken);
        ReactiveJwtDecoder reactiveJwtDecoder = TokenUtils.getRefreshTokenDecoder();
        return reactiveJwtDecoder.decode(refreshToken).map(jwt -> {
            HelloUser user = HelloUser.builder().userId(jwt.getSubject()).role(jwt.getClaim("scope")).build();
            log.info("verify successfully. user:{}", user);
            return user;
        });
    }

    public HelloToken signToken(HelloUser user) {
        HelloToken token = new HelloToken();
        UserToken accessToken = TokenUtils.generateAccessToken(user);
        tokenRepository.storeAccessToken(accessToken.getTokenId(), accessToken.getUser());
        UserToken refreshToken = TokenUtils.generateRefreshToken(user);
        tokenRepository.storeFreshToken(refreshToken.getTokenId(), refreshToken.getUser());
        token.setAccessToken(accessToken.getToken());
        token.setRefreshToken(refreshToken.getToken());
        return token;
    }

    public void revokeAccessToken() {
        tokenRepository.deleteAccessToken(tokenId);
    }
}

package org.feuyeux.kio.secure.jwt;

import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.secure.db.HelloTokenRepository;
import org.feuyeux.kio.utils.BeanUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

/**
 * @author feuyeux@gmail.com
 */
public class HelloJwtDecoder implements ReactiveJwtDecoder {
    private final ReactiveJwtDecoder reactiveJwtDecoder;
    private HelloTokenRepository tokenRepository = BeanUtils.getBean(HelloTokenRepository.class);
    private HelloJwtService helloJwtService = BeanUtils.getBean(HelloJwtService.class);

    public HelloJwtDecoder(ReactiveJwtDecoder reactiveJwtDecoder) {
        this.reactiveJwtDecoder = reactiveJwtDecoder;
    }

    @Override
    public Mono<Jwt> decode(String token) throws JwtException {
        return reactiveJwtDecoder.decode(token).doOnNext(jwt -> {
            String id = jwt.getId();
            HelloUser auth = tokenRepository.getAuthFromAccessToken(id);
            if (auth == null) {
                throw new JwtException("Invalid HelloUser");
            }
            //TODO
            helloJwtService.setTokenId(id);
        });
    }
}

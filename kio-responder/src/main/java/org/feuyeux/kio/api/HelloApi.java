package org.feuyeux.kio.api;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.HelloRequest;
import org.feuyeux.kio.pojo.HelloResponse;
import org.feuyeux.kio.pojo.secure.HelloToken;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.feuyeux.kio.secure.jwt.HelloJwtService;
import org.feuyeux.kio.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
@Controller
public class HelloApi {
    @Autowired
    private HelloJwtService helloJwtService;
    @Autowired
    private HelloService helloService;

    @MessageMapping("signin.v1")
    Mono<HelloToken> signin(HelloUser helloUser) {
        String principal = helloUser.getUserId();
        String credential = helloUser.getPassword();
        HelloUser user = helloJwtService.authenticate(principal, credential);
        if (user != null) {
            HelloToken token = helloJwtService.signToken(user);
            return Mono.just(token);
        }
        return Mono.empty();
    }

    @MessageMapping("refresh.v1")
    Mono<HelloToken> refresh(String token) {
        String refreshToken = token.replace("\"", "");
        log.info("authenticate refreshToken: {}", refreshToken);
        Mono<HelloUser> mono = helloJwtService.authenticate(refreshToken);
        return mono.map(u -> helloJwtService.signToken(u));
    }

    @MessageMapping("signout.v1")
    public Mono<Void> signout(/*@Headers Map<String, Object> headers*/) {
        helloJwtService.revokeAccessToken();
        return Mono.empty();
    }

    @MessageMapping("hire.v1")
    Mono<HelloResponse> hire(HelloRequest helloRequest) {
        return Mono.just(helloService.hire(helloRequest));
    }

    @MessageMapping("fire.v1")
    Mono<HelloResponse> fire(HelloRequest helloRequest) {
        return Mono.just(helloService.fire(helloRequest));
    }

    @MessageMapping("info.v1")
    Mono<HelloResponse> info(long id) {
        HelloResponse data = helloService.info(id);
        log.info("info result={}", data);
        return Mono.just(data);
    }

    @MessageMapping("list.v1")
    Flux<HelloResponse> list() {
        return helloService.list();
    }
}
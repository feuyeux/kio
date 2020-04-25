package org.feuyeux.kio.api;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.HelloRequest;
import org.feuyeux.kio.pojo.HelloResponse;
import org.feuyeux.kio.pojo.secure.HelloToken;
import org.feuyeux.kio.service.HelloRSocketAdapter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
@RestController
@RequestMapping("api")
public class HelloController {
    @Autowired
    private HelloRSocketAdapter helloRSocketAdapter;

    @GetMapping("signin")
    Mono<HelloToken> signIn(@RequestParam String u, @RequestParam String p) {
        log.info("[signIn] input={},{}", u, p);
        return helloRSocketAdapter.signIn(u, p);
    }

    @GetMapping("refresh/{token}")
    Mono<HelloToken> refresh(@PathVariable String token) {
        log.info("[refresh] input={}", token);
        return helloRSocketAdapter.refresh(token);
    }

    @PostMapping(value = "signout")
    Mono<Void> signOut() {
        log.info("[signOut]");
        return helloRSocketAdapter.signOut();
    }

    @PostMapping(value = "hire", consumes = APPLICATION_STREAM_JSON_VALUE)
    Mono<HelloResponse> hire(@RequestBody Mono<HelloRequest> requestStream) {
        log.info("[hire]");
        return helloRSocketAdapter.hire(requestStream);
    }

    @PostMapping(value = "fire", consumes = APPLICATION_STREAM_JSON_VALUE)
    Mono<HelloResponse> fire(@RequestBody Mono<HelloRequest> requestStream) {
        log.info("[fire]");
        return helloRSocketAdapter.fire(requestStream);
    }

    @GetMapping("info/{id}")
    Mono<HelloResponse> info(@PathVariable long id) {
        log.info("[info] input={}", id);
        return helloRSocketAdapter.info(id);
    }

    @GetMapping(value = "list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Publisher<HelloResponse> list() {
        log.info("[list]");
        return helloRSocketAdapter.list();
    }
}

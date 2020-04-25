package org.feuyeux.kio.service;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.kio.pojo.HelloRequest;
import org.feuyeux.kio.pojo.HelloResponse;
import org.feuyeux.kio.pojo.secure.HelloToken;
import org.feuyeux.kio.pojo.secure.HelloUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.BearerTokenMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author feuyeux@gmail.com
 */
@Slf4j
@Component
public class HelloRSocketAdapter {
    private final MimeType mimeType = BearerTokenMetadata.BEARER_AUTHENTICATION_MIME_TYPE;
    @Autowired
    private RSocketRequester requester;
    private String accessToken;

    public Mono<HelloToken> signIn(String principal, String credential) {
        return requester
                .route("signin")
                .data(HelloUser.builder().userId(principal).password(credential).build())
                .retrieveMono(HelloToken.class)
                .doOnNext(token -> {
                    accessToken = token.getAccessToken();
                })
                .onErrorStop();
    }

    public Mono<HelloToken> refresh(String token) {
        return requester
                .route("refresh")
                .data(token)
                .retrieveMono(HelloToken.class);
    }

    public Mono<Void> signOut() {
        return requester
                .route("signout")
                .metadata(this.accessToken, this.mimeType)
                .send();
    }

    public Mono<HelloResponse> info(long id) {
        return requester
                .route("info")
                .metadata(this.accessToken, this.mimeType)
                .data(id)
                .retrieveMono(HelloResponse.class);
    }

    public Mono<HelloResponse> hire(Mono<HelloRequest> requestStream) {
        return requester
                .route("hire")
                .metadata(this.accessToken, this.mimeType)
                .data(requestStream, HelloRequest.class)
                .retrieveMono(HelloResponse.class);
    }

    public Mono<HelloResponse> fire(Mono<HelloRequest> requestStream) {
        return requester
                .route("fire")
                .metadata(this.accessToken, this.mimeType)
                .data(requestStream, HelloRequest.class)
                .retrieveMono(HelloResponse.class);
    }

    public Flux<HelloResponse> list() {
        return requester
                .route("list")
                .metadata(this.accessToken, this.mimeType)
                .retrieveFlux(HelloResponse.class);
    }
}

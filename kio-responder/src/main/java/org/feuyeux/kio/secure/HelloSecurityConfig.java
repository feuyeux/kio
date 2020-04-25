package org.feuyeux.kio.secure;

import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;

import static org.feuyeux.kio.pojo.secure.HelloRole.ADMIN;
import static org.feuyeux.kio.pojo.secure.HelloRole.USER;

/**
 * @author feuyeux@gmail.com
 */
public class HelloSecurityConfig {
    protected RSocketSecurity pattern(RSocketSecurity security) {
        return security.authorizePayload(authorize -> authorize
                .route("signin").permitAll()
                .route("refresh").permitAll()
                .route("signout").authenticated()
                .route("hire").hasRole(ADMIN)
                .route("fire").hasRole(ADMIN)
                .route("info").hasAnyRole(USER, ADMIN)
                .route("list").hasAnyRole(USER, ADMIN)
                .anyRequest().authenticated()
                .anyExchange().permitAll()
        );
    }

    protected RSocketMessageHandler getMessageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler mh = new RSocketMessageHandler();
        mh.getArgumentResolverConfigurer().addCustomResolver(
                new org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver());
        mh.setRSocketStrategies(strategies);
        return mh;
    }
}
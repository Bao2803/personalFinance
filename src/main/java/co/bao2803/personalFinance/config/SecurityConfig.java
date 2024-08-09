package co.bao2803.personalFinance.config;

import co.bao2803.personalFinance.service.JwtService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    @Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PublicEndpoint.ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/auth/login"))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                        )
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        return http.build();
    }

    @Bean
    public UserDetailsService users(final PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(username)
                        .password(passwordEncoder.encode(password))
                        .authorities("main-user")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(this.key).build();
        decoder.setJwtValidator(blackListValidator());
        return decoder;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        final JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        final JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    private OAuth2TokenValidator<Jwt> blackListValidator() {
        return new BlackListValidator(redisTemplate);
    }

    @RequiredArgsConstructor
    public static class BlackListValidator implements OAuth2TokenValidator<Jwt> {
        private final RedisTemplate<String, String> redisTemplate;

        @Override
        public OAuth2TokenValidatorResult validate(final Jwt jwt) {
            final String blackListTime = redisTemplate.opsForValue().get(JwtService.createBlackListKey(jwt));
            if (blackListTime != null) {
                final OAuth2Error error = new OAuth2Error(
                        "WH_BL",
                        "Token is invalidated at " + blackListTime,
                        null
                );
                return OAuth2TokenValidatorResult.failure(error);
            }

            return OAuth2TokenValidatorResult.success();
        }
    }
}

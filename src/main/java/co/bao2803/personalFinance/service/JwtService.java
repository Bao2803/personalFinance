package co.bao2803.personalFinance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder encoder;

    @Value("${jwt.duration}")
    private long JWT_DURATION;  // in minutes

    public String generateJwt(final Authentication authentication) {
        final Instant issueTime = Instant.now();
        final String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(issueTime)
                .expiresAt(issueTime.plus(JWT_DURATION, ChronoUnit.MINUTES))
                .subject(authentication.getName() + "JWT")
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public void invalidateJwt(final JwtAuthenticationToken token) {
        System.err.println("Invalidated " + token.getToken().getTokenValue());  // TODO: black list token
    }
}

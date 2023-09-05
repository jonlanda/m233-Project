package ch.zli.m223.coworkingspace.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;

import org.eclipse.microprofile.jwt.Claims;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class LoginService {

    @Inject
    private EntityManager entityManager;

    public String generateToken(String email, String password) {
        final List<ApplicationUser> user = entityManager
                .createQuery("FROM ApplicationUser WHERE email = :email AND password = :password",
                        ApplicationUser.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList();
        if (user.size() != 0) {
            final Boolean isAdmin = user.get(0).getIsAdmin();
            if (isAdmin == true) {
                String token = Jwt.issuer("https://example.com/issuer")
                        .upn("jon")
                        .groups(new HashSet<>(Arrays.asList("Admin")))
                        .claim(Claims.email.name(), user.get(0).getEmail())
                        .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                        .sign();
                return token;
            } else {
                String token = Jwt.issuer("https://example.com/issuer")
                        .upn("jon")
                        .groups(new HashSet<>(Arrays.asList("User")))
                        .claim(Claims.email.name(), user.get(0).getEmail())
                        .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                        .sign();
                return token;
            }
        } else {
            throw new BadRequestException("NO USER");
        }
    }
}

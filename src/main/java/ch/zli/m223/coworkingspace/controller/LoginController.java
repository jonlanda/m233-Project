package ch.zli.m223.coworkingspace.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import ch.zli.m223.coworkingspace.service.LoginService;

@Path("/login")
public class LoginController {

    @Inject
    LoginService loginService;

    @POST
    @Path("/generate")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Login with email and password", description = "Authenticates you and if your user exists it will give you a token.")

    public String generatedToken(ApplicationUser user) {
        return loginService.generateToken(user.getEmail(), user.getPassword());
    }
}

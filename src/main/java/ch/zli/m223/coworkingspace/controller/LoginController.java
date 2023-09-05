package ch.zli.m223.coworkingspace.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public String generatedToken(ApplicationUser user) {
        return loginService.generateToken(user.getEmail(), user.getPassword());
    }
}

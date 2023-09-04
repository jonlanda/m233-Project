package ch.zli.m223.coworkingspace.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestPath;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import ch.zli.m223.coworkingspace.service.UserService;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Users.", description = "Returns a list of all Users.")
    public List<ApplicationUser> index() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index specific User", description = "Returns a specific User")
    public ApplicationUser getUser(@RestPath long id) {
        return userService.findSpecific(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new User.", description = "Creates a new User and returns the newly added User.")
    public ApplicationUser create(ApplicationUser applicationUser) {
        return userService.createApplicationUser(applicationUser);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a User.", description = "Deletes User")
    public void delete(@RestPath long id) {
        userService.deleteApplicationUser(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edits a User.", description = "Edits a User and returns the User.")
    public ApplicationUser updateUser(ApplicationUser appliacationUser) {
        return userService.updateApplicationUser(appliacationUser);
    }
}

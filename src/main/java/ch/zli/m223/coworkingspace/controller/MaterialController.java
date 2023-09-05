package ch.zli.m223.coworkingspace.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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

import ch.zli.m223.coworkingspace.model.Material;
import ch.zli.m223.coworkingspace.service.MaterialService;

@Path("/materials")
public class MaterialController {

    @Inject
    MaterialService materialService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    @Operation(summary = "Index all Materials.", description = "Returns a list of all Materials.")
    public List<Material> index() {
        return materialService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    @Operation(summary = "Index specific Material", description = "Returns a specific Material")
    public Material getMaterial(@RestPath long id) {
        return materialService.findSpecific(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    @Operation(summary = "Creates a new Material.", description = "Creates a new material and returns the newly added material.")
    public Material create(Material material) {
        return materialService.createMaterial(material);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    @Operation(summary = "Deletes a material.", description = "Deletes material")
    public void delete(@RestPath long id) {
        materialService.deleteMaterial(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    @Operation(summary = "Edits a material.", description = "Edits a material and returns the material.")
    public Material updateEntry(Material material) {
        return materialService.updateMaterial(material);
    }
}

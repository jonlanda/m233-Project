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

import ch.zli.m223.coworkingspace.model.Booking;
import ch.zli.m223.coworkingspace.service.BookingService;

@Path("/materials")
public class MaterialController {

    @Inject
    BookingService bookingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Materials.", description = "Returns a list of all Materials.")
    public List<Booking> index() {
        return bookingService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index specific Material", description = "Returns a specific Material")
    public Booking getBooking(@RestPath long id) {
        return bookingService.findSpecific(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new Material.", description = "Creates a new material and returns the newly added material.")
    public Booking create(Booking booking) {
        return bookingService.createBooking(booking);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a material.", description = "Deletes material")
    public void delete(@RestPath long id) {
        bookingService.deleteBooking(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edits a booking.", description = "Edits a booking and returns the booking.")
    public Booking updateEntry(Booking booking) {
        return bookingService.updateBooking(booking);
    }
}

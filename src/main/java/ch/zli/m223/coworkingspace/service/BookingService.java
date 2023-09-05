package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import ch.zli.m223.coworkingspace.model.Booking;

@ApplicationScoped
public class BookingService {
    @Inject
    private EntityManager entityManager;

    public List<Booking> findAll() {
        var query = entityManager.createQuery("FROM Booking", Booking.class);
        return query.getResultList();
    }

    public Booking findSpecific(long id) {
        Booking booking;
        booking = entityManager.find(Booking.class, id);
        if (booking == null) {
            throw new NotFoundException();
        } else {
            return booking;
        }
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        entityManager.persist(booking);
        return booking;
    }

    @Transactional
    public void deleteBooking(long id, String userEmail, Boolean isAdmin) {
        Booking booking = entityManager.find(Booking.class, id);
        if (booking == null) {
            throw new NotFoundException();
        } else {
            if (booking.getUser().getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
                entityManager.remove(booking);
            } else {
                throw new ForbiddenException("NOT AUTHORIZED");
            }
        }
    }

    @Transactional
    public Booking updateBooking(Booking booking, String userEmail, Boolean isAdmin) {
        if (booking.getUser().getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
            if (isAdmin == false && booking.getStatus() == true) {
                throw new ForbiddenException("NOT AUTHORIZED");
            } else {
                Booking booking1 = new Booking();
                booking1 = entityManager.find(Booking.class, booking.getId());
                if (booking1 == null) {
                    throw new NotFoundException();
                } else {
                    entityManager.merge(booking);
                    return booking;
                }
            }
        } else {
            throw new ForbiddenException("NOT AUTHORIZED");
        }
    }
}

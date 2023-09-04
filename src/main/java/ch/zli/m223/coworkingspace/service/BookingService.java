package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
        Booking booking = entityManager.find(Booking.class, id);
        return booking;
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        entityManager.persist(booking);
        return booking;
    }

    @Transactional
    public void deleteBooking(long id) {
        Booking booking = entityManager.find(Booking.class, id);
        entityManager.remove(booking);
    }

    @Transactional
    public Booking updateBooking(Booking booking) {
        entityManager.merge(booking);
        return booking;
    }
}

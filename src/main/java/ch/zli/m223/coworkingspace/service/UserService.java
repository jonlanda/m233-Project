package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;

import ch.zli.m223.coworkingspace.model.ApplicationUser;

@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager entityManager;

    public List<ApplicationUser> findAll() {
        var query = entityManager.createQuery("FROM ApplicationUser", ApplicationUser.class);
        return query.getResultList();
    }

    public ApplicationUser findSpecific(long id) {
        ApplicationUser applicationUser = entityManager.find(ApplicationUser.class, id);
        return applicationUser;
    }

    @Transactional
    public ApplicationUser createApplicationUser(ApplicationUser applicationUser) {
        entityManager.persist(applicationUser);
        return applicationUser;
    }

    @Transactional
    public void deleteApplicationUser(long id, String userEmail, Boolean isAdmin) {
        ApplicationUser applicationUser = entityManager.find(ApplicationUser.class, id);
        if (applicationUser.getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
            entityManager.remove(applicationUser);
        } else {
            throw new ForbiddenException("NOT AUTHORIZED");
        }
    }

    @Transactional
    public ApplicationUser updateApplicationUser(ApplicationUser applicationUser, String userEmail, Boolean isAdmin) {
        if (applicationUser.getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
            if (isAdmin == false && applicationUser.getIsAdmin() == true) {
                throw new ForbiddenException("NOT AUTHORIZED");
            } else {
                entityManager.merge(applicationUser);
                return applicationUser;
            }
        } else {
            throw new ForbiddenException("NOT AUTHORIZED");
        }
    }
}

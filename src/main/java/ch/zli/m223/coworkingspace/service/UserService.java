package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

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
        if (applicationUser == null) {
            throw new NotFoundException();
        } else {
            return applicationUser;
        }
    }

    @Transactional
    public ApplicationUser createApplicationUser(ApplicationUser applicationUser, Boolean isAdmin) {
        if (findAll().size() == 0) {
            applicationUser.setIsAdmin(true);
            entityManager.persist(applicationUser);
            return applicationUser;
        } else if (isAdmin == false && applicationUser.getIsAdmin() == true) {
            throw new ForbiddenException("FORBIDDEN");
        } else {
            entityManager.persist(applicationUser);
            return applicationUser;
        }
    }

    @Transactional
    public void deleteApplicationUser(long id, String userEmail, Boolean isAdmin) {
        ApplicationUser applicationUser = entityManager.find(ApplicationUser.class, id);
        if (applicationUser == null) {
            throw new NotFoundException();
        } else {
            if (applicationUser.getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
                entityManager.remove(applicationUser);
            } else {
                throw new ForbiddenException("NOT AUTHORIZED");
            }
        }
    }

    @Transactional
    public ApplicationUser updateApplicationUser(ApplicationUser applicationUser, String userEmail, Boolean isAdmin) {
        if (applicationUser.getEmail().equalsIgnoreCase(userEmail) || isAdmin == true) {
            if (isAdmin == false && applicationUser.getIsAdmin() == true) {
                throw new ForbiddenException("NOT AUTHORIZED");
            } else {
                ApplicationUser user = new ApplicationUser();
                user = entityManager.find(ApplicationUser.class, applicationUser.getId());
                if (user == null) {
                    throw new NotFoundException();
                } else {
                    entityManager.merge(applicationUser);
                    return applicationUser;
                }
            }
        } else {
            throw new ForbiddenException("NOT AUTHORIZED");
        }
    }
}

package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    public void deleteApplicationUser(long id) {
        ApplicationUser applicationUser = entityManager.find(ApplicationUser.class, id);
        entityManager.remove(applicationUser);
    }

    @Transactional
    public ApplicationUser updateApplicationUser(ApplicationUser applicationUser) {
        entityManager.merge(applicationUser);
        return applicationUser;
    }
}

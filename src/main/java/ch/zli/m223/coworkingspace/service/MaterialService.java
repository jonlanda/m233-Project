package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.coworkingspace.model.Material;

@ApplicationScoped
public class MaterialService {
    @Inject
    private EntityManager entityManager;

    public List<Material> findAll() {
        var query = entityManager.createQuery("FROM Material", Material.class);
        return query.getResultList();
    }

    public Material findSpecific(long id) {
        Material material = entityManager.find(Material.class, id);
        return material;
    }

    @Transactional
    public Material createMaterial(Material material) {
        entityManager.persist(material);
        return material;
    }

    @Transactional
    public void deleteMaterial(long id) {
        Material material = entityManager.find(Material.class, id);
        entityManager.remove(material);
    }

    @Transactional
    public Material updateMaterial(Material material) {
        entityManager.merge(material);
        return material;
    }
}

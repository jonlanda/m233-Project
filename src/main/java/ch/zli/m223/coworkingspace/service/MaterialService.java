package ch.zli.m223.coworkingspace.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

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
        if (material == null) {
            throw new NotFoundException();
        } else {
            return material;
        }
    }

    @Transactional
    public Material createMaterial(Material material) {
        entityManager.persist(material);
        return material;
    }

    @Transactional
    public void deleteMaterial(long id) {
        Material material = entityManager.find(Material.class, id);
        if (material == null) {
            throw new NotFoundException();
        } else {
            entityManager.remove(material);
        }
    }

    @Transactional
    public Material updateMaterial(Material material) {
        Material material2 = entityManager.find(Material.class, material.getId());
        if (material2 == null) {
            throw new NotFoundException();
        } else {
            entityManager.merge(material);
            return material;
        }
    }
}

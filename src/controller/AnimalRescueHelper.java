package controller;

import model.AnimalRescue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class AnimalRescueHelper {
    static EntityManagerFactory emfactory =
            Persistence.createEntityManagerFactory("AnimalRescue");

    public void insertItem(AnimalRescue li){
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(li);
        em.getTransaction().commit();
        em.close();
    }

    public List<AnimalRescue> showAllItems(){
        EntityManager em = emfactory.createEntityManager();
        List<AnimalRescue> allItems = em.createQuery("SELECT i FROM AnimalRescue i").getResultList();
        return allItems;
    }

    public void deleteItem(AnimalRescue toDelete) {
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<AnimalRescue> typedQuery = em.createQuery("select li from AnimalRescue li where li.animalName = :selectedAnimalName and li.animalType = :selectedAnimalType", AnimalRescue.class);
        typedQuery.setParameter("selectedAnimalName", toDelete.getanimalName());
        typedQuery.setParameter("selectedAnimalType", toDelete.getAnimalType());
        typedQuery.setMaxResults(1);
        AnimalRescue result = typedQuery.getSingleResult();
        em.remove(result);
        em.getTransaction().commit();
        em.close();
    }

    public List<AnimalRescue> searchForItemByAnimalName(String storeName) {
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<AnimalRescue> typedQuery = em.createQuery("select li from AnimalRescue li where li.animalName = :selectedAnimalName", AnimalRescue.class);
        typedQuery.setParameter("selectedAnimalName", storeName);
        List<AnimalRescue> foundItems = typedQuery.getResultList();
        em.close();
        return foundItems;
    }

    public List<AnimalRescue> searchForItemByAnimalType(String itemName) {
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<AnimalRescue> typedQuery = em.createQuery("select li from AnimalRescue li where li.animalType = :selectedAnimalType", AnimalRescue.class);
        typedQuery.setParameter("selectedAnimalType", itemName);
        List<AnimalRescue> foundItems = typedQuery.getResultList();
        em.close();
        return foundItems;
    }

    public AnimalRescue searchForItemById(int idToEdit) {
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        AnimalRescue found = em.find(AnimalRescue.class, idToEdit);
        em.close();
        return found;
    }

    public void updateItem(AnimalRescue toEdit) {
        EntityManager em = emfactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(toEdit);
        em.getTransaction().commit();
        em.close();
    }

    public void cleanUp(){
        emfactory.close();
    }
}

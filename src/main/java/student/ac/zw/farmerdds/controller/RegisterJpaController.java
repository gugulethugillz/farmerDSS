/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import student.ac.zw.farmerdds.controller.exception.NonexistentEntityException;
import student.ac.zw.farmerdds.controller.exception.PreexistingEntityException;
import student.ac.zw.farmerdds.model.Register;
import student.ac.zw.farmerdds.logic.PersistenceUnit;
import student.ac.zw.farmerdds.logic.farmerMail;

/**
 *
 * @author gugsy
 */
public class RegisterJpaController implements Serializable {
    
    PersistenceUnit pu = new PersistenceUnit();

    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return pu.getEntityManager();
    }

    public void create(Register register,HttpServletRequest request) throws PreexistingEntityException, Exception {
        EntityManager em = null;
    String salutation = request.getParameter("salutation");
    String fname = request.getParameter("fname");
    String surname = request.getParameter("surname");
    String email = request.getParameter("email");
    String password =request.getParameter("password");
    String farmerId = request.getParameter("farmerId");
    String address = request.getParameter("address");
    String city =request.getParameter("city");
    register.setSalutation(salutation);
    register.setFname(fname);
    register.setSurname(surname);
    register.setEmail(email);
    register.setPassword(password);
    register.setAddress(address);
    register.setCity(city);
    register.setFarmerId(farmerId);

    
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(register);
            em.getTransaction().commit();
            farmerMail fm = new farmerMail(farmerId, password, email, fname, surname);
        } catch (Exception ex) {
            if (findRegister(register.getFarmerId()) != null) {
                throw new PreexistingEntityException("Register " + register + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
    public void login(Register register,HttpServletRequest request){
    
        EntityManager em =pu.getEntityManager();
      
        register.setFarmerId(request.getParameter("farmerId"));
        register.setPassword(request.getParameter("password"));
        
    
    }
    
    
    public List<Register> findUsers() {
       
        EntityManager em =pu.getEntityManager();
        
        List result = new ArrayList();
         try{
         em.getTransaction().begin();
         Query query = em.createNamedQuery("Register.findAll");
         result = query.getResultList();
         
         
         }
    
     
         
         finally{
         {
            if (em != null) {
                em.close();
            }
         }
        
        return result;
    }
    }

    public void edit(Register register) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            register = em.merge(register);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = register.getFarmerId();
                if (findRegister(id) == null) {
                    throw new NonexistentEntityException("The register with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Register register;
            try {
                register = em.getReference(Register.class, id);
                register.getFarmerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The register with id " + id + " no longer exists.", enfe);
            }
            em.remove(register);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Register> findRegisterEntities() {
        return findRegisterEntities(true, -1, -1);
    }

    public List<Register> findRegisterEntities(int maxResults, int firstResult) {
        return findRegisterEntities(false, maxResults, firstResult);
    }

    private List<Register> findRegisterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Register.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Register findRegister(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Register.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegisterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Register> rt = cq.from(Register.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

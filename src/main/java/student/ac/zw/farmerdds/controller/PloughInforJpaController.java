/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import student.ac.zw.farmerdds.controller.exception.NonexistentEntityException;
import student.ac.zw.farmerdds.model.PloughInfor;
import student.ac.zw.farmerdds.logic.PersistenceUnit;

/**
 *
 * @author gugsy
 */
public class PloughInforJpaController implements Serializable {

  PersistenceUnit pu = new PersistenceUnit();
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return pu.getEntityManager();
    }

    public void create(PloughInfor ploughInfor,HttpServletRequest request) {
        
        LocalDate currentDate = LocalDate.now();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String datePlanted = dateFormat.format(currentDate);



        ploughInfor.setCropName(request.getParameter("cropName"));
        ploughInfor.setDatePlanted(datePlanted);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ploughInfor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //logic  for notification
    
    @RequestMapping(value="/notification", method=RequestMethod.POST)
    
    public ModelAndView notification(ModelAndView model){
    
        
    return model;
    }

    public void edit(PloughInfor ploughInfor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ploughInfor = em.merge(ploughInfor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ploughInfor.getId();
                if (findPloughInfor(id) == null) {
                    throw new NonexistentEntityException("The ploughInfor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PloughInfor ploughInfor;
            try {
                ploughInfor = em.getReference(PloughInfor.class, id);
                ploughInfor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ploughInfor with id " + id + " no longer exists.", enfe);
            }
            em.remove(ploughInfor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PloughInfor> findPloughInforEntities() {
        return findPloughInforEntities(true, -1, -1);
    }

    public List<PloughInfor> findPloughInforEntities(int maxResults, int firstResult) {
        return findPloughInforEntities(false, maxResults, firstResult);
    }

    private List<PloughInfor> findPloughInforEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PloughInfor.class));
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

    public PloughInfor findPloughInfor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PloughInfor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPloughInforCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PloughInfor> rt = cq.from(PloughInfor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

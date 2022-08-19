/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import student.ac.zw.farmerdds.controller.exception.NonexistentEntityException;
import student.ac.zw.farmerdds.model.Queries;
import student.ac.zw.farmerdds.logic.PersistenceUnit;

/**
 *
 * @author gugsy
 */
@Controller
public class QueriesJpaController implements Serializable {
    PersistenceUnit pu = new PersistenceUnit();

    public EntityManager getEntityManager() {
        return pu.getEntityManager();
    }
    
    
    
@RequestMapping(value = "/createQ", method = RequestMethod.POST)
    public ModelAndView createQ(Queries queries,ModelAndView model,HttpServletRequest request) {
       
        String question = request.getParameter("question");
        String response = request.getParameter("response");
         queries.setQuestion(question);
         queries.setResponse(response);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(queries);
            em.getTransaction().commit();
           viewQuestions(model);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return model;
    }
//@RequestMapping(value="/editA",method =RequestMethod.POST)
    public Queries editAnn(ModelAndView model,HttpServletRequest request) throws NonexistentEntityException, Exception {
        Queries queries = new Queries();
       String question = request.getParameter("question");
        String response = request.getParameter("response");
        int id = Integer.parseInt(request.getParameter("id"));
       // queries.setId(id);
        
        EntityManager em = null;
        
        try {
            em = getEntityManager();
            queries=em.find(Queries.class, id);
            em.getTransaction().begin();
            queries.setQuestion(question);
            queries.setResponse(response);
            System.out.println(response);
            em.getTransaction().commit();
            viewQuestions(model);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = queries.getId();
                if (findQueries(id) == null) {
                    throw new NonexistentEntityException("The queries with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return queries;
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Queries queries;
            try {
                queries = em.getReference(Queries.class, id);
                queries.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The queries with id " + id + " no longer exists.", enfe);
            }
            em.remove(queries);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Queries> findQueriesEntities() {
        return findQueriesEntities(true, -1, -1);
    }
@RequestMapping(value = "/viewQuestions",method = RequestMethod.GET)

public ModelAndView viewQuestions(ModelAndView model){
    
            EntityManager em ;  
            em = getEntityManager();
            em.getTransaction().begin();
            Query q1 = em.createQuery("SELECT q FROM Queries q");
             List<Queries> qq = q1.getResultList();
            em.close();
            model.addObject("qq", qq);
            model.setViewName("viewAllq.jsp");
            return model;



}

    public List<Queries> findQueriesEntities(int maxResults, int firstResult,ModelAndView model) {
           return findQueriesEntities(false, maxResults, firstResult);
    }

    private List<Queries> findQueriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Queries.class));
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

    public Queries findQueries(Integer id) {
        
              EntityManager em = getEntityManager();
        try {
            return em.find(Queries.class, id);
        } finally {
            em.close();
        }
    }

    public int getQueriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Queries> rt = cq.from(Queries.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

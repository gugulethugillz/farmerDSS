/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
import student.ac.zw.farmerdds.model.CropInfor;
import student.ac.zw.farmerdds.logic.PersistenceUnit;

/**
 *
 * @author gugsy
 */
  @Controller 
public class CropInforJpaController implements Serializable {

    PersistenceUnit pu = new PersistenceUnit();
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return pu.getEntityManager();
    }

    public void create( ModelAndView model ,CropInfor cropInfor,HttpServletRequest request) {
        EntityManager em = null;
        
        String cropName = request.getParameter("cropName");
        String soilType = request.getParameter("soilType");
        String phValue = request.getParameter("phValue");
        String moistureValue = request.getParameter("moistureValue");
        
        cropInfor.setCropName(cropName);
        cropInfor.setSoilType(soilType);
        cropInfor.setPhValue(phValue);
        cropInfor.setMoistureValue(moistureValue);
         try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cropInfor);
            
            em.getTransaction().commit();
        } 
        
        
        finally {
            if (em != null) {
                em.close();
            }
        }
      
  
    
      
    }

   
    public void edit(CropInfor cropInfor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cropInfor = em.merge(cropInfor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cropInfor.getId();
                if (findCropInfor(id) == null) {
                    throw new NonexistentEntityException("The cropInfor with id " + id + " no longer exists.");
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
            CropInfor cropInfor;
            try {
                cropInfor = em.getReference(CropInfor.class, id);
                cropInfor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cropInfor with id " + id + " no longer exists.", enfe);
            }
            em.remove(cropInfor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
//find all crops
    
    public List<CropInfor> findCropInforEntities() {
        return findCropInforEntities(true, -1, -1);
    }

    public List<CropInfor> findCropInforEntities(int maxResults, int firstResult) {
        return findCropInforEntities(false, maxResults, firstResult);
    }

    private List<CropInfor> findCropInforEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CropInfor.class));
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

    public CropInfor findCropInfor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CropInfor.class, id);
        } finally {
            em.close();
        }
    }

    public int getCropInforCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CropInfor> rt = cq.from(CropInfor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
 
    
    //method to classify 
    @RequestMapping(value = "/classify",method = RequestMethod.POST)
    
    public ModelAndView classifyCrops( ModelAndView model ,HttpServletRequest request){
   
        String cropName = request.getParameter("cropName");
        String soilType = request.getParameter("soilType");
        String phValue = request.getParameter("phValue");
        String moistureValue = request.getParameter("moistureValue");
        
        CropInfor crops = new CropInfor();
        crops.setCropName(cropName);
        crops.setSoilType(soilType);
        crops.setPhValue(phValue);
        crops.setMoistureValue(moistureValue);
        
    if (cropName.equalsIgnoreCase("maize")){
    
        if(soilType.equalsIgnoreCase("loam")&&phValue.equalsIgnoreCase("acidic")&&moistureValue.equalsIgnoreCase("adequate")) {
               
                    model.setViewName("idealMaize.jsp");
        }     
                
            
        
        if(phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("betterMaize.jsp");
                   
                }
            }
        
        if(phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("noMaize.jsp");
                   
                }
            }
    
    }
    
      if (cropName.equalsIgnoreCase("beans")){
    
        if(phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("ideaMaize.jsp");
                   
                }
            }
        
        if(phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("ideaMaize.jsp");
                   
                }
            }
        
        if(phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("ideaMaize.jsp");
                   
                }
            }
    
    }
      
        if (cropName.equalsIgnoreCase("cotton")){
    
        if(phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("idealCotton.jsp");
                   
                }
            }
        
        if(phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("betterCotton.jsp");
                   
                }
            }
        
        if(phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("badCotton.jsp");
                   
                }
            }
    
    }
        
        
    return model;
    }
    
}

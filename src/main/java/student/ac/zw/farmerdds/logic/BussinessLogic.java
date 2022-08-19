/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import student.ac.zw.farmerdds.controller.CropInforJpaController;
import student.ac.zw.farmerdds.controller.PloughInforJpaController;
import student.ac.zw.farmerdds.controller.QueriesJpaController;
import student.ac.zw.farmerdds.controller.RegisterJpaController;
import student.ac.zw.farmerdds.controller.exception.NonexistentEntityException;
import student.ac.zw.farmerdds.model.CropInfor;
import student.ac.zw.farmerdds.model.PloughInfor;
import student.ac.zw.farmerdds.model.Queries;
import student.ac.zw.farmerdds.model.Register;

/**
 *
 * @author gugsy
 */
@SessionAttributes("farmerId")
@Controller
public class BussinessLogic {

    RegisterJpaController rjc = new RegisterJpaController();
    CropInforJpaController cij = new CropInforJpaController();
    PloughInforJpaController pij = new PloughInforJpaController();
    CropInfor crops = new CropInfor();
    PersistenceUnit pu = new PersistenceUnit();
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return pu.getEntityManager();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)

    public ModelAndView register(Register register, HttpServletRequest request, ModelAndView model) throws Exception {

        rjc.create(register, request);
        model.setViewName("registerSuccess.jsp");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)

    public ModelAndView login(ModelAndView model, HttpServletRequest request) {

        String farmerId = request.getParameter("farmerId");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        //userId = (String)session.getAttribute("appId"); 
        System.out.println("this is the applicant is " + farmerId);
        session.setAttribute("farmerId", farmerId);
        EntityManager em1 = getEntityManager();
     
        em1.getTransaction().begin();

        Query query1 = em1.createQuery("SELECT r FROM Register r WHERE r.farmerId = :farmerId and r.password =:password");
        query1.setParameter("farmerId", farmerId);
        query1.setParameter("password", password);
        List<Register> rr = query1.getResultList();
        em1.close();
        if (!rr.isEmpty()) {

            EntityManager em = getEntityManager();

            em.getTransaction().begin();
            Query q1 = em.createQuery("SELECT c FROM CropInfor c WHERE c.farmerId = :farmerId");
            q1.setParameter("farmerId", farmerId);
            List<CropInfor> crops1 = q1.getResultList();
            em.close();
            model.addObject("crops", crops1);
            model.setViewName("viewAll.jsp");

        } else {

            model.setViewName("loginError.jsp");
        }

        return model;
    }

    
    
    //code to reset password
    
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView reset(ModelAndView model,HttpServletRequest request){
    
    String farmerId = request.getParameter("farmerId");
    String password = request.getParameter("password");
    
    EntityManager em2 = getEntityManager();
     
        em2.getTransaction().begin();

        Query query3 = em2.createQuery("UPDATE Register r set r.password :password where r.farmerId :farmerId ");
        return model;
    }
    //view all crop information
    @RequestMapping(value = "/viewAll", method = RequestMethod.GET)

    public ModelAndView viewAll(ModelAndView model, HttpServletRequest request) {

        //session
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        //listing all patients 
        EntityManager em = null;

        em = getEntityManager();
        em.getTransaction().begin();
        Query q1 = em.createQuery("SELECT c FROM CropInfor c WHERE c.farmerId = :farmerId");
        q1.setParameter("farmerId", farmerId);
        List<CropInfor> cropsV = q1.getResultList();
        em.close();
        model.addObject("crops", cropsV);
        System.out.println("about to print");
        model.setViewName("viewAll.jsp");
        return model;
    }

    //logic for farmer to input measurements
    @RequestMapping(value = "/createCrop", method = RequestMethod.POST)

    public ModelAndView createCrop(CropInfor cropInfor, ModelAndView model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

//call on method in controller that defines how to save crop information
        EntityManager em = null;

        String cropName = request.getParameter("cropName");
        String soilType = request.getParameter("soilType");
        String phValue = request.getParameter("phValue");
        String moistureValue = request.getParameter("moistureValue");
        String datePlanted = request.getParameter("datePlanted");

        cropInfor.setCropName(cropName);
        cropInfor.setSoilType(soilType);
        cropInfor.setPhValue(phValue);
        cropInfor.setMoistureValue(moistureValue);
        cropInfor.setFarmerId(farmerId);
        cropInfor.setDatePlanted(datePlanted);
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cropInfor);

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
          //  classifyCrops(model,request);
        //function of ModelAndView to view page on where function is executed
        model.setViewName("viewAll.jsp");
        return model;

    }

    //logic to edit 
    @RequestMapping(value = "/edit", method = RequestMethod.GET)

    public ModelAndView edit(ModelAndView model, HttpServletRequest request) {

        //session
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        int id = Integer.parseInt(request.getParameter("id"));
        CropInfor crops = cij.findCropInfor(id);
        System.out.println("Are we here yet");
        model.addObject("crops", crops);
        model.setViewName("editCrop.jsp");

        System.out.println(crops.getCropName());
        return model;

    }
    //code to redirect to crop classification

    @RequestMapping(value = "/generateReport", method = RequestMethod.GET)

    public ModelAndView generateReport(ModelAndView model, HttpServletRequest request) {

        //session
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        int cropId = Integer.parseInt(request.getParameter("cropId"));
        CropInfor crops2 = cij.findCropInfor(cropId);
        System.out.println("Are we here yet");
        model.setViewName("generateReport.jsp");
        model.addObject("crops", crops2);

        return model;

    }

    //have different logic for different crops and information entered and conditions offered
    //code to print the guide
    //code to classify crops
    @RequestMapping(value = "/classifyCrops", method = RequestMethod.GET)

    public ModelAndView classifyCrops(ModelAndView model,Model mm, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        int id = Integer.parseInt(request.getParameter("id"));
        CropInfor cropsA = cij.findCropInfor(id);

        String cropName = cropsA.getCropName();
        String soilType = cropsA.getSoilType();
        String phValue = cropsA.getPhValue();
        String moistureValue = cropsA.getMoistureValue();

        if (cropName.equalsIgnoreCase("maize")) {

            if (soilType.equalsIgnoreCase("loam") && phValue.equalsIgnoreCase("acidic") && moistureValue.equalsIgnoreCase("adequate")) {

                model.setViewName("maize1.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);
                
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize2.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize3.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize4.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize5.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize6.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize7.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize8.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize9.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize10.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize11.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize12.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize13.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize14.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize15.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize16.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize17.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize18.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize19.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);
                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize20.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize21.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize22.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize23.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize24.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("maize25.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("maize26.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("maize27.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
        }

if (cropName.equalsIgnoreCase("cotton")) {
            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton1.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton2.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton3.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton4.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton5.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton6.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton7.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton8.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton9.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton10.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton11.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton12.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton13.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton14.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton15.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton16.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton17.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton18.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton19.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton20.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton21.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton22.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton23.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton24.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("cotton25.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("cotton26.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("cotton27.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }          
        }
		
if (cropName.equalsIgnoreCase("tobacco")) {

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco1.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco2.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco3.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco4.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco5.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco6.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco7.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco8.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("loam")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco9.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco10.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco11.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco12.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco13.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco14.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco15.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco16.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco17.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("clay")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco18.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }

            if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco19.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco20.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("acidic") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco21.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco22.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco23.jsp");
                mm.addAttribute("soilType", soilType);
                System.out.println(soilType);
                mm.addAttribute("phValue",phValue);
                mm.addAttribute("moistureValue",moistureValue);
                mm.addAttribute("cropName",cropName);

                }
            }
			
            if (phValue.equalsIgnoreCase("alkaline") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco24.jsp");
                    mm.addAttribute("soilType", soilType);
                    System.out.println(soilType);
                    mm.addAttribute("phValue",phValue);
                    mm.addAttribute("moistureValue",moistureValue);
                    mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("adequate")) {
                    model.setViewName("tobacco25.jsp");
                    mm.addAttribute("soilType", soilType);
                    System.out.println(soilType);
                    mm.addAttribute("phValue",phValue);
                    mm.addAttribute("moistureValue",moistureValue);
                    mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("not enough")) {
                    model.setViewName("tobacco26.jsp");
                    mm.addAttribute("soilType", soilType);
                    System.out.println(soilType);
                    mm.addAttribute("phValue",phValue);
                    mm.addAttribute("moistureValue",moistureValue);
                    mm.addAttribute("cropName",cropName);

                }
            }
			
	    if (phValue.equalsIgnoreCase("neutral") && soilType.equalsIgnoreCase("sand")) {
                if (moistureValue.equalsIgnoreCase("over saturated")) {
                    model.setViewName("tobacco27.jsp");
                    mm.addAttribute("soilType", soilType);
                    System.out.println(soilType);
                    mm.addAttribute("phValue",phValue);
                    mm.addAttribute("moistureValue",moistureValue);
                    mm.addAttribute("cropName",cropName);

                }
            }
        }

        return model;
    }

    //function to remove crop from view
    @RequestMapping(value = "/removeQueue", method = RequestMethod.GET)

    public ModelAndView removeQueue(ModelAndView model, HttpServletRequest request) throws NonexistentEntityException {

        //session
        CropInforJpaController contr = new CropInforJpaController();
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("username", farmerId);

        String cropIdD = request.getParameter("id");
        int removeD = Integer.parseInt(cropIdD);
        System.out.println(cropIdD);
        contr.destroy(removeD);
        viewAll(model,request);
        model.setViewName("viewAll.jsp");
        return model;

    }

    //farming time table logic
    @RequestMapping(value = "/plough", method = RequestMethod.POST)

    public ModelAndView plough(HttpServletRequest request, ModelAndView model, PloughInfor ploughInfor) throws ParseException {

        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("username", farmerId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //  String datePlanted = dateFormat.format(LocalDate.now());
        Date date = new Date();
        String datePlanted = dateFormat.format(date);
        ploughInfor.setCropName(request.getParameter("cropName"));
        ploughInfor.setFarmerId(farmerId);
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

        model.setViewName("viewPloughed.jsp");

        return model;

    }

    //viewAllPloughed
    @RequestMapping(value = "/viewPloughed", method = RequestMethod.GET)

    public ModelAndView viewPloughed(ModelAndView model, HttpServletRequest request) {

        //session
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        //listing all patients 
        EntityManager em = null;

        em = getEntityManager();
        em.getTransaction().begin();
        Query q1 = em.createQuery("SELECT c FROM PloughInfor c WHERE c.farmerId = :farmerId");
        q1.setParameter("farmerId", farmerId);
        List<PloughInfor> plough = q1.getResultList();
        em.close();
        model.addObject("plough", plough);
        System.out.println("about to print");
        //model.setViewName("viewPloughed.jsp");
        model.setViewName("aGraph.jsp");
        return model;
    }

    //plough notifications
    @RequestMapping(value = "/timetable", method = RequestMethod.GET)

    public ModelAndView timetable(ModelAndView model, HttpServletRequest request) throws ParseException {

        //session
        HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);

        int id = Integer.parseInt(request.getParameter("id"));
        PloughInfor cic = pij.findPloughInfor(id);

        String cropName = cic.getCropName();
        String datePlanted = cic.getDatePlanted();
        //String phValue = cic.getphValue();
        Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(datePlanted);
        //get current date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        //compare two dates using difference between dates
        long diff = date.getTime() - date1.getTime();
        long diff1 = date.compareTo(date1);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        int months = (int) diffDays / 30;

        //code to get email Parameters
        //listing all patients 
        EntityManager em = null;

        em = getEntityManager();
        em.getTransaction().begin();
        Query q1 = em.createQuery("SELECT c FROM Register c WHERE c.farmerId = :farmerId");
        q1.setParameter("farmerId", farmerId);
        List<Register> regi = new ArrayList<>();
        Register rr = regi.get(0);
        String email = regi.get(0).getEmail();
        String fname = regi.get(0).getFname();
        String surname = regi.get(0).getSurname();
        em.close();
       

        if (cropName.equalsIgnoreCase("maize") /*&& months > 3*/) {

            IdealMail imail = new IdealMail(fname, surname, email);
            model.setViewName("idealMaize.jsp");
            //model.setViewName("aGraph.jsp");
        } else {
            model.setViewName("registerSuccess.jsp");
        }
        System.out.println("Are we here yet");
        return model;

    }
/*Protected void (HTTPServletRequest request, HTTPServlertResponse response){
        Gson gson = new Gson();
        
                }*/
    
    //code to edit queries
    
   @RequestMapping(value="/editQ",method =RequestMethod.GET)  
    public ModelAndView editQ(ModelAndView model,HttpServletRequest request) throws Exception{
          HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);
        int id = Integer.parseInt(request.getParameter("id"));
    QueriesJpaController qjc = new QueriesJpaController();
        Queries qq = qjc.findQueries(id);
        System.out.println("Are we here yet");
       
        model.setViewName("response.jsp");
        model.addObject("qq", qq);
        return model;
    }
    
    @RequestMapping(value="/editA",method =RequestMethod.POST)
     public ModelAndView editA(ModelAndView model,HttpServletRequest request) throws Exception{
          HttpSession session = request.getSession(false);
        String farmerId = (String) session.getAttribute("farmerId");
        System.out.println("this is the applicant id in personal details controller\n" + farmerId);
        session.setAttribute("farmerId", farmerId);
        QueriesJpaController qqc1 = new QueriesJpaController();
        Queries quer = qqc1.editAnn(model, request);
        return model;
    
        
}
}
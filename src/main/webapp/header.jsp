<%@page import="student.ac.zw.farmerdds.model.Register" %>
<%@page import="student.ac.zw.farmerdds.model.CropInfor" %>
<%@page import ="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<header class="topbar">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <!-- ============================================================== -->
                <!-- Logo -->
                <!-- ============================================================== -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="login.jsp">
                        <!-- Logo icon --><b>
                            <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                            <!-- Dark Logo icon 
                            <img src="resources/assets/images/logo-icon.png" alt="homepage" class="dark-logo" />
                            <!-- Light Logo icon
                            <img src="resources/assets/images/logo-light-icon.png" alt="homepage" class="light-logo" />
                       --> </b>
                        <!--End Logo icon -->
                        <!-- Logo text --><span>
                         <!-- dark Logo text 
                         <img src="resources/assets/images/logo-text.png" alt="homepage" class="dark-logo" />
                         <!-- Light Logo text    
                         <img src="resources/assets/images/logo-light-text.png" class="light-logo" alt="homepage" /></span> </a>
               --> </div>
                <!-- ============================================================== -->
                <!-- End Logo -->
                <!-- ============================================================== -->
                <div class="navbar-collapse">
                    <!-- ============================================================== -->
                    <!-- toggle and nav items -->
                    <!-- ============================================================== -->
           
                    <!-- ============================================================== -->
                    <!-- User profile and search -->
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="${pageContext.request.contextPath}/viewAll" button class="btn pull-right hidden-sm-down btn-success"><i class="mdi mdi-plus-circle"></i> Home</a>
                       
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a href="${pageContext.request.contextPath}/viewQuestions" button class="btn pull-right hidden-sm-down btn-success"><i class="mdi mdi-plus-circle"></i> View Questions</a>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="cropInformation.jsp" button class="btn pull-right hidden-sm-down btn-success"><i class="mdi mdi-plus-circle"></i> Add Crop Information</a>&nbsp;
                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="${pageContext.request.contextPath}/viewPloughed" button class="btn pull-right hidden-sm-down btn-success"><i class="mdi mdi-plus-circle"></i> View Ploughed Crops</a>
                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="login.jsp" button class="btn pull-right hidden-sm-down btn-success"><i class="mdi mdi-plus-circle"></i> Logout</a>
                       

                    
                </div>
            </nav>
        </header>
       
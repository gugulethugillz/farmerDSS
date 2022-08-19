 <aside class="left-sidebar">
            <!-- Sidebar scroll-->
            <div class="scroll-sidebar">
                <!-- User profile -->
                <div class="user-profile">
                    <!-- User profile image -->
                    <div class="profile-img"> <img src="../assets/images/users/1.jpg" alt="user" /> </div>
                    <!-- User profile text-->
                    <div class="profile-text"> <a href="#" class="dropdown-toggle link u-dropdown" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">Markarn Doe <span class="caret"></span></a>
                        <div class="dropdown-menu animated flipInY">
                            <a href="#" class="dropdown-item"><i class="ti-user"></i> My Profile</a>
                            <a href="#" class="dropdown-item"><i class="ti-wallet"></i> My Balance</a>
                            <a href="#" class="dropdown-item"><i class="ti-email"></i> Inbox</a>
                            <div class="dropdown-divider"></div> <a href="#" class="dropdown-item"><i class="ti-settings"></i> Account Setting</a>
                            <div class="dropdown-divider"></div> <a href="login.html" class="dropdown-item"><i class="fa fa-power-off"></i> Logout</a>
                        </div>
                    </div>
                </div>
                <!-- End User profile text-->
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="nav-small-cap">MENU</li>
                        <li>
                            <a class="has-arrow" href="#" aria-expanded="false"><i class="mdi mdi-gauge"></i><span class="hide-menu">Patient Booking<span class="label label-rounded label-success">5</span></span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="addPatient.jsp">Book A Patient</a></li>
                                <li><a href="editPatient.jsp">Edit Patient Booking</a></li>
                                <li><a href="${pageContext.request.contextPath}/todayBookings">Today Patients</a></li>
                                <li><a href="${pageContext.request.contextPath}/viewAll" >All Patients</a></li>
                            </ul>
                        </li>
                        <li>
                            <a class="has-arrow " href="#" aria-expanded="false"><i class="mdi mdi-bullseye"></i><span class="hide-menu">Invoicing</span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="invoice.jsp">BOMAID</a></li>
                                <li><a href="invoicePula.jsp">PULA</a></li>
                                <li><a href="invoiceBotsong.jsp">BOTSONG</a></li>
                                <li><a href="cashInvoice.jsp">CASH</a></li>
                                <li><a href="rate.jsp">Medical Aid Rate</a></li>
                            </ul>
                        </li>
                        
                          <li>
                            <a class="has-arrow " href="#" aria-expanded="false"><i class="mdi mdi-bullseye"></i><span class="hide-menu">Medical Aid Rates</span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="invoice.jsp">View Current Medical Aid Rates</a></li>
                                <li><a href="rate.jsp">Edit Medical Aid Rate</a></li>
                            </ul>
                        </li>
                        
                       <li>
                            <a class="has-arrow" href="#" aria-expanded="false"><i class="mdi mdi-chart-bubble"></i><span class="hide-menu">REPORTS</span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="ui-cards.html">List of Patients by Date</a></li>
                                <li><a href="ui-user-card.html"></a>List of Patients by Scan</li>
                                <li><a href="ui-buttons.html">BOMAID Patients</a></li>
                                <li><a href="ui-modals.html">PULA Patients</a></li>
                                <li><a href="ui-tab.html">BOTSONG Patients</a></li>
                                <li><a href="ui-tooltip-popover.html">CASH patients</a></li>
                                <li><a href="ui-tooltip-stylish.html">Invoice by Month</a></li>
                                <li><a href="ui-sweetalert.html">Invoice by Year</a></li>
                                <li><a href="${pageContext.request.contextPath}/viewRates">Current Rates</a></li>
                      
                            </ul>
                        </li>
                        <li class="nav-devider"></li>
                    
                    </ul>
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
            <!-- Bottom points-->
            <div class="sidebar-footer">
                <!-- item-->
                <a href="" class="link" data-toggle="tooltip" title="Settings"><i class="ti-settings"></i></a>
                <!-- item-->
                <a href="" class="link" data-toggle="tooltip" title="Email"><i class="mdi mdi-gmail"></i></a>
                <!-- item-->
                <a href="" class="link" data-toggle="tooltip" title="Logout"><i class="mdi mdi-power"></i></a>
            </div>
            <!-- End Bottom points-->
        </aside>
       
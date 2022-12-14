<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="resources/assets/images/favicon.png">
    <title>Farmer Decision Support System</title>
    <!-- Bootstrap Core CSS -->
    <link href="resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="resources/css/style.css" rel="stylesheet">
    <!-- You can change the theme colors from here -->
    <link href="resources/css/colors/green.css" id="theme" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
    <style>
div.center {
  background-color: lightgrey;
  width: 1000px;
  height: 550px;
  border: 15px solid green;
  padding: 70px;
  text-align: center;
  top: 70px;
  left: 200px;
 }
 a{
   top:35px;
 }
</style>
</head>

<body class="fix-header card-no-border">
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <section id="wrapper" class="error-page">
        <div class= "w3-container error-box center" style="float: center"> 
                <h2 class="text-themecolor m-b-0 m-t-0">REPORT</h2>
                <h3 class="text-muted">Not Ideal Conditions!</h3>
                <p class="m-t-30 m-b-30">Sandy soils have very low fertility causing reduced biomass and crop production. Sandy soils inherently have low amounts of nutrients, low soil organic matter and are weakly structured. Ways of managing and replenishing soil fertility in such fields include application of compost manure, planting tree species and leguminous plants (e.g. Tephrosia vogelli), incorporating crop residues during land preparations, leaving fields to fallow as well as applying inorganic fertilizer.</p>
                <p>Crop name is ${cropName} while SoilType is ${soilType}</p>
                <a href="loginError.jsp"></a>
                <a href="${pageContext.request.contextPath}/viewAll" class="btn btn-info btn-rounded waves-effect waves-light m-b-40">Home</a> </div>
            <footer class="footer text-center">2020? gugulethumasuku</footer>
        
    </section>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
    <!-- ============================================================== -->
    <script src="resources/assets/plugins/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="resources/assets/plugins/bootstrap/js/popper.min.js"></script>
    <script src="resources/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <!--Wave Effects -->
    <script src="resouces/js/waves.js"></script>
</body>

</html>

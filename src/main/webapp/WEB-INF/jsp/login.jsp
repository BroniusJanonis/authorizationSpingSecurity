<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--pasidarom kelia, kad gautumem resursus, nes taip bus lengviau susirasti kelia, Bootsrap, Css--%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Registration window</title>
    <%--randam, kur yra registracijos langas--%>
    <link rel="stylesheet" href="${path}/resources/css/style.css">
    <link rel="stylesheet" href="${path}/resources/css/bootstrap.min.css">
</head>
<body>
<div>

    <div class="container">
        <div class="row">

            <div class="col-md-8 align-content-lg-center">
                <%--/login dave sping'as kaip veina is pasirinkimu--%>
                <form method="post" action="${path}/login" class="form-control">
                    <input type="text" name="username" placeholder="Username" autofocus="true"/>
                    <input type="password" name="password" placeholder="Password"/>
                    <%--kadangi kalbejom, jog paduosim token, tai springe naudojamas "_csrf"  < cia yra panasus kaip sesijos kodas, nes eisim per puslapi
                    , ji issikviesim kiekveinam puslapy, jis bus headeriuose ir taip autintifikuosimes. Jis alia yra saugesnis nei sesija--%>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" value="Log In">Log In</button>
                    <%--jei nesuveikia loginas, tai nesi prisiregistraves, tada nukreipiam i registracija--%>
                    <h4><a href="${path}/register"> Create new user </a></h4>
                </form>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${path}/resources/js/bootstrap.js"/>
<script type="text/javascript" src="${path}/resources/js/jquery-3.2.1.js"/>
</body>
</html>
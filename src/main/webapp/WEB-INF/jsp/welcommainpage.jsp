<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--pasidarom kelia, kad gautumem resursus, nes taip bus lengviau susirasti kelia, Bootsrap, Css--%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Welcome window</title>
    <%--randam, kur yra registracijos langas--%>
    <link rel="stylesheet" href="${path}/resources/css/style.css">
    <link rel="stylesheet" href="${path}/resources/css/bootstrap.min.css">
</head>
<body>

Veikia
<c:if test="${pageContext.request.userPrincipal.name!=null}">
    <%--/logout cia yra Spring'o logout metodas, kuris atsakingas uz token sesijos atsijungima.
    Pirma siunciam i WebSecurityConfiguration > "/logout" ir ten atjungiam bei on success nukreipia i mvc3 controlerio "/" langa, kuris pas mus yra irgi logino langas--%>
    <form id="logoutForm" method="post" action="${path}/logout">
        <%--perduodam tokenus--%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    </form>
    <%--cia pateikiam vartotoja ir onclick padarom, jog atjungtumem vartotoja--%>
    <h2>Labas vartotojau vardu: ${pageContext.request.userPrincipal.name} |->
        <a onclick="document.forms['logoutForm'].submit()">Logout</a>
    </h2>
</c:if>

<script type="text/javascript" src="${path}/resources/js/bootstrap.js"/>
<script type="text/javascript" src="${path}/resources/js/jquery-3.2.1.js"/>
</body>
</html>
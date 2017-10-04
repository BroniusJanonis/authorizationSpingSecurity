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
                <%--kadangi form:form tipe nurodom modelAttribute="userForm", tai ir kontroleryje reikia nusirodyt @ModelAttribute("userForm") --%>
                <form:form method="post" modelAttribute="userForm" cssClass="formdesign">
                        <form:input type="text" path="username" placeholder="Username" autofocus="true"/>
                        <form:errors path="username"></form:errors>
                        <form:input type="password" path="password" placeholder="Password"/>
                        <form:errors path="password"></form:errors>
                        <form:input type="password" path="passwordconfirm" placeholder="Confirm Password"/>
                        <form:errors path="passwordconfirm"></form:errors>
                        <form:button type="submit" class="btn" value="save"/>

                </form:form>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${path}/resources/js/bootstrap.js"/>
<script type="text/javascript" src="${path}/resources/js/jquery-3.2.1.js"/>
</body>
</html>
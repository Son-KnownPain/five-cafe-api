<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        Welcome
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        
    </jsp:attribute>
    <jsp:body>
        <div class="flex items-center justify-center mt-5">
            <div>
                <div class="flex items-center justify-center mb-5">
                    <img width="200px" src="${pageContext.request.contextPath}/resources/images/layout/FiveCafeLogo.png" alt="Logo" />
                </div>
                <h1 class="font-semibold text-5xl text-gray-900">Hi, welcome to Five Cafe management website</h1>
            </div>
        </div>
    </jsp:body>
</t:admin_layout>
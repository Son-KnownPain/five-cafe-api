<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:no_layout>
    <jsp:attribute name="title">
        Menu
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/menu.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="fixed inset-0 flex items-center flex-col bg-opacity-20 bg-cover bg-no-repeat bg-center overflow-y-auto" style="background-image: url('https://statics.vincom.com.vn/xu-huong/0-0-0-0-9-quan-ca-phe/THE-VIBES-1.jpg');">
            <!-- Heading -->
            <div class="mt-4">
                <img src="${pageContext.request.contextPath}/resources/images/layout/FiveCafeLogo.png" alt="Logo" class="h-16" />
            </div>
            <!-- Content -->
            <div class="mt-4 rounded-lg bg-cyan-950 w-full max-w-screen-md p-5" id="menu-content">
                <!-- RenderByJS -->
            </div>
        </div>
    </jsp:body>
</t:no_layout>
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
        <script src="${pageContext.request.contextPath}/resources/js/pages/greet.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="flex items-center justify-center mt-5">
            <div class="text-center">
                <h1 class="font-bold text-4xl text-gray-900">Five Cafe management website</h1>
                <p class="mt-3 font-normal text-gray-500 text-sm">Hi, welcome to the Five Cafe Management Website, where you can effortlessly oversee and optimize all aspects of your cafe operations. Explore intuitive tools for employee management, inventory control, bill tracking, and more to enhance the efficiency and success of your cafe business.</p>
            </div>
        </div>

        <!-- Material quantityInStock below 5 -->
        <div class="mt-5 bg-slate-100 rounded-md p-4" id="product-out-quantityinstock-box">
            <h1 class="text-sm font-semibold text-gray-500">The product has very little inventory</h1>
            <div class="grid grid-cols-1 gap-4" id="product-out-quantityinstock-content">
                <!-- RenderedByJS -->
            </div>
        </div>
    </jsp:body>
</t:admin_layout>
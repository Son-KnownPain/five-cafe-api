<%@tag description="Main layout" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="styles" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>
<%@attribute name="title" fragment="true" %>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="my-app-name" content="${pageContext.request.contextPath}"><!-- Favicon -->
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/layout/favicon.png"/>
    <title><jsp:invoke fragment="title" /> | Five Cafe</title>
    <%@include file="../../../resources/includes/css.jsp" %>
    <jsp:invoke fragment="styles" />
</head>
<body>
    <div id="app">
        <%@include file="../../../resources/partials/header.jsp" %>
        <!-- Main Content -->
        <div id="content">
            <!-- Sidebar -->
            <%@include file="../../../resources/partials/sidebar.jsp" %>

            <!-- Main Content Area -->
            <main id="main-content">
                <!-- Your content goes here -->
                <jsp:doBody />
            </main>
        </div>
    </div>
    
    <%@include file="../../../resources/includes/js.jsp" %>
    <jsp:invoke fragment="scripts" />
</body>
</html>
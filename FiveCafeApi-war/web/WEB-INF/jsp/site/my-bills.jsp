<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        My bills
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/create-outbound.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Page head introduction -->
        <div class="mb-4">
            <h1 class="font-semibold text-3xl text-gray-800">My bills</h1>
            <p class="font-normala text-gray-500 text-base mt-3">
                You can view the bills you have created and edit them. If you wish to delete a bill, please contact a user with higher privileges, such as the owner, to perform the operation.
            </p>
        </div>
        <!-- Alerts -->
            <!-- Success alert -->
        <div id="success-alert" class="hidden items-center p-4 mb-4 text-green-800 border-t-4 border-green-300 bg-green-50 dark:text-green-400 dark:bg-gray-800 dark:border-green-800" role="alert">
            <i class="fa-regular fa-circle-check"></i>
            <div class="ms-3 text-sm font-medium" id="success-alert-content"></div>
            <button type="button" class="ms-auto -mx-1.5 -my-1.5 bg-green-50 text-green-500 rounded-lg focus:ring-2 focus:ring-green-400 p-1.5 hover:bg-green-200 inline-flex items-center justify-center h-8 w-8 dark:bg-gray-800 dark:text-green-400 dark:hover:bg-gray-700"  data-dismiss-target="#success-alert" aria-label="Close">
              <span class="sr-only">Dismiss</span>
              <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
              </svg>
            </button>
        </div>
            <!-- Invalid alert -->
        <div id="invalid-alert" class="hidden p-4 mb-4 text-sm text-yellow-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-yellow-400" role="alert">
            <svg class="flex-shrink-0 inline w-4 h-4 me-3 mt-[2px]" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
            </svg>
            <span class="sr-only">Danger</span>
            <div>
                <span id="invalid-alert-title" class="font-medium"></span>
                <ul id="invalid-alert-list" class="mt-1.5 list-disc list-inside"></ul>
            </div>
        </div>

        <!-- Content -->
        <div>
            
        </div>
    </jsp:body>
</t:admin_layout>
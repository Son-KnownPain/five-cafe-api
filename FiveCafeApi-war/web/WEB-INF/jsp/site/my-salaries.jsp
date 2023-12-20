<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        My salaries
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/my-salaries.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Page head introduction -->
        <div class="mb-4">
            <h1 class="font-semibold text-3xl text-gray-800">My salaries</h1>
            <p class="font-normala text-gray-500 text-base mt-3">You can view your salaries.</p>
        </div>

        <!-- Detail modal -->
        <div id="detail-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <!-- Modal content -->
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <!-- Modal header -->
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                            View Salary Detail
                        </h3>
                        <button id="close-detail-modal-btn" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="detail-modal">
                            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                            </svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                    </div>
                    <!-- Modal body -->
                    <div class="p-4 md:p-5 space-y-4">
                        <div id="salary-info" class="mb-4">
                            <!-- RenderByJS -->
                        </div>
                        <div id="details-content">
                            <!-- RenderByJS -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Actions -->
        <div>
            <button data-modal-target="detail-modal" data-modal-toggle="detail-modal" class="hidden">Detail</button>
        </div>

        <!-- Table -->
        <div>
            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                Date
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Total salary
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <span class="sr-only">Edit</span>
                            </th>
                        </tr>
                    </thead>
                    <tbody id="table-body">
                        <!-- BE RENDERED BY JAVASCRIPT -->
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:body>
</t:admin_layout>
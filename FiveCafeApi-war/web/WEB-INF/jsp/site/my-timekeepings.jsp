<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        My timekeepings
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/my-timekeepings.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Page head introduction -->
        <div class="mb-4">
            <h1 class="font-semibold text-3xl text-gray-800">My timekeepings</h1>
            <p class="font-normala text-gray-500 text-base mt-3">You can view your timekeepings.</p>
        </div>
        <!-- Table -->
        <div>
            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                Timekeeping ID
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Shift
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Created Date
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Salary
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Paid
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
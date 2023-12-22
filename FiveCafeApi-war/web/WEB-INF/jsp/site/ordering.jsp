<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        Ordering
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/ordering.js"></script>
    </jsp:attribute>
    <jsp:body>
        

        <!-- Tabs -->
        <div class="mb-4 border-b border-gray-200 dark:border-gray-700">
            <ul class="flex flex-wrap -mb-px text-sm font-medium text-center" id="tab-list" data-tabs-toggle="#tab-panels" role="tablist">
                <li class="me-2" role="presentation">
                    <button class="inline-block p-4 border-b-2 rounded-t-lg" id="ordering-tab" data-tabs-target="#ordering" type="button" role="tab" aria-controls="ordering" aria-selected="false">Ordering</button>
                </li>
                <li class="me-2" role="presentation">
                    <button class="inline-block p-4 border-b-2 rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300" id="not-served-bills-tab" data-tabs-target="#not-served-bills" type="button" role="tab" aria-controls="not-served-bills" aria-selected="false">Not served bills</button>
                </li>
            </ul>
        </div>
        <div id="tab-panels">
            <!-- Ordering tab panel -->
            <div class="hidden p-2 rounded-lg bg-white" id="ordering" role="tabpanel" aria-labelledby="ordering-tab">
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
                <div class="grid grid-cols-2 gap-4">
                    <form id="ordering-form">
                        <div class="p-4 bg-slate-100 rounded-md">
                            <div class="grid gap-4 mb-4 grid-cols-2">
                                <div class="col-span-2 form-gr">
                                    <label for="cardCode" class="block mb-2 text-sm font-medium text-gray-500">Card code</label>
                                    <input type="text" name="cardCode" id="cardCode" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Card code">
                                    <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                </div>
                                <div class="col-span-2 form-gr">
                                    <div class="text-right">
                                        <p class="font-normal text-base mt-4 mb-2 text-gray-500">Total</p>
                                        <p class="font-semibold text-2xl text-black" id="total-price-val">0 VNĐ</p>
                                    </div>
                                </div>
                            </div>
                            <div class="flex justify-end items-center">
                                <button type="submit" class="text-white inline-flex items-center bg-cyan-700 hover:bg-cyan-800 focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-cyan-600 dark:hover:bg-cyan-700 dark:focus:ring-cyan-800">
                                    <i class="fa-solid fa-receipt mr-2"></i>
                                    Order
                                </button>
                            </div>
                        </div>
                        <div class="p-4 bg-slate-100 rounded-md mt-4 bg-white dark:bg-gray-700" id="chosen-product">
                            <h1 class="text-base font-normal text-center text-gray-900 dark:text-white">Choose product to order</h1>
                            <!-- RenderByJS -->
                        </div>
                    </form>
                    <div class="p-4 bg-slate-100 rounded-md">
                        <h1 class="font-semibold text-lg mb-4 text-gray-500">Choose product</h1>
                        <form id="search-product-form">   
                            <label for="nameSearch" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
                            <div class="relative">
                                <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                    <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                    </svg>
                                </div>
                                <input type="search" id="nameSearch" name="name" placeholder="Search product..." class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                <button type="submit" id="search-btn" class="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search</button>
                            </div>
                        </form>
                        <div id="search-product-result" class="bg-white dark:bg-gray-700 mt-4 p-4 rounded-md grid gap-4 max-h-96 overflow-y-auto">
                            <!-- Render By JS -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- Not served bills tab panel -->
            <div class="hidden p-2 rounded-lg bg-white" id="not-served-bills" role="tabpanel" aria-labelledby="not-served-bills-tab">
                <!-- Table -->
                <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            <tr>
                                <th scope="col" class="px-6 py-3">
                                    Created Date
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Card Code
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    <span class="sr-only">Actions</span>
                                </th>
                            </tr>
                        </thead>
                        <tbody id="not-served-bills-table">
                            <!-- BE RENDERED BY JAVASCRIPT -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:admin_layout>
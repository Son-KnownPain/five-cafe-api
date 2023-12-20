<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">All statistic</jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <script src="${pageContext.request.contextPath}/resources/js/statistic/all.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Page head introduction -->
        <div class="mb-4">
            <h1 class="font-semibold text-3xl text-gray-800">All statistic</h1>
            <p class="font-normala text-gray-500 text-base mt-3">Based on the statistical data below, you can come up with a suitable business strategy for your store to increase profits and reduce costs.</p>
        </div>

        <!-- Sections -->
        <div class="mt-4">
            <section class="p-2 rounded-md bg-slate-100">
                <h1 class="text-sm text-gray-500 font-semibold mb-3">Revenue by days</h1>
                <div>
                    <div class="w-full bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">
                        <div class="flex justify-between">
                        <div>
                            <h5 class="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2" id="total-revenue-by-days"></h5>
                            <p class="text-base font-normal text-gray-500 dark:text-gray-400">Total revenue</p>
                        </div>
                        <!-- <div class="flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center">
                            12%
                            <svg class="w-3 h-3 ms-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13V1m0 0L1 5m4-4 4 4"/>
                            </svg>
                        </div> -->
                        </div>
                        <div id="revenue-by-days-chart"></div>
                        <div class="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                            <div class="flex justify-between items-center pt-5">
                                <!-- Button -->
                                <button
                                    id="dropdownDefaultButton"
                                    data-dropdown-toggle="chooseRevenueStatistic"
                                    data-dropdown-placement="bottom"
                                    class="text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-900 text-center inline-flex items-center dark:hover:text-white"
                                    type="button"
                                >
                                    Last 3 days
                                    <svg class="w-2.5 m-2.5 ms-1.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                                    </svg>
                                </button>
                                <!-- Dropdown menu -->
                                <div id="chooseRevenueStatistic" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                                    <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownDefaultButton">
                                        <li>
                                            <a data-revenue-by-days data-last-days="3" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 3 days</a>
                                        </li>
                                        <li>
                                            <a data-revenue-by-days data-last-days="7" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 7 days</a>
                                        </li>
                                        <li>
                                            <a data-revenue-by-days data-last-days="30" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 30 days</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="">
                                    <div class="grid md:grid-cols-3 md:gap-6">
                                        <div class="relative z-0 w-full group">
                                            <input type="text" name="revenueByDaysStartDate" id="revenueByDaysStartDate" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                            <label for="revenueByDaysStartDate" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">From</label>
                                        </div>
                                        <div class="relative z-0 w-full group">
                                            <input type="text" name="revenueByDaysEndDate" id="revenueByDaysEndDate" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                            <label for="revenueByDaysEndDate" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">To</label>
                                        </div>
                                        <div class="relative z-0 w-full group">
                                            <button id="revenueByDaysFilterBtn" type="button" class="text-white w-full bg-cyan-800 hover:bg-cyan-900 focus:outline-none focus:ring-4 focus:ring-cyan-300 font-medium rounded-full text-sm px-5 py-2.5 dark:bg-cyan-800 dark:hover:bg-cyan-700 dark:focus:ring-cyan-700 dark:border-cyan-700">Filter days</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="mt-3 p-2 rounded-md bg-slate-100">
                <h1 class="text-sm text-gray-500 font-semibold mb-3">Import cost by days</h1>
                <div>
                    <div class="w-full bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">
                        <div class="flex justify-between">
                        <div>
                            <h5 class="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2" id="total-cost-by-days"></h5>
                            <p class="text-base font-normal text-gray-500 dark:text-gray-400">Total cost</p>
                        </div>
                        <!-- <div class="flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center">
                            12%
                            <svg class="w-3 h-3 ms-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13V1m0 0L1 5m4-4 4 4"/>
                            </svg>
                        </div> -->
                        </div>
                        <div id="cost-by-days-chart"></div>
                        <div class="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                            <div class="flex justify-between items-center pt-5">
                                <!-- Button -->
                                <button
                                    id="costDayChoiceDropdown"
                                    data-dropdown-toggle="chooseCostStatistic"
                                    data-dropdown-placement="bottom"
                                    class="text-sm font-medium text-gray-500 dark:text-gray-400 hover:text-gray-900 text-center inline-flex items-center dark:hover:text-white"
                                    type="button"
                                >
                                    Last 3 days
                                    <svg class="w-2.5 m-2.5 ms-1.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                                    </svg>
                                </button>
                                <!-- Dropdown menu -->
                                <div id="chooseCostStatistic" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                                    <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="costDayChoiceDropdown">
                                        <li>
                                            <a data-cost-by-days data-last-days="3" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 3 days</a>
                                        </li>
                                        <li>
                                            <a data-cost-by-days data-last-days="7" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 7 days</a>
                                        </li>
                                        <li>
                                            <a data-cost-by-days data-last-days="30" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Last 30 days</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="">
                                    <div class="grid md:grid-cols-3 md:gap-6">
                                        <div class="relative z-0 w-full group">
                                            <input type="text" name="costByDaysStartDate" id="costByDaysStartDate" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                            <label for="costByDaysStartDate" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">From</label>
                                        </div>
                                        <div class="relative z-0 w-full group">
                                            <input type="text" name="costByDaysEndDate" id="costByDaysEndDate" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                            <label for="costByDaysEndDate" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">To</label>
                                        </div>
                                        <div class="relative z-0 w-full group">
                                            <button id="costByDaysFilterBtn" type="button" class="text-white w-full bg-cyan-800 hover:bg-cyan-900 focus:outline-none focus:ring-4 focus:ring-cyan-300 font-medium rounded-full text-sm px-5 py-2.5 dark:bg-cyan-800 dark:hover:bg-cyan-700 dark:focus:ring-cyan-700 dark:border-cyan-700">Filter days</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </jsp:body>
</t:admin_layout>

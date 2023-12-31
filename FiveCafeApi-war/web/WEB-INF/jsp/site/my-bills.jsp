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
        <script src="${pageContext.request.contextPath}/resources/js/pages/my-bills.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Bill Detail Modal -->
        <div id="bill-detail-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <!-- Modal content -->
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <!-- Modal header -->
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                            View Bill Detail
                        </h3>
                        <button id="close-bill-detail-modal-btn" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="bill-detail-modal">
                            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                            </svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                    </div>
                    <!-- Alerts -->
                        <!-- Success alert -->
                    <div id="detail-success-alert" class="hidden items-center p-4 text-green-800 border-t-4 border-green-300 bg-green-50 dark:text-green-400 dark:bg-gray-800 dark:border-green-800" role="alert">
                        <i class="fa-regular fa-circle-check"></i>
                        <div class="ms-3 text-sm font-medium" id="detail-success-alert-content"></div>
                        <button type="button" class="ms-auto -mx-1.5 -my-1.5 bg-green-50 text-green-500 rounded-lg focus:ring-2 focus:ring-green-400 p-1.5 hover:bg-green-200 inline-flex items-center justify-center h-8 w-8 dark:bg-gray-800 dark:text-green-400 dark:hover:bg-gray-700"  data-dismiss-target="#detail-success-alert" aria-label="Close">
                            <span class="sr-only">Dismiss</span>
                            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                            </svg>
                        </button>
                    </div>
                    <!-- Modal body -->
                    <div class="p-4 md:p-5 space-y-4">
                        <div id="bill-info" class="mb-4">
                            <!-- RenderByJS -->
                        </div>
                        <form id="edit-bill-form" class="hidden py-4 border-t border-solid border-gray-500">
                            <h1 class="text-xs font-semibold dark:text-gray-400 text-gray-700 mb-3 uppercase">Edit bill</h1>
                            <div class="grid gap-4 mb-4 grid-cols-2">
                                <div class="col-span-2 form-gr">
                                    <input type="hidden" name="billID" id="billIDEdit">
                                    <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                </div>
                                <div class="col-span-2 form-gr">
                                    <label for="billStatusIDEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Bill Status ID</label>
                                    <select id="billStatusIDEdit" name="billStatusID" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                      </select>
                                    <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                </div>
                                <div class="col-span-2 form-gr">
                                    <label for="cardCodeEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Card Code</label>
                                    <input type="text" name="cardCode" id="cardCodeEdit" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="15000">
                                    <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                </div>
                            </div>
                            
                            <div class="flex justify-end items-center">
                                <button id="cancel-edit-bill-btn" type="button" class="py-2.5 px-5 mr-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                                    Cancel
                                </button>
                                <button type="submit" class="text-white inline-flex items-center bg-yellow-700 hover:bg-yellow-800 focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-yellow-600 dark:hover:bg-yellow-700 dark:focus:ring-yellow-800">
                                    <i class="fa-solid fa-pencil mr-3"></i>
                                    Update
                                </button>
                            </div>
                        </form>
                        <div id="add-pro-item-btn-box" class="">
                            <button id="add-pro-item-btn" type="submit" class="text-white inline-flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                <i class="fa-solid fa-plus mr-3"></i>
                                Add product
                            </button>
                        </div>
                        <!-- Add product -->
                        <div id="detail-add-form" class="hidden py-4 border-y border-solid border-gray-200">
                            <h1 class="text-xs font-semibold dark:text-gray-400 text-gray-700 mb-3 uppercase">ADD PRODUCT</h1>
                            <form id="add-product-form">
                                <input type="hidden" id="billIDAdd" name="billID">
                                <div class="grid gap-4 mb-4 grid-cols-2">
                                    <div class="col-span-2 form-gr">
                                        <label for="productIDAdd" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product</label>
                                        <select id="productIDAdd" name="productID" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                            <!-- Rendered by JS -->
                                          </select>
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="col-span-2 form-gr">
                                        <label for="quantityAdd" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                                        <input type="number" id="quantityAdd" name="quantity" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="100">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                </div>
                                <div class="flex justify-end items-center">
                                    <button id="cancel-add-pro-btn" type="button" class="py-2.5 px-5 mr-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                                        Cancel
                                    </button>
                                    <button type="submit" class="text-white inline-flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                        <i class="fa-solid fa-plus mr-3"></i>
                                        Add
                                    </button>
                                </div>
                            </form>
                        </div>
                        <!-- Update product -->
                        <div id="detail-update-form" class="hidden py-4 border-y border-solid border-gray-200">
                            <h1 class="text-xs font-semibold dark:text-gray-400 text-gray-700 mb-3 uppercase">EDIT PRODUCT</h1>
                            <form id="update-product-form">
                                <div class="grid gap-4 mb-4 grid-cols-2">
                                    <div class="form-gr">
                                        <input type="hidden" name="billID" id="detailBillIDEdit">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="form-gr">
                                        <input type="hidden" name="productID" id="productIDEdit">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="col-span-2 form-gr">
                                        <label for="quantityEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                                        <input type="number" name="quantity" id="quantityEdit" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="100">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                </div>
                                <div class="flex justify-end items-center">
                                    <button id="cancel-update-pro-btn" type="button" class="py-2.5 px-5 mr-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                                        Cancel
                                    </button>
                                    <button type="submit" class="text-white inline-flex items-center bg-yellow-700 hover:bg-yellow-800 focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-yellow-600 dark:hover:bg-yellow-700 dark:focus:ring-yellow-800">
                                        <i class="fa-solid fa-pencil mr-3"></i>
                                        Update
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div id="details-content">
                            <!-- RenderByJS -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    

        <!-- Page head introduction -->
        <div class="mb-4">
            <h1 class="font-semibold text-3xl text-gray-800">My bills</h1>
            <p class="font-normala text-gray-500 text-base mt-3">You can view the bills you have created and edit them. If you wish to delete a bill, please contact a user with higher privileges, such as the owner, to perform the operation.</p>
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

        <!-- Actions -->
        <div>
            <button data-modal-target="bill-detail-modal" data-modal-toggle="bill-detail-modal" class="hidden">Detail</button>
        </div>

        <!-- Table -->
        <div>
            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                Bill Status
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Created Date
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Card Code
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
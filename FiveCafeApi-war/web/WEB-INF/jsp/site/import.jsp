<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:admin_layout>
    <jsp:attribute name="title">
        Imports management
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/resources/js/pages/import.js"></script>
    </jsp:attribute>
    <jsp:body>
        <!-- Create Modal -->
        <div id="create-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <!-- Modal content -->
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <!-- Modal header -->
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
                            Create New Import
                        </h3>
                        <button id="close-create-modal-btn" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="create-modal">
                            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                            </svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                    </div>
                    <!-- Modal body -->
                    <form id="choose-material-form" class="p-4 md:p-5">
                        <div class="grid gap-4 mb-4 grid-cols-2">
                            <div class="col-span-2 form-gr">
                                <label for="materialID" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Material</label>
                                <select id="materialID" name="materialID" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                    <!-- Rendered by JS -->
                                  </select>
                                <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                            </div>
                            <div class="col-span-2 form-gr">
                                <label for="supplierID" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Supplier</label>
                                <select id="supplierID" name="supplierID" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                    <!-- Rendered by JS -->
                                  </select>
                                <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                            </div>
                            <div class="col-span-2 form-gr">
                                <label for="unitPrice" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Unit price</label>
                                <input type="number" name="unitPrice" id="unitPrice" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="15000">
                                <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                            </div>
                            <div class="col-span-2 form-gr">
                                <label for="quantity" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                                <input type="number" name="quantity" id="quantity" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="100">
                                <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                            </div>
                        </div>
                        <div class="flex justify-end items-center">
                            <button type="submit" class="text-white inline-flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                <svg class="me-1 -ms-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                Add material
                            </button>
                        </div>
                    </form>
                    <!-- Materials box -->
                    <h1 class="text-base mx-4 mt-4 font-semibold dark:text-white text-white">Materials</h1>
                    <div id="materialsBox" class="p-4 md:p-5 border-t border-solid border-gray-500">
                        <!-- RenderByJS -->
                    </div>
                    <!-- Import form -->
                    <form id="create-import-form" class="p-4 md:p-5 border-t border-solid border-gray-500">
                        <div class="flex justify-end items-center">
                            <button type="submit" class="text-white inline-flex items-center bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                <svg class="me-1 -ms-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                Add new import
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Delete confirmation modal -->
        <div id="DC-popup-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <button type="button" class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="DC-popup-modal">
                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                        </svg>
                        <span class="sr-only">Close modal</span>
                    </button>
                    <div class="p-4 md:p-5 text-center">
                        <svg class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                        </svg>
                        <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Are you sure you want to delete this roles?</h3>
                        <button id="yes-dlt-btn" data-modal-hide="DC-popup-modal" type="button" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center me-2">
                            Yes, I'm sure
                        </button>
                        <button data-modal-hide="DC-popup-modal" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">No, cancel</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Import Detail Modal -->
        <div id="import-detail-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <!-- Modal content -->
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <!-- Modal header -->
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                            View Import Detail
                        </h3>
                        <button id="close-import-detail-modal-btn" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="import-detail-modal">
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
                        <div id="import-info" class="mb-4">
                            <!-- RenderByJS -->
                        </div>
                        <!-- Update material -->
                        <div id="detail-update-form" class="hidden py-4 border-y border-solid border-gray-200">
                            <h1 class="text-xs font-semibold dark:text-gray-400 text-gray-700 mb-3 uppercase">EDIT MATERIAL</h1>
                            <form id="update-material-form">
                                <div class="grid gap-4 mb-4 grid-cols-2">
                                    <div class="form-gr">
                                        <input type="hidden" name="importID" id="importIDEdit">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="form-gr">
                                        <input type="hidden" name="materialID" id="materialIDEdit">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="col-span-2 form-gr">
                                        <label for="supplierIDEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Supplier</label>
                                        <select id="supplierIDEdit" name="supplierID" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                            <!-- Rendered by JS -->
                                        </select>
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="col-span-2 form-gr">
                                        <label for="unitPriceEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Unit price</label>
                                        <input type="number" name="unitPrice" id="unitPriceEdit" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="15000">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                    <div class="col-span-2 form-gr">
                                        <label for="quantityEdit" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                                        <input type="number" name="quantity" id="quantityEdit" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="100">
                                        <span class="text-base font-normal text-red-500 mt-1 form-message"></span>
                                    </div>
                                </div>
                                <div class="flex justify-end items-center">
                                    <button id="cancel-update-mat-btn" type="button" class="py-2.5 px-5 mr-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
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
            <h1 class="font-semibold text-3xl text-gray-800">Imports management</h1>
            <p class="font-normala text-gray-500 text-base mt-3">The Import Management feature provides a comprehensive solution for handling the importation of goods into the system. It facilitates the efficient tracking and organization of import details, ensuring accurate and streamlined management of incoming inventory.</p>
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
            <button data-modal-target="create-modal" data-modal-toggle="create-modal" class="focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Create import</button>
            <button data-modal-target="import-detail-modal" data-modal-toggle="import-detail-modal" class="hidden">Detail</button>
            <button data-modal-target="DC-popup-modal" data-modal-toggle="DC-popup-modal" id="dlt-btn" class="hidden focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900">Delete</button>
        </div>

        <!-- Table -->
        <div>
            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                Check
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Import ID
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Import Date
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
<header id="header">
    <img src="${pageContext.request.contextPath}/resources/images/layout/FiveCafeLogo.png" alt="Logo" class="logo" />
    <button data-drawer-target="header-user-drawer" data-drawer-show="header-user-drawer" aria-controls="header-user-drawer" class="hover:bg-sky-500 bg-sky-700 duration-200 ease-in-out transition text-white p-2 rounded-full flex items-center justify-center">
        <i class="fa-solid fa-user"></i>
    </button>
</header>
 
 <!-- drawer component -->
<div id="header-user-drawer" class="fixed top-0 left-0 z-40 h-screen p-4 overflow-y-auto transition-transform -translate-x-full bg-white w-80 dark:bg-gray-800" tabindex="-1" aria-labelledby="drawer-label">
    <p class="text-base font-medium text-gray-500">Hi, Nguyen Hong Son</p>
    <div class="mt-2">
        <a href="#" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-receipt mr-2"></i>
            My Bills
        </a><a href="#" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-money-bill mr-2"></i>
            My Salaries
        </a>
    </div>
</div>
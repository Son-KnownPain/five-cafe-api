<header id="header">
    <img src="${pageContext.request.contextPath}/resources/images/layout/FiveCafeLogo.png" alt="Logo" class="logo" />
    <div class="flex items-center">
        <a href="/FiveCafeApi-war/menu" target="_blank" class="mr-4 relative inline-flex items-center justify-center p-0.5 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-cyan-500 to-blue-500 group-hover:from-cyan-500 group-hover:to-blue-500 hover:text-white dark:text-white focus:ring-4 focus:outline-none focus:ring-cyan-200 dark:focus:ring-cyan-800">
            <span class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                Customer Menu
            </span>
        </a>
        <button data-drawer-target="header-user-drawer" data-drawer-show="header-user-drawer" aria-controls="header-user-drawer" class="hover:bg-sky-500 bg-sky-700 duration-200 ease-in-out transition text-white p-2 rounded-full flex items-center justify-center">
            <i class="fa-solid fa-user"></i>
        </button>
    </div>
</header>
 
 <!-- drawer component -->
<div id="header-user-drawer" class="fixed top-0 left-0 z-40 h-screen p-4 overflow-y-auto transition-transform -translate-x-full bg-white w-80 dark:bg-gray-800" tabindex="-1" aria-labelledby="drawer-label">
    <!-- User info -->
    <div class="flex items-center gap-4">
        <img id="info-img" class="object-cover w-10 h-10 rounded-full"s alt="Avatar">
        <div class="font-medium dark:text-white">
            <div id="info-name">
                <!-- Rendered by JS -->
            </div>
            <div id="info-username" class="text-sm text-gray-500 dark:text-gray-400">
                <!-- Rendered by JS -->
            </div>
        </div>
    </div>
    <div class="mt-3">
        <div class="flex items-center text-gray-700 dark:text-gray-300 text-sm">
            <i class="fa-solid fa-square-phone-flip mr-2"></i>
            <span id="info-phone">
                <!-- Rendered by JS -->
            </span>
        </div>
        <div class="mt-2 flex items-center text-gray-700 dark:text-gray-300 text-sm">
            <i class="fa-solid fa-clipboard-user mr-2"></i>
            <span id="info-role-name">
                <!-- Rendered by JS -->
            </span>
        </div>
    </div>
    <!-- Actions -->
    <div class="mt-2">
        <a href="/FiveCafeApi-war/my-bills" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-receipt mr-2"></i>
            My Bills
        </a>
        <a href="/FiveCafeApi-war/my-timekeepings" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-business-time mr-2"></i>
            My Timekeepings
        </a>
        <a href="/FiveCafeApi-war/my-salaries" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-money-bill mr-2"></i>
            My Salaries
        </a>
        <a href="/FiveCafeApi-war/my-outbounds" class="w-full mt-3 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
            <i class="fa-solid fa-money-bill mr-2"></i>
            My Outbounds
        </a>
        <a onclick="logout()" class="cursor-pointer w-full mt-3 text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center me-2 dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800">
            <i class="fa-solid fa-right-from-bracket mr-2"></i>
            Logout
        </a>
    </div>
</div>

<script>
    // Logout event handler
    function logout() {
        const loginPath = window.APP_NAME + '/api/employee/logout';
        fetch(loginPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) location.reload()
        })
        .catch(res => {});
    }
    
    // Fetch login info data
    function fetchLoginInfoData() {
        const path = document.querySelector('meta[name="my-app-name"]').content + "/api/employee/info";
        fetch(path)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                const info = res.data;
                // Handle render HTML
                const imgElm = document.getElementById('info-img')
                const nameElm = document.getElementById('info-name')
                const usernameElm = document.getElementById('info-username')
                const phoneElm = document.getElementById('info-phone')
                const roleNameElm = document.getElementById('info-role-name')

                imgElm.src = info.image;
                nameElm.textContent = info.name;
                usernameElm.textContent = info.username;
                phoneElm.textContent = info.phone;
                roleNameElm.textContent = info.roleName;
            }
        })
    }
    fetchLoginInfoData();
</script>
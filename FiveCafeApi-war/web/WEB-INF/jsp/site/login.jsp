<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts/" %>

<t:no_layout>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>
    <jsp:attribute name="styles">

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script>
            Validator({
                form: '#sign-in-form',
                formGroup: '.login-form-gr',
                errorSelector: '.login-form-message',
                rules: [
                    Validator.isRequired('#username', 'Username is required'),
                    Validator.isRequired('#password', 'Password is required'),
                    Validator.minLength('#password', 8, 'Enter at least 8 characters'),
                ],
                onSubmit: function(data) {
                    const loginPath = window.APP_NAME + '/api/employee/login';
                    fetch(loginPath, {
                        method: 'POST',
                        credentials: 'same-origin',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            username: data.username,
                            password: data.password,
                        })
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            location.href = window.APP_NAME
                        } else if (res.status == 401) {
                            const errElm = document.getElementById('error')
                            document.getElementById('error-content').textContent = res.message;
                            errElm.classList.remove('hidden')
                            errElm.classList.add('flex')
                        }
                    })
                }
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="fixed inset-0 flex items-center justify-center bg-slate-800">
            <div class="w-96 p-3">
                <p class="text-3xl text-white font-semibold text-center mb-4">Login</p>
                <!-- Alert -->
                <div id="error" class="hidden items-center p-4 mb-4 text-yellow-800 border-t-4 border-yellow-300 bg-yellow-50 dark:text-yellow-300 dark:bg-gray-800 dark:border-yellow-800" role="alert">
                    <svg class="flex-shrink-0 w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                    </svg>
                    <div id="error-content" class="ms-3 text-sm font-medium">
                        <!-- Rendered by JS -->
                    </div>
                    <button type="button" class="ms-auto -mx-1.5 -my-1.5 bg-yellow-50 text-yellow-500 rounded-lg focus:ring-2 focus:ring-yellow-400 p-1.5 hover:bg-yellow-200 inline-flex items-center justify-center h-8 w-8 dark:bg-gray-800 dark:text-yellow-300 dark:hover:bg-gray-700" data-dismiss-target="#error" aria-label="Close">
                        <span class="sr-only">Dismiss</span>
                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                        </svg>
                    </button>
                </div>
                <!-- Form -->
                <form class="max-w-sm mx-auto" id="sign-in-form">
                    <div class="mb-5 login-form-gr">
                        <label for="username"
                            class="block mb-2 text-sm font-medium text-white dark:text-white">Username</label>
                        <input type="text" id="username"
                            name="username"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            placeholder="yourusername123">
                        <span class="text-base font-normal text-red-500 mt-1 login-form-message"></span>
                    </div>
                    <div class="mb-5 login-form-gr">
                        <label for="password"
                            class="block mb-2 text-sm font-medium text-white dark:text-white">Password</label>
                        <input type="password" id="password"
                            name="password"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            placeholder="********"
                            >
                        <span class="text-base font-normal text-red-500 mt-1 login-form-message"></span>
                    </div>
                    <div class="flex justify-end">
                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Login</button>
                    </div>
                </form>

            </div>
        </div>
    </jsp:body>
</t:no_layout>
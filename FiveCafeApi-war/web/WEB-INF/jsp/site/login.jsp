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
            const loginPath = window.APP_NAME + '/api/employee/login';
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
                    .then(res => {
                        if (res.status == 200) {
                            location.href = window.APP_NAME
                        }
                    })
                    .catch(res => {
                        console.log(res);
                    })
                }
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="fixed inset-0 flex items-center justify-center bg-slate-800">
            <div class="w-96 p-3">
                <p class="text-3xl text-white font-semibold text-center mb-4">Login</p>
                <form class="max-w-sm mx-auto" id="sign-in-form">
                    <div class="mb-5 login-form-gr">
                        <label for="username"
                            class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                        <input type="text" id="username"
                            name="username"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            placeholder="yourusername123">
                        <span class="text-base font-normal text-red-500 mt-1 login-form-message"></span>
                    </div>
                    <div class="mb-5 login-form-gr">
                        <label for="password"
                            class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
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
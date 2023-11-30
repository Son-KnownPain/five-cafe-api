// Fetch table data
function fetchTableData() {
    const fetchPath = window.APP_NAME + '/api/role/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                <input type="checkbox" name="delete-checkbox" value="${item.roleID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.roleID}
                            </th>
                            <td class="px-6 py-4">
                                ${item.roleName}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a data-edit-id="${item.roleID}" data-modal-target="update-modal" data-modal-toggle="update-modal" class="cursor-pointer font-medium text-yellow-400 dark:text-yellow-400 hover:underline">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                            </td>
                        </tr>
                    `
                }).join('');
            // Init flowbite
            initFlowbite();

            // Handle checkbox
            let checkedRowsToDelete = [];

            function renderDeleteBtn() {
                const dltBtn = document.getElementById('dlt-btn');
                if (checkedRowsToDelete.length > 0) {
                    dltBtn.classList.remove('hidden')
                } else {
                    dltBtn.classList.add('hidden')
                }

                const yesDltBtn = document.getElementById('yes-dlt-btn')
                yesDltBtn.onclick = () => {
                    fetch(`${window.APP_NAME}/api/role/delete?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete roles');
                            fetchTableData();
                            checkedRowsToDelete = [];
                            renderDeleteBtn();
                        }
                    })
                    .catch(_err => {})
                }
            }

            const deleteCheckboxs = document.querySelectorAll('input[name="delete-checkbox"]');
            deleteCheckboxs.forEach(checkbox => {
                checkbox.oninput = e => {
                    if (e.target.checked) {
                        checkedRowsToDelete.push(checkbox.value);
                    } else {
                        checkedRowsToDelete = checkedRowsToDelete.filter(item => item != checkbox.value);
                    }
                    renderDeleteBtn();
                }
            })

            // Handle click edit
            const editBtns = document.querySelectorAll('a[data-edit-id]');
            Array.from(editBtns).forEach(btn => {
                btn.onclick = () => {
                    const role = res.data.find(item => item.roleID == btn.dataset.editId);

                    document.getElementById('roleIDEdit').value = role.roleID;
                    document.getElementById('roleNameEdit').value = role.roleName;
                }
            })
        }
    })
    .catch(res => { })
}
fetchTableData();

// Alerts
let successAlert = "";

function showSuccessAlert(content) {
    const sAElm = document.getElementById('success-alert');
    const sAContentElm = document.getElementById('success-alert-content');

    sAContentElm.textContent = content;

    sAElm.classList.remove('hidden');
    sAElm.classList.add('flex');
}

let warningAlert = [];

function showWarningAlert(titleContent = '', alerts = []) {
    const wrapper = document.getElementById('invalid-alert');
    const title = document.getElementById('invalid-alert-title');
    const list = document.getElementById('invalid-alert-list');

    title.textContent = titleContent;
    list.innerHTML = alerts.map(alert => `
        <li>${alert}</li>
    `).join('');

    wrapper.classList.remove('hidden');
    wrapper.classList.add('flex');
}

function hideWarningAlert() {
    const wrapper = document.getElementById('invalid-alert');

    wrapper.classList.remove('flex');
    wrapper.classList.add('hidden');
}

// Validate create role
Validator({
    form: '#create-role-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#roleID', 'Role ID is required'),
        Validator.isRequired('#roleName', 'Role Name is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/role/store';
        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                roleID: data.roleID,
                roleName: data.roleName,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create new role');
                fetchTableData();
                resetForm({
                    roleID: '',
                    roleName: '',
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-create-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update role
Validator({
    form: '#update-role-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#roleIDEdit', 'Role ID is required'),
        Validator.isRequired('#roleNameEdit', 'Role Name is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/role/update';
        fetch(updatePath, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                roleID: data.roleID,
                roleName: data.roleName,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-update-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully update role');
                fetchTableData();
                resetForm({
                    roleID: '',
                    roleName: '',
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-update-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});
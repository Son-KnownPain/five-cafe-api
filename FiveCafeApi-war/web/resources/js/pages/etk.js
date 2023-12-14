// Fetch table data
function fetchTableData(searching = null) {
    // search cái j không biết.
    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/etk/search?keyword=${searching.keyword}${searching.roleID ? `&roleID=${searching.roleID}` : ""}`
    } else  {
        fetchPath += '/api/etk/all';
    }
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                <input type="checkbox" name="delete-checkbox" value="${item.timeKeepingID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <td class="px-6 py-4">
                                ${item.date}
                            </td>
                            <td class="px-6 py-4">
                                ${item.employeeName}
                            </td>
                            <td class="px-6 py-4">
                                ${window.currencyOutput(item.salary)}
                            </td>
                            <td class="px-6 py-4">
                                ${item.paid ? 'Paid' : 'Not paid'}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a data-edit-id="${item.timeKeepingID}" data-modal-target="update-modal" data-modal-toggle="update-modal" class="block cursor-pointer font-semibold text-yellow-500 dark:text-yellow-500 hover:text-yellow-300">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                                <a data-detail-id="${item.timeKeepingID}" data-modal-target="emp-detail-modal" data-modal-toggle="emp-detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
                                    <i class="fa-solid fa-circle-info"></i>
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

                // Handle delete
                const yesDltBtn = document.getElementById('yes-dlt-btn')
                yesDltBtn.onclick = () => {
                    fetch(`${window.APP_NAME}/api/etk/delete?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete employee timekeeping');
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
                    const etk = res.data.find(item => item.timeKeepingID == btn.dataset.editId);

                    document.getElementById('timeKeepingIDEdit').value = etk.timeKeepingID;
                    document.getElementById('employeeIDEdit').value = etk.employeeID;
                    document.getElementById('shiftIDEdit').value = etk.shiftID;

                }
            })

            // Handle click view detail
            const detailBtns = document.querySelectorAll('a[data-detail-id]')
            Array.from(detailBtns).forEach(btn => {
                btn.onclick = () => {
                    const etk = res.data.find(item => item.timeKeepingID == btn.dataset.detailId);   
                    document.getElementById("detail-employeeID").textContent = etk.employeeName;
                    document.getElementById("detail-shiftID").textContent = etk.shiftName;
                    document.getElementById("detail-date").textContent = etk.date;
                    document.getElementById("detail-salary").textContent = window.currencyOutput(etk.salary);
                    document.getElementById("detail-ispaid").textContent = etk.paid ? "Paid" : "Not paid";

                   
                }
            })
        }
    })
    .catch(res => { })
}
fetchTableData();

 // Handle searching
// function renderRoleFilter() {
//     const items = [
//         {
            // seach cái ông  làm nha 
//         },
        //...
//     ]
//     document.getElementById('role-filter').innerHTML = items.map(item => {
//         return `
//             <li data-role-id="${item.roleID}" data-role-name="${item.roleName}">
//                 <button type="button" class="inline-flex w-full px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">${item.roleName}</button>
//             </li>
//         `
//     }).join('');
// }





// function processClickChooseRole() {
//     Array.from(document.querySelectorAll('li[data-role-id]')).forEach(li => {
//         li.onclick = () => {
//             document.getElementById('roleIDSearch').value = li.dataset.roleId;
//             document.getElementById('dropdown-button').innerHTML = `
//                 ${li.dataset.roleName}
//                 <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
//                     <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
//                 </svg>
//             `
//         }
//     })
// }
// document.getElementById('search-form').onsubmit = e => {
//     e.preventDefault();
//     const roleID = document.getElementById('roleIDSearch').value;
//     const keyword = document.getElementById('keywordSearch').value;
//     fetchTableData({
//         roleID: roleID.length && roleID,
//         keyword: keyword
//     })
// }

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



/// Validate create
Validator({
    form: '#create-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#employeeID', 'Employee ID is required'),
        Validator.isRequired('#shiftID', 'Shift ID is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/etk/store';
        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                employeeID: data.employeeID,
                shiftID: data.shiftID,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create new employee timekeeping');
                fetchTableData();
                resetForm({
                    employeeID: data.employeeID,
                shiftID: data.shiftID,
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-create-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update employee timwkeeping
Validator({
    form: '#update-role-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#timeKeepingID', 'timeKeeping ID is required'),
        Validator.isRequired('#employeeID', 'Employee ID is required'),
        Validator.isRequired('#shiftID', 'Shift ID is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/etk/store';
        fetch(storePath, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                timeKeepingID: data.timeKeepingID,
                employeeID: data.employeeID,
                shiftID: data.shiftID,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-update-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create new employee timekeeping');
                fetchTableData();
                resetForm({
                    
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-update-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});


var emps = []

// Handle fetch employee data
function fetchEmployee() {
    const fetchPath = window.APP_NAME + '/api/employee/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            emps = res.data;
            renderETKSelects([
                'employeeID',
                'employeeIDEdit',
            ]);
            renderETKFilter();
            processClickChooseETK();
        }
    })
    .catch(_res => { })
}
fetchEmployee();
function renderETKSelects(selectsID) {
    selectsID.forEach(selectID => {
        document.getElementById(selectID).innerHTML = 
        emps.map(item => {
            return `
                <option value="${item.employeeID}">${item.name}</option>
            `
        }).join('');
    })
}

var shift = []

// Handle fetch Shift data
function fetchShift() {
    const fetchPath = window.APP_NAME + '/api/shift/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            shift = res.data;
            renderShiftSelects([
                'shiftID',
                'shiftIDEdit',
            ]);
            renderShiftFilter();
            processClickChooseShift();
        }
    })
    .catch(_res => { })
}
fetchShift();
function renderShiftSelects(selectsID) {
    selectsID.forEach(selectID => {
        document.getElementById(selectID).innerHTML = 
        shift.map(item => {
            return `
                <option value="${item.shiftID}">${item.name}</option>
            `
        }).join('');
    })
}
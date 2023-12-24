// Websocket
var wsURI = "wss://" + document.location.host + "/FiveCafeApi-war/salary";

var websocket = new WebSocket(wsURI);

// Fetch table data
function fetchTableData(prop = {}) {
    const { detailClickID = null, searching = null } = prop;

    let fetchPath = window.APP_NAME;
    
    if (searching) {
        fetchPath += `/api/salary/search?dateForm=${searching.dateForm}&dateTo=${searching.dateTo}`
    } else {
        fetchPath += `/api/salary/all`;
    }

    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                <input type="checkbox" name="delete-checkbox" value="${item.employeeSalaryID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.employeeSalaryID}
                            </th>
                            <td class="px-6 py-4">
                                ${item.employeeName}
                            </td>
                            <td class="px-6 py-4">
                                ${window.currencyOutput(item.details.reduce((acc, cur) => acc + cur.salary * (1 + cur.bonus / 100) - cur.deduction, 0))}
                            </td>
                            <td class="px-6 py-4">
                                ${item.date}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a title="View detail" data-detail-id="${item.employeeSalaryID}" data-modal-target="detail-modal" data-modal-toggle="detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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

                const yesDltBtn = document.getElementById('yes-dlt-btn')
                yesDltBtn.onclick = () => {
                    fetch(`${window.APP_NAME}/api/salary/delete?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete salaries');
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

            // Handle click view detail
            const detailBtns = document.querySelectorAll('a[data-detail-id]')

            if (!document.getElementById('update-item-form').classList.contains('hidden')) {
                document.getElementById('update-item-form').classList.add('hidden');
            }
            // if (!document.getElementById('detail-add-form').classList.contains('hidden')) {
            //     document.getElementById('detail-add-form').classList.add('hidden');
            // }
            document.getElementById('cancel-add-etk-form').onclick = () => {
                document.getElementById('detail-add-form').classList.add('hidden');
                    document.getElementById('add-etk-btn').classList.remove('hidden');
            }
            document.getElementById('cancel-update-detail-btn').onclick = () => {
                document.getElementById('update-item-form').classList.add('hidden');
            }
            
            Array.from(detailBtns).forEach(btn => {
                btn.onclick = () => {
                    document.getElementById('update-item-form').classList.add('hidden');

                    const salaryItem = res.data.find(item => item.employeeSalaryID == btn.dataset.detailId);

                    // document.getElementById('billIDEdit').value = billItem.billID;

                    document.getElementById('salary-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee Salary ID: <span class="font-bold dark:text-white text-black">${salaryItem.employeeSalaryID}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Created Date: <span class="font-bold dark:text-white text-black">${salaryItem.date}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee: <span class="font-bold dark:text-white text-black">${salaryItem.employeeName}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Total salary: <span class="font-bold dark:text-white text-black">${window.currencyOutput(salaryItem.details.reduce((acc, cur) => acc + cur.salary * (1 + cur.bonus / 100) - cur.deduction, 0))}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = salaryItem.details.reverse().map(detail => `
                        <div class="max-w-sm mb-1 p-4 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
                            <a>
                                <h5 class="mb-1 text-base font-bold tracking-tight text-gray-900 dark:text-white">${detail.shiftName}</h5>
                            </a>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-money-bill-wave text-yellow-500 mr-1"></i>
                                Base: ${window.currencyOutput(detail.salary)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-plus text-green-500 mr-1"></i>
                                Bonus: ${detail.bonus}%
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-minus text-red-500 mr-1"></i>
                                Deduction: ${window.currencyOutput(detail.deduction)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-regular fa-money-bill-1 text-yellow-700 mr-1"></i>
                                Total: ${window.currencyOutput(detail.salary * (1 + detail.bonus / 100) - detail.deduction)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-regular fa-calendar text-blue-700 mr-1"></i>
                                Timekeeping Date: ${detail.timeKeepingDate}
                            </p>
                            <div class="mt-4">
                                <button data-edit-item-id="${detail.employeeTimekeepingID}" type="button" class="text-yellow-700 border border-yellow-700 hover:bg-yellow-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-yellow-500 dark:text-yellow-500 dark:hover:text-white dark:focus:ring-yellow-800 dark:hover:bg-yellow-500">
                                    <i class="fa-solid fa-pencil"></i>
                                </button>
                                <button data-delete-item-id="${detail.employeeTimekeepingID}" type="button" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                    <i class="fa-regular fa-trash-can"></i>
                                </button>
                                <span data-confirmation-toggle-id="${detail.employeeTimekeepingID}" class="ml-2 hidden">
                                    <button data-yes-dc-item-id="${detail.employeeTimekeepingID}" class="text-cyan-700 border border-cyan-700 hover:bg-cyan-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-cyan-500 dark:text-cyan-500 dark:hover:text-white dark:focus:ring-cyan-800 dark:hover:bg-cyan-500">
                                        <i class="fa-solid fa-check"></i>
                                    </button>
                                    <button data-no-dc-item-id="${detail.employeeTimekeepingID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                        <i class="fa-solid fa-xmark"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                    `).join('')

                    // Handle click delete detail item
                    const deleteItemBtns = document.querySelectorAll('button[data-delete-item-id]');
                    Array.from(deleteItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const etkID = btn.dataset.deleteItemId;

                            // Hide trash icon btn
                            btn.classList.add('hidden')
                            // Show confirmation
                            document.querySelector(`span[data-confirmation-toggle-id='${etkID}']`).classList.remove('hidden')

                            // When click yes
                            document.querySelector(`button[data-yes-dc-item-id='${etkID}']`).onclick = () => {
                                const employeeSalaryID = salaryItem.employeeSalaryID;

                                const deletePath = `${window.APP_NAME}/api/salary/delete-detail-item?etkID=${etkID}&salaryID=${employeeSalaryID}`;
                                fetch(deletePath, {
                                    method: 'DELETE',
                                    credentials: 'same-origin',
                                })
                                .then(res => res.json())
                                .then(res => {
                                    if (res.status == 200) {
                                        document.getElementById('close-detail-modal-btn').click();
                                        hideWarningAlert();
                                        showSuccessAlert(res.message);
                                        showDetailSuccessAlert("Delete success")
                                        fetchTableData({ detailClickID: employeeSalaryID })
                                    } else if (res.status == 400 && res.invalid) {
                                        showWarningAlert('Invalid some fields', res.errors);
                                        document.getElementById('close-detail-modal-btn').click();
                                    }
                                })
                                .catch(_res => {})
                            }
                            // When click no
                            document.querySelector(`button[data-no-dc-item-id='${etkID}']`).onclick = () => {
                                // Show trash icon btn
                                document.querySelector(`button[data-delete-item-id='${etkID}']`).classList.remove('hidden')
                                // Hide confirmation
                                document.querySelector(`span[data-confirmation-toggle-id='${etkID}']`).classList.add('hidden')
                            }
                        }
                    });

                    // Handle click add etk item
                    document.getElementById('add-etk-btn').onclick = () => {
                        document.getElementById('employeeSalaryID').value = salaryItem.employeeSalaryID;
                        fetchETK(
                            [
                                {
                                    boxID: 'etkAddBox',
                                    employeeID: salaryItem.employeeID,
                                }
                            ]
                        )
                        document.getElementById('add-etk-btn').classList.add('hidden');
                        document.getElementById('detail-add-form').classList.remove('hidden');
                    }

                    // Handle click edit item
                    const editItemBtns = document.querySelectorAll('button[data-edit-item-id]');
                    
                    Array.from(editItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const detail = salaryItem.details.find(item => item.employeeTimekeepingID == btn.dataset.editItemId);

                            document.getElementById('employeeSalaryIDEdit').value = salaryItem.employeeSalaryID;
                            document.getElementById('etkIDEdit').value = detail.employeeTimekeepingID;
                            document.getElementById('bonusEdit').value = detail.bonus;
                            document.getElementById('deductionEdit').value = detail.deduction;

                            document.getElementById('update-item-form').classList.remove('hidden');
                        }
                    })
                }
            })

            // Handle auto click
            if (detailClickID) {
                document.querySelector(`a[data-detail-id='${detailClickID}']`).click();
            }
        }
    })
    .catch(res => { })
}
fetchTableData();

// Hands Search
document.getElementById('search-form').onsubmit = e => {
    e.preventDefault();
    const dateForm = document.getElementById('dateForm').value;
    const dateTo = document.getElementById('dateTo').value;
    fetchTableData({
        searching: {
            dateForm: dateForm,
            dateTo: dateTo
        }
    })
 }

// Alerts
let successAlert = "";

function showSuccessAlert(content) {
    const sAElm = document.getElementById('success-alert');
    const sAContentElm = document.getElementById('success-alert-content');

    sAContentElm.textContent = content;

    sAElm.classList.remove('opacity-0');
    sAElm.classList.remove('hidden');
    sAElm.classList.add('flex');
}

function showDetailSuccessAlert(content) {
    const sAElm = document.getElementById('detail-success-alert');
    const sAContentElm = document.getElementById('detail-success-alert-content');

    sAContentElm.textContent = content;

    sAElm.classList.remove('opacity-0');
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

// Validate create salary
Validator({
    form: '#create-salary-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#employeeID', 'Employee ID is required'),
    ],
    onSubmit: function(data) {
        const body = {};

        body.employeeID = data.employeeID;
        if (data.etks) {
            body.details = data.etks.map(etk => (
                {
                    etkID: etk,
                    bonus: data[`bonus-${etk}`],
                    deduction: data[`deduction-${etk}`],
                }
            ))
        } else {
            body.details = []
        }

        const storePath = window.APP_NAME + '/api/salary/store';
        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(body)
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create new employee salary');
                fetchTableData();
                websocket.send('refetch')
                document.getElementById('employeeID').value = 0;
                document.getElementById('etkBox').innerHTML = '<p class="text-base font-normal text-gray-500">Select employee first</p>';
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-create-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate add etk
Validator({
    form: '#add-etk-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#employeeSalaryID', 'Employee Salary ID is required'),
    ],
    onSubmit: function(data) {
        const body = {};

        body.employeeSalaryID = data.employeeSalaryID;
        if (data.etks) {
            body.details = data.etks.map(etk => (
                {
                    etkID: etk,
                    bonus: data[`bonus-${etk}`],
                    deduction: data[`deduction-${etk}`],
                }
            ))
        } else {
            body.details = []
        }

        

        const addPath = window.APP_NAME + '/api/salary/add-details';
        fetch(addPath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(body)
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully add new employee salary detail');
                showDetailSuccessAlert("Add detail success")
                fetchTableData({ detailClickID: data.employeeSalaryID });
                document.getElementById('employeeSalaryID').value = 0;
                document.getElementById('cancel-add-etk-form').click();
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update etk item
Validator({
    form: '#update-item-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#employeeSalaryIDEdit', 'Employee Salary ID is required'),
        Validator.isRequired('#etkIDEdit', 'ETK ID is required'),
        Validator.isRequired('#bonusEdit', 'Bonus is required'),
        Validator.isRequired('#deductionEdit', 'Deduction is required'),
    ],
    onSubmit: function(data) {
        const updatePath = window.APP_NAME + '/api/salary/update-detail-item';
        fetch(updatePath, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully update salary detail');
                showDetailSuccessAlert("Update detail success")
                fetchTableData({ detailClickID: data.employeeSalaryID });
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

function fetchEmployees(selectsID) {
    const fetchPath = window.APP_NAME + '/api/employee/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            selectsID.forEach(selectID => {
                const emps = res.data;
                emps.push({employeeID: 0, name: 'Choose employee'})
                document.getElementById(selectID).innerHTML = 
                emps.reverse().map(item => {
                    return `
                        <option value="${item.employeeID}">${item.name}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchEmployees([
    'employeeID',
]);

var etks = []
function fetchETK(boxs) {
    const fetchPath = window.APP_NAME + '/api/etk/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            etks = res.data;
            boxs.forEach(box => {
                document.getElementById(box.boxID).innerHTML = !etks.filter(etk => etk.employeeID == box.employeeID && !etk.paid)?.length ? '<h1 class="text-center font-semibold text-base text-white">Need to add timekeeping for this employee before create new salary</h1>' :
                etks.filter(etk => etk.employeeID == box.employeeID && !etk.paid).map(etk => {
                    return `
                        <div class="flex items-center mb-3">
                            <input id="etk-checkbox-${etk.timeKeepingID}" name="etks" type="checkbox" value="${etk.timeKeepingID}" checked class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            <label for="etk-checkbox-${etk.timeKeepingID}" class="select-none cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                ${etk.shiftName}, ${window.currencyOutput(etk.salary)}, ${etk.date}
                            </label>
                        </div>
                        <div class="grid md:grid-cols-2 md:gap-6">
                            <div class="relative z-0 w-full mb-5 group">
                                <input type="number" value="0" name="bonus-${etk.timeKeepingID}" id="bonus-${etk.timeKeepingID}" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                <label for="bonus-${etk.timeKeepingID}" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Bonus (%)</label>
                            </div>
                            <div class="relative z-0 w-full mb-5 group">
                                <input type="number" value="0" name="deduction-${etk.timeKeepingID}" id="deduction-${etk.timeKeepingID}" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                                <label for="deduction-${etk.timeKeepingID}" class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Deduction (VNĐ)</label>
                            </div>
                        </div>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}

// Handle choose employee'
function handleChooseEmployee() {
    document.getElementById('employeeID').oninput = e => {
        fetchETK(
            [
                {
                    boxID: 'etkBox',
                    employeeID: e.target.value,
                }
            ]
        )
    }
}
handleChooseEmployee();
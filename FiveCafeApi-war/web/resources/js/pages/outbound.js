// Fetch table data
function fetchTableData(prop = {}) {
    const { detailClickID = null, searching = null } = prop;

    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/outbound/search?dateForm=${searching.dateForm}&dateTo=${searching.dateTo}`
    } else  {
        fetchPath += '/api/outbound/all';
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
                                <input type="checkbox" name="delete-checkbox" value="${item.outboundID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <td scope="row" class="px-6 py-4">
                                ${item.outboundID}
                            </td>
                            <td scope="row" class="px-6 py-4">
                                ${item.name}
                            </td>
                            <td class="px-6 py-4">
                                ${item.date}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a title="View detail" data-detail-id="${item.outboundID}" data-modal-target="outbound-detail-modal" data-modal-toggle="outbound-detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
                                    <i class="fa-solid fa-circle-info"></i>
                                </a>
                            </td>
                        </tr>
                    `
                }).join('');
            // Init flowbite
            initFlowbite();

            // Handle checkbox to delete
            let checkedRowsToDelete = [];

            // Check if has any checked checkbox then show delete button
            function renderDeleteBtn() {
                const dltBtn = document.getElementById('dlt-btn');
                if (checkedRowsToDelete.length > 0) {
                    dltBtn.classList.remove('hidden')
                } else {
                    dltBtn.classList.add('hidden')
                }

                // Handle click delete
                const yesDltBtn = document.getElementById('yes-dlt-btn')
                yesDltBtn.onclick = () => {
                    fetch(`${window.APP_NAME}/api/outbound/delete?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete outbound');
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

            if (!document.getElementById('detail-update-form').classList.contains('hidden')) {
                document.getElementById('detail-update-form').classList.add('hidden');
            }
            if (!document.getElementById('detail-add-form').classList.contains('hidden')) {
                document.getElementById('detail-add-form').classList.add('hidden');
            }
            document.getElementById('cancel-update-mat-btn').onclick = () => {
                document.getElementById('detail-update-form').classList.add('hidden');
            }
            document.getElementById('cancel-add-mat-btn').onclick = () => {
                document.getElementById('detail-add-form').classList.add('hidden');
                document.getElementById('add-mat-item-btn-box').classList.remove('hidden');
            }

            Array.from(detailBtns).forEach(btn => {
                btn.onclick = () => {
                    document.getElementById('detail-update-form').classList.add('hidden');

                    const outboundItem = res.data.find(item => item.outboundID == btn.dataset.detailId);

                    document.getElementById('outboundIDEdit').value = outboundItem.outboundID;

                    document.getElementById('outbound-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Outbound ID: <span class="font-bold dark:text-white">${outboundItem.outboundID}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Outbound Date: <span class="font-bold dark:text-white">${outboundItem.date}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee: <span class="font-bold dark:text-white">${outboundItem.name}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = outboundItem.details.map(detail => `
                        <div class="flex py-2">
                            <img class="object-cover rounded w-32 h-32" src="${detail.materialImage}" alt="Material">
                            <div class="flex flex-col px-4 leading-normal">
                                <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">
                                    ${detail.materialName}
                                    <button data-edit-mat-id="${detail.materialID}" class="ml-2 text-yellow-700 border border-yellow-700 hover:bg-yellow-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-yellow-500 dark:text-yellow-500 dark:hover:text-white dark:focus:ring-yellow-800 dark:hover:bg-yellow-500">
                                        <i class="fa-solid fa-pencil"></i>
                                    </button>
                                    <button data-delete-mat-id="${detail.materialID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>

                                    <div data-confirmation-toggle-id="${detail.materialID}" class="hidden">
                                        <button data-yes-dc-mat-id="${detail.materialID}" class="text-cyan-700 border border-cyan-700 hover:bg-cyan-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-cyan-500 dark:text-cyan-500 dark:hover:text-white dark:focus:ring-cyan-800 dark:hover:bg-cyan-500">
                                            <i class="fa-solid fa-check"></i>
                                        </button>
                                        <button data-no-dc-mat-id="${detail.materialID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                            <i class="fa-solid fa-xmark"></i>
                                        </button>
                                    </div>
                                </h5>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${detail.quantity} ${detail.unit}</p>
                            </div>
                        </div>
                    `).join('')

                    // Handle click delete mat item
                    const deleteItemBtns = document.querySelectorAll('button[data-delete-mat-id]');
                    Array.from(deleteItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const matID = btn.dataset.deleteMatId;

                            // Hide trash icon btn
                            btn.classList.add('hidden')
                            // Show confirmation
                            document.querySelector(`div[data-confirmation-toggle-id='${matID}']`).classList.remove('hidden')

                            // When click yes
                            document.querySelector(`button[data-yes-dc-mat-id='${matID}']`).onclick = () => {
                                const outboundID = outboundItem.outboundID;

                                const deletePath = `${window.APP_NAME}/api/outbound/delete-mat-item?matID=${matID}&outboundID=${outboundID}`;
                                fetch(deletePath, {
                                    method: 'DELETE',
                                    credentials: 'same-origin',
                                })
                                .then(res => res.json())
                                .then(res => {
                                    if (res.status == 200) {
                                        document.getElementById('close-outbound-detail-modal-btn').click();
                                        hideWarningAlert();
                                        showSuccessAlert(res.message);
                                        showDetailSuccessAlert("Delete success")
                                        fetchTableData({ detailClickID: outboundID })
                                    } else if (res.status == 400 && res.invalid) {
                                        showWarningAlert('Invalid some fields', res.errors);
                                        document.getElementById('close-outbound-detail-modal-btn').click();
                                    }
                                })
                                .catch(_res => {})
                            }
                            // When click no
                            document.querySelector(`button[data-no-dc-mat-id='${matID}']`).onclick = () => {
                                // Show trash icon btn
                                document.querySelector(`button[data-delete-mat-id='${matID}']`).classList.remove('hidden')
                                // Hide confirmation
                                document.querySelector(`div[data-confirmation-toggle-id='${matID}']`).classList.add('hidden')
                            }
                        }
                    });

                    // Handle click add material item
                    document.getElementById('add-mat-item-btn').onclick = () => {
                        document.getElementById('outboundIDAdd').value = outboundItem.outboundID;
                        document.getElementById('add-mat-item-btn-box').classList.add('hidden');
                        document.getElementById('detail-add-form').classList.remove('hidden');
                    }

                    // Handle click edit material item
                    const editItemBtns = document.querySelectorAll('button[data-edit-mat-id]');
                    Array.from(editItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const materialData = outboundItem.details.find(item => item.materialID == btn.dataset.editMatId);

                            document.getElementById('materialIDEdit').value = materialData.materialID;
                            document.getElementById('quantityEdit').value = materialData.quantity;

                            document.getElementById('detail-update-form').classList.remove('hidden');
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
    .catch(_res => { })
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

// Validate choose material
Validator({
    form: '#choose-material-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#materialID', 'Material ID is required'),
        Validator.isRequired('#quantity', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        resetForm({
            materialID: data.materialID,
            quantity: '0',
        })
        insertMaterialItem(data)
    }
});

// Validate create outbound
Validator({
    form: '#create-outbound-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [],
    onSubmit: function(data) {
        const storePath = window.APP_NAME + '/api/outbound/store';

        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                employeeID: data.employeeID,
                details: materialsOutbound,
            }),
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create outbound');
                fetchTableData();
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-create-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate add material
Validator({
    form: '#add-material-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#materialIDAdd', 'Material ID is required'),
        Validator.isRequired('#quantityAdd', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const addPath = `${window.APP_NAME}/api/outbound/store-mat-item`
        fetch(addPath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-outbound-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    quantity: '0',
                })
                showDetailSuccessAlert("Add material success")
                fetchTableData({ detailClickID: data.outboundID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-outbound-detail-modal-btn').click();
            }
        })
        .res(_err => {})
    }
});

// Validate update material item
Validator({
    form: '#update-material-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#outboundIDEdit', 'Outbound ID is required'),
        Validator.isRequired('#materialIDEdit', 'Material ID is required'),
        Validator.isRequired('#quantityEdit', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/outbound/update';

        fetch(updatePath, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-outbound-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    quantity: 0,
                });
                showDetailSuccessAlert("Update success")
                fetchTableData({ detailClickID: data.outboundID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-outbound-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// ------------ MATERIALS

var materialsOutbound = [];


function insertMaterialItem({ materialID, quantity }) {
    materialsOutbound.push({
        materialID: materialID,
        quantity: quantity,
    })

    renderMaterials();
}

function renderMaterials() {
    const materialsBox = document.getElementById("materialsBox");

    materialsBox.innerHTML = materialsOutbound.map(matItem => {
        const material = materials.find(x => x.materialID ==  matItem.materialID);
        return `
            <div class="flex py-2">
                <img class="object-cover rounded w-32 h-32" src="${material.image}" alt="Material">
                <div class="flex flex-col justify-between px-4 leading-normal">
                    <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">${material.name}</h5>
                    <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${matItem.quantity}</p>
                    <button data-remove-item-id="${matItem.materialID}" class="px-3 py-2 text-xs font-medium text-center inline-flex items-center text-white bg-red-700 rounded-lg hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">
                        <i class="fa-regular fa-circle-xmark mr-2"></i>
                        Remove
                    </button>
                </div>
            </div>
        `
    }).join('');

    Array.from(document.querySelectorAll('button[data-remove-item-id]')).forEach(btn => {
        btn.onclick = () => {
            removeMaterialItem(btn.dataset.removeItemId)
        }
    })
}

function removeMaterialItem(materialID) {
    materialsOutbound = materialsOutbound.filter(item => item.materialID != materialID);

    renderMaterials();
}

// ------------ SELECT

var materials = [];

// Handle fetch material data
function fetchMaterials(selectsID) {
    const fetchPath = window.APP_NAME + '/api/material/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            materials = res.data;
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.materialID}">${item.name}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchMaterials([
    'materialID',
    'materialIDAdd',
]);

var employees = [];

// Handle fetch material data
function fetchEmployee(selectsID) {
    const fetchPath = window.APP_NAME + '/api/employee/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            employees = res.data;
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.employeeID}">${item.name}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchEmployee([
    'employeeID',
    'employeeIDAdd',
]);

var outbounds = [];

// Handle fetch material data
function fetchBotbound(selectsID) {
    const fetchPath = window.APP_NAME + '/api/outbound/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            outbounds = res.data;
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.outboundID}">${item.outboundID}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchBotbound([
    'outboundIDEdit',
]);
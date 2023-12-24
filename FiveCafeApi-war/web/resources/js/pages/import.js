// Fetch table data
function fetchTableData(prop = {}) {
    const { detailClickID = null, searching = null } = prop;
    
    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/import/search?dateForm=${searching.dateForm}&dateTo=${searching.dateTo}`
    } else {
        fetchPath += `/api/import/all`;
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
                                <input type="checkbox" name="delete-checkbox" value="${item.importID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.importID}
                            </th>
                            <td class="px-6 py-4">
                                ${item.importDate}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a title="View detail" data-detail-id="${item.importID}" data-modal-target="import-detail-modal" data-modal-toggle="import-detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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
                    fetch(`${window.APP_NAME}/api/import/delete_import?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete imports');
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
            if (document.getElementById('add-mat-item-btn-box').classList.contains('hidden')) {
                document.getElementById('add-mat-item-btn-box').classList.remove('hidden');
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

                    const importItem = res.data.find(item => item.importID == btn.dataset.detailId);

                    document.getElementById('importIDEdit').value = importItem.importID;

                    document.getElementById('import-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Import ID: <span class="font-bold dark:text-white text-black">${importItem.importID}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Import Date: <span class="font-bold dark:text-white text-black">${importItem.importDate}</span></p>
                        <p class="text-base font-normal dark:text-gray-400 text-gray-700">Total cost: <span class="font-bold dark:text-white text-black">${window.currencyOutput(importItem.details.reduce((acc, cur) => acc + cur.unitPrice * cur.quantity, 0))}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = importItem.details.map(detail => `
                        <div class="flex py-2">
                            <img class="object-cover rounded w-32 h-32" src="${detail.materialImage}" alt="Material">
                            <div class="flex flex-col justify-between px-4 leading-normal">
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
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Unit price: ${window.currencyOutput(detail.unitPrice)}</p>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${detail.quantity} ${detail.unit}</p>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Total: ${window.currencyOutput(detail.unitPrice * detail.quantity)}</p>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Supplier: ${detail.supplierContactName}</p>
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
                                const importID = importItem.importID;

                                const deletePath = `${window.APP_NAME}/api/import/delete-mat-item?matID=${matID}&impID=${importID}`;
                                fetch(deletePath, {
                                    method: 'DELETE',
                                    credentials: 'same-origin',
                                })
                                .then(res => res.json())
                                .then(res => {
                                    if (res.status == 200) {
                                        document.getElementById('close-import-detail-modal-btn').click();
                                        hideWarningAlert();
                                        showSuccessAlert(res.message);
                                        showDetailSuccessAlert("Delete success")
                                        fetchTableData({ detailClickID: importID })
                                    } else if (res.status == 400 && res.invalid) {
                                        showWarningAlert('Invalid some fields', res.errors);
                                        document.getElementById('close-import-detail-modal-btn').click();
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
                        document.getElementById('importIDAdd').value = importItem.importID;
                        document.getElementById('add-mat-item-btn-box').classList.add('hidden');
                        document.getElementById('detail-add-form').classList.remove('hidden');
                    }

                    // Handle click edit material item
                    const editItemBtns = document.querySelectorAll('button[data-edit-mat-id]');
                    Array.from(editItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const materialData = importItem.details.find(item => item.materialID == btn.dataset.editMatId);

                            document.getElementById('materialIDEdit').value = materialData.materialID;
                            document.getElementById('supplierIDEdit').value = materialData.supplierID;
                            document.getElementById('unitPriceEdit').value = materialData.unitPrice;
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
        Validator.isRequired('#supplierID', 'Supplier ID is required'),
        Validator.isRequired('#unitPrice', 'Unit price is required'),
        Validator.isRequired('#quantity', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        resetForm({
            materialID: data.materialID,
            supplierID: data.supplierID,
            unitPrice: '0',
            quantity: '0',
        })
        insertMaterialItem(data)
    }
});

// Validate create import
Validator({
    form: '#create-import-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [],
    onSubmit: function() {
        const storePath = window.APP_NAME + '/api/import/store';

        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                details: materialsImport,
            }),
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create import');
                fetchTableData();
                fetchImport();
                clearMaterialImports();
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
        Validator.isRequired('#supplierIDAdd', 'Supplier ID is required'),
        Validator.isRequired('#unitPriceAdd', 'Unit price is required'),
        Validator.isRequired('#quantityAdd', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const addPath = `${window.APP_NAME}/api/import/store-mat-item`
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
                document.getElementById('close-import-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    unitPrice: '0',
                    quantity: '0',
                })
                showDetailSuccessAlert("Add material success")
                fetchTableData({ detailClickID: data.importID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-import-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update material item
Validator({
    form: '#update-material-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#importIDEdit', 'Import ID is required'),
        Validator.isRequired('#materialIDEdit', 'Material ID is required'),
        Validator.isRequired('#supplierIDEdit', 'Supplier ID is required'),
        Validator.isRequired('#unitPriceEdit', 'Unit price is required'),
        Validator.isRequired('#quantityEdit', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/import/update-mat-item';

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
                document.getElementById('close-import-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    quantity: 0,
                    unitPrice: 0,
                });
                showDetailSuccessAlert("Update success")
                fetchTableData({ detailClickID: data.importID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-import-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// ------------ IMPORT

var materialsImport = [];


function insertMaterialItem({ materialID, supplierID, unitPrice, quantity }) {
    materialsImport.push({
        materialID: materialID,
        supplierID: supplierID,
        unitPrice: unitPrice,
        quantity: quantity,
    })

    renderMaterials();
}

function renderMaterials() {
    const materialsBox = document.getElementById("materialsBox");

    materialsBox.innerHTML = materialsImport.map(matItem => {
        const material = materials.find(x => x.materialID ==  matItem.materialID);
        const supplier = suppliers.find(x => x.supplierID == matItem.supplierID);
        return `
            <div class="flex py-2">
                <img class="object-cover rounded w-32 h-32" src="${material.image}" alt="Material">
                <div class="flex flex-col justify-between px-4 leading-normal">
                    <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">${material.name}</h5>
                    <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Unit price: ${window.currencyOutput(matItem.unitPrice)}</p>
                    <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${matItem.quantity}</p>
                    <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Total: ${window.currencyOutput(matItem.unitPrice * matItem.quantity)}</p>
                    <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Supplier: ${supplier.contactName}</p>
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
    materialsImport = materialsImport.filter(item => item.materialID != materialID);

    renderMaterials();
}

function clearMaterialImports() {
    materialsImport = [];
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

var suppliers = [];

// Handle fetch supplier data
function fetchSuppliers(selects) {
    const fetchPath = window.APP_NAME + '/api/supplier/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            suppliers = res.data;
            selects.forEach(select => {
                document.getElementById(select.selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.supplierID}" ${item.supplierID == select.selectedID ? 'selected' : ''}>${item.contactName}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchSuppliers([
    {
        selectID: 'supplierID',
        selectedID:  0,
    },
    {
        selectID: 'supplierIDEdit',
        selectedID:  0,
    },
    {
        selectID: 'supplierIDAdd',
        selectedID:  0,
    },
]);

// Thông báo material <5
function fetchImport() {
    const fetchPath = window.APP_NAME + '/api/material/get-the-quantityinstock-below-five';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            if (res.data?.length) {
                document.getElementById('product-out-quantityinstock-content').innerHTML = 
                    res.data.reverse().map(item => {
                        return `
                            <div class="flex items-center justify-between mt-3 bg-slate-300 p-2 rounded-md text-base font-normal">
                                "${item.name}" material with quantity in stock is ${item.quantityInStock} ${item.unit}
                                <button data-mat-id="${item.materialID}" type="button" class="text-red-700 hover:text-white border border-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:hover:bg-red-600 dark:focus:ring-red-900">Import now</button>
                            </div>
                        `
                    }
                ).join('');
                Array.from(document.querySelectorAll('button[data-mat-id]')).forEach(btn => {
                    btn.onclick = () => {
                        const matID = btn.dataset.matId;


                        document.getElementById('materialID').value = matID;


                        document.getElementById('create-btn').click();
                    }
                })
            } else {
                document.getElementById('product-out-quantityinstock-box').classList.add('hidden')
            }
        }
    })
    .catch(_res => { })
}

fetchImport();

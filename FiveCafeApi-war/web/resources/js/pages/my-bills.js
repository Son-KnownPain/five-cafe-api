// Fetch table data
function fetchTableData(prop = {}) {
    const { detailClickID = null, searching = null } = prop;

    fetchPath = window.APP_NAME + '/api/employee/all-my-bills';

    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <td class="px-6 py-4">
                                ${item.billStatusValue}
                            </td>
                            <td class="px-6 py-4">
                                ${item.createDate}
                            </td>
                            <td class="px-6 py-4">
                                ${item.cardCode}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a title="View detail" data-detail-id="${item.billID}" data-modal-target="bill-detail-modal" data-modal-toggle="bill-detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
                                    <i class="fa-solid fa-circle-info"></i>
                                </a>
                            </td>
                        </tr>
                    `
                }).join('');
            // Init flowbite
            initFlowbite();

            // Handle click view detail
            const detailBtns = document.querySelectorAll('a[data-detail-id]')

            if (!document.getElementById('detail-update-form').classList.contains('hidden')) {
                document.getElementById('detail-update-form').classList.add('hidden');
            }
            if (!document.getElementById('detail-add-form').classList.contains('hidden')) {
                document.getElementById('detail-add-form').classList.add('hidden');
            }
            if (!document.getElementById('edit-bill-form').classList.contains('hidden')) {
                document.getElementById('edit-bill-form').classList.add('hidden');
            }
            document.getElementById('cancel-update-pro-btn').onclick = () => {
                document.getElementById('detail-update-form').classList.add('hidden');
            }
            document.getElementById('cancel-edit-bill-btn').onclick = () => {
                document.getElementById('edit-bill-form').classList.add('hidden');
            }
            document.getElementById('cancel-add-pro-btn').onclick = () => {
                document.getElementById('detail-add-form').classList.add('hidden');
                document.getElementById('add-pro-item-btn-box').classList.remove('hidden');
            }

            Array.from(detailBtns).forEach(btn => {
                btn.onclick = () => {
                    document.getElementById('detail-update-form').classList.add('hidden');
                    document.getElementById('edit-bill-form').classList.add('hidden');

                    const billItem = res.data.find(item => item.billID == btn.dataset.detailId);

                    document.getElementById('billIDEdit').value = billItem.billID;
                    document.getElementById('detailBillIDEdit').value = billItem.billID;

                    document.getElementById('bill-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">
                            Bill ID: 
                            <span class="font-bold dark:text-white text-black">
                                ${billItem.billID}
                            </span>
                            <button data-edit-bill-id="${billItem.billID}" class="ml-2 text-yellow-700 border border-yellow-700 hover:bg-yellow-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-yellow-500 dark:text-yellow-500 dark:hover:text-white dark:focus:ring-yellow-800 dark:hover:bg-yellow-500">
                                <i class="fa-solid fa-pencil"></i>
                            </button>
                        </p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Bill Create Date: <span class="font-bold dark:text-white text-black">${billItem.createDate}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Total price: <span class="font-bold dark:text-white text-black">${window.currencyOutput(billItem.details.reduce((acc, cur) => acc + cur.unitPrice * cur.quantity, 0))}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee: <span class="font-bold dark:text-white text-black">${billItem.employeeName}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Bill Status: <span class="font-bold dark:text-white text-black">${billItem.billStatusValue}</span></p>
                        <p class="text-base font-normal dark:text-gray-400 text-gray-700">Card Code: <span class="font-bold dark:text-white text-black">${billItem.cardCode}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = billItem.details.map(detail => `
                        <div class="flex py-2">
                            <img class="object-cover rounded w-32 h-32" src="${detail.image}" alt="Product">
                            <div class="flex flex-col justify-between px-4 leading-normal">
                                <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">
                                    ${detail.name}
                                    <button data-edit-pro-id="${detail.productID}" class="ml-2 text-yellow-700 border border-yellow-700 hover:bg-yellow-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-yellow-500 dark:text-yellow-500 dark:hover:text-white dark:focus:ring-yellow-800 dark:hover:bg-yellow-500">
                                        <i class="fa-solid fa-pencil"></i>
                                    </button>
                                    <button data-delete-pro-id="${detail.productID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>

                                    <div data-confirmation-toggle-id="${detail.productID}" class="hidden">
                                        <button data-yes-dc-pro-id="${detail.productID}" class="text-cyan-700 border border-cyan-700 hover:bg-cyan-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-cyan-500 dark:text-cyan-500 dark:hover:text-white dark:focus:ring-cyan-800 dark:hover:bg-cyan-500">
                                            <i class="fa-solid fa-check"></i>
                                        </button>
                                        <button data-no-dc-pro-id="${detail.productID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                            <i class="fa-solid fa-xmark"></i>
                                        </button>
                                    </div>
                                </h5>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Unit price: ${window.currencyOutput(detail.unitPrice)}</p>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${detail.quantity}</p>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Total: ${window.currencyOutput(detail.unitPrice * detail.quantity)}</p>
                            </div>
                        </div>
                    `).join('')

                    // Handle click delete mat item
                    const deleteItemBtns = document.querySelectorAll('button[data-delete-pro-id]');
                    Array.from(deleteItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const productID = btn.dataset.deleteProId;

                            // Hide trash icon btn
                            btn.classList.add('hidden')
                            // Show confirmation
                            document.querySelector(`div[data-confirmation-toggle-id='${productID}']`).classList.remove('hidden')

                            // When click yes
                            document.querySelector(`button[data-yes-dc-pro-id='${productID}']`).onclick = () => {
                                const billID = billItem.billID;

                                const deletePath = `${window.APP_NAME}/api/employee/delete-pro-of-bill?productID=${productID}&billID=${billID}`;
                                fetch(deletePath, {
                                    method: 'DELETE',
                                    credentials: 'same-origin',
                                })
                                .then(res => res.json())
                                .then(res => {
                                    if (res.status == 200) {
                                        document.getElementById('close-bill-detail-modal-btn').click();
                                        hideWarningAlert();
                                        showSuccessAlert(res.message);
                                        showDetailSuccessAlert("Delete success")
                                        fetchTableData({ detailClickID: billID })
                                    } else if (res.status == 400 && res.invalid) {
                                        showWarningAlert('Invalid some fields', res.errors);
                                        document.getElementById('close-bill-detail-modal-btn').click();
                                    }
                                })
                                .catch(_res => {})
                            }
                            // When click no
                            document.querySelector(`button[data-no-dc-pro-id='${productID}']`).onclick = () => {
                                // Show trash icon btn
                                document.querySelector(`button[data-delete-pro-id='${productID}']`).classList.remove('hidden')
                                // Hide confirmation
                                document.querySelector(`div[data-confirmation-toggle-id='${productID}']`).classList.add('hidden')
                            }
                        }
                    });

                    // Handle click add product item
                    document.getElementById('add-pro-item-btn').onclick = () => {
                        document.getElementById('billIDAdd').value = billItem.billID;
                        document.getElementById('add-pro-item-btn-box').classList.add('hidden');
                        document.getElementById('detail-add-form').classList.remove('hidden');
                    }

                    // Handle click edit product item
                    const editItemBtns = document.querySelectorAll('button[data-edit-pro-id]');
                    
                    Array.from(editItemBtns).forEach(btn => {
                        btn.onclick = () => {
                            const productData = billItem.details.find(item => item.productID == btn.dataset.editProId);

                            document.getElementById('productIDEdit').value = productData.productID;
                            document.getElementById('quantityEdit').value = productData.quantity;

                            document.getElementById('detail-update-form').classList.remove('hidden');
                        }
                    })

                    // Handle click edit Bill item
                    const editBillBtns = document.querySelectorAll('button[data-edit-bill-id]');
                    
                    Array.from(editBillBtns).forEach(btn => {
                        btn.onclick = () => {
                            const billItemEdit = res.data.find(x => x.billID == btn.dataset.editBillId)
                            document.getElementById('billStatusIDEdit').value = billItemEdit.billStatusID;
                            document.getElementById('cardCodeEdit').value = billItemEdit.cardCode;
                            document.getElementById('edit-bill-form').classList.remove('hidden');
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

// Validate edit bill
Validator({
    form: '#edit-bill-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#billStatusIDEdit', 'Bill status'),
        Validator.isRequired('#cardCodeEdit', 'Card code is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + "/api/employee/update-my-bill"

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
                document.getElementById('close-bill-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    cardCode: '',
                })
                showDetailSuccessAlert("Update success");
                fetchTableData({ detailClickID: data.billID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-bill-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate add product
Validator({
    form: '#add-product-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#productIDAdd', 'Product ID is required'),
        Validator.isRequired('#quantityAdd', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const addPath = `${window.APP_NAME}/api/employee/add-pro-of-bill`
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
                document.getElementById('close-bill-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    quantity: '0',
                })
                showDetailSuccessAlert("Add product success");
                if (document.getElementById('add-pro-item-btn-box').classList.contains("hidden")) {
                    document.getElementById('add-pro-item-btn-box').classList.remove("hidden")
                }
                fetchTableData({ detailClickID: data.billID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-bill-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update product item
Validator({
    form: '#update-product-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#detailBillIDEdit', 'Bill ID is required'),
        Validator.isRequired('#productIDEdit', 'Product ID is required'),
        Validator.isRequired('#quantityEdit', 'Quantity is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/employee/update-pro-of-bill';

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
                document.getElementById('close-bill-detail-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert(res.message);
                resetForm({
                    quantity: 0,
                });
                showDetailSuccessAlert("Update success")
                fetchTableData({ detailClickID: data.billID })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-bill-detail-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// ------------ SELECT

// var products = [];

// Handle fetch product data
function fetchProducts(selectsID) {
    const fetchPath = window.APP_NAME + '/api/product/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            // products = res.data;
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.productID}">${item.name}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchProducts([
    'productIDAdd',
]);

// function fetchEmployees(selectsID) {
//     const fetchPath = window.APP_NAME + '/api/employee/all';
//     fetch(fetchPath)
//     .then(res => res.json())
//     .then(res => {
//         if (res.status == 200) {
//             selectsID.forEach(selectID => {
//                 document.getElementById(selectID).innerHTML = 
//                 res.data.map(item => {
//                     return `
//                         <option value="${item.employeeID}">${item.name}</option>
//                     `
//                 }).join('');
//             })
//         }
//     })
//     .catch(_res => { })
// }
// fetchEmployees([
//     'employeeID',
//     'employeeIDEdit',
// ]);

function fetchBillStatus(selectsID) {
    const fetchPath = window.APP_NAME + '/api/bill-sts/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.billStatusID}">${item.billStatusValue}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchBillStatus([
    'billStatusIDEdit',
]);
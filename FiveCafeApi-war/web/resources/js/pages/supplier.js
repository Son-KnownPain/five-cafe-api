// Fetch table data
function fetchTableData(searching = null) {
    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/supplier/search?keyword=${window.removeVietnameseDiacritics(searching.keyword)}`
    } else  {
        fetchPath += '/api/supplier/all';
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
                                <input type="checkbox" name="delete-checkbox" value="${item.supplierID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.supplierID}
                            </th>
                            <td class="px-6 py-4">
                                ${item.contactName}
                            </td>
                            <td class="px-6 py-4">
                                ${item.contactNumber}
                            </td>
                            <td class="px-6 py-4">
                                ${item.address}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a data-edit-id="${item.supplierID}" data-modal-target="update-modal" data-modal-toggle="update-modal" class="cursor-pointer font-medium text-yellow-400 dark:text-yellow-400 hover:underline">
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
                    fetch(`${window.APP_NAME}/api/supplier/delete?ids=${checkedRowsToDelete.join(',')}`, {
                        method: 'DELETE',
                        credentials: 'same-origin',
                    })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert('Successfully delete supplier');
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
                    const sup = res.data.find(item => item.supplierID == btn.dataset.editId);
                    console.log(sup);
                    document.getElementById('supplierIDEdit').value = sup.supplierID;
                    document.getElementById('contactNameEdit').value = sup.contactName;
                    document.getElementById('contactNumberEdit').value = sup.contactNumber;
                    document.getElementById('addressEdit').value = sup.address;

                }
            })
        }
    })
    .catch(res => { })
}
fetchTableData();

// Handle searching 
document.getElementById('search-form').onsubmit = e => {
    e.preventDefault();
    const keyword = document.getElementById('keywordSearch').value;
    fetchTableData({
        keyword: keyword
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

// Validate create
Validator({
    form: '#create-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#contactName', 'Contact Name is required'),
        Validator.isRequired('#contactNumber', 'Contact Number is required'),
        Validator.isRequired('#address', 'Address is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/supplier/store';
        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                contactName: data.contactName,
                contactNumber: data.contactNumber,
                address: data.address,

            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-create-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully create new supplier');
                fetchTableData();
                resetForm({
                    contactName: '',
                    contactNumber: '',
                    address: '',
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-create-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});

// Validate update
Validator({
    form: '#update-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#supplierIDEdit', 'Supplier ID is required'),
        Validator.isRequired('#contactNameEdit', 'Contact Name is required'),
        Validator.isRequired('#contactNumberEdit', 'Contact Number is required'),
        Validator.isRequired('#addressEdit', 'Address From is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/supplier/update';
        fetch(updatePath, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                supplierID: data.supplierID,
                contactName: data.contactName,
                contactNumber: data.contactNumber,
                address: data.address,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                document.getElementById('close-update-modal-btn').click();
                hideWarningAlert();
                showSuccessAlert('Successfully update supplier');
                fetchTableData();
                resetForm({
                    supplierID: '',
                    contactName: '',
                    contactNumber: '',
                    address: '',
                })
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
                document.getElementById('close-update-modal-btn').click();
            }
        })
        .catch(_err => {})
    }
});
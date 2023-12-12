// Fetch table data
function fetchTableData(searching = null) {
    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/material/search?name=${searching.name}${searching.materialCategoryID ? `&materialCategoryID=${searching.materialCategoryID}` : ""}`
    } else {
        fetchPath += '/api/material/all';
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
                                <input type="checkbox" name="delete-checkbox" value="${item.materialID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                <img class="object-cover w-20 h-20 rounded" src="${item.image}" alt="Large avatar">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.name}
                            </th>
                            <td class="px-6 py-4">
                                ${item.unit}
                            </td>
                            <td class="px-6 py-4">
                                ${item.quantityInStock}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a data-edit-id="${item.materialID}" data-modal-target="update-modal" data-modal-toggle="update-modal" class="block cursor-pointer font-semibold text-yellow-500 dark:text-yellow-500 hover:text-yellow-300">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                                <a data-detail-id="${item.materialID}" data-modal-target="detail-modal" data-modal-toggle="detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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
                        fetch(`${window.APP_NAME}/api/material/delete?ids=${checkedRowsToDelete.join(',')}`, {
                            method: 'DELETE',
                            credentials: 'same-origin',
                        })
                            .then(res => res.json())
                            .then(res => {
                                if (res.status == 200) {
                                    hideWarningAlert();
                                    showSuccessAlert('Successfully delete material');
                                    fetchTableData();
                                    checkedRowsToDelete = [];
                                    renderDeleteBtn();
                                }
                            })
                            .catch(_err => { })
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
                        const employee = res.data.find(item => item.materialID == btn.dataset.editId);

                        document.getElementById('materialIDEdit').value = employee.materialID;
                        document.getElementById('previewImgEdit').src = employee.image;
                        document.getElementById('previewImgEdit').classList.remove('hidden')
                        document.getElementById('materialCategoryIDEdit').value = employee.materialCategoryID;
                        document.getElementById('nameEdit').value = employee.name;
                        document.getElementById('unitEdit').value = employee.unit;
                        document.getElementById('quantityInStockEdit').value = employee.quantityInStock;
                    }
                })

                // Handle click view detail
                const detailBtns = document.querySelectorAll('a[data-detail-id]')
                Array.from(detailBtns).forEach(btn => {
                    btn.onclick = () => {
                        const material = res.data.find(item => item.materialID == btn.dataset.detailId);

                        document.getElementById("detail-image").src = material.image;
                        document.getElementById("detail-name").textContent = material.name;
                        document.getElementById("detail-unit").textContent = material.unit;
                        document.getElementById("detail-quantityInStock").textContent = material.quantityInStock;
                        document.getElementById("detail-category").textContent = material.materialCategoryName;
                    }
                })
            }
        })
        .catch(res => { })
}
fetchTableData();

// Handle searching
function renderMatCatFilter() {
    const items = [
        {
            materialCategoryID: '',
            name: 'Tất cả danh mục',
        },
        ...materialCategoryName,
    ]
    document.getElementById('matcat-filter').innerHTML = items.map(item => {
        return `
            <li data-mat-id="${item.materialCategoryID}" data-mat-name="${item.name}">
                <button type="button" class="inline-flex w-full px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">${item.name}</button>
            </li>
        `
    }).join('');
}

function processClickChooseMatCat() {
    Array.from(document.querySelectorAll('li[data-mat-id]')).forEach(li => {
        li.onclick = () => {
            document.getElementById('materialCategoryIDSearch').value = li.dataset.matId;
            document.getElementById('dropdown-button').innerHTML = `
                ${li.dataset.matName}
                <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                </svg>
            `
        }
    })
}
document.getElementById('search-form').onsubmit = e => {
    e.preventDefault();
    const materialCategoryID = document.getElementById('materialCategoryIDSearch').value;
    const name = document.getElementById('nameSearch').value;
    fetchTableData({
        materialCategoryID: materialCategoryID.length && materialCategoryID,
        name: name
    })
}

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



// Validate create
Validator({
    form: '#create-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#image', 'Image is required'),
        Validator.isRequired('#materialCategoryID', 'Material category is required'),
        Validator.isRequired('#name', 'Name is required'),
        Validator.isRequired('#unit', 'Unit is required'),
        Validator.isRequired('#quantityInStock', 'Quantity in stock is required'),
    ],
    onSubmit: function (data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/material/store';
        // Form Data
        const { image, ...otherData } = data;
        const [imageFile] = image;

        const formData = new FormData();

        formData.append("image", imageFile);
        formData.append("data", new Blob(
            [
                JSON.stringify({
                    materialCategoryID: otherData.materialCategoryID,
                    name: otherData.name,
                    unit: otherData.unit,
                    quantityInStock: otherData.quantityInStock,
                })
            ],
            {
                type: "application/json"
            }
        ))

        // Fetch post
        fetch(storePath, {
            method: 'POST',
            credentials: 'same-origin',
            body: formData,
        })
            .then(res => res.json())
            .then(res => {
                if (res.status == 200) {
                    document.getElementById('close-create-modal-btn').click();
                    hideWarningAlert();
                    showSuccessAlert('Successfully create new material');
                    fetchTableData();
                    resetPreviewImage('#previewImg')
                    resetForm({
                        image: '',
                        materialCategoryID: otherData.materialCategoryID,
                        name: '',
                        unit: '',
                        quantityInStock: '0',
                    })
                } else if (res.status == 400 && res.invalid) {
                    showWarningAlert('Invalid some fields', res.errors);
                    document.getElementById('close-create-modal-btn').click();
                }
            })
            .catch(_err => { })
    }
});



// Validate update
Validator({
    form: '#update-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#materialIDEdit', 'Material ID is required'),
        Validator.isRequired('#materialCategoryIDEdit', 'Material category is required'),
        Validator.isRequired('#nameEdit', 'Name is required'),
        Validator.isRequired('#unitEdit', 'Unit is required'),
        Validator.isRequired('#quantityInStockEdit', 'Quantity in stock is required'),
    ],
    onSubmit: function (data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/material/update';
        // Form Data
        const { image, ...otherData } = data;
        const imageFile = image.length > 0 ? image[0] : null;

        const formData = new FormData();

        formData.append("image", imageFile);
        formData.append("data", new Blob(
            [
                JSON.stringify({
                    materialID: otherData.materialID,
                    materialCategoryID: otherData.materialCategoryID,
                    name: otherData.name,
                    unit: otherData.unit,
                    quantityInStock: otherData.quantityInStock,
                })
            ],
            {
                type: "application/json"
            }
        ))

        // Fetch post
        fetch(updatePath, {
            method: 'POST',
            credentials: 'same-origin',
            body: formData,
        })
            .then(res => res.json())
            .then(res => {
                if (res.status == 200) {
                    document.getElementById('close-update-modal-btn').click();
                    hideWarningAlert();
                    showSuccessAlert('Successfully update material data');
                    fetchTableData();
                    resetPreviewImage('#previewImgEdit')
                    resetForm({
                        materialID: '',
                        image: '',
                        materialCategoryID: otherData.materialCategoryID,
                        name: '',
                        unit: '',
                        quantityInStock: '0',
                    })
                } else if (res.status == 400 && res.invalid) {
                    showWarningAlert('Invalid some fields', res.errors);
                    document.getElementById('close-update-modal-btn').click();
                }
            })
            .catch(_err => { })
    }
});



// Handle preview image before upload
function previewImageBeforeUploadListener({ inputSelector, previewerSelector }) {
    const input = document.querySelector(inputSelector);
    const previewImg = document.querySelector(previewerSelector);

    input.onchange = () => {
        const [file] = input.files;
        if (file) {
            previewImg.src = URL.createObjectURL(file);
            previewImg.classList.remove('hidden')
        }
    }
}
previewImageBeforeUploadListener({
    inputSelector: '#image',
    previewerSelector: '#previewImg'
});
previewImageBeforeUploadListener({
    inputSelector: '#imageEdit',
    previewerSelector: '#previewImgEdit'
});
function resetPreviewImage(previewerSelector) {
    const previewImg = document.querySelector(previewerSelector);
    if (previewImg) {
        previewImg.src = ''
        previewImg.classList.add('hidden')
    }
}

// Handle fetch material category data
var materialCategoryName = []

function fetchMaterialCategoryName() {
    const fetchPath = window.APP_NAME + '/api/mat-category/all';
    fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                materialCategoryName = res.data;
                renderMaterialCategorySelects([
                    'materialCategoryID',
                    'materialCategoryIDEdit',
                ]);
                renderMatCatFilter();
                processClickChooseMatCat();
            }
        })
        .catch(_res => { })
}
fetchMaterialCategoryName();
function renderMaterialCategorySelects(selectsID) {
    selectsID.forEach(selectID => {
        document.getElementById(selectID).innerHTML =
            materialCategoryName.map(item => {
                return `
                        <option value="${item.materialCategoryID}">${item.name}</option>
                    `
            }).join('');
    })
}
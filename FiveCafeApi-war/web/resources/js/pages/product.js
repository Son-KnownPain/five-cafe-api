// Fetch table data

// function fetchTableData(searching = null) {
//     let fetchPath = window.APP_NAME;
//     if (searching) {
//         fetchPath += `/api/product/search?name=${searching.name}${searching.productCategoryID ? `&productCategoryID=${searching.productCategoryID}` : ""}`
//     } else {
//         fetchPath += '/api/product/all';
//     }
function fetchTableData(prop = {}) {
    const {
        detailClickID = null, searching = null } = prop;

    let fetchPath = window.APP_NAME;
    if (searching) {
        fetchPath += `/api/product/search?name=${searching.name}${searching.productCategoryID ? `&productCategoryID=${searching.productCategoryID}` : ""}`
    } else {
        fetchPath += '/api/product/all';
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
                                <input type="checkbox" name="delete-checkbox" value="${item.productID}" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                <img class="object-cover w-20 h-20 rounded" src="${item.image}" alt="Large avatar">
                            </th>
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                ${item.name}
                            </th>
                            <td class="px-6 py-4">
                                ${window.currencyOutput(item.price)}
                            </td>
                            <td class="px-6 py-4">
                                ${item.selling ? 'Selling' : 'Stop selling'}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a data-edit-id="${item.productID}" data-modal-target="update-modal" data-modal-toggle="update-modal" class="block cursor-pointer font-semibold text-yellow-500 dark:text-yellow-500 hover:text-yellow-300">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                                <a data-detail-id="${item.productID}" data-modal-target="detail-modal" data-modal-toggle="detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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
                        fetch(`${window.APP_NAME}/api/product/delete?ids=${checkedRowsToDelete.join(',')}`, {
                            method: 'DELETE',
                            credentials: 'same-origin',
                        })
                            .then(res => res.json())
                            .then(res => {
                                if (res.status == 200) {
                                    hideWarningAlert();
                                    showSuccessAlert('Successfully delete product');
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
                        const product = res.data.find(item => item.productID == btn.dataset.editId);

                        document.getElementById('productIDEdit').value = product.productID;
                        document.getElementById('previewImgEdit').src = product.image;
                        document.getElementById('previewImgEdit').classList.remove('hidden')
                        document.getElementById('productCategoryIDEdit').value = product.productCategoryID;
                        document.getElementById('nameEdit').value = product.name;
                        document.getElementById('priceEdit').value = product.price;
                        document.getElementById('isSellingEdit').checked = product.selling;
                    }
                })

                // Handle click view detail
                const detailBtns = document.querySelectorAll('a[data-detail-id]')
                Array.from(detailBtns).forEach(btn => {
                    btn.onclick = () => {
                        const product = res.data.find(item => item.productID == btn.dataset.detailId);

                        document.getElementById('productIDAdd').value = product.productID

                        document.getElementById("detail-image").src = product.image;
                        document.getElementById("detail-name").textContent = product.name;
                        document.getElementById("detail-price").textContent = product.price;
                        document.getElementById("detail-status").textContent = product.selling ? 'Selling' : 'Stop selling';
                        document.getElementById("detail-category").textContent = product.productCategoryName;

                        // Hiển thị mat to pro ở đây, code here
                        renderMatToPro(product.productID)
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

Validator({
    form: '#create-mtp-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#materialIDAdd', 'Material ID is required'),
        Validator.isRequired('#descriptionMTP', 'Description is required'),
    ],
    onSubmit: function (data, { resetForm }) {

        const storeMTP = window.APP_NAME + `/api/mat-to-pro/store`;
        fetch(storeMTP, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                productID: data.productID,
                materialID: data.materialID,
                description: data.description,
            }),
        })
            .then(res => res.json())
            .then(res => {
                if (res.status == 200) {
                    document.getElementById('cancel-add-mtp-btn').click();
                    hideWarningAlert();
                    showSuccessAlert(res.message);
                    showDetailSuccessAlert('Successfully create new material to product');
                    fetchMatToProData(data.productID);
                    resetForm({
                        description: '',
                    })
                } else if (res.status == 400 && res.invalid) {
                    showWarningAlert('Invalid some fields', res.errors);
                    document.getElementById('cancel-add-mtp-btn').click();
                }
            })
            .catch(_err => { })
    }
})

// Handle searching
function renderProCatFilter() {
    const items = [
        {
            productCategoryID: '',
            name: 'Tất cả danh mục',
        },
        ...productCategoryName,
    ]
    document.getElementById('procat-filter').innerHTML = items.map(item => {
        return `
            <li data-pro-id="${item.productCategoryID}" data-pro-name="${item.name}">
                <button type="button" class="inline-flex w-full px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">${item.name}</button>
            </li>
        `
    }).join('');
}

function processClickChooseProCat() {
    Array.from(document.querySelectorAll('li[data-pro-id]')).forEach(li => {
        li.onclick = () => {
            document.getElementById('productCategoryIDSearch').value = li.dataset.proId;
            document.getElementById('dropdown-button').innerHTML = `
                ${li.dataset.proName}
                <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                </svg>
            `
        }
    })
}
document.getElementById('search-form').onsubmit = e => {
    e.preventDefault();
    const productCategoryID = document.getElementById('productCategoryIDSearch').value;
    const name = document.getElementById('nameSearch').value;
    fetchTableData({
        productCategoryID: productCategoryID,
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



// Validate create
Validator({
    form: '#create-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#image', 'Image is required'),
        Validator.isRequired('#productCategoryID', 'Product category is required'),
        Validator.isRequired('#name', 'Name is required'),
        Validator.isRequired('#price', 'Price is required'),
    ],
    onSubmit: function (data, { resetForm }) {
        const storePath = window.APP_NAME + '/api/product/store';
        // Form Data
        const { image, ...otherData } = data;
        const [imageFile] = image;

        const formData = new FormData();

        formData.append("image", imageFile);
        formData.append("data", new Blob(
            [
                JSON.stringify({
                    productCategoryID: otherData.productCategoryID,
                    name: otherData.name,
                    price: otherData.price,
                    selling: typeof otherData.isSelling === 'string' ? false : otherData.isSelling.includes('yes'),
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
                    showSuccessAlert('Successfully create new product');
                    fetchTableData();
                    resetPreviewImage('#previewImg')
                    resetForm({
                        image: '',
                        productCategoryID: otherData.productCategoryID,
                        name: '',
                        price: '',
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
        Validator.isRequired('#productCategoryIDEdit', 'Product category is required'),
        Validator.isRequired('#nameEdit', 'Name is required'),
        Validator.isRequired('#priceEdit', 'Price is required'),
    ],
    onSubmit: function (data, { resetForm }) {
        const updatePath = window.APP_NAME + '/api/product/update';
        // Form Data
        const { image, ...otherData } = data;
        const imageFile = image.length > 0 ? image[0] : null;

        const formData = new FormData();

        formData.append("image", imageFile);
        formData.append("data", new Blob(
            [
                JSON.stringify({
                    productID: otherData.productID,
                    productCategoryID: otherData.productCategoryID,
                    name: otherData.name,
                    price: otherData.price,
                    selling: typeof otherData.isSelling === 'string' ? false : otherData.isSelling.includes('yes'),
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
                    showSuccessAlert('Successfully update product data');
                    fetchTableData();
                    resetPreviewImage('#previewImgEdit')
                    resetForm({
                        productID: '',
                        image: '',
                        productCategoryID: otherData.productCategoryID,
                        name: '',
                        price: '',
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

// Handle fetch product category data
var productCategoryName = []

function fetchProductCategoryName() {
    const fetchPath = window.APP_NAME + '/api/pro-category/all';
    fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                productCategoryName = res.data;
                renderProductCategorySelects([
                    'productCategoryID',
                    'productCategoryIDEdit',
                ]);
                renderProCatFilter();
                processClickChooseProCat();
            }
        })
        .catch(_res => { })
}
fetchProductCategoryName();

// Fetch mat to pro data
var matToProData = []

function fetchMatToProData(productID = null) {
    const fetchPath = window.APP_NAME + '/api/mat-to-pro/all';
    fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                matToProData = res.data;
                if (productID) {
                    renderMatToPro(productID)
                }
            }
        })
        .catch(_res => { })

}
fetchMatToProData();



function fetchMats(selectsID) {
    const fetchPath = window.APP_NAME + '/api/material/all';
    fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
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
fetchMats([
    'materialIDAdd'
]);

var matToPro = [];
function insertMTPItem({ materialID, description }) {
    matToPro.push({
        materialID: materialID,
        description: description,
    })
    renderMatToPro();
}


/////Render mat to pro 
function renderMatToPro(productID) {
    document.getElementById("mat-to-pro").innerHTML =
        matToProData.filter(mtp => mtp.productID == productID).map(mtp => `
            <div class="col-span-1 flex items-center gap-4">
                <img id="detail-image-mat" class="w-12 h-12 rounded object-cover"
                    src="${mtp.image}">
                <div class="flex flex-col justify-between px-4 leading-normal">
                <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">
                    <div id="detail-name-mat" class="text-xs">${mtp.materialName}
                        <button data-delete-mtp-id="${mtp.materialID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                        <i class="fa-regular fa-trash-can"></i>
                            </button>

                        <div data-confirmation-toggle-id="${mtp.materialID}" class="hidden">
                            <button data-yes-dc-mat-id="${mtp.materialID}" class="text-cyan-700 border border-cyan-700 hover:bg-cyan-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-cyan-500 dark:text-cyan-500 dark:hover:text-white dark:focus:ring-cyan-800 dark:hover:bg-cyan-500">
                                <i class="fa-solid fa-check"></i>
                            </button>
                            <button data-no-dc-mat-id="${mtp.materialID}" class="ml-2 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800 dark:hover:bg-red-500">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        </div>
                    </h5>
                    <div class="text-xs text-gray-500 dark:text-gray-400 mt-2">
                        <span>Description:</span>
                        <span id="detail-des">${mtp.description}</span>
                    </div>
                </div>
            </div>
        `).join('')

    //Add material to product:
    const addMTPBtn = document.getElementById('add-mtp-btn');

    addMTPBtn.onclick = () => {
        document.getElementById('add-mtp-btn').classList.add('hidden');
        document.getElementById('detail-add-form').classList.remove('hidden');
    };

    // Handle click "Cancel" trong form Add Material
    document.getElementById('cancel-add-mtp-btn').onclick = () => {
        document.getElementById('detail-add-form').classList.add('hidden');
        document.getElementById('add-mtp-btn').classList.remove('hidden');
    };

    // //Delete mat to pro:
    const deleteMTPBtns = document.querySelectorAll('button[data-delete-mtp-id]');
    Array.from(deleteMTPBtns).forEach(btn => {
        btn.onclick = () => {
            const matID = btn.dataset.deleteMtpId;

            // Hide trash icon btn
            btn.classList.add('hidden')
            // Show confirmation
            document.querySelector(`div[data-confirmation-toggle-id='${matID}']`).classList.remove('hidden')

            // When click yes
            document.querySelector(`button[data-yes-dc-mat-id='${matID}']`).onclick = () => {
                const deleteMTPPath = `${window.APP_NAME}/api/mat-to-pro/delete?productID=${productID}&materialID=${matID}`;
                fetch(deleteMTPPath, {
                    method: 'DELETE',
                    credentials: 'same-origin',
                })
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            hideWarningAlert();
                            showSuccessAlert(res.message);
                            showDetailSuccessAlert("Delete success")
                            fetchMatToProData(productID)
                        } else if (res.status == 400 && res.invalid) {
                            showWarningAlert('Invalid some fields', res.errors);
                            document.getElementById('close-mtp-detail-modal-btn').click();
                        }
                    })
                    .catch(_res => { })
            }
            // When click no
            document.querySelector(`button[data-no-dc-mat-id='${matID}']`).onclick = () => {
                // Show trash icon btn
                document.querySelector(`button[data-delete-mtp-id='${matID}']`).classList.remove('hidden')
                // Hide confirmation
                document.querySelector(`div[data-confirmation-toggle-id='${matID}']`).classList.add('hidden')
            }
        }
    });
}


function renderProductCategorySelects(selectsID) {
    selectsID.forEach(selectID => {
        document.getElementById(selectID).innerHTML =
            productCategoryName.map(item => {
                return `
                        <option value="${item.productCategoryID}">${item.name}</option>
                    `
            }).join('');
    })
}

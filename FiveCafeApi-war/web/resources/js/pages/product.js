// Fetch table data
function fetchTableData() {
    const fetchPath = window.APP_NAME + '/api/product/all';
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
                                ${item.price}
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

                    document.getElementById("detail-image").src = product.image;
                    document.getElementById("detail-name").textContent = product.name;
                    document.getElementById("detail-price").textContent = product.price;
                    document.getElementById("detail-status").textContent = product.selling ? 'Selling' : 'Stop selling';
                    document.getElementById("detail-category").textContent = product.productCategoryName;
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
    onSubmit: function(data, { resetForm }) {
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
        .catch(_err => {})
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
    onSubmit: function(data, { resetForm }) {
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
        .catch(_err => {})
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

// Handle fetch role data
function fetchProductCategories(selectsID) {
    const fetchPath = window.APP_NAME + '/api/pro-category/all';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            selectsID.forEach(selectID => {
                document.getElementById(selectID).innerHTML = 
                res.data.map(item => {
                    return `
                        <option value="${item.productCategoryID}">${item.name}</option>
                    `
                }).join('');
            })
        }
    })
    .catch(_res => { })
}
fetchProductCategories([
    'productCategoryID',
    'productCategoryIDEdit',
]);
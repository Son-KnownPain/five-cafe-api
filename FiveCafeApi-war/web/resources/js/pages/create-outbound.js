var products = [];

function fetchProducts({ searching }) {
    let fetchPath = window.APP_NAME + '/api/material/all';
    if (searching) {
        fetchPath = window.APP_NAME + `/api/material/search?name=${searching.name}`;
    } else {
        fetchPath = window.APP_NAME + '/api/material/all';
    }
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            products = res.data;
            renderProducts();
        }
    })
    .catch(_res => { })
}
fetchProducts({});

function renderProducts() {
    const chosenProductIDs = chosenProducts.map(x => x.materialID);
    document.getElementById('search-product-result').innerHTML = 
    products.filter(x => !chosenProductIDs.includes(x.materialID)).map(item => {
        return `
            <div class="flex gap-4">
                <img id="detail-image" class="w-32 h-32 rounded object-cover" src="${item.image}" alt="">
                <div class="font-medium dark:text-white">
                    <div class="text-gray-500 dark:text-white">${item.name}</div>
                    <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            Unit:
                        </span>
                        <span>${item.unit}</span>
                    </div>
                    <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            Quantity in stock:
                        </span>
                        <span>${item.quantityInStock}</span>
                    </div>
                    <div class="mt-2">
                        <button data-product-id="${item.materialID}" type="button" class="text-white bg-[#3b5998] hover:bg-[#3b5998]/90 focus:ring-4 focus:outline-none focus:ring-[#3b5998]/50 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#3b5998]/55">
                            <i class="fa-solid fa-arrow-left mr-2"></i>
                            Add to outbound
                        </button>
                    </div>
                </div>
            </div>
        `
    }).join('');

    const addToBillBtns = document.querySelectorAll('button[data-product-id]');
    Array.from(addToBillBtns).forEach(btn => {
        btn.onclick = () => {
            const materialID = btn.dataset.productId;
            const product = products.find(x => x.materialID == materialID);

            insertChosenProduct({
                quantity: 1,
                ...product
            })
        }
    })
}

var chosenProducts = [];

function insertChosenProduct(data) {
    chosenProducts.push(data);
    renderChooseProducts();
    renderProducts();
}

function removeChosenProduct(materialID) {
    chosenProducts = chosenProducts.filter(x => x.materialID != materialID);
    renderChooseProducts();
    renderProducts();
}

function renderChooseProducts() {
    document.getElementById('chosen-product').innerHTML = chosenProducts.map(product => `
        <div class="flex gap-4 mt-2">
            <img id="detail-image" class="w-32 h-32 rounded object-cover" src="${product.image}" alt="">
            <div class="font-medium dark:text-white">
                <div class="text-gray-500 dark:text-white">${product.name}</div>
                <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            Unit:
                        </span>
                        <span>${product.unit}</span>
                    </div>
                    <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            Quantity in stock:
                        </span>
                        <span>${product.quantityInStock}</span>
                    </div>
                <div class="mt-2">
                    <button data-remove-id="${product.materialID}" type="button" class="text-white bg-red-700 hover:bg-red-500 focus:ring-4 focus:outline-none focus:ring-red-500 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-red-500">
                        <i class="fa-regular fa-trash-can mr-2"></i>
                        Remove from outbound
                    </button>
                    <div class="mt-2">
                        <label for="quantity-${product.materialID}" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                        <input data-quantity-input="${product.materialID}" value="${product.quantity}" type="number" id="quantity-${product.materialID}" name="quantity-${product.materialID}" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Quantity">
                    </div>
                </div>
            </div>
        </div>
    `).join('')

    // Handle quantity change
    const quantityInputs = document.querySelectorAll('input[data-quantity-input]');
    Array.from(quantityInputs).forEach(input => {
        input.oninput = e => {
            const materialID = input.dataset.quantityInput;
            
            const product = chosenProducts.find(x => x.materialID == materialID);

            product.quantity = e.target.value;
        }
    })

    const removeBtns = document.querySelectorAll('button[data-remove-id]');
    Array.from(removeBtns).forEach(btn => {
        btn.onclick = () => {
            const materialIDToRemove = btn.dataset.removeId;

            removeChosenProduct(materialIDToRemove)
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

// Validate order form
Validator({
    form: '#create-outbound-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [],
    onSubmit: function() {
        let details = [];

        details = chosenProducts.map(item => ({
            materialID: item.materialID,
            quantity: item.quantity,
        }))

        const orderingPath = window.APP_NAME + '/api/employee/create-outbound';
        fetch(orderingPath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                details,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                hideWarningAlert();
                showSuccessAlert('Successfully create outbound');
                fetchTableData();
            } else if (res.status == 400 && res.invalid) {
                showWarningAlert('Invalid some fields', res.errors);
            }
        })
        .catch(_err => {})
    }
});

// Validate search
Validator({
    form: '#search-product-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [],
    onSubmit: function(data) {
        fetchProducts({
            searching: {
                name: data.name,
            }
        })
    }
});
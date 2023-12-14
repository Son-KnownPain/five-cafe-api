var products = [];

function fetchProducts({ searching }) {
    let fetchPath = window.APP_NAME + '/api/product/all';
    if (searching) {
        fetchPath = window.APP_NAME + `/api/product/search?name=${searching.name}`;
    } else {
        fetchPath = window.APP_NAME + '/api/product/all';
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
    const chosenProductIDs = chosenProducts.map(x => x.productID);
    document.getElementById('search-product-result').innerHTML = 
    products.filter(x => !chosenProductIDs.includes(x.productID)).map(item => {
        return `
            <div class="flex gap-4">
                <img id="detail-image" class="w-32 h-32 rounded object-cover" src="${item.image}" alt="">
                <div class="font-medium dark:text-white">
                    <div class="text-gray-500 dark:text-white">${item.name}</div>
                    <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            <i class="fa-solid fa-money-bill"></i>
                        </span>
                        <span>${window.currencyOutput(item.price)}</span>
                    </div>
                    <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                        <span class="mr-1">
                            <i class="fa-solid fa-list"></i>
                            Category:
                        </span>
                        <span>${item.productCategoryName}</span>
                    </div>
                    <div class="mt-2">
                        <button data-product-id="${item.productID}" type="button" class="text-white bg-[#3b5998] hover:bg-[#3b5998]/90 focus:ring-4 focus:outline-none focus:ring-[#3b5998]/50 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#3b5998]/55">
                            <i class="fa-solid fa-arrow-left mr-2"></i>
                            Add to bill
                        </button>
                    </div>
                </div>
            </div>
        `
    }).join('');

    const addToBillBtns = document.querySelectorAll('button[data-product-id]');
    Array.from(addToBillBtns).forEach(btn => {
        btn.onclick = () => {
            const productID = btn.dataset.productId;
            const product = products.find(x => x.productID == productID);

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

function removeChosenProduct(productID) {
    chosenProducts = chosenProducts.filter(x => x.productID != productID);
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
                        <i class="fa-solid fa-money-bill"></i>
                    </span>
                    <span>${window.currencyOutput(product.price)}</span>
                </div>
                <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                    <span class="mr-1">
                        <i class="fa-solid fa-list"></i>
                        Category:
                    </span>
                    <span>${product.productCategoryName}</span>
                </div>
                <div class="mt-2">
                    <button data-remove-id="${product.productID}" type="button" class="text-white bg-red-700 hover:bg-red-500 focus:ring-4 focus:outline-none focus:ring-red-500 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-red-500">
                        <i class="fa-regular fa-trash-can mr-2"></i>
                        Remove from bill
                    </button>
                    <div class="mt-2">
                        <label for="quantity-${product.productID}" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Quantity</label>
                        <input data-quantity-input="${product.productID}" value="${product.quantity}" type="number" id="quantity-${product.productID}" name="quantity-${product.productID}" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Quantity">
                    </div>
                </div>
            </div>
        </div>
    `).join('')

    // Handle quantity change
    const quantityInputs = document.querySelectorAll('input[data-quantity-input]');
    Array.from(quantityInputs).forEach(input => {
        input.oninput = e => {
            const productID = input.dataset.quantityInput;
            
            const product = chosenProducts.find(x => x.productID == productID);

            product.quantity = e.target.value;
        }
    })

    const removeBtns = document.querySelectorAll('button[data-remove-id]');
    Array.from(removeBtns).forEach(btn => {
        btn.onclick = () => {
            const productIDToRemove = btn.dataset.removeId;

            removeChosenProduct(productIDToRemove)
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
    form: '#ordering-form',
    formGroup: '.form-gr',
    errorSelector: '.form-message',
    rules: [
        Validator.isRequired('#billStatusID', 'Bill status is required'),
        Validator.isRequired('#cardCode', 'Card code is required'),
    ],
    onSubmit: function(data, { resetForm }) {
        let details = [];

        details = chosenProducts.map(item => ({
            productID: item.productID,
            quantity: item.quantity,
        }))

        const orderingPath = window.APP_NAME + '/api/employee/ordering';
        fetch(orderingPath, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                billStatusID: data.billStatusID,
                cardCode: data.cardCode,
                details,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                hideWarningAlert();
                showSuccessAlert('Successfully ordering');
                fetchTableData();
                resetForm({
                    cardCode: '',
                })
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
    'billStatusID',
]);
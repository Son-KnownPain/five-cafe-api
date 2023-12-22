var products = [];

function fetchProducts({ searching }) {
    let fetchPath
    if (searching) {
        fetchPath = window.APP_NAME + `/api/product/search?name=${searching.name}&selling`;
    } else {
        fetchPath = window.APP_NAME + '/api/product/all?selling';
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

function calculateTotalPrice() {
    const total = document.getElementById('total-price-val');

    total.textContent = window.currencyOutput(chosenProducts.reduce((acc, cur) => acc + cur.price * cur.quantity, 0))
}

function resetChosenProduct() {
    chosenProducts = [];
    renderChooseProducts();
    renderProducts();
}

function renderChooseProducts() {
    document.getElementById('chosen-product').innerHTML = !chosenProducts?.length 
    ? '<h1 class="text-base font-normal text-center text-gray-900 dark:text-white">Choose product to order</h1>' 
    : chosenProducts.map(product => `
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

    calculateTotalPrice();

    // Handle quantity change
    const quantityInputs = document.querySelectorAll('input[data-quantity-input]');
    Array.from(quantityInputs).forEach(input => {
        input.oninput = e => {
            const productID = input.dataset.quantityInput;
            
            const product = chosenProducts.find(x => x.productID == productID);

            product.quantity = e.target.value;

            calculateTotalPrice();
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
                cardCode: data.cardCode,
                details,
            })
        })
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                hideWarningAlert();
                showSuccessAlert('Successfully ordering');
                resetForm({
                    cardCode: '',
                })
                resetChosenProduct();
                fetchNotServedBills();
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


// NOT SERVED BILLS
function fetchNotServedBills() {
    const fetchPath = window.APP_NAME + '/api/employee/not-served-bills'
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('not-served-bills-table').innerHTML = res.data.reverse().map(bill => `
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                    <td class="px-6 py-4">
                        ${bill.createDate}
                    </td>
                    <td class="px-6 py-4">
                        <span class="p-2 text-white rounded-full border border-white w-8 h-8 flex items-center justify-center">
                            ${bill.cardCode}
                        </span>
                    </td>
                    <td class="px-6 py-4 text-right">
                        <button data-served-bill-id="${bill.billID}" class="relative inline-flex items-center justify-center p-0.5 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-green-400 to-blue-600 group-hover:from-green-400 group-hover:to-blue-600 hover:text-white dark:text-white focus:ring-4 focus:outline-none focus:ring-green-200 dark:focus:ring-green-800">
                            <span class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                                Serve
                            </span>
                        </button>
                    </td>
                </tr>
            `).join('');

            // Handle click served
            Array.from(document.querySelectorAll('button[data-served-bill-id]')).forEach(btn => {
                btn.onclick = () => {
                    const billID = btn.dataset.servedBillId;

                    fetch(`${window.APP_NAME}/api/employee/served-bill?billID=${billID}`)
                    .then(res => res.json())
                    .then(res => {
                        if (res.status == 200) {
                            btn.innerHTML = `
                                <span class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                                    <i class="fa-solid fa-circle-check text-xl text-green-500"></i>
                                </span>
                            `
                        }
                    })
                }
            })
        }
    })
}
fetchNotServedBills();
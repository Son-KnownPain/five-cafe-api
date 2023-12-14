function fetchTableData() {
    const fetchPath = window.APP_NAME + '/api/material/get-the-quantityinstock-below-five';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('product-out-quantityinstock').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                    <div class="p-4 md:p-5 space-y-4">
                        <div class="flex items-center gap-4">
                            <img class="w-32 h-32 rounded object-cover" src="${item.image}" alt="">
                            <div class="font-medium dark:text-white">
                                <div id="detail-name">${item.name}</div>
                                <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                                    <span>Unit: ${item.unit}</span>
                                </div>
                                <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                                    <span>quantityInStock: ${item.quantityInStock}</span>
                                </div>
                                <div class="text-sm text-gray-500 dark:text-gray-400 mt-2">
                                    <span>Material category: ${item.materialCategoryName}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    `
                }
            ).join('');
        }
    })
    .catch(_res => { })
}
fetchTableData();
function fetchTableData() {
    const fetchPath = window.APP_NAME + '/api/material/get-the-quantityinstock-below-five';
    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            if (res.data?.length) {
                document.getElementById('product-out-quantityinstock-content').innerHTML = 
                    res.data.reverse().map(item => {
                        return `
                            <div class="mt-3 bg-slate-300 p-2 rounded-md text-base font-normal">
                                "${item.name}" material with quantity in stock is ${item.quantityInStock} ${item.unit}
                            </div>
                        `
                    }
                ).join('');
            } else {
                document.getElementById('product-out-quantityinstock-box').classList.add('hidden')
            }
        }
    })
    .catch(_res => { })
}
fetchTableData();
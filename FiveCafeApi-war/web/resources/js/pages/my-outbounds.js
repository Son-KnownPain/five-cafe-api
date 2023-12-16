// Fetch table data
function fetchTableData() {
    fetchPath = window.APP_NAME + '/api/employee/all-my-outbounds';

    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                    <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                            ${item.name}
                        </th>
                        <td class="px-6 py-4">
                            ${item.date}
                        </td>
                        <td class="px-6 py-4 text-right">
                            <a title="View detail" data-detail-id="${item.outboundID}" data-modal-target="outbound-detail-modal" data-modal-toggle="outbound-detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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
            Array.from(detailBtns).forEach(btn => {
                btn.onclick = () => {
                    const outboundItem = res.data.find(item => item.outboundID == btn.dataset.detailId);

                    document.getElementById('outbound-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Outbound ID: <span class="font-bold dark:text-white">${outboundItem.outboundID}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Outbound Date: <span class="font-bold dark:text-white">${outboundItem.date}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee: <span class="font-bold dark:text-white">${outboundItem.name}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = outboundItem.details.map(detail => `
                        <div class="flex py-2">
                            <img class="object-cover rounded w-32 h-32" src="${detail.materialImage}" alt="Material">
                            <div class="flex flex-col px-4 leading-normal">
                                <h5 class="mb-2 text-base font-semibold tracking-tight text-gray-900 dark:text-white">
                                    ${detail.materialName}
                                </h5>
                                <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">Quantity: ${detail.quantity} ${detail.unit}</p>
                            </div>
                        </div>
                    `).join('')
                }
            })
        }
    })
    .catch(_res => { })
}
fetchTableData();
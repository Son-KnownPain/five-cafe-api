// Fetch table data
function fetchTableData() {
    fetchPath = window.APP_NAME + '/api/employee/all-my-salaries';

    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <td class="px-6 py-4">
                                ${item.date}
                            </td>
                            <td class="px-6 py-4">
                                ${window.currencyOutput(item.details.reduce((acc, cur) => acc + cur.salary * (1 + cur.bonus / 100) - cur.deduction, 0))}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a title="View detail" data-detail-id="${item.employeeSalaryID}" data-modal-target="detail-modal" data-modal-toggle="detail-modal" class="mt-2 block cursor-pointer font-semibold text-blue-500 dark:text-blue-500 hover:text-blue-300">
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
                    const salaryItem = res.data.find(item => item.employeeSalaryID == btn.dataset.detailId);

                    document.getElementById('salary-info').innerHTML = `
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee Salary ID: <span class="font-bold dark:text-white text-black">${salaryItem.employeeSalaryID}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Created Date: <span class="font-bold dark:text-white text-black">${salaryItem.date}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Employee: <span class="font-bold dark:text-white text-black">${salaryItem.employeeName}</span></p>
                        <p class="text-base font-normal mb-2 dark:text-gray-400 text-gray-700">Total salary: <span class="font-bold dark:text-white text-black">${window.currencyOutput(salaryItem.details.reduce((acc, cur) => acc + cur.salary * (1 + cur.bonus / 100) - cur.deduction, 0))}</span></p>
                    `

                    document.getElementById("details-content").innerHTML = salaryItem.details.reverse().map(detail => `
                        <div class="max-w-sm mb-1 p-4 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
                            <a>
                                <h5 class="mb-1 text-base font-bold tracking-tight text-gray-900 dark:text-white">${detail.shiftName}</h5>
                            </a>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-money-bill-wave text-yellow-500 mr-1"></i>
                                Base: ${window.currencyOutput(detail.salary)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-plus text-green-500 mr-1"></i>
                                Bonus: ${detail.bonus}%
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-solid fa-minus text-red-500 mr-1"></i>
                                Deduction: ${window.currencyOutput(detail.deduction)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-regular fa-money-bill-1 text-yellow-700 mr-1"></i>
                                Total: ${window.currencyOutput(detail.salary * (1 + detail.bonus / 100) - detail.deduction)}
                            </p>
                            <p class="mb-1 text-sm font-normal text-gray-700 dark:text-gray-400">
                                <i class="fa-regular fa-calendar text-blue-700 mr-1"></i>
                                Timekeeping Date: ${detail.timeKeepingDate}
                            </p>
                        </div>
                    `).join('')
                }
            })
        }
    })
    .catch(_res => { })
}
fetchTableData();

// WEBSOCKET

var wsURI = "wss://" + document.location.host + "/FiveCafeApi-war/salary";

var websocket = new WebSocket(wsURI);

websocket.onmessage = function(event) {
    if (event.data == 'refetch') {
        fetchTableData()
    }
}
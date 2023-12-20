// Fetch table data
function fetchTableData() {
    fetchPath = window.APP_NAME + '/api/employee/all-my-etk';

    fetch(fetchPath)
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            document.getElementById('table-body').innerHTML = 
                res.data.reverse().map(item => {
                    return `
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <td class="px-6 py-4">
                                ${item.timeKeepingID}
                            </td>
                            <td class="px-6 py-4">
                                ${item.shiftName}
                            </td>
                            <td class="px-6 py-4">
                                ${item.date}
                            </td>
                            <td class="px-6 py-4">
                                ${window.currencyOutput(item.salary)}
                            </td>
                            <td class="px-6 py-4">
                                ${item.paid ? "Yes" : "No"}
                            </td>
                        </tr>
                    `
                }).join('');
        }
    })
    .catch(_res => { })
}
fetchTableData();
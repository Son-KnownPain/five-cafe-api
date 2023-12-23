// ApexCharts options and config
window.addEventListener("load", function () {
    function renderRevenueByDaysChart(lastDays = 3) {
        const chartElm = document.getElementById('revenue-by-days-chart');

        const fetchPath = window.APP_NAME + `/api/statistic/revenue?last-days=${lastDays}`;

        fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                const revenueResponse = res.data;

                let options = {
                    // set the labels option to true to show the labels on the X and Y axis
                    xaxis: {
                    show: true,
                    categories: Array.from(revenueResponse).map(item => item.date),
                    labels: {
                        show: true,
                        style: {
                        fontFamily: "'Noto Sans', sans-serif",
                        cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400'
                        }
                    },
                    axisBorder: {
                        show: false,
                    },
                    axisTicks: {
                        show: false,
                    },
                    },
                    yaxis: {
                    show: true,
                    labels: {
                        show: true,
                        style: {
                        fontFamily: "Inter, sans-serif",
                        cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400'
                        },
                        formatter: function (value) {
                            return window.currencyOutput(value);
                        }
                    }
                    },
                    series: [
                        {
                            name: "Revenue",
                            data: Array.from(revenueResponse).map(item => item.revenue),
                            color: "#1A56DB",
                        },
                    ],
                    chart: {
                        sparkline: {
                            enabled: false
                        },
                        height: "100%",
                        width: "100%",
                        type: "area",
                        fontFamily: "Inter, sans-serif",
                        dropShadow: {
                            enabled: false,
                        },
                        toolbar: {
                            show: false,
                        },
                    },
                    tooltip: {
                        enabled: true,
                        x: {
                            show: false,
                        },
                        y: {
                            formatter: function (val) {
                                return window.currencyOutput(val);
                            }
                        },
                    },
                    fill: {
                        type: "gradient",
                        gradient: {
                            opacityFrom: 0.55,
                            opacityTo: 0,
                            shade: "#1C64F2",
                            gradientToColors: ["#1C64F2"],
                        },
                    },
                    dataLabels: {
                        enabled: false,
                    },
                    stroke: {
                        width: 6,
                    },
                    legend: {
                        show: false
                    },
                    grid: {
                        show: false,
                    },
                }
            
                if (chartElm && typeof ApexCharts !== 'undefined') {
                    const chart = new ApexCharts(chartElm, options);
                    chart.render();

                    // Handle change last days
                    Array.from(document.querySelectorAll('a[data-revenue-by-days]')).forEach(btn => {
                        btn.onclick = () => {
                            document.getElementById('revenueByDaysDropdown').innerHTML = `
                                ${btn.textContent}
                                <svg class="w-2.5 m-2.5 ms-1.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                                </svg>
                            `;
                            const fetchPath = window.APP_NAME + `/api/statistic/revenue?last-days=${btn.dataset.lastDays}`;
                            fetch(fetchPath)
                            .then(res => res.json())
                            .then(res => {
                                if (res.status == 200) {
                                    const revenueResponse = res.data;

                                    chart.updateOptions(
                                        {
                                            xaxis: {
                                                categories: Array.from(revenueResponse).map(item => item.date),
                                            },
                                            series: [
                                                {
                                                    data: Array.from(revenueResponse).map(item => item.revenue),
                                                },
                                            ],
                                        }
                                    );

                                    document.getElementById('total-revenue-by-days').textContent = window.currencyOutput(Array.from(revenueResponse).reduce((acc, cur) => acc + cur.revenue, 0));
                                }
                            })
                        }
                    });

                    // Handle filter days
                    document.getElementById('revenueByDaysFilterBtn').onclick = () => {
                        const startDate = document.getElementById('revenueByDaysStartDate').value;
                        const endDate = document.getElementById('revenueByDaysEndDate').value;

                        const fetchPath = window.APP_NAME + `/api/statistic/revenue?startDate=${startDate}&endDate=${endDate}`;
                        fetch(fetchPath)
                        .then(res => res.json())
                        .then(res => {
                            if (res.status == 200) {
                                const revenueResponse = res.data;

                                chart.updateOptions(
                                    {
                                        xaxis: {
                                            categories: Array.from(revenueResponse).map(item => item.date),
                                        },
                                        series: [
                                            {
                                                data: Array.from(revenueResponse).map(item => item.revenue),
                                            },
                                        ],
                                    }
                                );

                                document.getElementById('total-revenue-by-days').textContent = window.currencyOutput(Array.from(revenueResponse).reduce((acc, cur) => acc + cur.revenue, 0));
                            }
                        })
                    }
                }

                document.getElementById('total-revenue-by-days').textContent = window.currencyOutput(Array.from(revenueResponse).reduce((acc, cur) => acc + cur.revenue, 0));
            }
        })
    }
    renderRevenueByDaysChart();

    function renderCostByDaysChart(lastDays = 3) {
        const chartElm = document.getElementById('cost-by-days-chart');

        const fetchPath = window.APP_NAME + `/api/statistic/cost?last-days=${lastDays}`;

        fetch(fetchPath)
        .then(res => res.json())
        .then(res => {
            if (res.status == 200) {
                const costResponse = res.data;

                let options = {
                    // set the labels option to true to show the labels on the X and Y axis
                    xaxis: {
                    show: true,
                    categories: Array.from(costResponse).map(item => item.date),
                    labels: {
                        show: true,
                        style: {
                        fontFamily: "Inter, sans-serif",
                        cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400'
                        }
                    },
                    axisBorder: {
                        show: false,
                    },
                    axisTicks: {
                        show: false,
                    },
                    },
                    yaxis: {
                    show: true,
                    labels: {
                        show: true,
                        style: {
                        fontFamily: "Inter, sans-serif",
                        cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400'
                        },
                        formatter: function (value) {
                            return window.currencyOutput(value);
                        }
                    }
                    },
                    series: [
                        {
                            name: "Cost",
                            data: Array.from(costResponse).map(item => item.cost),
                            color: "#1A56DB",
                        },
                    ],
                    chart: {
                        sparkline: {
                            enabled: false
                        },
                        height: "100%",
                        width: "100%",
                        type: "area",
                        fontFamily: "Inter, sans-serif",
                        dropShadow: {
                            enabled: false,
                        },
                        toolbar: {
                            show: false,
                        },
                    },
                    tooltip: {
                        enabled: true,
                        x: {
                            show: false,
                        },
                        y: {
                            formatter: function (val) {
                                return window.currencyOutput(val);
                            }
                        },
                    },
                    fill: {
                        type: "gradient",
                        gradient: {
                            opacityFrom: 0.55,
                            opacityTo: 0,
                            shade: "#1C64F2",
                            gradientToColors: ["#1C64F2"],
                        },
                    },
                    dataLabels: {
                        enabled: false,
                    },
                    stroke: {
                        width: 6,
                    },
                    legend: {
                        show: false
                    },
                    grid: {
                        show: false,
                    },
                }
            
                if (chartElm && typeof ApexCharts !== 'undefined') {
                    const chart = new ApexCharts(chartElm, options);
                    chart.render();

                    // Handle change last days
                    Array.from(document.querySelectorAll('a[data-cost-by-days]')).forEach(btn => {
                        btn.onclick = () => {
                            document.getElementById('costDayChoiceDropdown').innerHTML = `
                                ${btn.textContent}
                                <svg class="w-2.5 m-2.5 ms-1.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                                </svg>
                            `;
                            const fetchPath = window.APP_NAME + `/api/statistic/cost?last-days=${btn.dataset.lastDays}`;
                            fetch(fetchPath)
                            .then(res => res.json())
                            .then(res => {
                                if (res.status == 200) {
                                    const costResponse = res.data;

                                    chart.updateOptions(
                                        {
                                            xaxis: {
                                                categories: Array.from(costResponse).map(item => item.date),
                                            },
                                            series: [
                                                {
                                                    data: Array.from(costResponse).map(item => item.cost),
                                                },
                                            ],
                                        }
                                    );

                                    document.getElementById('total-cost-by-days').textContent = window.currencyOutput(Array.from(costResponse).reduce((acc, cur) => acc + cur.cost, 0));
                                }
                            })
                        }
                    });

                    // Handle filter days
                    document.getElementById('costByDaysFilterBtn').onclick = () => {
                        const startDate = document.getElementById('costByDaysStartDate').value;
                        const endDate = document.getElementById('costByDaysEndDate').value;

                        const fetchPath = window.APP_NAME + `/api/statistic/cost?startDate=${startDate}&endDate=${endDate}`;
                        fetch(fetchPath)
                        .then(res => res.json())
                        .then(res => {
                            if (res.status == 200) {
                                const costResponse = res.data;

                                chart.updateOptions(
                                    {
                                        xaxis: {
                                            categories: Array.from(costResponse).map(item => item.date),
                                        },
                                        series: [
                                            {
                                                data: Array.from(costResponse).map(item => item.cost),
                                            },
                                        ],
                                    }
                                );

                                document.getElementById('total-cost-by-days').textContent = window.currencyOutput(Array.from(costResponse).reduce((acc, cur) => acc + cur.cost, 0));
                            }
                        })
                    }
                }

                document.getElementById('total-cost-by-days').textContent = window.currencyOutput(Array.from(costResponse).reduce((acc, cur) => acc + cur.cost, 0));
            }
        })
    }
    renderCostByDaysChart();
});


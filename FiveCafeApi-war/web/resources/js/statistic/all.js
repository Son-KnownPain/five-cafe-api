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

                // Render chart in here
                let options = {
                    chart: {
                        height: "100%",
                        maxWidth: "100%",
                        type: "area",
                        fontFamily: "'Noto Sans', sans-serif",
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
                    grid: {
                        show: false,
                        strokeDashArray: 4,
                        padding: {
                            left: 2,
                            right: 2,
                            top: 0
                        },
                    },
                    series: [
                        {
                            name: "Revenue",
                            data: Array.from(revenueResponse).map(item => item.revenue),
                            color: "#1A56DB",
                        },
                    ],
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
                    xaxis: {
                        categories: Array.from(revenueResponse).map(item => item.date),
                        labels: {
                            show: false,
                        },
                        axisBorder: {
                            show: false,
                        },
                        axisTicks: {
                            show: false,
                        },
                    },
                    yaxis: {
                        show: false,
                    },
                }
            
                if (chartElm && typeof ApexCharts !== 'undefined') {
                    const chart = new ApexCharts(chartElm, options);
                    chart.render();

                    // Handle change last days
                    Array.from(document.querySelectorAll('a[data-revenue-by-days]')).forEach(btn => {
                        btn.onclick = () => {
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

                // Render chart in here
                let options = {
                    chart: {
                        height: "100%",
                        maxWidth: "100%",
                        type: "area",
                        fontFamily: "'Noto Sans', sans-serif",
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
                    grid: {
                        show: false,
                        strokeDashArray: 4,
                        padding: {
                            left: 2,
                            right: 2,
                            top: 0
                        },
                    },
                    series: [
                        {
                            name: "Cost",
                            data: Array.from(costResponse).map(item => item.cost),
                            color: "#1A56DB",
                        },
                    ],
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
                    xaxis: {
                        categories: Array.from(costResponse).map(item => item.date),
                        labels: {
                            show: false,
                        },
                        axisBorder: {
                            show: false,
                        },
                        axisTicks: {
                            show: false,
                        },
                    },
                    yaxis: {
                        show: false,
                    },
                }
            
                if (chartElm && typeof ApexCharts !== 'undefined') {
                    const chart = new ApexCharts(chartElm, options);
                    chart.render();

                    // Handle change last days
                    Array.from(document.querySelectorAll('a[data-cost-by-days]')).forEach(btn => {
                        btn.onclick = () => {
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


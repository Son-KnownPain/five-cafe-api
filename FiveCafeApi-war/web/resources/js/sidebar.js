fetch(window.APP_NAME + '/api/employee/nav')
    .then(res => res.json())
    .then(res => {
        if (res.status == 200) {
            const sidebarElm = document.getElementById('sidebar')
            sidebarElm.innerHTML = res.data.map(section => `
                <section class="section">
                    <p class="section-title">${section.sectionLabel}</p>
                    <ul class="pl-1 m-0">
                        ${section.items.map(item => `
                            <li class="rounded-md transition duration-200 ease-in-out bg-white hover:bg-sky-700 hover:text-white cursor-pointer">
                                <a href="/FiveCafeApi-war${item.path}" class="p-2 block">
                                    ${item.labelName}
                                </a>
                            </li>
                        `).join('')}
                    </ul>
                </section>
            `).join('')
        }
    });
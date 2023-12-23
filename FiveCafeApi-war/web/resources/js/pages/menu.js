const fetchPath = window.APP_NAME + '/api/menu/selling'
fetch(fetchPath)
.then(res => res.json())
.then(res => {
    if (res.status == 200) {
        document.getElementById("menu-content").innerHTML = `
            <h1 class="text-center font-bold text-3xl text-white uppercase">Menu</h1>
            ${res.data.map(section => section.products?.length ? `
                <section class="mt-4">
                    <h2 class="font-semibold text-xl italic text-zinc-200">${section.categoryName}</h2>
                    <div class="mt-2 grid grid-cols-3 gap-4">
                        ${section.products.map(item => `
                            <div class="col-span-1">
                                <div class="w-full rounded-md bg-center bg-no-repeat bg-cover" style="background-image: url('${item.image}'); padding-top: 100%;"></div>
                                <h3 class="text-base text-white font-normal text-center mt-3">${item.name}</h3>
                                <p class="text-lg text-white font-semibold text-center mt-3">${window.currencyOutput(item.price)}</p>
                            </div>
                        `).join('')}
                    </div>
                </section>    
            ` : '').join("")}
        `;
    }
})
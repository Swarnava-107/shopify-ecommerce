const BASE_URL = "http://localhost:8080";

// All products fetched from the API — kept in memory so search can re-filter
// without making another network request.
let allProducts = [];

/**
 * If the URL contains a hash (e.g. #clothing-products), scroll to that element.
 * Called after products are injected so sections have real height.
 */
function scrollToHash() {
    const hash = window.location.hash;
    if (!hash) return;
    const target = document.querySelector(hash);
    if (target) target.scrollIntoView({ behavior: "smooth", block: "start" });
}

/**
 * Build one product card's HTML string.
 */
function buildProductCard(product) {
    return `
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6">
            <div class="card">
                <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                <div class="card-body">
                    <h5 class="card-title">${product.name}</h5>
                    <p class="card-text">${product.description}</p>
                    <p class="price"><strong>₹${product.price}</strong></p>
                    <button class="btn btn-primary"
                        onclick="addToCart(${product.id}, '${product.name}', ${product.price}, '${product.imageUrl}')">
                        Add to Cart
                    </button>
                </div>
            </div>
        </div>
    `;
}

/**
 * Render a given product list into the three category sections.
 * Each section <section> element is shown only when it has at least one card.
 * Shows/hides the "no results" message accordingly.
 */
function renderProducts(products) {
    const trendingList  = document.getElementById("trending-products");
    const clothingList  = document.getElementById("clothing-products");
    const electronicsList = document.getElementById("electronics-products");
    const noResults     = document.getElementById("search-no-results");

    trendingList.innerHTML   = "";
    clothingList.innerHTML   = "";
    electronicsList.innerHTML = "";

    products.forEach((product) => {
        const card = buildProductCard(product);
        if (product.category === "Clothing") {
            clothingList.innerHTML += card;
        } else if (product.category === "Electronics") {
            electronicsList.innerHTML += card;
        } else {
            trendingList.innerHTML += card;
        }
    });

    // Show/hide each section heading based on whether it has cards
    document.getElementById("section-trending").style.display =
        trendingList.innerHTML   ? "" : "none";
    document.getElementById("section-clothing").style.display =
        clothingList.innerHTML   ? "" : "none";
    document.getElementById("section-electronics").style.display =
        electronicsList.innerHTML ? "" : "none";

    // Show "no results" only when nothing matched at all
    noResults.style.display = products.length === 0 ? "block" : "none";
}

/**
 * Filter allProducts by prefix match on name (case-insensitive) and re-render.
 * Called on every keystroke in the search input.
 */
function filterProducts() {
    const query = document.getElementById("product-search").value.trim().toLowerCase();

    if (query === "") {
        renderProducts(allProducts);
        scrollToHash();
        return;
    }

    const matched = allProducts.filter((p) =>
        p.name.toLowerCase().startsWith(query)
    );
    renderProducts(matched);
}

/**
 * Fetch products from the API, store them, and do the initial render.
 */
async function loadProducts() {
    try {
        const response = await fetch(`${BASE_URL}/products`);
        allProducts = await response.json();
        console.log(allProducts);

        renderProducts(allProducts);

        // Scroll to hash target now that sections have real height
        scrollToHash();

    } catch (error) {
        console.log("Error fetching products:", error);
    }
}


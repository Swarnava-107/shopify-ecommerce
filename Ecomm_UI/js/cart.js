// ─── Cart helpers ────────────────────────────────────────────────────────────

/** Always read the live cart from localStorage – single source of truth. */
function getCart() {
    return JSON.parse(localStorage.getItem("cart")) || [];
}

/** Persist the cart array to localStorage. */
function saveCart(cart) {
    localStorage.setItem("cart", JSON.stringify(cart));
}

// ─── Badge ────────────────────────────────────────────────────────────────────

/**
 * Updates the navbar cart badge with the TOTAL UNITS in the cart.
 * Uses a null-check so it does not throw on pages that have no .cart-badge
 * (e.g. cart.html).
 */
function updateCartCounter() {
    const badge = document.querySelector(".cart-badge");
    if (!badge) return;                           // safe: cart.html has no badge

    const cart = getCart();
    const totalQty = cart.reduce((sum, item) => sum + item.quantity, 0);
    badge.innerText = totalQty;
}

// ─── Add to cart ──────────────────────────────────────────────────────────────

function addToCart(id, name, price, imageUrl) {
    console.log("Adding product to cart:", id, name, price, imageUrl);

    price = parseFloat(price);
    let cart = getCart();                         // always read fresh from storage

    let itemIndex = cart.findIndex((item) => item.id === id);
    if (itemIndex !== -1) {
        cart[itemIndex].quantity += 1;
    } else {
        cart.push({
            id: id,
            name: name,
            price: price,
            imageUrl: imageUrl,
            quantity: 1
        });
    }

    saveCart(cart);
    updateCartCounter();
}

// ─── Cart page rendering ──────────────────────────────────────────────────────

function loadCart() {
    const cart = getCart();
    const cartItems = document.getElementById("cart-items");
    if (!cartItems) return;                       // safe: element absent on index.html
    let totalAmount = 0;
    cartItems.innerHTML = "";

    cart.forEach((item, index) => {
        let itemTotal = item.price * item.quantity;
        totalAmount += itemTotal;

        cartItems.innerHTML += `
            <tr>
                <td><img src="${item.imageUrl}" width="50"></td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>
                    <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, -1)">-</button>
                    ${item.quantity}
                    <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, 1)">+</button>
                </td>
                <td>₹ ${itemTotal}</td>
                <td><button class="btn btn-danger btn-sm" onclick="removeFromCart(${index})">X</button></td>
            </tr>
        `;
    });

    document.getElementById("total-amount").innerText = totalAmount;
    updateCartCounter();
}

// ─── Quantity change (+ / -) ──────────────────────────────────────────────────

/**
 * Increase or decrease an item's quantity by `change` (+1 or -1).
 * When quantity drops to 0, the item is completely removed from the cart.
 * `change` is always provided explicitly by the + / - buttons.
 */
function changeQuantity(index, change) {
    let cart = getCart();
    cart[index].quantity += change;

    if (cart[index].quantity <= 0) {
        cart.splice(index, 1);                    // quantity hit 0 → remove entirely
    }

    saveCart(cart);
    loadCart();
}

// ─── Remove item ──────────────────────────────────────────────────────────────

/**
 * Completely removes the item at `index` from the cart.
 * Called by the X button, which now passes the index explicitly.
 */
function removeFromCart(index) {
    let cart = getCart();
    cart.splice(index, 1);
    saveCart(cart);
    loadCart();
}

// ─── Init ─────────────────────────────────────────────────────────────────────

document.addEventListener("DOMContentLoaded", function () {
    loadCart();           // renders cart table on cart.html (no-op on index.html)
    updateCartCounter();  // syncs badge from localStorage on every page load
});

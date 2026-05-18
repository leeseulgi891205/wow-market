const STORAGE_USER = "wow-market-user";
const STORAGE_CART = "wow-market-cart";
const STORAGE_ORDERS = "wow-market-orders";

function formatPrice(value) {
  return `${Number(value).toLocaleString("ko-KR")}원`;
}

function getUser() {
  const raw = localStorage.getItem(STORAGE_USER);
  return raw ? JSON.parse(raw) : null;
}

function setUser(user) {
  if (user) {
    localStorage.setItem(STORAGE_USER, JSON.stringify(user));
  } else {
    localStorage.removeItem(STORAGE_USER);
  }
}

function getCart() {
  const raw = localStorage.getItem(STORAGE_CART);
  return raw ? JSON.parse(raw) : [];
}

function saveCart(items) {
  localStorage.setItem(STORAGE_CART, JSON.stringify(items));
}

function getOrders() {
  const raw = localStorage.getItem(STORAGE_ORDERS);
  return raw ? JSON.parse(raw) : [];
}

function saveOrders(orders) {
  localStorage.setItem(STORAGE_ORDERS, JSON.stringify(orders));
}

function findProduct(id) {
  return window.WOW_PRODUCTS.find((product) => product.id === Number(id));
}

function filterProducts(category, keyword) {
  return window.WOW_PRODUCTS.filter((product) => {
    const categoryMatch = !category || category === "ALL" || product.category === category;
    const keywordMatch = !keyword || product.name.toLowerCase().includes(keyword.toLowerCase());
    return categoryMatch && keywordMatch;
  });
}

function addToCart(productId, quantity = 1) {
  const product = findProduct(productId);
  if (!product) return;
  const cart = getCart();
  const existing = cart.find((item) => item.productId === product.id);
  if (existing) {
    existing.quantity += quantity;
  } else {
    cart.push({ productId: product.id, quantity });
  }
  saveCart(cart);
}

function removeFromCart(productId) {
  saveCart(getCart().filter((item) => item.productId !== Number(productId)));
}

function cartTotal(cart) {
  return cart.reduce((sum, item) => {
    const product = findProduct(item.productId);
    return sum + (product ? product.price * item.quantity : 0);
  }, 0);
}

function renderAuthLinks(container) {
  const user = getUser();
  container.innerHTML = user
    ? `<a href="/orders.html">주문배송</a><a href="/cart.html">장바구니</a><button type="button" data-logout>로그아웃</button>`
    : `<a href="/login.html">로그인</a><a href="/signup.html">회원가입</a>`;
  container.querySelector("[data-logout]")?.addEventListener("click", () => {
    setUser(null);
    window.location.href = "/";
  });
}

function queryParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function discountRate(index) {
  return `${15 + index * 7}%`;
}

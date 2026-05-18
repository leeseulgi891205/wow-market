const widget = document.querySelector("[data-chatbot]");
const toggle = document.querySelector("[data-chatbot-toggle]");
const closeButton = document.querySelector("[data-chatbot-close]");
const form = document.querySelector("[data-chatbot-form]");
const messages = document.querySelector("[data-chatbot-messages]");

const mockReplies = [
  "오늘은 패션 카테고리 특가가 진행 중이에요. Aero Knit Hoodie를 추천드려요.",
  "3만 원 이상 주문 시 무료배송이 적용됩니다.",
  "회원가입 후 장바구니와 주문 내역을 저장할 수 있어요.",
  "선물용이라면 Ceramic Brew Set이나 Nova Smart Watch가 인기입니다."
];

function addMessage(text, type) {
  const message = document.createElement("div");
  message.className = `message ${type}`;
  message.textContent = text;
  messages.appendChild(message);
  messages.scrollTop = messages.scrollHeight;
}

toggle?.addEventListener("click", () => {
  widget.classList.add("open");
});

closeButton?.addEventListener("click", () => {
  widget.classList.remove("open");
});

form?.addEventListener("submit", (event) => {
  event.preventDefault();
  const input = form.elements.message;
  const text = input.value.trim();
  if (!text) return;

  addMessage(text, "user");
  input.value = "";

  const loading = document.createElement("div");
  loading.className = "message bot loading";
  loading.textContent = "답변을 준비하고 있어요...";
  messages.appendChild(loading);
  messages.scrollTop = messages.scrollHeight;

  window.setTimeout(() => {
    loading.remove();
    const reply = mockReplies[Math.floor(Math.random() * mockReplies.length)];
    addMessage(reply, "bot");
  }, 700);
});

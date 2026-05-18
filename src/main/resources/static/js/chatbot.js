const widget = document.querySelector("[data-chatbot]");
const toggle = document.querySelector("[data-chatbot-toggle]");
const closeButton = document.querySelector("[data-chatbot-close]");
const form = document.querySelector("[data-chatbot-form]");
const messages = document.querySelector("[data-chatbot-messages]");

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

form?.addEventListener("submit", async (event) => {
    event.preventDefault();
    const input = form.elements.message;
    const text = input.value.trim();
    if (!text) {
        return;
    }

    addMessage(text, "user");
    input.value = "";
    input.disabled = true;

    const loading = document.createElement("div");
    loading.className = "message bot loading";
    loading.textContent = "답변을 준비하고 있어요...";
    messages.appendChild(loading);
    messages.scrollTop = messages.scrollHeight;

    try {
        const response = await fetch("/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message: text })
        });
        const data = await response.json();
        loading.remove();
        addMessage(data.reply || "답변을 생성하지 못했습니다.", "bot");
    } catch (error) {
        loading.remove();
        addMessage("네트워크 연결을 확인한 뒤 다시 시도해 주세요.", "bot");
    } finally {
        input.disabled = false;
        input.focus();
    }
});

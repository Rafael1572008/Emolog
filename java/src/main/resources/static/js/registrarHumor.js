document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("moodForm");
    const statusText = document.getElementById("statusText");
    const moodInput = document.getElementById("moodValue");
    const noteInput = document.getElementById("note");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const humor = moodInput.value;
        const texto = noteInput.value.trim();

        if (!humor) {
            statusText.textContent = "Selecione uma carinha antes de salvar.";
            statusText.style.color = "#f87171";
            return;
        }

        statusText.textContent = "Enviando...";
        statusText.style.color = "#9ca3af";

        try {
            const response = await fetch('/humor', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ texto, humor })
            });

           if (response.ok) {
               alert('Humor registrado com sucesso');
               window.location.href = '/';
           } else {
               alert('Erro ao registrar humor. Tente novamente!');
               form.reset();
               document.querySelectorAll(".mood-btn").forEach(b => b.classList.remove("selected"));
           }

        } catch (e) {
            statusText.textContent = "Falha ao enviar. Tente novamente.";
            statusText.style.color = "#f87171";
        }
    });

});

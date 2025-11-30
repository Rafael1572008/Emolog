document.addEventListener("DOMContentLoaded", function () {

    const modal = document.getElementById("modalCriarTag");
    const form = document.getElementById("formCriarTag");
    const input = document.getElementById("nomeTag");

    window.abrirModalCriar = () => {
        modal.style.display = "flex";
        input.focus();
    };

    window.fecharModalCriar = () => {
        modal.style.display = "none";
        form.reset();
    };

    document.addEventListener("keydown", e => {
        if (e.key === "Escape") fecharModalCriar();
    });

    modal.addEventListener("click", e => {
        if (e.target === modal) fecharModalCriar();
    });

    form.addEventListener("submit", e => {
        e.preventDefault();
        const nome = input.value.trim();

        if (!nome) {
            alert("Digite um nome para a tag");
            return;
        }

        fetch("/tag", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome })
        })
        .then(r => r.ok ? r.json() : r.text().then(t => { throw t }))
        .then(() => {
            alert("Tag criada com sucesso!");
            fecharModalCriar();
            location.reload();
        })
        .catch(err => alert("Erro: " + err));
    });
});

function toggleMenu(btn) {
    btn.parentElement.classList.toggle("show");
}
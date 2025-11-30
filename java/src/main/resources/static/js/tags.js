document.addEventListener("DOMContentLoaded", function () {
    const modal       = document.getElementById("modalCriarTag");
    const form        = document.getElementById("formCriarTag");
    const input       = document.getElementById("nomeTag");
    const tituloModal = document.getElementById("tituloModal");
    const btnSalvar   = document.getElementById("btnSalvarTag");

    let modoEdicao  = false;
    let tagIdEdicao = null;

    /// Abrir para crair
    window.abrirModalCriar = () => {
        modoEdicao = false;
        tagIdEdicao = null;
        input.value = "";
        tituloModal.textContent = "Criar nova tag";
        btnSalvar.textContent   = "Criar tag";
        modal.style.display = "flex";
        input.focus();
    };

    /// Abrir para editar
    window.editarTag = (id, nomeAtual) => {
        modoEdicao   = true;
        tagIdEdicao  = id;
        input.value  = nomeAtual || "";
        input.focus();
        input.select();
        tituloModal.textContent = "Editar tag";
        btnSalvar.textContent   = "Salvar alterações";
        modal.style.display = "flex";
    };

    /// Fechar model
    window.fecharModalCriar = () => {
        modal.style.display = "none";
        form.reset();
        modoEdicao  = false;
        tagIdEdicao = null;
    };

    /// Fechar
    document.addEventListener("keydown", e => {
        if (e.key === "Escape") fecharModalCriar();
    });
    modal.addEventListener("click", e => {
        if (e.target === modal) fecharModalCriar();
    });

    /// Salvar, idependente do que seja
    form.addEventListener("submit", async e => {
        e.preventDefault();
        const nome = input.value.trim();
        if (!nome) {
            alert("O nome da tag é obrigatório!");
            return;
        }

        const payload = modoEdicao ? { texto: nome } : { nome };
        const url     = modoEdicao ? `/tag/${tagIdEdicao}/texto` : "/tag";
        const method  = modoEdicao ? "PATCH" : "POST";

        try {
            const res = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (!res.ok) {
                const erro = await res.text();
                throw new Error(erro || "Erro na operação");
            }

            alert(modoEdicao ? "Tag atualizada com sucesso!" : "Tag criada com sucesso!");
            fecharModalCriar();
            location.reload();

        } catch (err) {
            alert("Erro: " + err.message);
        }
    });

    /// Excluir tag
    window.deletarTag = async function (id) {
        const botao = document.querySelector(`button[onclick*="deletarTag(${id})"]`);
        console.log(botao)
        const linha = botao.closest(".tag-item");
        console.log(linha)
        const nomeTag = linha.querySelector(".dataHora").textContent;
        console.log(nomeTag)

        console.log("Ponto 0")
        try {
            const res = await fetch(`/tag/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error("Erro ao excluir");

            alert("Tag excluída com sucesso!");
            location.reload();
        } catch (err) {
            alert("Erro ao excluir a tag");
        }
    };
});

// Menu lateral
function toggleMenu(btn) {
    btn.parentElement.classList.toggle("show");
}
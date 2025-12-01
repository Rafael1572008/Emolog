// ==================== MENU ====================
function toggleMenu(button) {
    const container = button.parentElement;
    document.querySelectorAll('.menu-container').forEach(c => {
        if (c !== container) c.classList.remove('show');
    });
    container.classList.toggle('show');
}

// ==================== MODAL ====================
let humorIdAtual = null;

function abrirModalEditar(id, textoAtual) {
    humorIdAtual = id;
    const textarea = document.getElementById('textoEditado');
    textarea.value = textoAtual || '';
    document.getElementById('modalEditarTexto').style.display = 'block';
    textarea.focus();
    textarea.select();
}

function fecharModal() {
    document.getElementById('modalEditarTexto').style.display = 'none';
    humorIdAtual = null;
}

// ==================== CLIQUE FORA (MENU + MODAL) ====================
window.addEventListener('click', function(e) {
    // Fecha menu
    if (!e.target.closest('.menu-button')) {
        document.querySelectorAll('.menu-container').forEach(c => c.classList.remove('show'));
    }
    // Fecha modal
    const modal = document.getElementById('modalEditarTexto');
    if (e.target === modal) fecharModal();
});

// ==================== CLIQUE NO LÁPIS (agora com data-*) ====================
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.btn-editar').forEach(img => {
        img.addEventListener('click', function() {
            const id = this.dataset.id;
            const texto = this.dataset.texto || '';
            abrirModalEditar(id, texto);
        });
    });
});

// ==================== SALVAR TEXTO ====================
document.getElementById('formEditarTexto').onsubmit = function(e) {
    e.preventDefault();
    const novoTexto = document.getElementById('textoEditado').value.trim();

    fetch(`/humor/${humorIdAtual}/texto`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ texto: novoTexto })
    })
    .then(r => {
        if (r.ok) {

            const bloco = document.querySelector(`.texto-humor[data-humor-id="${humorIdAtual}"]`);

            if (bloco) {
                bloco.textContent = novoTexto || '(sem texto)';
                bloco.style.display = novoTexto ? 'block' : 'none';
            }

            fecharModal();
        } else {
            alert('Erro ao salvar.');
        }
    });
};


document.addEventListener('keydown', e => {
    if (e.key === 'Escape') fecharModal();
});

function excluirHumor(id) {
    if (!confirm(`Tem certeza que deseja deletar este registro?`)) {
        return;
    }

    fetch(`/humor/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.ok) {
                alert('Registro deletado com sucesso!');
                const itemParaRemover = document.querySelector(`.texto-humor[data-humor-id="${id}"]`).closest('.humor-item');

                if (itemParaRemover) {
                    itemParaRemover.remove();
                    if (document.querySelectorAll('.humor-item').length === 0) {
                        const semRegistros = document.querySelector('.sem-registros');
                        if (semRegistros) semRegistros.style.display = 'block';
                    }
                }
                const container = document.querySelector(".historico-humor");
                if (container.children.length === 0) {
                    container.innerHTML = `
                        <div class="sem-registros">
                            Não há registros de humor.
                        </div>
                    `;
                }
            } else {
                alert(`Erro ao deletar o registro.`);
            }
        })
    }

document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".btn-add-tag").forEach(btn => {
        btn.addEventListener("click", () => {
            const menu = btn.parentElement.querySelector(".tag-menu");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        });
    });

    document.querySelectorAll(".tag-opcao").forEach(opcao => {
        opcao.addEventListener("click", async () => {

            const tagId = opcao.dataset.id;

            const container = opcao.closest(".humor-item");
            const humorId = container.dataset.humorId;

            const tagsDiv = container.querySelector(".tags");

            const jaTem = Array.from(tagsDiv.querySelectorAll(".tag"))
                .some(t => t.textContent.trim() === opcao.textContent.trim());

            if (jaTem) return;


            console.log("Humor ID:", humorId);
            console.log("Tag ID:", tagId);
            console.log("Enviando:", JSON.stringify([ Number(tagId) ]));

            await fetch(`/humor/${humorId}/tags`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify([ Number(tagId) ])
            });

            window.location.href = '/'; //atualiza as tags

            const nova = document.createElement("span");
            nova.classList.add("tag");
            nova.textContent = opcao.textContent;
            tagsDiv.appendChild(nova);

            opcao.parentElement.style.display = "none";
        });
    });

});

document.addEventListener("click", async function(e) {

    if (!e.target.classList.contains("tag-remove")) return;

    const tagSpan = e.target.closest(".tag");
    const tagId = tagSpan.dataset.tagId;

    const container = e.target.closest(".humor-item");
    const humorId = container.dataset.humorId;

    console.log("Remover Tag -> Humor:", humorId, "Tag:", tagId);

    const resp = await fetch(`/humor/${humorId}/tags`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify([ Number(tagId) ])
    });

    if (!resp.ok) {
        alert("Erro ao remover tag.");
        return;
    }

    tagSpan.remove();
});

function toggleFiltro(botao) {
    const menu = botao.nextElementSibling;
    menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
}

document.querySelectorAll('.filtro-tag-opcao').forEach(tag => {
    tag.addEventListener('click', () => {
        tag.classList.toggle('selecionada');
        aplicarFiltro();
    });
});

function aplicarFiltro() {
    const selecionadas = Array.from(document.querySelectorAll('.filtro-tag-opcao.selecionada'))
                              .map(t => t.textContent.trim());

    document.querySelectorAll('.humor-item').forEach(item => {
        if (selecionadas.length === 0) {
            item.style.display = 'flex';
            return;
        }

        const itemTags = Array.from(item.querySelectorAll('.tag span:first-child'))
                              .map(t => t.textContent.trim());

        const possuiTodas = selecionadas.every(tag => itemTags.includes(tag));

        item.style.display = possuiTodas ? 'flex' : 'none';
    });
}

document.addEventListener('click', function(event) {
    const filtro = document.querySelector('.filtro-tags-container');
    if (!filtro.contains(event.target)) {
        document.querySelector('.filtro-menu').style.display = 'none';
    }
});
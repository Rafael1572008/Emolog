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
            const bloco = document.querySelector(`[data-humor-id="${humorIdAtual}"]`);
            if (bloco) {
                bloco.textContent = novoTexto || '(sem texto)';
                if (!novoTexto) bloco.style.display = 'none';
                else bloco.style.display = 'block';
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


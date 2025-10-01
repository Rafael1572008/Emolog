document.getElementById('changePasswordForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const senhaAtual = document.getElementById('current-password').value
    const novaSenha = document.getElementById('new-password').value
    const confirmarNovaSenha = document.getElementById('new-password-confirmation').value

    if (novaSenha !== confirmarNovaSenha) {
        alert('As senhas não coincidem!');
        return;
    }

    const response = await fetch('/novasenha', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({novaSenha}),
    });

    if (response.ok) {
        alert('Senha alterada com sucesso!');
        window.location.href = '/'; // Redireciona para a página de login
    } else {
        alert('Erro ao alterar a senha. Tente novamente!');
    }
});

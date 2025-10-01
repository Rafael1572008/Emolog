document.getElementById('changePasswordForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const senhaNova = document.getElementById('new-password').value
    const confirmarSenhaNova = document.getElementById('new-password-confirmation').value

    if (senhaNova !== confirmarSenhaNova) {
        alert('As senhas não coincidem!');
        return;
    }

    const response = await fetch('/novasenha', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ senhaNova }),
    });

    if (response.ok) {
            alert('Senha alterada com sucesso!');
            window.location.href = '/'; // Redireciona para a página de login
        } else {
            alert('Erro ao alterar a senha. Tente novamente!');
        }
});

document.getElementById('signupForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nome = document.getElementById('name').value || null;
    const email = document.getElementById('email').value || "rafa@gmail.com";
    const senhaHash = document.getElementById('password').value || null;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Validação básica no frontend
    if (senhaHash !== confirmPassword) {
        alert('As senhas não coincidem!');
        return;
    }

    const response = await fetch('/usuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, email, senhaHash }),
    });

    if (response.ok) {
        alert('Cadastro realizado com sucesso!');
        window.location.href = '/login'; // Redireciona para a página de login
    } else {
        alert('Erro ao cadastrar. Tente novamente!');
    }
});
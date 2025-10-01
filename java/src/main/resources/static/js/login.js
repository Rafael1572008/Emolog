document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const email = e.target.email.value.trim();
        const senha = e.target.senha.value.trim();


        try {
            const response = await fetch('/usuario/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, senha }),
            });

            if (response.ok) {
                alert('Login realizado com sucesso!');
                window.location.href = '/';
            } else if (response.status === 401) {
                alert('Credenciais inválidas!');
            } else {
                alert('Erro ao tentar logar. Tente novamente!');
            }
        } catch (error) {
            console.error('Erro no login:', error);
            alert('Falha de conexão com o servidor.');
        }
    });

    // Função para validar email
    function validateEmail(email) {
        const re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    // Social login
    const googleBtn = document.querySelector('.google');
    const facebookBtn = document.querySelector('.facebook');

    [googleBtn, facebookBtn].forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            window.open(
                btn.getAttribute('href'),
                '_blank',
                'width=500,height=600,top=100,left=100'
            );
        });
    });
});

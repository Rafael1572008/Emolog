const form = document.querySelector('form');

form.addEventListener('submit', (e) => {
  e.preventDefault(); 

  const email = form.email.value.trim();
  const senha = form.senha.value.trim();

  if (!validateEmail(email)) {
    alert('Por favor, insira um email válido');
    return;
  }

  if (senha.length < 6) {
    alert('A senha precisa ter pelo menos 6 caracteres');
    return;
  }

  form.submit();
});

function validateEmail(email) {
  const re = /\S+@\S+\.\S+/;
  return re.test(email);
}

const googleBtn = document.querySelector('.google');
const facebookBtn = document.querySelector('.facebook');

[googleBtn, facebookBtn].forEach(btn => {
  btn.addEventListener('click', (e) => {
    e.preventDefault();
    window.open(
      btn.href,
      '_blank',
      'width=500,height=600,top=100,left=100'
    );
  });
});
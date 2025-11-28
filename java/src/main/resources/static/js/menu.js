function toggleMenu(button) {
  const container = button.parentElement;
  container.classList.toggle('show');
}

// fecha o menu
window.onclick = function(event) {
  if (!event.target.matches('.menu-button')) {
    document.querySelectorAll('.menu-container').forEach(c => c.classList.remove('show'));
  }
};

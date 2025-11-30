package br.edu.ifsp.spo.java.controllers.web;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.service.HumorService;
import br.edu.ifsp.spo.java.service.TagService;
import br.edu.ifsp.spo.java.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TagsPageController {
    private final UsuarioService usuarioService;
    private final TagService tagService;

    public TagsPageController(UsuarioService usuarioService, TagService tagService) {
        this.usuarioService = usuarioService;
        this.tagService = tagService;
    }

    @GetMapping("/tagsmain")
    public String tagsmain(HttpSession session, Model model){

        // Se não tem usuário logado → login
        String email = (String) session.getAttribute("usuario");
        Long idUser = (Long) session.getAttribute("idUser");

        if (email == null || idUser == null) {
            return "redirect:/login";
        }

        // Busca o usuário de forma segura
        var usuarioOpt = usuarioService.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            session.invalidate();
            return "redirect:/login";
        }

        List<TagModel> tags = tagService.findByIdUser(idUser);
        if (tags == null) {
            tags = List.of(); // garante lista vazia
        }

        model.addAttribute("tags", tags);

        // se existe usuário, mostra a página
        return "tags";
    }
}

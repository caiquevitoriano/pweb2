package br.com.ifpb.pweb2.securitydemo.controller;

import br.com.ifpb.pweb2.securitydemo.domain.Usuario;
import br.com.ifpb.pweb2.securitydemo.service.UsuarioException;
import br.com.ifpb.pweb2.securitydemo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/{login}")
    public Usuario recuperarPorLogin(@PathVariable("login") String login) {
        return usuarioService.recuperarPorLogin(login).orElseThrow(RuntimeException::new);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            usuario = usuarioService.salvarUsuario(usuario);
        } catch(UsuarioException e) {
            return ResponseEntity.badRequest().header("erro", e.getMessage()).build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping
    public Usuario atualizarUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioService.atualizarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void removerUsuario(@PathVariable("id") Long id) {
        usuarioService.removerUsuario(id);
    }

}

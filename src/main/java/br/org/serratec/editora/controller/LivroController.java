package br.org.serratec.editora.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.org.serratec.editora.dto.LivroDto;
import br.org.serratec.editora.service.LivroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
	@Autowired
	private LivroService livroService;
	
	@GetMapping
	public ResponseEntity<List<LivroDto>> obterTodos(){
		return ResponseEntity.ok(livroService.obterTodos());
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<List<LivroDto>> obterPorInicioTitulo(@RequestBody String inicioDoTitulo){
		return ResponseEntity.ok(livroService.obterPorInicioTitulo(inicioDoTitulo));
	}
	
	@GetMapping("/autor")
	public ResponseEntity<List<LivroDto>> obterPorFinalAutor(@RequestBody String fimDoAutor){
		return ResponseEntity.ok(livroService.obterPorFinalAutor(fimDoAutor));
	}
	
	@GetMapping("/autor-e-titulo")
	public ResponseEntity<List<LivroDto>> buscarAutor(@RequestParam String autor, @RequestParam String titulo){
		return ResponseEntity.ok(livroService.obterPorAutoreTitulo(autor, titulo));
	}
	
	@PostMapping
	public ResponseEntity<LivroDto> salvarLivro(@Valid @RequestBody LivroDto novoLivro){
		return new ResponseEntity<LivroDto>(livroService.salvarLivro(novoLivro), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<LivroDto> atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroDto livroAlterado) {
		Optional<LivroDto> livro = livroService.atualizarLivro(id, livroAlterado);
		
		if (livro.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(livro.get());
	}

	@GetMapping("/{id}")
    public ResponseEntity<LivroDto> obterLivroPorId(@PathVariable Long id) {
        Optional<LivroDto> livro = livroService.obterLivroPorId(id);

        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        }

        return ResponseEntity.notFound().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(livroService.excluirLivro(id)){
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}

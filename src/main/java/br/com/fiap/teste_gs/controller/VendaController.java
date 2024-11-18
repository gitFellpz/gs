package br.com.fiap.teste_gs.controller;

import br.com.fiap.teste_gs.dto.VendaDTO;
import br.com.fiap.teste_gs.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@Tag(name = "Vendas", description = "Controller para Vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @Operation(
        description = "Listar vendas",
            summary = "Retorna uma lista de vendas",
            responses = {
                @ApiResponse(description = "Tudo certo moreno", responseCode = "200")
            }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<VendaDTO>> findAll() {

        List<VendaDTO> dto = service.findAll();
        return ResponseEntity.ok(dto);
    }


    @Operation(
            description = "Retorna uma venda a partir de um ID fornecido",
            summary = "Consulta por ID",
            responses = {
                    @ApiResponse(description = "Tudo certo moreno", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendaDTO> findById(@PathVariable Long id) {
        VendaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<VendaDTO> insert(@RequestBody @Valid VendaDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> update(@PathVariable @NotNull Long id,
                                           @RequestBody @Valid VendaDTO dto ){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

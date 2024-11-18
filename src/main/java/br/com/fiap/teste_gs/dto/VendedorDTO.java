package br.com.fiap.teste_gs.dto;

import br.com.fiap.teste_gs.model.Vendedor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VendedorDTO {
    private Long id;

    @NotBlank(message = "O campo nome não pode ser nulo")
    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    public VendedorDTO(Vendedor entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
    }


}

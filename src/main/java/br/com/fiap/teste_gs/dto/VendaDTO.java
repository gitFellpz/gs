package br.com.fiap.teste_gs.dto;

import br.com.fiap.teste_gs.model.Venda;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VendaDTO {

    @Schema(description = "ID da venda gerada pelo banco de dados")
    private Long id;

    @NotNull(message = "Campo obrigatório!")
    private double total;

    @NotNull(message = "Campo obrigatório!")
    private LocalDate data;

    @NotNull(message = "Campo obrigatório!")
    private double comissao;

    @NotNull(message = "Campo obrigatório!")
    private String status;

    private VendedorDTO vendedor;

    public VendaDTO(Venda entity) {
        id = entity.getId();
        total = entity.getTotal();
        data = entity.getData();
        comissao = entity.getComissao();
        status = entity.getStatus();
        vendedor = new VendedorDTO(entity.getVendedor());
    }
}

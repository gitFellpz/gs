package br.com.fiap.teste_gs.repository;

import br.com.fiap.teste_gs.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> { }

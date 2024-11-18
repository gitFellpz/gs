package br.com.fiap.teste_gs.repository;

import br.com.fiap.teste_gs.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> { }

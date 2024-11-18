package br.com.fiap.teste_gs.service;

import br.com.fiap.teste_gs.dto.VendaDTO;
import br.com.fiap.teste_gs.model.Venda;
import br.com.fiap.teste_gs.model.Vendedor;
import br.com.fiap.teste_gs.repository.VendaRepository;
import br.com.fiap.teste_gs.repository.VendedorRepository;
import br.com.fiap.teste_gs.service.exception.DatabaseException;
import br.com.fiap.teste_gs.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Transactional(readOnly = true)
    public List<VendaDTO> findAll() {
        return repository.findAll().stream()
                .map(VendaDTO::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public VendaDTO findById(Long id) {
        Venda entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id)
        );

        return new VendaDTO(entity);
    }

    @Transactional
    public VendaDTO insert(VendaDTO dto){

        Venda entity = new Venda();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new VendaDTO(entity);
    }

    @Transactional
    public VendaDTO update(Long id, VendaDTO dto) {
        try {
            Venda entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new VendaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado! Id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado! Id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(VendaDTO dto, Venda entity) {

        entity.setTotal(dto.getTotal());
        entity.setData(dto.getData());
        Vendedor vendedor = vendedorRepository.getReferenceById(dto.getVendedor().getId());
        entity.setVendedor(vendedor);

        Double comissao = (dto.getTotal() * 10) / 100;
        entity.setComissao(comissao);
        if(dto.getTotal() < 15000){
            entity.setStatus("BAIXA");
        } else if (dto.getTotal() < 30000) {
            entity.setStatus("MEDIA");
        }
        else {
            entity.setStatus("ALTA");
        }

    }


}

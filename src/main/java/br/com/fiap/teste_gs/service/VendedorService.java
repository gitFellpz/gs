package br.com.fiap.teste_gs.service;

import br.com.fiap.teste_gs.dto.VendaDTO;
import br.com.fiap.teste_gs.dto.VendedorDTO;
import br.com.fiap.teste_gs.model.Vendedor;
import br.com.fiap.teste_gs.repository.VendedorRepository;
import br.com.fiap.teste_gs.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.fiap.teste_gs.service.exception.DatabaseException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<VendedorDTO> findAll() {
        return repository.findAll().stream()
                .map(vendedor -> modelMapper.map(vendedor, VendedorDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VendedorDTO findById(Long id) {
        Vendedor entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id)
        );

        return modelMapper.map(entity, VendedorDTO.class);
    }

    @Transactional
    public VendedorDTO insert(VendedorDTO dto) {
        Vendedor entity = modelMapper.map(dto, Vendedor.class);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new VendedorDTO(entity);
    }

    private void copyDtoToEntity(VendedorDTO dto, Vendedor entity) {

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
    }


    @Transactional
    public VendedorDTO update(Long id, VendedorDTO dto) {
        try {
            Vendedor entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new VendedorDTO(entity);
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



}

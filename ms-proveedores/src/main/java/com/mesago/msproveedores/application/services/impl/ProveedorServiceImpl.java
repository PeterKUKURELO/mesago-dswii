package com.mesago.msproveedores.application.services.impl;

import com.mesago.msproveedores.application.services.ProveedorService;
import com.mesago.msproveedores.domain.entities.Proveedor;
import com.mesago.msproveedores.domain.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository repo;

    public ProveedorServiceImpl(ProveedorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Proveedor> listar() {
        return repo.findAll();
    }

    @Override
    public Proveedor buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return repo.save(proveedor);
    }

    @Override
    public Proveedor actualizar(Long id, Proveedor proveedor) {
        Proveedor existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setNombre(proveedor.getNombre());
            existing.setRuc(proveedor.getRuc());
            existing.setTelefono(proveedor.getTelefono());
            existing.setEstado(proveedor.getEstado());
            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
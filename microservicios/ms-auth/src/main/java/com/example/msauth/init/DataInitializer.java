package com.example.msauth.init;

import com.example.msauth.model.Rol;
import com.example.msauth.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RolRepository rolRepository;

    @PostConstruct
    public void initRoles() {
        for (Rol.ERol nombreRol : Rol.ERol.values()) {
            rolRepository.findByNombre(nombreRol).orElseGet(() -> {
                Rol rol = new Rol();
                rol.setNombre(nombreRol);
                return rolRepository.save(rol);
            });
        }
    }
}
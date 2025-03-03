package com.tarea.services;

import com.tarea.entities.Tarea;
import com.tarea.repositories.TareaRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioTarea {
    private TareaRepositorio miRepositorio;

    public ServicioTarea(TareaRepositorio miRepositorio) {
        this.miRepositorio = miRepositorio;
    }

    public List<Tarea> getTareas(){
        return this.miRepositorio.findAll();
    }
    public Tarea postTareas(Tarea tarea){
        return this.miRepositorio.saveAndFlush(tarea);
    }
    public Tarea updateTareas(Long id, Tarea tareaModificada) {
        Tarea tareaExistente = miRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        tareaExistente.setName(tareaModificada.getName());
        tareaExistente.setDescription(tareaModificada.getDescription());
        tareaExistente.setDate(tareaModificada.getDate()); // Actualizar la fecha

        return miRepositorio.save(tareaExistente);
    }

    public void deleteTarea(Long id) {
        if (miRepositorio.existsById(id)) {
            miRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Tarea no encontrada con ID: " + id);
        }
    }
}

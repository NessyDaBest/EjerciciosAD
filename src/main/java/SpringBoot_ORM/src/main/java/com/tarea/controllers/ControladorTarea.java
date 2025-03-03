package com.tarea.controllers;

import com.tarea.entities.Tarea;
import com.tarea.services.ServicioTarea;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorTarea {
    private final ServicioTarea servicio;

    public ControladorTarea(ServicioTarea servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/tareas")
    public List<Tarea> obtenerTareas() {
        return this.servicio.getTareas();
    }

    @PostMapping("/tareas")
    public Tarea agregarTarea(@RequestBody Tarea tareacreada) {
        return this.servicio.postTareas(tareacreada);
    }

    @PutMapping("/tareas/{id}")
    public Tarea editarTarea(@PathVariable Long id, @RequestBody Tarea tareamodificada) {
        return this.servicio.updateTareas(id, tareamodificada);
    }

    @DeleteMapping("/tareas/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        servicio.deleteTarea(id);
        return "Tarea con ID " + id + " eliminada correctamente.";
    }
}
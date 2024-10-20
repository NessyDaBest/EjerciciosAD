import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {
    private static final String ARCHIVO = "resources/notas_estudiantes.txt";


    public void añadirEstudiante(Estudiante estudiante) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(estudiante.getNombre() + "," + estudiante.getNota());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mostrarEstudiantes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void buscarEstudiante(String nombre) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            boolean encontrado = false;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equalsIgnoreCase(nombre)) {
                    System.out.println("Estudiante: " + datos[0] + ", Nota: " + datos[1]);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Estudiante no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void calcularMedia() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para calcular la media.");
            return;
        }

        double suma = 0;
        for (Estudiante estudiante : estudiantes) {
            suma += estudiante.getNota();
        }

        double media = suma / estudiantes.size();
        System.out.println("La media de las notas es: " + media);
    }


    private List<Estudiante> leerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0];
                double nota = Double.parseDouble(datos[1]);
                estudiantes.add(new Estudiante(nombre, nota));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estudiantes;
    }
}

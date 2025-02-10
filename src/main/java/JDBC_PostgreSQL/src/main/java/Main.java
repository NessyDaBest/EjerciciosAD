import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConectorBD conector = new ConectorBD();
        List<Tarea> listaTareas = new ArrayList<>();

        try (Connection conn = conector.getConnection()) {
            if (conn != null) {
                System.out.println("Conexión exitosa");
                String sql = "CREATE TABLE IF NOT EXISTS Tarea ("
                        + " id SERIAL PRIMARY KEY ,"
                        + " nombre VARCHAR(125) NOT NULL,"
                        + " descripcion TEXT,"
                        + " completada BOOLEAN DEFAULT FALSE"
                        + ");";
                conector.crearTabla(sql);

                cargarTareas(conn, listaTareas);

                Scanner scanner = new Scanner(System.in);
                boolean continuar = true;

                while (continuar) {
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Crear tarea");
                    System.out.println("2. Consultar tarea");
                    System.out.println("3. Editar tarea");
                    System.out.println("4. Eliminar tarea");
                    System.out.println("5. Mostrar todas las tareas");
                    System.out.println("6. Salir");

                    int opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese el nombre de la tarea:");
                            String nombre = scanner.nextLine();
                            System.out.println("Ingrese la descripción de la tarea:");
                            String descripcion = scanner.nextLine();
                            System.out.println("¿Está completada? (true/false):");
                            boolean completada = scanner.nextBoolean();
                            new Tarea(0, nombre, descripcion, completada).crearTarea(conn, nombre, descripcion, completada);
                            cargarTareas(conn, listaTareas);
                            break;
                        case 2:
                            System.out.println("Ingrese el ID de la tarea a consultar:");
                            int idConsulta = scanner.nextInt();
                            for (Tarea tarea : listaTareas) {
                                if (tarea.getId() == idConsulta) {
                                    System.out.println("ID: " + tarea.getId() + ", Nombre: " + tarea.getNombre() + ", Descripción: " + tarea.getDescripcion() + ", Completada: " + tarea.isCompletada());
                                    break;
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Ingrese el ID de la tarea a editar:");
                            int idEdicion = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Ingrese el nuevo nombre:");
                            String nuevoNombre = scanner.nextLine();
                            System.out.println("Ingrese la nueva descripción:");
                            String nuevaDescripcion = scanner.nextLine();
                            System.out.println("¿Está completada? (true/false):");
                            boolean nuevaCompletada = scanner.nextBoolean();
                            new Tarea(0, "", "", false).editarTarea(conn, idEdicion, nuevoNombre, nuevaDescripcion, nuevaCompletada);
                            cargarTareas(conn, listaTareas);
                            break;
                        case 4:
                            System.out.println("Ingrese el ID de la tarea a eliminar:");
                            int idEliminacion = scanner.nextInt();
                            new Tarea(0, "", "", false).eliminarTarea(conn, idEliminacion);
                            cargarTareas(conn, listaTareas);
                            break;
                        case 5:
                            System.out.println("\nLista de tareas:");
                            for (Tarea tarea : listaTareas) {
                                System.out.println("ID: " + tarea.getId() + ", Nombre: " + tarea.getNombre() + ", Descripción: " + tarea.getDescripcion() + ", Completada: " + tarea.isCompletada());
                            }
                            break;
                        case 6:
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opción no válida, intente de nuevo.");
                    }
                }
                scanner.close();
            } else {
                System.out.println("Conexión fallida");
            }
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error en la conexión de la base de datos: " + e.getMessage());
        }
    }

    private static void cargarTareas(Connection conn, List<Tarea> listaTareas) {
        listaTareas.clear();
        String sql = "SELECT * FROM Tarea";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                listaTareas.add(new Tarea(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getBoolean("completada")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar las tareas: " + e.getMessage());
        }
    }
}

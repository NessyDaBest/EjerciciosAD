import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean completada;

    public Tarea(int id, String nombre, String descripcion, boolean completada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    // Crear una nueva tarea
    public void crearTarea(Connection conn, String nombre, String descripcion, boolean completada) {
        String sql = "INSERT INTO Tarea (nombre, descripcion, completada) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setBoolean(3, completada);
            pstmt.executeUpdate();
            System.out.println("Tarea creada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tarea: " + e.getMessage());
        }
    }

    // Leer tarea por ID
    public void consultarTarea(Connection conn, int id) {
        String sql = "SELECT * FROM Tarea WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Completada: " + rs.getBoolean("completada"));
            } else {
                System.out.println("No se encontró la tarea con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la tarea: " + e.getMessage());
        }
    }

    // Actualizar tarea
    public void editarTarea(Connection conn, int id, String nombre, String descripcion, boolean completada) {
        String sql = "UPDATE Tarea SET nombre = ?, descripcion = ?, completada = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setBoolean(3, completada);
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tarea actualizada correctamente.");
            } else {
                System.out.println("No se encontró la tarea con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la tarea: " + e.getMessage());
        }
    }

    // Eliminar tarea
    public void eliminarTarea(Connection conn, int id) {
        String sql = "DELETE FROM Tarea WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tarea eliminada correctamente.");
            } else {
                System.out.println("No se encontró la tarea con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la tarea: " + e.getMessage());
        }
    }
}

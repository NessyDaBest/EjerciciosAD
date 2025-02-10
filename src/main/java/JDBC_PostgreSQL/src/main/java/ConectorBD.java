import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConectorBD implements ConexionBD {
    private String usuario;
    private String url;
    private String password;

    public ConectorBD() {
        Map<String, String> envVars = loadEnvFile();
        this.usuario = envVars.get("DB_USER");
        this.url = envVars.get("DB_URL");
        this.password = envVars.get("DB_PASSWORD");
    }

    // Método para cargar variables de entorno desde el archivo .env
    private static Map<String, String> loadEnvFile() {
        Map<String, String> env = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // Ignorar líneas vacías o comentarios
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    env.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo .env: " + e.getMessage());
        }
        return env;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public void crearTabla(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabla creada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}

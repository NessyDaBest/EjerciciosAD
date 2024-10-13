import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalizadorDatosAbiertos {

    public static void main(String[] args) {
        System.out.println("Introduce la ruta del archivo: ");
        String rutaArchivo = new Scanner(System.in).nextLine();

        if (rutaArchivo.endsWith(".csv")) {
            List<String[]> datosCSV = parsearCSV(rutaArchivo);
            mostrarResumenCSV(datosCSV);
        } else if (rutaArchivo.endsWith(".json")) {
            JsonObject datosJSON = parsearJSON(rutaArchivo);
            mostrarResumenJSON(datosJSON);
        } else if (rutaArchivo.endsWith(".xml")) {
            Document datosXML = parsearXML(rutaArchivo);
            mostrarResumenXML(datosXML);
        } else {
            System.out.println("Formato de archivo no soportado.");
        }
    }

    public static List<String[]> parsearCSV(String rutaArchivo) {
        List<String[]> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                registros.add(valores);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return registros;
    }

    public static JsonObject parsearJSON(String rutaArchivo) {
        JsonObject jsonObject = null;
        try (FileReader reader = new FileReader(rutaArchivo)) {
            jsonObject = new Gson().fromJson(reader, JsonObject.class);
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Document parsearXML(String rutaArchivo) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(rutaArchivo);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
        }
        return doc;
    }

    public static void mostrarResumenCSV(List<String[]> datos) {
        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos.");
            return;
        }
        System.out.println("Resumen del archivo CSV:");
        System.out.println("Número total de filas: " + datos.size());
        System.out.println("Número de columnas: " + datos.get(0).length);
        System.out.println("\nPrimeros 5 registros:");
        for (int i = 0; i < Math.min(5, datos.size()); i++) {
            System.out.println(String.join(" | ", datos.get(i)));
        }
    }

    public static void mostrarResumenJSON(JsonObject datos) {
        if (datos == null || datos.isEmpty()) {
            System.out.println("No se encontraron datos en el archivo JSON.");
            return;
        }

        System.out.println("Resumen del archivo JSON:");
        System.out.println("Número total de claves: " + datos.entrySet().size());

        // Mostrar las primeras 5 claves y sus valores
        System.out.println("\nPrimeros 5 pares clave-valor:");
        int count = 0;
        for (String key : datos.keySet()) {
            if (count < 5) {
                System.out.println(key + ": " + datos.get(key));
                count++;
            }
        }
    }

    public static void mostrarResumenXML(Document datos) {
        if (datos == null) {
            System.out.println("No se encontraron datos en el archivo XML.");
            return;
        }

        System.out.println("Resumen del archivo XML:");

        // Obtener el elemento raíz
        Element raiz = datos.getDocumentElement();
        System.out.println("Elemento raíz: " + raiz.getNodeName());

        // Obtener los nodos hijos del elemento raíz
        NodeList nodos = raiz.getChildNodes();
        System.out.println("Número total de nodos: " + nodos.getLength());

        // Mostrar los nombres de los primeros 5 nodos y su contenido
        System.out.println("\nPrimeros 5 nodos:");
        int count = 0;
        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i) instanceof Element && count < 5) {
                Element elem = (Element) nodos.item(i);
                System.out.println("Nodo: " + elem.getNodeName() + ", Contenido: " + elem.getTextContent());
                count++;


            }
        }
    }
}

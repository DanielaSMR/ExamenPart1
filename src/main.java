import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Main
* Donde se iniciara la base de datos y se llamara a las funciones
* @author Daniela Perez / Empresa
* @version 0.1, 
*/
public class main {
    public static ArrayList<Libro> libros  = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        
          try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/practica", "postgres", "a");
        Statement st = connection.createStatement();
        connection.setAutoCommit(false);

        GestorLibros.cargarDB(libros,st);
        
        int eleccion;
        do{
            System.out.println("Elige una opcion\n"+
            "1- Añadir un libro\n"+
            "2- Añadir dos libros\n"+
            "3- Actualizar un libro\n"+
            "4- Eliminar un libro\n"+
            "5- Consultar libros por autor\n"+
            "6- Consultar todos los libros\n"+
            "7- Salir\n");
            eleccion = (int)Integer.parseInt(IO.pedirTexto());
            switch (eleccion) {
                case 1:
                    GestorLibros.añadirLibros(libros, st);
                    break;
                case 2:
                    GestorLibros.añadirDosLibros(libros, st);
                    break;
                case 3:
                    GestorLibros.actualizarLibros(libros, st);
                    break;
                case 4:
                    GestorLibros.eliminarLibro(libros,st);
                    break;
                case 5:
                    GestorLibros.consultarAutores(libros);
                    break; 
                case 6:
                    GestorLibros.consultarLibros(libros);
                     break;
                case 7:
                    System.out.println("Adios");
                    break;
                default:
                    break;
            }
        }while(eleccion != 7);
        connection.commit();
        connection.close();
    }
}
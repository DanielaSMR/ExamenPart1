import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* GestorLibro
* Donde se ejecuta todos los metodos
* @author Daniela Perez / Empresa
* @version 0.1, 
*/
public class GestorLibros{

    public static void cargarDB(ArrayList<Libro> libros,Statement st) throws SQLException, Exception{
        libros.clear();
        try {
                String sentenciaSql = "	SELECT * FROM public.libroperez;";
                PreparedStatement sentencia = null;
                ResultSet resultado = null;
                
                try {
                    sentencia = st.getConnection().prepareStatement(sentenciaSql, Statement.RETURN_GENERATED_KEYS);
                    resultado = sentencia.executeQuery();

                    // ResultSet rs2 = st.executeQuery("SELECT autor FROM public.libroperez WHERE autor = 'censurado';");
                    // if(rs2.next()){
                    //     System.out.println("El libro no tiene autor por lo que no se cargara");
                    // }else{
                    while (resultado.next()) {
                        if(!resultado.getString("autor").matches("censurado")){
                            Libro libroCarga = new Libro(0,null, null, 0, null);
                            libroCarga.setId(resultado.getInt("id"));
                            libroCarga.setTitulo(resultado.getString("titulo"));
                            libroCarga.setAutor(resultado.getString("autor"));
                            libroCarga.setAny_publicacion(resultado.getInt("any_publicacion"));
                            libroCarga.setGenero(resultado.getString("genero"));


                            try{
                                libros.add(libroCarga);
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        
                    }
                }catch(SQLException sqle){
                    sqle.printStackTrace();
                }
                
                System.out.println("Libros cargados de la base de datos correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void añadirLibros(ArrayList<Libro> libros,Statement st) throws SQLException, Exception{

        System.out.println("Añade el titulo");
        String titulo = IO.pedirTexto();
        System.out.println("Escribe el autor");
        String autor = IO.pedirTexto();
        System.out.println("Escribe la año de publicacion");
        Integer any = IO.pedirEntero();
        System.out.println("Escribe el genero");
        String genero = IO.pedirTexto();

        PreparedStatement ps = null;
        ResultSet resultado = null;
        try {
                String sentenciaSql = "INSERT INTO public.libroperez (titulo, autor, any_publicacion, genero) VALUES (?, ?, ?, ?);";
                ps = st.getConnection().prepareStatement(sentenciaSql,  Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, titulo);
                ps.setString(2, autor);
                ps.setInt(3, any);
                ps.setString(4, genero);
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = ps.getGeneratedKeys();
                    if(resultado.next()){
                        int id = resultado.getInt(1);

                        Libro libroTemporal = new Libro(id, titulo, autor, any, genero);
                        libros.add(libroTemporal);
                    }
                    System.out.println("Se añadió el libro con éxito.");
                    st.getConnection().commit();
                } else {
                    System.out.println("No se pudo añadir el libro.");
                }
                
               
            
            ps.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


    public static void añadirDosLibros(ArrayList<Libro> libros,Statement st) throws SQLException {//Arreglar mas tarde
        System.out.println("Añade el titulo");
        String titulo = IO.pedirTexto();
        System.out.println("Escribe el autor");
        String autor = IO.pedirTexto();
        System.out.println("Escribe la año de publicacion");
        Integer any = IO.pedirEntero();
        System.out.println("Escribe el genero");
        String genero = IO.pedirTexto();

        PreparedStatement ps = null;
        ResultSet resultado = null;
        try {
                String sentenciaSql = "INSERT INTO public.libroperez (titulo, autor, any_publicacion, genero) VALUES (?, ?, ?, ?);";
                ps = st.getConnection().prepareStatement(sentenciaSql,  Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, titulo);
                ps.setString(2, autor);
                ps.setInt(3, any);
                ps.setString(4, genero);
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = ps.getGeneratedKeys();
                    if(resultado.next()){
                        int id = resultado.getInt(1);

                        Libro libroTemporal = new Libro(id, titulo, autor, any, genero);
                        libros.add(libroTemporal);
                    }
                    System.out.println("Se añadió el libro con éxito.");
                    st.getConnection().commit();
                } else {
                    System.out.println("No se pudo añadir el libro.");
                }
                
                titulo += " parte2";
                String sentenciaSql2 = "INSERT INTO public.libroperez (titulo, autor, any_publicacion, genero) VALUES (?, ?, ?, ?);";
                ps = st.getConnection().prepareStatement(sentenciaSql2,  Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, titulo);
                ps.setString(2, autor);
                ps.setInt(3, any);
                ps.setString(4, genero);
                int filasAfectadas2 = ps.executeUpdate();
                if (filasAfectadas2 > 0) {
                    resultado = ps.getGeneratedKeys();
                    if(resultado.next()){
                        int id = resultado.getInt(1);

                        Libro libroTemporal = new Libro(id, titulo, autor, any, genero);
                        libros.add(libroTemporal);
                    }
                    System.out.println("Se añadió el libro con éxito.");
                    st.getConnection().commit();
                } else {
                    System.out.println("No se pudo añadir el libro.");
                }
               
            
            ps.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void actualizarLibros(ArrayList<Libro> libros,Statement st)  throws Exception {
        consultarLibros(libros);
        System.out.println("Que libro quieres actualizar?(Dime el id)");
        int id = IO.pedirEntero();
        System.out.println("Que quieres cambiar? 1-Titulo 2-Autor 3-Año 4-Genero");
        int eleccion = (int)Integer.parseInt(IO.pedirTexto());
        switch (eleccion) {
            case 1:
                System.out.println("Dime el nuevo titulo");
                String titulo = IO.pedirTexto();
                String sentenciaSqlTitulo = "UPDATE public.libroperez SET titulo = ? WHERE id = ? ;";
                try{
                    boolean b = false;
                    ResultSet rs = st.executeQuery("SELECT * FROM libroperez");
                    while(rs.next()){
                        if(rs.getInt("id") == id){
                            PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSqlTitulo);
                            ps.setString(1, titulo);
                            ps.setInt(2,id);
                            int filasAfectadas = ps.executeUpdate();
                            b = true;
                            if (filasAfectadas > 0) {
                                for(Libro lib : libros){
                                    if(lib.getId() == id){
                                        lib.setTitulo(titulo);
                                        System.out.println("Array actualizado");
                                    }
                                }
                                System.out.println("Se actualizo el libro con éxito.");
                                st.getConnection().commit();
                            } else {
                                System.out.println("No se pudo actualizar el libro.");
                            }
                            ps.close();}
                    }
                    if(!b){
                        System.out.println("No se encontro el libro");
                    }
                    rs.close();
                }catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Dime el nuevo autor");
                String autor = IO.pedirTexto();
                String sentenciaSqlAutor = "UPDATE public.libroperez SET autor = ? WHERE id = ? ;";
                try{
                    boolean b = false;
                    ResultSet rs = st.executeQuery("SELECT * FROM libroperez");
                    while(rs.next()){
                        if(rs.getInt("id") == id){
                            PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSqlAutor);
                            ps.setString(1, autor);
                            ps.setInt(2,id);
                            int filasAfectadas = ps.executeUpdate();
                            b = true;
                            if (filasAfectadas > 0) {
                                for(Libro lib : libros){
                                    if(lib.getId() == id){
                                        lib.setTitulo(autor);
                                        System.out.println("Array actualizado");
                                    }
                                }
                                System.out.println("Se actualizo el libro con éxito.");
                                st.getConnection().commit();
                            } else {
                                System.out.println("No se pudo actualizar el libro.");
                            }
                            ps.close();}
                    }
                    if(!b){
                        System.out.println("No se encontro el libro");
                    }
                    rs.close();
                }catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Dime el nuevo año");
                int anyo = IO.pedirEntero();
                String sentenciaSqlAnyo = "UPDATE public.libroperez SET any_publicacion = ? WHERE id = ? ;";
                try{
                    boolean b = false;
                    ResultSet rs = st.executeQuery("SELECT * FROM libroperez");
                    while(rs.next()){
                        if(rs.getInt("id") == id){
                            PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSqlAnyo);
                            ps.setInt(1, anyo);
                            ps.setInt(2,id);
                            int filasAfectadas = ps.executeUpdate();
                            b = true;
                            if (filasAfectadas > 0) {
                                for(Libro lib : libros){
                                    if(lib.getId() == id){
                                        lib.setAny_publicacion(anyo);
                                        System.out.println("Array actualizado");
                                    }
                                }
                                System.out.println("Se actualizo el libro con éxito.");
                                st.getConnection().commit();
                            } else {
                                System.out.println("No se pudo actualizar el libro.");
                            }
                            ps.close();}
                    }
                    if(!b){
                        System.out.println("No se encontro el libro");
                    }
                    rs.close();
                }catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case 4:
                System.out.println("Dime el nuevo genero");
                String genero = IO.pedirTexto();
                String sentenciaSqlGenero = "UPDATE public.libroperez SET genero = ? WHERE id = ? ;";
                try{
                    boolean b = false;
                    ResultSet rs = st.executeQuery("SELECT * FROM libroperez");
                    while(rs.next()){
                        if(rs.getInt("id") == id){
                            PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSqlGenero);
                            ps.setString(1, genero);
                            ps.setInt(2,id);
                            int filasAfectadas = ps.executeUpdate();
                            b = true;
                            if (filasAfectadas > 0) {
                                for(Libro lib : libros){
                                    if(lib.getId() == id){
                                        lib.setGenero(genero);
                                        System.out.println("Array actualizado");
                                    }
                                }
                                System.out.println("Se actualizo el libro con éxito.");
                                st.getConnection().commit();
                            } else {
                                System.out.println("No se pudo actualizar el libro.");
                            }
                            ps.close();}
                    }
                    if(!b){
                        System.out.println("No se encontro el libro");
                    }
                    rs.close();
                }catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            default:
                break;
        }


       
    }

    public static void eliminarLibro(ArrayList<Libro> libros,Statement st)  throws Exception{
        System.out.println("Cual es la id del libro quieres borrar?");
            consultarLibros(libros);
            int id = IO.pedirEntero();
            String sentenciaSql = "DELETE FROM public.libroperez WHERE id = ? ;";
            try{
                boolean b = false;
                ResultSet rs = st.executeQuery("SELECT * FROM libroperez");
                while(rs.next()){
                    if(rs.getInt(1) == id){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setInt(1, id);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        b = true;
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                            System.out.println("Fueron afectadas " + filasAfectadas + " fila/s");
                            st.getConnection().commit();
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();}
                }
                if(!b){
                    System.out.println("No se encontro el libro");
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            
        boolean encontrado = false;
        Iterator<Libro> iterator = libros.iterator();//Recorre el array

        while (iterator.hasNext() && !encontrado) {
            Libro libroTemp = iterator.next();

            if (libroTemp.getId() == id) {
                System.out.println("Se eliminará el empleado " + libroTemp.getTitulo());
                iterator.remove(); // Eliminar el empleado usando el iterador
                encontrado = true;
            }
        }

        if(!encontrado){//Excepcion personalizada
            throw new LibroNoEncontrado("Libro no encontrado");
        }
    }
    
    public static void consultarAutores(ArrayList<Libro> libros) throws Exception{
        System.out.println("Escribe el nombre del autor que buscas");
        String autor = IO.pedirTexto();
       int contador = 0;
       System.out.println("Los libros con este autor:");
       for(int i = 0;i < libros.size();i++){
            if(autor.equals(libros.get(i).getAutor())){
               contador++;
               System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");

    }

    public static void consultarLibros(ArrayList<Libro> libros) throws Exception{
        System.out.println("Estos son los Libros");
        for(Libro lib : libros){
            System.out.println("ID: " + lib.getId() + "nombre:" + lib.getTitulo());
        }

    }
}

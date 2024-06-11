
/**
* LibroNoEncontradoException
* Una clase que tiene una excepcion personalizada
* @author Daniela Perez / Empresa
* @version 0.1, 
*/
public class LibroNoEncontrado extends Exception {
    public LibroNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
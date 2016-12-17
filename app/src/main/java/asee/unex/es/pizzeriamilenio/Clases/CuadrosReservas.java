package asee.unex.es.pizzeriamilenio.Clases;

/**
 * Created by juan on 20/11/16.
 */

public class CuadrosReservas {
    private String nombre, fecha, hora, numComensales, ident;

    public CuadrosReservas(String nombre, String fecha, String hora, String numComensales, String ident) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.numComensales = numComensales;
        this.ident = ident;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getIdent() {
        return ident;
    }

    public String getNumComensales() {
        return numComensales;
    }
}
package datagramafiles;

import java.io.Serializable;

/*@author Isaac*/
public class Files implements Serializable        
{    
    private int nP;
    private long pesoF;
    private String rutaF;
    private String nombreF;
    private byte[] datos;
  
    public Files(byte[] datosF, String nombreFi, String rutaA, long peso, int nP)
    {        
        datos = datosF;
        nombreF = nombreFi;
        rutaF = rutaA;
        pesoF = peso;
        this.nP = nP;
    }
    
    public int getnP() {
        return nP;
    }

    public void setnP(int nP) {
        this.nP = nP;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
    
    public long getPesoF() {
        return pesoF;
    }

    public void setPesoF(long pesoF) {
        this.pesoF = pesoF;
    }

    public String getRutaF() {
        return rutaF;
    }

    public void setRutaF(String rutaF) {
        this.rutaF = rutaF;
    }

    public String getNombreF() {
        return nombreF;
    }

    public void setNombreF(String nombreF) {
        this.nombreF = nombreF;
    }

}

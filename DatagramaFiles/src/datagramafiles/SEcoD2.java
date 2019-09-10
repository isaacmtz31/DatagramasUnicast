package datagramafiles;
import java.net.*;
import java.io.*;
/**
 *
 * @author axele
 */
public class SEcoD2 {
    public static void main(String[] args){
        try{
            
            long pesoL = 0;
            long recibidos = 0;
            
            int pto = 1234;
            int max = 65535;

            DatagramSocket s = new DatagramSocket(pto);            
            System.out.println("Servidor de datagrama iniciado en el puerto "+s.getLocalPort());
            String nombre = "";
            String peso = ""    ;
            while(true)
            {
                DatagramPacket p = new DatagramPacket(new byte[max],max);                                
                s.receive(p);               
                peso = new String(p.getData(), 0, p.getLength());
                s.receive(p);
                nombre = new String(p.getData(), 0, p.getLength());
                //DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\Users\\Isaac\\Desktop\\datagrama\\"+nombre));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\Users\\alumno\\Desktop\\Datagrama\\"+nombre));
                try
                {
                    pesoL = Long.parseLong(peso);
                    while(recibidos < pesoL)
                    {                                                
                        s.receive(p);
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));                
                        Files f = (Files)ois.readObject();                
                        System.out.println("Segmento: "+ f.getnP());
                        recibidos = recibidos + f.getPesoF();                        
                        dos.write(f.getDatos(), 0, f.getDatos().length);                        
                        System.out.println("PORCETAJE DEL ARCHIVO: " + ((recibidos*100))/pesoL);
                    }
                    System.out.println("RECIBIDO");   
                    dos.close();
                }catch(Exception ee)
                {
                    System.out.println("Imposible castear");
                    ee.printStackTrace();
                }                                
            }//while             
        }catch(Exception e){
            System.out.println("Error en el socket ");
            e.printStackTrace();
        }
    }
}

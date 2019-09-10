package datagramafiles;

import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;


/*@author Isaac*/
public class CEcoD2 
{
    public static void main(String[] args)
    {
        try
        {            
            JFileChooser jf = new JFileChooser();
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jf.setMultiSelectionEnabled(true); 
            int r = jf.showOpenDialog(null);            
            if(r == JFileChooser.APPROVE_OPTION)
            {
                File[] f = jf.getSelectedFiles();
                for (int i = 0; i < f.length; i++) 
                {
                    if(f[i].isFile())
                    {
                        sentFile(f[i]);
                    }
                    else
                    {
                        System.out.println("something");
                    }
                }                  
            }
                        
        }catch(Exception e){
            e.printStackTrace();
        }
    }//main
    
    public static void sentFile(File f)
    {
        int br;
        int brx;
        int pto = 1234;
        int tamPaq = 1500;
        long peso = f.length();
        
        try
        {
            InetAddress direccion = InetAddress.getByName("127.0.0.1");
            DatagramSocket cl = new DatagramSocket();            
            DataInputStream dis = new DataInputStream(new FileInputStream(f.getAbsolutePath()));                    
            System.out.println("Cliente iniciado en el puerto: " + cl.getLocalPort());                       
            
            byte[] b = new byte[tamPaq]; //MTU tarjeta de red                                  
            
            
            /*Mandamos el peso del archivo*/
            String wg = peso + "";
            byte[] c = wg.getBytes();
            DatagramPacket p1 = new DatagramPacket(c, c.length, direccion, pto);
            cl.send(p1);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            
            String nam = f.getName();
            byte[] c1 = nam.getBytes();
            DatagramPacket p11 = new DatagramPacket(c1, c1.length, direccion, pto);
            cl.send(p11);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }//
            
            
            if(tamPaq < peso) //Fragmentamos
            {
                System.out.println("FRAGMENTAMOS");                                             
                long numPart = f.length() / tamPaq; //Numero de partes que tenemos que fragmentar
                
                if(peso % tamPaq > 0)
                    numPart = numPart + 1;
                
                for(int i = 0; i < numPart; i++)
                {
                    br = dis.read(b);                  
                    ByteArrayInputStream bais = new ByteArrayInputStream(b);                     
                    byte[] b2 = new byte[br];
                    brx = bais.read(b2);                        
                    Files fi = new Files(b2, f.getName(), f.getAbsolutePath(), brx, i);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(fi);
                    oos.flush();
                    byte[] tmp = baos.toByteArray();
                    System.out.println("Tamaño del paquete #"+ i + ":  -  "+tmp.length);                      
                    DatagramPacket p = new DatagramPacket(tmp, tmp.length, direccion, pto);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    cl.send(p); 
                    oos.close();
                    baos.close();    
                
                }//for                                                                 
            } else{      
                        br = dis.read(b);                                          
                        Files d = new Files(b, f.getName(), f.getAbsolutePath(), br, 0);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(d);
                        oos.flush();
                        byte[]tmp = baos.toByteArray();
                        //DatagramPacket p = new DatagramPacket(b2,n,dir,pto);
                        DatagramPacket p = new DatagramPacket(tmp, tmp.length, direccion, pto);
                        cl.send(p);                        
                        oos.close();
                        baos.close();
                        
                }//else
                dis.close();
                cl.close();
            }catch(Exception e)
            {
                System.out.println("ERROR"); e.printStackTrace();
            }
    }
}//class


package optiuam.bc.modelo;

import java.util.ArrayList;

public class MedidorPotencia extends Componente {
    
    private Double sensibilidad = 0.0;
    
    public MedidorPotencia(String name, int id,String elementoConectado, boolean conectado) {
        this.nombre = name;

        this.id = id;
    }

    public MedidorPotencia() {
    }
    
    public double calcularPerdidas(){
        return  0.0;
    }

    public double calcularPotencia(ArrayList<Componente> elementos){
        double Dt= 0.0; //dispersion cromatica total
        double Pa= 0.0; //perdida por atenuacion de la fibra L*Fa 
        double S = 0.0; // anchura espectral
        double L = 0.0; // longitud de la fibra en km
        double Fa= 0.0; // atenuacion de la fibra
        double Dc= 0.0; // dispersion de la fibra
        double B = 0.0; // taza de bits
        double Pd= 0.0; //perdida por dispersion del enlace
        double Pt= 0.0; //perdida total del enlace 
        double Tp= 0.0; //potencia de la fuente dB
        //int    Tc=0;    //cantidad de conectores en el enlace
        double Pc =0.0; //perdida de los conectores
        ArrayList<Double> conectores = new ArrayList();//guarda las perdidas de los conectores en un enlace
        //int    Te=0;    //cantidad de empalmes en el enlace
        double Pe=0.0;  //perdida de los empalmes
        ArrayList<Double> empalmes = new ArrayList();//guarda las perdidas de los empalmes en un enlace
        double Ps=0.0;  //perdida del splitter
        int    Se=0;    //salidas del splitter
       // boolean isSplitter=false; //para saber si hubo un splitter en el enlace
        for(int i = elementos.size()-1;i>=0;i--){
            if(elementos.get(i).getNombre().contains("splitter")){
                //isSplitter=true;
                Splitter splitter_aux = (Splitter)elementos.get(i);
                Ps= splitter_aux.getPerdidaInsercion();
                Se=splitter_aux.getSalidas();
                Dt = Dc * S *L; // picosegungo x10-12
                Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
               System.out.println("Splitter ---Perdida por dispersion ="+ Pd);
                Pc=perdidaConectores_Empalmes(conectores);
               System.out.println("Splitter ---Perdida conectores="+Pc);
                Pe=perdidaConectores_Empalmes(empalmes);
                 System.out.println("Splitter ---Perdida empalmes="+Pe);
                Pa = L*Fa;
                 System.out.println("Splitter ---Perdida atenuacion fibra="+Pa);
                // Math.pow(2,(Se+1)) -> puede ser 2,4,8,16,32,64 
                Tp=(Tp-(Pd + Pc + Pe + Pa + Ps))/Math.pow(2,(Se+1)); 
                System.out.println("Splitter ----Potencia ="+Tp);
                //inicializar los valores a 0 para los nuevos enlaces
                L=0.0;
                conectores=new ArrayList();
                empalmes= new ArrayList();
                
                
            } 
            
            if(elementos.get(i).getNombre().contains("source")){
                Fuente fuente_aux = (Fuente)elementos.get(i);
                B=fuente_aux.getVelocidad();
                S=fuente_aux.getAnchura();
                Tp=fuente_aux.getPotencia();
            } 
            
            if(elementos.get(i).getNombre().contains("connector")){
                Conector conector_aux = (Conector)elementos.get(i);
                conectores.add(conector_aux.getPerdidaInsercion());
            }
            
            if(elementos.get(i).getNombre().contains("fiber")){
                Fibra fibra_aux = (Fibra)elementos.get(i);
                Dc = fibra_aux.getDispersion();
                Fa = fibra_aux.getAtenuacion();
                L = L + fibra_aux.getLongitud_km();
            }
            
            if(elementos.get(i).getNombre().contains("splice")){
                Empalme empalme_aux = (Empalme)elementos.get(i);
                empalmes.add(empalme_aux.getPerdidaInsercion());
            }
        }
        
       /* if(isSplitter){
            Dt = Dc * S *L; // picosegungo x10-12
            Pd = 0;
            //System.out.println("Perdida por dispersion ="+ Pd);
            Pc=perdidaConectores_Empalmes(conectores);
            //System.out.println("Perdida conectores="+Pc);
            Pe=perdidaConectores_Empalmes(empalmes);
           // System.out.println("Perdida empalmes="+Pe);
            Pa = L*Fa;
           // System.out.println("Perdida atenuacion fibra="+Pa);
            Pt=(Tp-(sensibilidad))-(Pd + Pc + Pe + Pa);
            BigDecimal auxPt = BigDecimal.valueOf(Pt); // convierte los valores tipo x10^# a x10^0
            
            return (Math.floor(Pt*100)/100);
        }*/
        //if(((B*Math.pow(10, 9))*(Dt*Math.pow(10, -12)))>0.25)//si B(x10^9) * Dt(x10-12) >.25 
          //  return-2;
        //else{
            Dt = Dc * S *L; // picosegungo x10-12
            Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
            System.out.println("Perdida por dispersion ="+ Pd);
            Pc=perdidaConectores_Empalmes(conectores);
            System.out.println("Perdida conectores="+Pc);
            Pe=perdidaConectores_Empalmes(empalmes);
            System.out.println("Perdida empalmes="+Pe);
            Pa = L*Fa;
            System.out.println("Perdida atenuacion fibra="+Pa);
            Pt=(Tp-(sensibilidad))-(Pd + Pc + Pe + Pa);
            //BigDecimal auxPt = BigDecimal.valueOf(Pt); // convierte los valores tipo x10^# a x10^0
            System.out.println("Potencia ="+Pt);
            System.out.println("Potencia ="+Math.floor(Pt*100)/100);
            return (Math.floor(Pt*100)/100);
        //}
    }
    
    public double perdidaConectores_Empalmes(ArrayList<Double> lista){
        Double perdidaTotal = 0.0;
        if(lista.size()==0)
            return perdidaTotal;
        for(Double perdida:lista)
            perdidaTotal=perdidaTotal+perdida;
        return perdidaTotal;
    }
    
    
    /*toString*/
    
    @Override
    public String toString() {
        return super.toString()+","+sensibilidad;
    }

    
    /*Getter and Setter*/
    
    public Double getSensibilidad() {
        return sensibilidad;
    }

    public void setSensibilidad(Double sensibilidad) {
        this.sensibilidad = sensibilidad;
    }
    
}
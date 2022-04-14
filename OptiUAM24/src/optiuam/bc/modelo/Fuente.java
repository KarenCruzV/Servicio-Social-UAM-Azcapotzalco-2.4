
package optiuam.bc.modelo;

public class Fuente extends Componente {
    
    /*Attribute Declaration*/
    private int tipo; // 0 laser | 1 led
    private double potencia;// 50 max na min mechanic | 50 max na min fusion
    private double anchura;
    private double velocidad;
    private int longitudOnda; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    
    //for the pulse 
    private float C = 0; //chirp
    private float A0 = 0; // amplitude
    private float W0 = 0; // frequency
    private float T0 = 0; // anchura
    private float M = 0; // gaussian or supergaussian
    
    private int idFuente;
    
    /*Constructor*/

    public Fuente() {
    }

    public Fuente(String name,int id,String elementoConectado, boolean conectado,int type, double potency, double width, double velocity, int wavelength) {
        this.tipo = type;
        this.potencia = potency;
        this.anchura = width;
        this.velocidad = velocity;
        this.longitudOnda = wavelength;
        this.elementoConectado= elementoConectado;
        this.conectado=conectado;
        this.nombre = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getAnchura() {
        return anchura;
    }

    public void setAnchura(double anchura) {
        this.anchura = anchura;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public int getLongitudOnda() {
        return longitudOnda;
    }

    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    public float getC() {
        return C;
    }

    public float getA0() {
        return A0;
    }

    public float getW0() {
        return W0;
    }

    public float getT0() {
        return T0;
    }

    public float getM() {
        return M;
    }
    
    public void setPulso(float A0, float T0, float W0, float C, float M){
        this.C = C; 
        this.A0 = A0;
        this.W0 = W0;
        this.T0 = T0;
        this.M = M;
    }
    
    
    /*Calcular pulso*/
    public float [] calcularPulso(){
        int n = 512;
        if(T0 <=35)
            n=256;
        else if(T0 >35 && T0< 100)
            n= 512;
        else
            n=1024;
            
        
        NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
        NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
        chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
        chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)
    
        //System.out.println(chirpXi.toString());
        NumeroComplejo[] Et= new NumeroComplejo[n];
        NumeroComplejo aux=null;
        for(int t=-(n/2); t<(n/2);t++){
            aux = new NumeroComplejo(chirpXi.getParteReal(), chirpXi.getParteImaginaria());
            aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
            Et[(n/2)+t]=aux;
            //System.out.println((t+1+256)+""+aux.toString());
        }
        NumeroComplejo aux2=null;
        for(int t=-(n/2); t<(n/2);t++){
             aux2 = new NumeroComplejo(Et[(n/2)+t].getParteReal(),Et[(n/2)+t].getParteImaginaria());
            //System.out.println((t+1+256)+";;;;"+aux2.toString());
             float x = aux2.getParteReal();
             float y = aux2.getParteImaginaria();
  
             aux2.setParteReal((float) (Math.exp(x)*Math.cos(y)));
             aux2.setParteImaginaria((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
             //System.out.println((t+1+256)+";;;;"+aux2.toString());
             aux2.multiplicar(A0,false);
             Et[(n/2)+t]=aux2;
             //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());
           
        }
        NumeroComplejo aux3=null;
        NumeroComplejo[] Ej= new NumeroComplejo[n];
        for(int t=-(n/2); t<(n/2);t++){
            //System.out.println("----------");
            aux3 = new NumeroComplejo(W0*t*0,-1*W0*t);
            //System.out.println(Et[256+t].toString());
            //System.out.println((t+1+256)+";;;;"+aux3.toString());
            float x = aux3.getParteReal();
            float y = aux3.getParteImaginaria();
            //System.out.println(x);
            //System.out.println(y);
            Ej[(n/2)+t]=Et[(n/2)+t].multiplicar(new NumeroComplejo((float) (Math.exp(x)*Math.cos(y)),(float) (Math.exp(x)*Math.sin(y))),true);
            //System.out.println((t+1+256)+";;;;"+Ej[256+t].toString());
           
        }
        
        float[] valoresReales = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresReales[(n/2)+t]= Ej[(n/2)+t].getParteReal();
        }
        return valoresReales;
    }

    public int getIdFuente() {
        return idFuente;
    }

    public void setIdFuente(int idFuente) {
        this.idFuente = idFuente;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() +","+ tipo + "," + potencia + "," + anchura + "," 
                + velocidad + "," + longitudOnda + "," + A0 + "," + T0 + "," 
                + W0 + "," + C + "," + M+","+idFuente;
    }
    //Fuente fuente = new Fuente("fuente", 0," ",false,tipo, potencia, anchura, velocidad, longitudOnda);
}
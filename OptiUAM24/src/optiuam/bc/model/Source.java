/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class Source extends Component {
    
    /*Attribute Declaration*/
    private int type; // 0 laser | 1 led
    private double potency;// 50 max na min mechanic | 50 max na min fusion
    private double width;
    private double velocity;
    private int wavelength; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    
    //for the pulse 
    private float C = 0; //chirp
    private float A0 = 0; // amplitude
    private float W0 = 0; // frequency
    private float T0 = 0; // width
    private float M = 0; // gaussian or supergaussian
    
    
    /*Constructor*/

    public Source() {
    }

    public Source(int type, double potency, double width, double velocity, int wavelength, 
            String name, int id) {
        this.type = type;
        this.potency = potency;
        this.width = width;
        this.velocity = velocity;
        this.wavelength = wavelength;
        this.name = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPotency() {
        return potency;
    }

    public void setPotency(double potency) {
        this.potency = potency;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getWavelength() {
        return wavelength;
    }

    public void setWavelength(int wavelength) {
        this.wavelength = wavelength;
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
            
        
        ComplexNumber complejo = new ComplexNumber(0, 1); // i o j
        ComplexNumber chirpXi= complejo.multiplicar(C, true);// i*C
        chirpXi.sumar(new ComplexNumber(1, 0), false); //1 + iC
        chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)
    
        //System.out.println(chirpXi.toString());
        ComplexNumber[] Et= new ComplexNumber[n];
        ComplexNumber aux=null;
        for(int t=-(n/2); t<(n/2);t++){
            aux = new ComplexNumber(chirpXi.getRealPart(), chirpXi.getImaginaryPart());
            aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
            Et[(n/2)+t]=aux;
            //System.out.println((t+1+256)+""+aux.toString());
        }
        ComplexNumber aux2=null;
        for(int t=-(n/2); t<(n/2);t++){
             aux2 = new ComplexNumber(Et[(n/2)+t].getRealPart(),Et[(n/2)+t].getImaginaryPart());
            //System.out.println((t+1+256)+";;;;"+aux2.toString());
             float x = aux2.getRealPart();
             float y = aux2.getImaginaryPart();
  
             aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
             aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
             //System.out.println((t+1+256)+";;;;"+aux2.toString());
             aux2.multiplicar(A0,false);
             Et[(n/2)+t]=aux2;
             //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());
           
        }
        ComplexNumber aux3=null;
        ComplexNumber[] Ej= new ComplexNumber[n];
        for(int t=-(n/2); t<(n/2);t++){
            //System.out.println("----------");
            aux3 = new ComplexNumber(W0*t*0,-1*W0*t);
            //System.out.println(Et[256+t].toString());
            //System.out.println((t+1+256)+";;;;"+aux3.toString());
            float x = aux3.getRealPart();
            float y = aux3.getImaginaryPart();
            //System.out.println(x);
            //System.out.println(y);
            Ej[(n/2)+t]=Et[(n/2)+t].multiplicar(new ComplexNumber((float) (Math.exp(x)*Math.cos(y)),(float) (Math.exp(x)*Math.sin(y))),true);
            //System.out.println((t+1+256)+";;;;"+Ej[256+t].toString());
           
        }
        
        float[] valoresReales = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresReales[(n/2)+t]= Ej[(n/2)+t].getRealPart();
        }
        return valoresReales;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + type + ", " + potency + ", " + width + ", " 
                + velocity + ", " + wavelength + ", " + A0 + ", " + T0 + ", " 
                + W0 + ", " + C + ", " + M;
    }
    
}


package optiuam.bc.modelo;


/**
 * Clase NumeroComplejo la cual contiene los atributos principales de un numero complejo
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class NumeroComplejo {
    
    /**Parte real del numero complejo*/
    private float parteReal;
    /**Parte imaginaria del numero complejo*/
    private float parteImaginaria;

    /**
     * Metodo constructor con parametros
     * @param parteReal Parte real del numero complejo
     * @param parteImaginaria Parte imaginaria del numero complejo
     */
    public NumeroComplejo(float parteReal, float parteImaginaria) {
        this.parteReal = parteReal;
        this.parteImaginaria = parteImaginaria;
    }

    /**Metodo que retorna la amplitud del numero complejp
     * @return amplitud*/
    public float getAmplitud() {
        return (float) Math.sqrt(Math.pow(parteReal, 2) + Math.pow(parteImaginaria, 2));
    }
    
    /**Metodo que retorna la fase del numero complejo
     * @return fase
     */
    public float getFase() {
        if (parteReal == 0) {
            if (parteImaginaria == 0) {
                return 0;
            } else if (parteImaginaria > 0) {
                return (float) (Math.PI / 2);
            } else {
                return (float) (-Math.PI / 2);
            }
        } else {
            return (float) Math.atan(parteImaginaria / parteReal);
        }
    }
    
    /**
     * Metodo que suma un numero complejo con otro this+sumando. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param sumando Numero complejo sumador
     * @param nuevo Nueva suma
     * @return resultado de la suma
     */
    public NumeroComplejo sumar(NumeroComplejo sumando, boolean nuevo) {
        if (sumando == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            }
            return this;
        }
        if (nuevo) {
            return new NumeroComplejo(this.parteReal + sumando.parteReal, this.parteImaginaria + sumando.parteImaginaria);
        }
        this.parteReal += sumando.parteReal;
        this.parteImaginaria += sumando.parteImaginaria;
        return this;
    }
    
    /**
     * Metodo que sresta un numero complejo con otro this-sustraendo.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param sustraendo Numero complejo restador
     * @param nuevo Nueva resta
     * @return resultado de la resta
     */
    public NumeroComplejo restar(NumeroComplejo sustraendo, boolean nuevo) {
        if (sustraendo == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            }
            return this;
        }
        if (nuevo) {
            return new NumeroComplejo(this.parteReal - sustraendo.parteReal, this.parteImaginaria - sustraendo.parteImaginaria);
        }
        this.parteReal -= sustraendo.parteReal;
        this.parteImaginaria -= sustraendo.parteImaginaria;
        return this;
    }

    
    /**
     * Metodo que multiplica un numero complejo con otro this*multiplicador. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param multiplicador Numero complejo multiplicador
     * @param nuevo Nueva multiplicacion
     * @return resultado de la multiplicacion
     */
    public NumeroComplejo multiplicar(NumeroComplejo multiplicador, boolean nuevo) {
        if (multiplicador == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            }
            return this;
        }

        float auxReal = this.parteReal * multiplicador.parteReal - this.parteImaginaria * multiplicador.parteImaginaria;
        float auxImaginaria = this.parteReal * multiplicador.parteImaginaria + this.parteImaginaria * multiplicador.parteReal;
        if (nuevo) {
            return new NumeroComplejo(auxReal, auxImaginaria);
        }
        this.parteReal = auxReal;
        this.parteImaginaria = auxImaginaria;
        return this;
    }

    /**
     * Metodo que multiplica un numero complejo con una constante this*multiplicador. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param multiplicador Constante multiplicador
     * @param nuevo Nueva multiplicacion
     * @return resultado de la multiplicacion
     */
    public NumeroComplejo multiplicar(float multiplicador, boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal * multiplicador, this.parteImaginaria * multiplicador);
        }
        this.parteReal *= multiplicador;
        this.parteImaginaria *= multiplicador;
        return this;
    }

    /**
     * Metodo que divide un numero complejo con otro this/divisor.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param divisor Numero complejo divisor
     * @param nuevo Nueva division
     * @return resultado de la division
     */
    public NumeroComplejo dividir(NumeroComplejo divisor, boolean nuevo) {
        if (divisor == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            } else {
                return this;
            }
        }
        float sumBase = (float) (Math.pow(divisor.parteReal, 2) + Math.pow(divisor.parteImaginaria, 2));
        float auxReal = (this.parteReal * divisor.parteReal + this.parteImaginaria * divisor.parteImaginaria) / sumBase;
        float auxImaginaria = (this.parteReal * divisor.parteImaginaria - this.parteImaginaria * divisor.parteReal) / sumBase;
        if (nuevo) {
            return new NumeroComplejo(auxReal, auxImaginaria);
        }
        this.parteReal = auxReal;
        this.parteImaginaria = auxImaginaria;
        return this;
    }
    
    /**
     * Metodo que regresa un nuevo numero complejo conjugado o el mismo.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param nuevo Nueva conjugacion
     * @return resultado de conjugacion
     */
    public NumeroComplejo conjugar(boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal, -this.parteImaginaria);
        }
        this.parteImaginaria = -this.parteImaginaria;
        return this;
    }

    /**
     * Metodo que muestra la parte real del numero complejo
     * @return parteReal
     */
    public float getParteReal() {
        return parteReal;
    }

    /**
     * Metodo que modifica la parte real del numero complejo
     * @param parteReal Parte real del numero complejo
     */
    public void setParteReal(float parteReal) {
        this.parteReal = parteReal;
    }
    
    /**
     * Metodo que muestra la parte imaginaria del numero complejo
     * @return parteImaginaria
     */
    public float getParteImaginaria() {
        return parteImaginaria;
    }

    /**
     * Metodo que modifica la parte imaginaria del numero complejo
     * @param parteImaginaria Parte imaginaria del numero complejo
     */
    public void setParteImaginaria(float parteImaginaria) {
        this.parteImaginaria = parteImaginaria;
    }
    
    /**
     * Metodo que modifica los valores del numero complejo
     * @param real Parte real
     * @param imaginaria Parte imaginaria
     */
    public void setValores(float real, float imaginaria) {
        this.parteReal = real;
        this.parteImaginaria = imaginaria;
    }
    
    /**
     * Asigna el valor de 0 al numero complejo
     */
    public void Limpiar() {
        this.parteReal = 0;
        this.parteImaginaria = 0;
    }

    
    /**
     * Metodo toString que retorna la parte real e imaginaria del numero complejo
     * @return parteReal, parteImaginaria
     */
    @Override
    public String toString() {
        if (parteImaginaria < 0) {
            return parteReal + "" + parteImaginaria + "i";
        }
        return parteReal + "+" + parteImaginaria + "i";
    }
    
}
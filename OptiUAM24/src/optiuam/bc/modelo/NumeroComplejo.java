
package optiuam.bc.modelo;

public class NumeroComplejo {
    
    private float parteReal;
    private float parteImaginaria;

    public NumeroComplejo(float realPart, float imaginaryPart) {
        this.parteReal = realPart;
        this.parteImaginaria = imaginaryPart;
    }

    //regresa la amplitud del numero complejo
    public float getAmplitud() {
        return (float) Math.sqrt(Math.pow(parteReal, 2) + Math.pow(parteImaginaria, 2));
    }
    //regresa la fase del numero complejo

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
    ///suma un numero complejo con otro this+sumando
    //si nuevo= true regresa el resultado en un nuevo objeto

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
    //resta un numero complejo con otro this-sustraendo
    //si nuevo= true regresa el resultado en un nuevo objeto

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

    //multiplica un numero complejo con otro this*multiplicador
    //si nuevo= true regresa el resultado en un nuevo objeto
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

    //multiplica un numero complejo con una constante this*multiplicador
    //si nuevo= true regresa el resultado en un nuevo objeto
    public NumeroComplejo multiplicar(float multiplicador, boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal * multiplicador, this.parteImaginaria * multiplicador);
        }
        this.parteReal *= multiplicador;
        this.parteImaginaria *= multiplicador;
        return this;
    }

    //Divide un numero complejo con otro this/divisor
    //si nuevo= true regresa el resultado en un nuevo objeto
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
    //regresa un nuevo numero complejo conjugado o el mismo

    public NumeroComplejo conjugar(boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal, -this.parteImaginaria);
        }
        this.parteImaginaria = -this.parteImaginaria;
        return this;
    }

    //regresa la parte real de numero complejo
    public float getParteReal() {
        return parteReal;
    }

    public void setParteReal(float parteReal) {
        this.parteReal = parteReal;
    }
    //regresa la parte imaginaria de un numero complejo

    public float getParteImaginaria() {
        return parteImaginaria;
    }

    public void setParteImaginaria(float parteImaginaria) {
        this.parteImaginaria = parteImaginaria;
    }
    //

    public void setValores(float real, float imaginaria) {
        this.parteReal = real;
        this.parteImaginaria = imaginaria;
    }
    //asigna el valor de 0 al numero complejo

    public void Limpiar() {
        this.parteReal = 0;
        this.parteImaginaria = 0;
    }

    //regresa en un String la parte real y la parte imaginara
    @Override
    public String toString() {
        if (parteImaginaria < 0) {
            return parteReal + "" + parteImaginaria + "i";
        }
        return parteReal + "+" + parteImaginaria + "i";
    }
    
}
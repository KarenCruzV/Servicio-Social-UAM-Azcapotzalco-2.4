/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class ComplexNumber {
    
    private float realPart;
    private float imaginaryPart;

    public ComplexNumber(float realPart, float imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    //regresa la amplitud del numero complejo
    public float getAmplitud() {
        return (float) Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
    }
    //regresa la fase del numero complejo

    public float getFase() {
        if (realPart == 0) {
            if (imaginaryPart == 0) {
                return 0;
            } else if (imaginaryPart > 0) {
                return (float) (Math.PI / 2);
            } else {
                return (float) (-Math.PI / 2);
            }
        } else {
            return (float) Math.atan(imaginaryPart / realPart);
        }
    }
    ///suma un numero complejo con otro this+sumando
    //si nuevo= true regresa el resultado en un nuevo objeto

    public ComplexNumber sumar(ComplexNumber sumando, boolean nuevo) {
        if (sumando == null) {
            if (nuevo) {
                return new ComplexNumber(this.realPart, this.imaginaryPart);
            }
            return this;
        }
        if (nuevo) {
            return new ComplexNumber(this.realPart + sumando.realPart, this.imaginaryPart + sumando.imaginaryPart);
        }
        this.realPart += sumando.realPart;
        this.imaginaryPart += sumando.imaginaryPart;
        return this;
    }
    //resta un numero complejo con otro this-sustraendo
    //si nuevo= true regresa el resultado en un nuevo objeto

    public ComplexNumber restar(ComplexNumber sustraendo, boolean nuevo) {
        if (sustraendo == null) {
            if (nuevo) {
                return new ComplexNumber(this.realPart, this.imaginaryPart);
            }
            return this;
        }
        if (nuevo) {
            return new ComplexNumber(this.realPart - sustraendo.realPart, this.imaginaryPart - sustraendo.imaginaryPart);
        }
        this.realPart -= sustraendo.realPart;
        this.imaginaryPart -= sustraendo.imaginaryPart;
        return this;
    }

    //multiplica un numero complejo con otro this*multiplicador
    //si nuevo= true regresa el resultado en un nuevo objeto
    public ComplexNumber multiplicar(ComplexNumber multiplicador, boolean nuevo) {
        if (multiplicador == null) {
            if (nuevo) {
                return new ComplexNumber(this.realPart, this.imaginaryPart);
            }
            return this;
        }

        float auxReal = this.realPart * multiplicador.realPart - this.imaginaryPart * multiplicador.imaginaryPart;
        float auxImaginaria = this.realPart * multiplicador.imaginaryPart + this.imaginaryPart * multiplicador.realPart;
        if (nuevo) {
            return new ComplexNumber(auxReal, auxImaginaria);
        }
        this.realPart = auxReal;
        this.imaginaryPart = auxImaginaria;
        return this;
    }

    //multiplica un numero complejo con una constante this*multiplicador
    //si nuevo= true regresa el resultado en un nuevo objeto
    public ComplexNumber multiplicar(float multiplicador, boolean nuevo) {
        if (nuevo) {
            return new ComplexNumber(this.realPart * multiplicador, this.imaginaryPart * multiplicador);
        }
        this.realPart *= multiplicador;
        this.imaginaryPart *= multiplicador;
        return this;
    }

    //Divide un numero complejo con otro this/divisor
    //si nuevo= true regresa el resultado en un nuevo objeto
    public ComplexNumber dividir(ComplexNumber divisor, boolean nuevo) {
        if (divisor == null) {
            if (nuevo) {
                return new ComplexNumber(this.realPart, this.imaginaryPart);
            } else {
                return this;
            }
        }
        float sumBase = (float) (Math.pow(divisor.realPart, 2) + Math.pow(divisor.imaginaryPart, 2));
        float auxReal = (this.realPart * divisor.realPart + this.imaginaryPart * divisor.imaginaryPart) / sumBase;
        float auxImaginaria = (this.realPart * divisor.imaginaryPart - this.imaginaryPart * divisor.realPart) / sumBase;
        if (nuevo) {
            return new ComplexNumber(auxReal, auxImaginaria);
        }
        this.realPart = auxReal;
        this.imaginaryPart = auxImaginaria;
        return this;
    }
    //regresa un nuevo numero complejo conjugado o el mismo

    public ComplexNumber conjugar(boolean nuevo) {
        if (nuevo) {
            return new ComplexNumber(this.realPart, -this.imaginaryPart);
        }
        this.imaginaryPart = -this.imaginaryPart;
        return this;
    }

    //regresa la parte real de numero complejo
    public float getRealPart() {
        return realPart;
    }

    public void setRealPart(float realPart) {
        this.realPart = realPart;
    }
    //regresa la parte imaginaria de un numero complejo

    public float getImaginaryPart() {
        return imaginaryPart;
    }

    public void setImaginaryPart(float imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }
    //

    public void setValores(float real, float imaginaria) {
        this.realPart = real;
        this.imaginaryPart = imaginaria;
    }
    //asigna el valor de 0 al numero complejo

    public void Limpiar() {
        this.realPart = 0;
        this.imaginaryPart = 0;
    }

    //regresa en un String la parte real y la parte imaginara
    @Override
    public String toString() {
        if (imaginaryPart < 0) {
            return realPart + "" + imaginaryPart + "i";
        }
        return realPart + "+" + imaginaryPart + "i";
    }
    
}

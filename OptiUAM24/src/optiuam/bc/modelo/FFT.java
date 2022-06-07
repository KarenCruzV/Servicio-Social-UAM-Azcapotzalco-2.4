
package optiuam.bc.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase FFT la cual contiene los atributos principales de la Transformada
 * Rapida de Fourier al igual que otros metodos
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class FFT {
    
    /**Tamaño*/
    private int size;
    /**Frecuencia de muestreo*/
    private int frecuenciaMuestreo;
    /**Numeros complejos*/
    private NumeroComplejoArray numerosComplejos;
    /**Tiempo*/
    private int tiempo;
    /**Vector indice*/
    private int[] vectorIndice;
    /**Raices*/
    private RaizUnitaria raices;
    /**Transformada Rapida de Fourier ordenada*/
    private float[] FFTordenados;
    /**Transformada Rapida de Fourier Inversa ordenada*/
    private float[] IFFTordenados;

    /**Metodo constructor con parametros
     * @param size Tamaño
     * @param frecuenciaMuestreo Frecuencia de muestreo*/
    public FFT(int size, int frecuenciaMuestreo) {
            inicializar(size,frecuenciaMuestreo);
    }

    /**
     * Metodo que se utiliza en el metodo constructor para inicializar los 
     * atributos
     */
    private void inicializar(int size, int frecuenciaMuestreo) {
            this.size = size;
            this.frecuenciaMuestreo = frecuenciaMuestreo;
            numerosComplejos = new NumeroComplejoArray(size);
            tiempo = calcularBinario(size-1);
            vectorIndice = crearIndice(size);
            raices =new RaizUnitaria(size);
            FFTordenados = new float[size];
            IFFTordenados = new float[size];
    }
    
    /**
     * Metodo el cual crea un índice después de la clasificación de paridad
     * @return indice
     */
    private int[] crearIndice(int n) {
        int[] indice = new int[n];
        int izquierda = 0;
        int mitad = n/2;
        int derecha = n;
        indice[izquierda] = 0;
        indice[mitad] = 1;
        ordenarIndice(indice,izquierda,mitad,1);
        ordenarIndice(indice,mitad,derecha,1);
        return indice;
    }
    
    /**Metodo en el cual se realiza el ordenamiento recursivo*/
    private void ordenarIndice(int[] indice, int izquierda ,int derecha, int multiple) {
        if(derecha - izquierda<=1) return;
        int valor = (int) Math.pow(2, multiple);
        int middle = (derecha+izquierda)/2;
        indice[middle]=(indice[izquierda]+valor);
        ordenarIndice(indice,izquierda,middle,multiple+1);
        ordenarIndice(indice,middle,derecha,multiple+1);
    }
    
    /**
     * Metodo que calcula un binario del numero
     * @param n Numero del cual se va a calcular el binario
     * @return cuenta*/
    public int calcularBinario(int n) {
        int cuenta = 0;
        while(n != 0) {
                cuenta++;
                n = n&(n -1);
        }
        return cuenta;
    }
    
    /**Metodo que ordena los datos en base al indice FFT*/
    private  float[] ordenarFFT(float[] datos) {
        limpiarArreglo(FFTordenados);
        for(int i=0;i<vectorIndice.length;i++) {
                int p = vectorIndice[i];
                FFTordenados[i] = datos[p];
        }
        return FFTordenados;
    }

    /**Metodo que ordena los datos en base al indice IFFT*/
    private  float[] ordenarIFFT(float[] data) {
        limpiarArreglo(IFFTordenados);
        for(int i=0;i<vectorIndice.length;i++) {
                int p = vectorIndice[i];
                IFFTordenados[p] = data[i];
        }
        return IFFTordenados;
    }
    
    /**Metodo que limpia el arreglo*/
    private void limpiarArreglo(float[] arreglo) {
            for(int i=0;i<arreglo.length;i++)
                    arreglo[i]=0;
    }
    
    /**Metodo el cual crea un arreglo de numeros complejos en base a un 
     * arreglo de numeros reales*/
    private NumeroComplejoArray crearArregloComplejos(float[] datos) {
        numerosComplejos.limpiarTodo();
        numerosComplejos.setNumerosComplejos(datos, null);
        return numerosComplejos;
    }

    /**
     * Metodo para la Transformada Rapida de Fourier
     * @param datos Datos a ordenar en base al indice FFT
     * @return arreglo de numeros complejos
     */
    public NumeroComplejoArray fft(float[] datos) {
        FFTordenados = ordenarFFT(datos);
        NumeroComplejoArray arregloComplejos = crearArregloComplejos(FFTordenados);

        for(int i=1;i<=tiempo;i++) {
            int sizeGrupo = (int)(Math.pow(2, i));
            int grupos = size/sizeGrupo;
            for(int j=0;j<grupos;j++) {
                int aux1 = j*sizeGrupo;
                int aux2 = aux1+sizeGrupo/2;
                for(int k=0;k<sizeGrupo/2;k++) {
                    arregloComplejos.multiplicar(aux2, raices.getRaizUnitaria(sizeGrupo, k));
                    NumeroComplejo complejo = arregloComplejos.getNumeroComplejo(aux2);
                    arregloComplejos.setNumeroComplejo(aux2, arregloComplejos.getParteReal(aux1), arregloComplejos.getParteImaginaria(aux1));
                    arregloComplejos.sumar(aux1, complejo);
                    arregloComplejos.restar(aux2, complejo);

                    aux1++;
                    aux2++;
                }
            }
        }
        return arregloComplejos;
    }

    /**Metodo para la Transformada Rapida de Fourier Inversa
     * @param arrayComplejos Arreglo de numeros complejos
     * @return ordenamiento de la parte real del arreglo de numeros complejos 
     * en base al indice IFFT*/
    public float[] ifft(NumeroComplejoArray arrayComplejos) {
        for(int i=tiempo;i>=0;i--) {
            int sizeGrupo = (int)(Math.pow(2, i));
            int grupos = size/sizeGrupo;
            for(int j=0;j<grupos;j++) {
                int aux1 = j*sizeGrupo;
                int aux2 = aux1+sizeGrupo/2;
                for(int k=0;k<sizeGrupo/2;k++) {
                    NumeroComplejo cNum = arrayComplejos.getNumeroComplejo(aux2);
                    arrayComplejos.setNumeroComplejo(aux2, arrayComplejos.getParteReal(aux1), arrayComplejos.getParteImaginaria(aux1));
                    arrayComplejos.sumar(aux1, cNum);
                    arrayComplejos.restar(aux2, cNum);
                    arrayComplejos.multiplicar(aux1, 0.5f);
                    arrayComplejos.multiplicar(aux2, raices.getRaizUnitaria(sizeGrupo, k).multiplicar(0.5f,true).conjugar(false));

                    aux1++;
                    aux2++;
                }
            }
        }
        return ordenarIFFT(arrayComplejos.getPartesReales());
    }

    /**Clase Analizador*/
    public static class Analizador{
        
        /**FFT fourier*/
        private FFT fourier; 
        
        /**
         * Metodo constructor con parametros
         * @param fourier FFT fourier
         */
        public Analizador(FFT fourier) {
                this.fourier = fourier;
        }
        
        /**
         * Metodo que muestra la amplitud maxima
         * @param complexNumberArray Arreglo de numeros complejos
         * @return amplitud
         */
        public float getMaxAmplitude(NumeroComplejoArray complexNumberArray) {
            float amplitude = 0;
            if(complexNumberArray != null) {
                float[] amplitudes = complexNumberArray.getAmplitudes();
                amplitude = maximo(amplitudes,0);
            }
            return amplitude;
        }
        
        /**
         * Metodo que muestra la amplitud maxima a excepcion del componente directo
         * @param complexNumberArray Arreglo de numeros complejos
         * @return amplitud
         */
        public float getMaxAmplitudeExceptDirectComponent(NumeroComplejoArray complexNumberArray) {
            float amplitude = 0;
            if(complexNumberArray != null) {
                float[] amplitudes = complexNumberArray.getAmplitudes();
                amplitude = maximo(amplitudes,1);
            }
            return amplitude;
        }

        /**
         * Metodo que muestra la frecuencia en la amplitud maxima
         * @param complexNumberArray Arreglo de numeros complejos
         * @return frecuencia
         */
        public float getFrequencyAtMaxAmplitude(NumeroComplejoArray complexNumberArray) {
            float frequency = 0;
            if(complexNumberArray != null) {
                float[] amplitudes = complexNumberArray.getAmplitudes();
                int index = indiceMaximo(amplitudes,0);
                frequency = index*resolucionFrecuencia();
            }
            return frequency;
        }

        /**
         * Metodo que muestra la frecuencia en la amplitud maxima a excepcion del componente directo
         * @param complexNumberArray Arreglo de numeros complejos
         * @return frecuencia
         */
        public float getFrequencyAtMaxAmplitudeExceptDirectComponent(NumeroComplejoArray complexNumberArray) {
            float frequency = 0;
            if(complexNumberArray != null) {
                float[] amplitudes = complexNumberArray.getAmplitudes();
                int index = indiceMaximo(amplitudes,1);
                frequency = index*resolucionFrecuencia();
            }
            return frequency;
        }
        
        /**
         * Metodo que retorna la fase maxima de un arreglo de numeros complejos
         * @param arreglo Arreglo de numeros complejos
         * @return fase maxima
         */
        public float faseMaxima(NumeroComplejoArray arreglo) {
            float fase = 0;
            if(arreglo != null) {
                float[] fases = arreglo.getFases();
                fase = maximo(fases,0);
            }
            return fase;
        }
        
        /**
         * Metodo que retorna la fase minima de un arreglo de numeros complejos
         * @param arreglo Arreglo de numeros complejos
         * @return fase minima
         */
        public float faseMinima(NumeroComplejoArray arreglo) {
            float fase = 0;
            if(arreglo != null) {
                    float[] fases = arreglo.getFases();
                    fase = minimo(fases,0);
            }
            return fase;
        }
        
        /**
         * Metodo que muestra la frecuencia en la fase maxima de un arreglo de 
         * numeros complejos
         * @param arregloComplejos Arreglo de numeros complejos
         * @return frecuencia
         */
        public float frecuenciaFaseMaxima(NumeroComplejoArray arregloComplejos) {
            float frecuencia = 0;
            if(arregloComplejos != null) {
                float[] fases = arregloComplejos.getFases();
                int indice = indiceMaximo(fases,0);
                frecuencia = indice*resolucionFrecuencia();
            }
            return frecuencia;
        }
        
        /**
         * Metodo que muestra la frecuencia en la fase minima de un arreglo de 
         * numeros complejos
         * @param arregloComplejos Arreglo de numeros complejos
         * @return frecuencia
         */
        public float frecuenciaFaseMinima(NumeroComplejoArray arregloComplejos) {
            float frecuencia = 0;
            if(arregloComplejos != null) {
                float[] fases = arregloComplejos.getFases();
                int indice = indiceMinimo(fases,0);
                frecuencia = indice*resolucionFrecuencia();
            }
            return frecuencia;
        }
        
        /**
         * Metodo que retorna la resolucion de la frecuencia
         * @return resolucion
         */
        public float resolucionFrecuencia() {
                return fourier.frecuenciaMuestreo/fourier.size;
        }
        
        /**
         * Metodo que retorna la frecuencia normalizada
         * @param frecuencia Frecuencia a normalizar 
         * @return frecuencia
         */
        public float frecuenciaNormalizada(float frecuencia) {
                return frecuencia/fourier.frecuenciaMuestreo;
        }
        
        /**
         * Metodo que retorna la frecuencia circular
         * @param frecuencia Frecuencia a utilizar
         * @return frecuencia
         */
        public float frecuenciaCircular(float frecuencia) {
                return (float)(frecuenciaNormalizada(frecuencia)*Math.PI*2);
        }
        
        /**
         * Metodo que retorna la frecuencia angular
         * @param frecuencia Frecuencia a utilizar
         * @return frecuencia
         */
        public float frecuenciaAngular(float frecuencia) {
                return (float)(frecuencia*Math.PI*2);
        }
        
        /**Metodo que encuentra el valor maximo de un arreglo desde un indice*/
        private float maximo(float[] arreglo, int indice) {
            float maximo = arreglo[indice];

            for(int i=indice+1;i<arreglo.length;i++) {
                if(arreglo[i]>maximo) {
                    maximo = arreglo[i];
                }
            }
            return maximo;
        }
        
        /**Metodo que encuentra el valor minimo en un arreglo desde un indice*/
        private float minimo(float[] arreglo, int indice) {
            float minimo = arreglo[indice];

            for(int i=indice+1;i<arreglo.length;i++) {
                if(arreglo[i]<minimo) {
                    minimo = arreglo[i];
                }
            }
            return minimo;
        }
        
        /**Metodo que obtiene el indice del maximo valor en un arreglo apartir 
         * de un indice inicial*/
        private int indiceMaximo(float[] arreglo,int indice) {
            float maximo = arreglo[indice];
            int index = indice;
            for(int i=indice+1;i<arreglo.length;i++) {
                if(arreglo[i]>maximo) {
                    maximo = arreglo[i];
                    index = i;
                }
            }
            return index;
        }
        
        /**Metodo que obtiene el indice del minimo valor en un arreglo apartir 
         * de un indice inicial*/
        private int indiceMinimo(float[] arreglo,int indice) {
            float minimo = arreglo[indice];
            int index = indice;
            for(int i=indice+1;i<arreglo.length;i++) {
                if(arreglo[i]<minimo) {
                    minimo = arreglo[i];
                    index = i;
                }
            }
            return index;
        }
    }
    
    /**Clase Raiz Unitaria*/
    class RaizUnitaria {
        
        /**Numero del cual se va a obtener la raiz*/
        private int n;
        /**Raices*/
        private List<NumeroComplejo> raices;

        /**Metodo constructor con parametros*/
        public RaizUnitaria(int n) {
            this.n = n;
            raices = new ArrayList<>();
            generarRaices();
        }

        /**Metodo encargado de generar las raices*/
        private void generarRaices() {
            for(int k=0;k<=n;k++) {
                float unit = (float) (2 * Math.PI  * k / n);
                raices.add(new NumeroComplejo((float)Math.cos(unit), -(float)Math.sin(unit)));
            }
        }

        /**Metodo que muestra las raices*/
        public List<NumeroComplejo> getRaices(){
            return raices;
        }
        
        /**Metodo que muestra la raiz unitaria*/
        public NumeroComplejo getRaizUnitaria(int n ,int k) {
            if(this.n == n) {
                return raices.get(k);
            }else if(this.n>n) {
                return raices.get(k*(this.n/n));
            }else if(this.n<n) {
                return raices.get(k/( n/this.n));
            }
            return null;
        }
    }
        
    /**Clase de arreglos de numeros complejos*/
    public class NumeroComplejoArray {

        /**Parte real del numero complejo*/
        private float[] partesReales;
        /**Parte imaginaria del numero complejo*/
        private float[] partesImaginarias;
        /**Tamaño*/
        private int size;

        /**
         * Metodo constructor con parametros
         * @param size Tamaño
         */
        public NumeroComplejoArray(int size) {
            if (size <= 0) {
                this.size = 128;
                partesReales = new float[this.size];
                partesImaginarias = new float[this.size];
            } else {
                this.size = size;
                partesReales = new float[size];
                partesImaginarias = new float[size];
            }
        }

        /**
         * Metodo que modifica posicion, parte real e imaginaria del numero complejo
         * @param posicion Posicion del numero complejo
         * @param real Parte real del numero complejo
         * @param imaginaria Parte imaginaria del numero complejo
         */
        public void setNumeroComplejo(int posicion, float real, float imaginaria) {
            partesReales[posicion] = real;
            partesImaginarias[posicion] = imaginaria;
        }

        /**
         * Metodo que modifica el numero complejo
         * @param posicion Posicion del numero complejo
         * @param numero Numero complejo
         */ 
        public void setNumeroComplejo(int posicion, NumeroComplejo numero) {
            if (numero != null) {
                partesReales[posicion] = numero.getRealPart();
                partesImaginarias[posicion] = numero.getImaginaryPart();
            }
        }
        
        /**
         * Metodo que modifica el arreglo de numeros complejos
         * @param array Arreglo de numeros complejos
         */
        public void setNumerosComplejos(NumeroComplejo[] array) {
            if (array != null) {
                for (int i = 0; i < size; i++) {
                    partesReales[i] = array[i].getRealPart();
                    partesImaginarias[i] = array[i].getImaginaryPart();
                }
            }
        }

        /**
         * Metodo que modifica las partes reales e imaginarias del arreglo
         * de numeros complejos
         * @param partesReales Partes reales de los numeros complejos
         * @param partesImaginarias Partes imaginarias de los numeros complejos
         */
        public void setNumerosComplejos(float[] partesReales, float[] partesImaginarias) {
            if (partesReales != null) {
                for (int i = 0; i < size; i++) {
                    this.partesReales[i] = partesReales[i];
                }
            }

            if (partesImaginarias != null) {
                for (int i = 0; i < size; i++) {
                    this.partesImaginarias[i] = partesImaginarias[i];
                }
            }
        }

        /**
         * Metodo que limpia la parte real e imaginaria del numero complejo
         * @param posicion Posicion del numero complejo
         */
        public void limpiarNumero(int posicion) {
            partesReales[posicion] = 0;
            partesImaginarias[posicion] = 0;
        }

        /**Metodo que limpia todo el numero complejo*/
        public void limpiarTodo() {
            for (int i = 0; i < size; i++) {
                limpiarNumero(i);
            }
        }
        
        /**
         * Metodo que muestra un numero complejo del arreglo de numeros complejos
         * @param posicion Posicion del numero complejo
         * @return Numero complejo
         */
        public NumeroComplejo getNumeroComplejo(int posicion) {
            return new NumeroComplejo(partesReales[posicion], partesImaginarias[posicion]);
        }

        /**
         * Metodo que muestra la parte real de un numero complejo del arreglo 
         * de numeros complejos
         * @param posicion Posicion del numero complejo
         * @return parte real
         */
        public float getParteReal(int posicion) {
            return partesReales[posicion];
        }

        /**
         * Metodo que muestra las partes reales del arreglo de numeros complejos
         * @return partes reales
         */
        public float[] getPartesReales() {
            return partesReales;
        }

        /**
         * Metodo que muestra la parte imaginaria de un numero complejo del arreglo 
         * de numeros complejos
         * @param posicion Posicion del numero complejo
         * @return parte imaginaria
         */
        public float getParteImaginaria(int posicion) {
            return partesImaginarias[posicion];
        }

        /**
         * Metodo que muestra las partes imaginarias del arreglo de numeros complejos
         * @return partes imaginarias
         */
        public float[] getPartesImaginarias() {
            return partesImaginarias;
        }

        /**
         * Metodo que modifica la parte real de un numero complejo del arreglo 
         * de numeros complejos
         * @param posicion Posicion del numero complejo
         * @param real Parte real del numero complejo
         */
        public void setParteReal(int posicion, float real) {
            partesReales[posicion] = real;
        }

        /**
         * Metodo que modifica la parte imaginaria de un numero complejo del arreglo 
         * de numeros complejos
         * @param posicion Posicion del numero complejo
         * @param imaginaria Parte imaginaria del numero complejo
         */
        public void setParteImaginaria(int posicion, float imaginaria) {
            partesImaginarias[posicion] = imaginaria;
        }
        
        /**
         * Metodo que muestra la amplitud de un numero complejo del arreglo de 
         * numeros complejos
         * @param posicion Posicion del numero complejo
         * @return amplitud
         */
        public float getAmplitud(int posicion) {
            return (float) Math.sqrt(Math.pow(partesReales[posicion], 2) + Math.pow(partesImaginarias[posicion], 2));
        }

        /**
         * Metodo que muestra las amplitudes del arreglo de numeros complejos
         * @return amplitudes
         */
        public float[] getAmplitudes() {
            float[] amplitudesArray = new float[size];
            for (int i = 0; i < size; i++) {
                amplitudesArray[i] = getAmplitud(i);
            }
            return amplitudesArray;
        }

        /**
         * Metodo que muestra la fase de un numero complejo del arreglo de 
         * numeros complejos
         * @param posicion Posicion del numero complejo
         * @return fase
         */
        public float getFase(int posicion) {
            float real = partesReales[posicion];
            float imaginaria = partesImaginarias[posicion];
            if (real == 0) {
                if (imaginaria == 0) {
                    return 0;
                } else if (imaginaria > 0) {
                    return (float) (Math.PI / 2);
                } else {
                    return (float) (-Math.PI / 2);
                }
            } else {
                return (float) Math.atan(imaginaria / real);
            }
        }

        /**
         * Metodo que muestra las fases del arreglo de numeros complejos
         * @return fases
         */
        public float[] getFases() {
            float[] fases = new float[size];
            for (int i = 0; i < size; i++) {
                fases[i] = getFase(i);
            }
            return fases;
        }

        /**
         * Metodo que suma un numero complejo del arreglo de numeros complejos
         * con otro (sumando)
         * @param posicion Posicion del numero complejo
         * @param sumando Numero complejo sumador
         */
        public void sumar(int posicion, NumeroComplejo sumando) {
            if (sumando != null) {
                partesReales[posicion] += sumando.getRealPart();
                partesImaginarias[posicion] += sumando.getImaginaryPart();
            }
        }
        
        /**
         * Metodo que suma todos los numeros del arreglo de numeros complejos
         * con otro (sumando)
         * @param sumando Numero complejo sumador
         */
        public void sumarTodo(NumeroComplejo sumando) {
            if (sumando != null) {
                for (int i = 0; i < size; i++) {
                    sumar(i, sumando);
                }
            }
        }

        /**
         * Metodo que resta un numero complejo del arreglo de numeros complejos
         * con otro (sustraendo)
         * @param posicion Posicion del numero complejo
         * @param sustraendo Numero complejo restador
         */
        public void restar(int posicion, NumeroComplejo sustraendo) {
            if (sustraendo != null) {
                partesReales[posicion] -= sustraendo.getRealPart();
                partesImaginarias[posicion] -= sustraendo.getImaginaryPart();
            }
        }
        
        /**
         * Metodo que resta todos los numeros del arreglo de numeros complejos
         * con otro (sustraendo)
         * @param sustraendo Numero complejo restador
         */
        public void restarTodo(NumeroComplejo sustraendo) {
            if (sustraendo != null) {
                for (int i = 0; i < size; i++) {
                    restar(i, sustraendo);
                }
            }
        }
        
        /**
         * Metodo que multiplica un numero complejo del arreglo de numeros complejos
         * con otro (multiplicador)
         * @param posicion Posicion del numero complejo
         * @param multiplicador Numero complejo multiplicador
         */
        public void multiplicar(int posicion, NumeroComplejo multiplicador) {
            if (multiplicador != null) {
                float auxReal = partesReales[posicion] * multiplicador.getRealPart() - partesImaginarias[posicion] * multiplicador.getImaginaryPart();
                float auxImaginario = partesReales[posicion] * multiplicador.getImaginaryPart() + partesImaginarias[posicion] * multiplicador.getRealPart();
                partesReales[posicion] = auxReal;
                partesImaginarias[posicion] = auxImaginario;
            }
        }
        
        /**
         * Metodo que multiplica todos los numeros del arreglo de numeros complejos
         * con otro (multiplicador)
         * @param multiplicador Numero complejo multiplicador
         */
        public void multiplicarTodo(NumeroComplejo multiplicador) {
            if (multiplicador != null) {
                for (int i = 0; i < size; i++) {
                    multiplicar(i, multiplicador);
                }
            }
        }
        
        /**
         * Metodo que multiplica un numero complejo del arreglo de numeros complejos
         * con un numero flotante (multiplicador)
         * @param position Posicion del numero complejo
         * @param multiplicador Numero flotante multiplicador
         */
        public void multiplicar(int position, float multiplicador) {
            partesReales[position] *= multiplicador;
            partesImaginarias[position] *= multiplicador;
        }
        
        /**
         * Metodo que multiplica todos los numeros del arreglo de numeros complejos
         * con un numero flotante (multiplicador)
         * @param multiplicador Numero flotante multiplicador
         */
        public void multiplicarTodo(float multiplicador) {
            for (int i = 0; i < size; i++) {
                multiplicar(i, multiplicador);
            }
        }
        
        /**
         * Metodo que divide un numero complejo del arreglo de numeros complejos
         * con otro (divisor)
         * @param posicion Posicion del numero complejo
         * @param divisor Numero complejo divisor
         */
        public void dividir(int posicion, NumeroComplejo divisor) {
            if (divisor != null) {
                float sumBase = (float) (Math.pow(divisor.getRealPart(), 2) + Math.pow(divisor.getImaginaryPart(), 2));
                float auxReal = (partesReales[posicion] * divisor.getRealPart() + partesImaginarias[posicion] * divisor.getImaginaryPart()) / sumBase;
                float auxImaginario = (partesImaginarias[posicion] * divisor.getRealPart() - partesReales[posicion] * divisor.getImaginaryPart()) / sumBase;
                partesReales[posicion] = auxReal;
                partesImaginarias[posicion] = auxImaginario;
            }
        }
        
        /**
         * Metodo que conjuga un numero complejo del arreglo de numeros complejos
         * @param posicion Posicion del numero complejo
         */
        public void conjugar(int posicion) {
            partesImaginarias[posicion] = -partesImaginarias[posicion];
        }
        
        /**
         * Metodo que conjuga todos los numeros del arreglo de numeros complejos
         */
        public void conjugarTodo() {
            for (int i = 0; i < size; i++) {
                conjugar(i);
            }
        }

        /**
         * Metodo que muestra un numero complejo del arreglo de numeros complejos
         * @param posicion Posicion del numero complejo
         * @return Numero complejo
         */
        public String getNumero(int posicion) {
            if (partesImaginarias[posicion] < 0) {
                return partesReales[posicion] + "" + partesImaginarias[posicion] + "i";
            }
            return partesReales[posicion] + "+" + partesImaginarias[posicion] + "i";
        }
    }

}
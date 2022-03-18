
package optiuam.bc.modelo;

import java.util.ArrayList;
import java.util.List;

public class FFT {
	private int size;
	private int frecuenciaMuestreo;
	private NumeroComplejoArray numerosComplejos;
	private int tiempo;
	private int[] vectorIndice;
	private RaizUnitaria raices;
	private float[] FFTordenados;
	private float[] IFFTordenados;
        
	public FFT(int size, int frecuenciaMuestreo) {
		inicializar(size,frecuenciaMuestreo);
	}
	
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
	//Crea índice después de la clasificación de paridad
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
	//ordenamiento Recursivo
	private void ordenarIndice(int[] indice, int izquierda ,int derecha, int multiple) {
		if(derecha - izquierda<=1) return;
		int valor = (int) Math.pow(2, multiple);
		int middle = (derecha+izquierda)/2;
		indice[middle]=(indice[izquierda]+valor);
		ordenarIndice(indice,izquierda,middle,multiple+1);
		ordenarIndice(indice,middle,derecha,multiple+1);
		
	}
	//calcula un binario del numero 
	public int calcularBinario(int n) {
		int cuenta = 0;
		while(n != 0) {
			cuenta++;
			n = n&(n -1);
		}
		return cuenta;
	}
	//ordenar los datos en base al indice
	//FFT
	private  float[] ordenarFFT(float[] datos) {
		limpiarArreglo(FFTordenados);
		for(int i=0;i<vectorIndice.length;i++) {
			int p = vectorIndice[i];
			FFTordenados[i] = datos[p];
		}
		return FFTordenados;
	}
        
	//ordenar los datos en base al indice
	//IFFT
	private  float[] ordenarIFFT(float[] data) {
		limpiarArreglo(IFFTordenados);
		for(int i=0;i<vectorIndice.length;i++) {
			int p = vectorIndice[i];
			IFFTordenados[p] = data[i];
		}
		return IFFTordenados;
	}
	//limpia el arrary
	private void limpiarArreglo(float[] arreglo) {
		for(int i=0;i<arreglo.length;i++)
			arreglo[i]=0;
	}
	//Crea un arreglo de complejos en base a un arreglo de reales
	private NumeroComplejoArray crearArregloComplejos(float[] datos) {
		numerosComplejos.limpriarTodo();
		numerosComplejos.setNumerosComplejos(datos, null);
		return numerosComplejos;
	}
    
	//FFT
	public  NumeroComplejoArray fft(float[] datos) {
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

	//IFFT
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
	
	public static class Analizador{
		private FFT fourier; 
		public Analizador(FFT fourier) {
			this.fourier = fourier;
		}
		
		public float getMaxAmplitude(NumeroComplejoArray complexNumberArray) {
			float amplitude = 0;
			if(complexNumberArray != null) {
				float[] amplitudes = complexNumberArray.getAmplitudes();
				amplitude = maximo(amplitudes,0);
			}
			return amplitude;
		}
		
		public float getMaxAmplitudeExceptDirectComponent(NumeroComplejoArray complexNumberArray) {
			float amplitude = 0;
			if(complexNumberArray != null) {
				float[] amplitudes = complexNumberArray.getAmplitudes();
				amplitude = maximo(amplitudes,1);
			}
			return amplitude;
		}
		
		public float getFrequencyAtMaxAmplitude(NumeroComplejoArray complexNumberArray) {
			float frequency = 0;
			if(complexNumberArray != null) {
				float[] amplitudes = complexNumberArray.getAmplitudes();
				int index = indiceMaximo(amplitudes,0);
				frequency = index*resolucionFrecuencia();
			}
			return frequency;
		}
		
		public float getFrequencyAtMaxAmplitudeExceptDirectComponent(NumeroComplejoArray complexNumberArray) {
			float frequency = 0;
			if(complexNumberArray != null) {
				float[] amplitudes = complexNumberArray.getAmplitudes();
				int index = indiceMaximo(amplitudes,1);
				frequency = index*resolucionFrecuencia();
			}
			return frequency;
		}
		//regresa la fase maxima de un arreglo de complejos
		public float faseMaxima(NumeroComplejoArray arreglo) {
			float fase = 0;
			if(arreglo != null) {
				float[] fases = arreglo.getFases();
				fase = maximo(fases,0);
			}
			return fase;
		}
		
                //regresa la fase minima de un arreglo de complejos
		public float faseMinima(NumeroComplejoArray arreglo) {
			float fase = 0;
			if(arreglo != null) {
				float[] fases = arreglo.getFases();
				fase = minimo(fases,0);
			}
			return fase;
		}
        
		public float frecuenciaFaseMaxima(NumeroComplejoArray arregloComplejos) {
			float frecuencia = 0;
			if(arregloComplejos != null) {
				float[] fases = arregloComplejos.getFases();
				int indice = indiceMaximo(fases,0);
				frecuencia = indice*resolucionFrecuencia();
			}
			return frecuencia;
		}
		
		public float frecuenciaFaseMinima(NumeroComplejoArray arregloComplejos) {
			float frecuencia = 0;
			if(arregloComplejos != null) {
				float[] fases = arregloComplejos.getFases();
				int indice = indiceMinimo(fases,0);
				frecuencia = indice*resolucionFrecuencia();
			}
			return frecuencia;
		}
		
		
		public float resolucionFrecuencia() {
			return fourier.frecuenciaMuestreo/fourier.size;
		}
		
		public float frecuenciaNormalizada(float frecuencia) {
			return frecuencia/fourier.frecuenciaMuestreo;
		}
		
		public float frecuenciaCircular(float frecuencia) {
			return (float)(frecuenciaNormalizada(frecuencia)*Math.PI*2);
		}
		
		public float frecuenciaAngular(float frecuencia) {
			return (float)(frecuencia*Math.PI*2);
		}
		
		//encuentra el valor maximo de un arreglo desde un indice
		private float maximo(float[] arreglo, int indice) {
			float maximo = arreglo[indice];
			
			for(int i=indice+1;i<arreglo.length;i++) {
				if(arreglo[i]>maximo) {
					maximo = arreglo[i];
				}
			}
			return maximo;
		}
		//Encuentra el valor minimo en un arreglo desde un indice
		private float minimo(float[] arreglo, int indice) {
			float minimo = arreglo[indice];
			
			for(int i=indice+1;i<arreglo.length;i++) {
				if(arreglo[i]<minimo) {
					minimo = arreglo[i];
				}
			}
			return minimo;
		}
	    //obtiene el indice del maximo valor en un arreglo apartir de un indice inicial 
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
                //obtiene el indice del maximo valor en un arreglo apartir de un indice inicial 
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
        
        class RaizUnitaria {
        	private int n;
	private List<NumeroComplejo> raices;
	
	public RaizUnitaria(int n) {
		this.n = n;
		raices = new ArrayList<>();
		generarRaices();
	}
	
	private void generarRaices() {
		for(int k=0;k<=n;k++) {
			float unit = (float) (2 * Math.PI  * k / n);
			raices.add(new NumeroComplejo((float)Math.cos(unit), -(float)Math.sin(unit)));
		}
	}
	
	public List<NumeroComplejo> getRaices(){
		return raices;
	}
	
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
        
    public class NumeroComplejoArray {

    private float[] partesReales;
    private float[] partesImaginarias;
    private int size;

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

    public void setNumeroComplejo(int posicion, float real, float imaginaria) {
        partesReales[posicion] = real;
        partesImaginarias[posicion] = imaginaria;
    }

    public void setNumeroComplejo(int posicion, NumeroComplejo numero) {
        if (numero != null) {
            partesReales[posicion] = numero.getParteReal();
            partesImaginarias[posicion] = numero.getParteImaginaria();
        }
    }

    public void setNumerosComplejos(NumeroComplejo[] array) {
        if (array != null) {
            for (int i = 0; i < size; i++) {
                partesReales[i] = array[i].getParteReal();
                partesImaginarias[i] = array[i].getParteImaginaria();
            }
        }
    }

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

    public void limpiarNumero(int posicion) {
        partesReales[posicion] = 0;
        partesImaginarias[posicion] = 0;
    }

    public void limpriarTodo() {
        for (int i = 0; i < size; i++) {
            limpiarNumero(i);
        }
    }

    public NumeroComplejo getNumeroComplejo(int posicion) {
        return new NumeroComplejo(partesReales[posicion], partesImaginarias[posicion]);
    }

    public float getParteReal(int posicion) {
        return partesReales[posicion];
    }

    public float[] getPartesReales() {
        return partesReales;
    }

    public float getParteImaginaria(int posicion) {
        return partesImaginarias[posicion];
    }

    public float[] getPartesImaginarias() {
        return partesImaginarias;
    }

    public void setParteReal(int posicion, float real) {
        partesReales[posicion] = real;
    }

    public void setParteImaginaria(int posicion, float imaginaria) {
        partesImaginarias[posicion] = imaginaria;
    }

    public float getAmplitud(int posicion) {
        return (float) Math.sqrt(Math.pow(partesReales[posicion], 2) + Math.pow(partesImaginarias[posicion], 2));
    }

    public float[] getAmplitudes() {
        float[] amplitudesArray = new float[size];
        for (int i = 0; i < size; i++) {
            amplitudesArray[i] = getAmplitud(i);
        }
        return amplitudesArray;
    }

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

    public float[] getFases() {
        float[] fases = new float[size];
        for (int i = 0; i < size; i++) {
            fases[i] = getFase(i);
        }
        return fases;
    }

    public void sumar(int posicion, NumeroComplejo sumando) {
        if (sumando != null) {
            partesReales[posicion] += sumando.getParteReal();
            partesImaginarias[posicion] += sumando.getParteImaginaria();
        }
    }

    //este metodo suma todos los numeros del array con el sumando
    public void sumarTodo(NumeroComplejo sumando) {
        if (sumando != null) {
            for (int i = 0; i < size; i++) {
                sumar(i, sumando);
            }
        }
    }

    public void restar(int posicion, NumeroComplejo sustraendo) {
        if (sustraendo != null) {
            partesReales[posicion] -= sustraendo.getParteReal();
            partesImaginarias[posicion] -= sustraendo.getParteImaginaria();
        }
    }
    

    public void restarTodo(NumeroComplejo sustraendo) {
        if (sustraendo != null) {
            for (int i = 0; i < size; i++) {
                restar(i, sustraendo);
            }
        }
    }
   
    public void multiplicar(int posicion, NumeroComplejo multiplicador) {
        if (multiplicador != null) {
            float auxReal = partesReales[posicion] * multiplicador.getParteReal() - partesImaginarias[posicion] * multiplicador.getParteImaginaria();
            float auxImaginario = partesReales[posicion] * multiplicador.getParteImaginaria() + partesImaginarias[posicion] * multiplicador.getParteReal();
            partesReales[posicion] = auxReal;
            partesImaginarias[posicion] = auxImaginario;
        }
    }
    

    public void multiplicarTodo(NumeroComplejo multiplicador) {
        if (multiplicador != null) {
            for (int i = 0; i < size; i++) {
                multiplicar(i, multiplicador);
            }
        }
    }
    

    public void multiplicar(int position, float multiplicador) {
        partesReales[position] *= multiplicador;
        partesImaginarias[position] *= multiplicador;
    }
    

    public void multiplicarTodo(float multiplicador) {
        for (int i = 0; i < size; i++) {
            multiplicar(i, multiplicador);
        }
    }
    

    public void dividir(int posicion, NumeroComplejo divisor) {
        if (divisor != null) {
            float sumBase = (float) (Math.pow(divisor.getParteReal(), 2) + Math.pow(divisor.getParteImaginaria(), 2));
            float auxReal = (partesReales[posicion] * divisor.getParteReal() + partesImaginarias[posicion] * divisor.getParteImaginaria()) / sumBase;
            float auxImaginario = (partesImaginarias[posicion] * divisor.getParteReal() - partesReales[posicion] * divisor.getParteImaginaria()) / sumBase;
            partesReales[posicion] = auxReal;
            partesImaginarias[posicion] = auxImaginario;
        }
    }
   
    public void conjugar(int posicion) {
        partesImaginarias[posicion] = -partesImaginarias[posicion];
    }

    public void conjugarTodo() {
        for (int i = 0; i < size; i++) {
            conjugar(i);
        }
    }

    public String getNumero(int posicion) {
        if (partesImaginarias[posicion] < 0) {
            return partesReales[posicion] + "" + partesImaginarias[posicion] + "i";
        }
        return partesReales[posicion] + "+" + partesImaginarias[posicion] + "i";
    }

  }

}
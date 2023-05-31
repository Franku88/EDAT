package estructuras.conjuntistas.estaticas;

/************* Autores ***********
- Franco Fabian Benitez, Legajo FAI-3169
*/

public class HeapMin {
    private Comparable[] heap;
    private int TAMANIO = 20;
    private int ultimo;
    
    public HeapMin() {
        this.heap = new Comparable[this.TAMANIO];
        this.ultimo = 0;
    }

    public boolean insertar(Comparable elem) {
        boolean exito;
        if (this.ultimo < this.TAMANIO-1) {
            exito = true;
            this.ultimo++;
            this.heap[this.ultimo] = elem;
            hacerSubir(this.ultimo);
        } else {
            exito = false;
        }
        return exito;
    }

    private void hacerSubir(int posHijo) {
        //Metodo que acomoda un nodo de manera que el padre del mismo sea menor a el
        boolean ordenado = false;
        Comparable auxHijo = this.heap[posHijo];
        //Mientras no este ordenado
        while (!ordenado) {
            //Calculo posicion del padre
            int posPadre = (int)(posHijo/2);
            //Si existe padre (hijo no es raiz)
            if (posPadre > 0) {
                //Si hijo es menor al padre, se intercambian
                if (auxHijo.compareTo(this.heap[posPadre]) < 0) {
                    this.heap[posHijo] = this.heap[posPadre];
                    this.heap[posPadre] = auxHijo;
                    posHijo = posPadre;
                } else {
                    //Si hijo es mayor a su padre
                    ordenado = true;
                }
            } else {
                //Si hijo es raiz
                ordenado = true;
            }
        }
    }

    public boolean eliminarCima() {
        boolean exito;
        //Si el arreglo no esta vacio
        if (this.ultimo > 0) {
            exito = true;
            this.heap[1] = this.heap[ultimo];
            this.ultimo--;
            hacerBajar(1);
        } else {
            exito = false;
        }
        return exito;
    }

    public void hacerBajar(int posPadre) {
        //Metodo aux que hace bajar al padre hasta que sus hijos sean mayores al mismo
        boolean ordenado = false;
        Comparable aux = this.heap[posPadre];
        int posHijo = posPadre * 2;

        //Mientras no este ordenado y nodo padre no sea hoja (tiene hijo/s) 
        while (!ordenado && posHijo <= this.ultimo) {
            //Si padre tiene hijo derecho
            if (posHijo < this.ultimo) {
                //Mantiene posicion del menor de los hijos
                if (this.heap[posHijo + 1].compareTo(this.heap[posHijo]) < 0) {
                    posHijo++;
                }
            }
            //Si hijo es menor a padre
            if (this.heap[posHijo].compareTo(aux) < 0) {
                //Los intercambia
                this.heap[posPadre] = this.heap[posHijo];
                this.heap[posHijo] = aux;
                //Actualiza posicion del padre
                posPadre = posHijo;
                //Actualiza posicion del posible hijo
                posHijo = posPadre * 2;
            } else {
                ordenado = true;
            }
        }
    }

    

}
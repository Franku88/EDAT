package estructuras.lineales.estaticas;

public class Pila {
    // Implementacion de Pila estatica
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 10;
 
 
    // Constructor
    public Pila() {
        //Crea nueva pila con tamanio estatico
        this.arreglo = new Object[TAMANIO];
        //Asigna tope -1 pues esta vacia
        this.tope = -1;
    }
 
 
    public boolean apilar(Object nuevoElemento) {
        //Agrega nuevo objeto al tope de la pila
        //Retorna false si la pila esta llena
        boolean exito;
        if (this.tope + 1 >= TAMANIO) {
            // la pila esta llena
            exito = false;
        } else {
            // Aumenta el tope y pone el nuevo elemento en esa posicion
            this.tope++;
            this.arreglo[this.tope] = nuevoElemento;
            exito = true;
        }
        return exito;
    }
 
 
    public boolean desapilar() {
        boolean exito;
        if (this.tope - 1 < -1) {
            //La pila esta vacia
            exito = false;
        } else {
            //vacia la posicion tope y disminuye el tope en -1
            this.arreglo[tope] = null;
            tope--;
            exito = true;
        }
        return exito;
    }
 
 
    public Object obtenerTope() {
        //Retorna elemento de la posicion tope
        Object elementoTope;
        if(tope!=-1) {
            elementoTope = arreglo[this.tope];
        } else {
            elementoTope = null;
        }
        return elementoTope;
    }
 
 
    public boolean esVacia() {
        //Si el tope es -1, entonces la pila esta vacia
        boolean vacio = (tope <= -1);
        return vacio;
    }
   
    public void vaciar() {
        /*Realiza un recorrido desde el tope hasta la posicion 0,
        asigna null en todas las posiciones, si el tope es -1, entonces
        el arreglo ya esta vacio*/
        if (!this.esVacia()) {
            for (int i = this.tope; i > -1; i--) {
                this.arreglo[i] = null;
            }
            this.tope = -1;
        }
    }

    public Pila clone() {
        //Metodo que crea una instancia con los mismos elementos y tope de la pila en el llamado
        //Crea clon vacio
        Pila pila2 = new Pila();
        //Verifica que la pila no este vacia
        if (!this.esVacia()) {
            //Asigna el mismo tope a pila2
            pila2.tope = this.tope;
            //Invoca metodo recursivo con ambos arreglos y el tope de ambas pilas
            cloneRecursive(this.arreglo, pila2.arreglo, this.tope);
        }
        return pila2;
    }

    private void cloneRecursive(Object[] arreglo1, Object[] arreglo2, int tope) {
        /*arreglo1: array de pila a clonar, arreglo2: array de clon, tope actual a copiar*/
        //Corta recursion si sobrepasa posicion 0 de arreglo (copia desde tope hasta 0)
        if (tope != -1) {
            //Asigna objeto de la posicion tope
            arreglo2[tope]= arreglo1[tope];
            //Invocacion recursiva pero con objecto debajo del tope actual
            cloneRecursive(arreglo1, arreglo2, tope-1);
        }
    }

    public String toString() {
        //Retorna una cadena con los objetos que contiene la pila
        String stringPila = "";
        //Si el tope es -1, entonces la pila esta vacia
        if (this.tope == -1) {
            stringPila = "Pila vacia";
        } else {
            int i;
            stringPila = "[";
            for (i = 0; i<=this.tope; i++) {
                stringPila = stringPila + this.arreglo[i].toString();
                if (i != this.tope) {
                    stringPila = stringPila + ",";
                }
            }
            stringPila = stringPila + "]";
        }
        return stringPila;
    }
}
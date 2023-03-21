public class Pila {
    // Implementacion de Pila estatica
    private Object[] arreglo;
    private int tope;
    private final int tamanio = 20;
 
 
    // Constructor
    public Pila() {
        this.arreglo = new Object[tamanio];
        this.tope = -1;
    }
 
 
    public boolean apilar(Object nuevoElemento) {
        boolean exito;
 
 
        if (this.tope + 1 >= this.tamanio) {
            // la pila esta llena
            exito = false;
        } else {
            // Aumenta el tope y pone el nuevo elemento en esa posicion
            tope++;
            this.arreglo[tope] = nuevoElemento;
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
 
 
    public Object obtenerElementoTope() {
        //Retorna elemento de la posicion tope
        Object elementoTope = arreglo[this.tope];
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
        if (!esVacia()) {
            for (int i = this.tope; i > -1; i--) {
                this.arreglo[i] = null;
            }
            this.tope = -1;
        }
    }

    public Pila clon() {
        //Metodo que crea una instancia con los mismos elementos y tope de la pila en el llamado
        //Crea clon vacio
        Pila pila2 = new Pila();
        //Verifica que la pila no este vacia
        if (!esVacia()) {
            //Asigna el mismo tope a pila2
            pila2.tope = this.tope;
            //Invoca metodo recursivo con ambos arreglos y el tope de ambas pilas
            pila2.arreglo = clonRecursivo(this.arreglo, pila2.arreglo, this.tope);
        }
        return pila2;
    }

    private Object[] clonRecursivo(Object[] arreglo1, Object[] arreglo2, int tope) {
        /*arreglo1: array de pila a clonar, arreglo2: array de clon, tope actual a copiar*/
        //Corta recursion si sobrepasa posicion 0 de arreglo (copia desde tope hasta 0)
        if (tope != -1) {
            //Asigna valor de la posicion tope
            arreglo2[tope]= arreglo1[tope];
            //Invocacion recursiva pero con objecto debajo del tope actual
            clonRecursivo(arreglo1, arreglo2, tope-1);
        }
        return arreglo2;
    }
}
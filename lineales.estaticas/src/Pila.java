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
        boolean vacio = tope <= -1;
        return vacio;
    }
   
    public void vaciar() {
       
        for (int i = 0; i < this.tope; i++) {
 
 
        }
 
 
    }
 }
 
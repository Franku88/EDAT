package estructuras.lineales.estaticas;

/************* Autores ***********
- Franco Benitez, Legajo FAI-3169
- Jamiro Zuñiga, Legajo FAI-3429
*/

public class Pila {
    //Implementacion de Pila estatica
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 10;
 
    //Constructor
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
        //Metodo que quita elemento del tope, luego cambia el tope actual
        boolean exito;
        if (this.esVacia()) {
            //La pila esta vacia
            exito = false;
        } else {
            //Vacia la posicion tope y disminuye el tope en -1
            this.arreglo[tope] = null;
            tope--;
            exito = true;
        }
        return exito;
    }
 
    public Object obtenerTope() {
        //Retorna elemento de la posicion tope
        Object elementoTope;
        if(!this.esVacia()) {
            elementoTope = arreglo[this.tope];
        } else {
            elementoTope = null;
        }
        return elementoTope;
    }
 
    public boolean esVacia() {
        //Si el tope es -1, entonces la pila esta vacia
        return (this.tope == -1);
    }
   
    public void vaciar() {
        /*Realiza un recorrido desde el tope hasta la posicion 0,
        asigna null en todas las posiciones, primero verifica que no este vacia*/
        if (!this.esVacia()) {
            for (int i = this.tope; i > -1; i--) {
                this.arreglo[i] = null;
            }
            this.tope = -1;
        }
    }

    public Pila clone() {
        //Metodo que crea una instancia con los mismos elementos y tope que la pila de la invocacion
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
        /*arreglo1: array de pila a clonar, arreglo2: array de clon, tope: elemento actual a copiar*/
        //Corta recursion si sobrepasa posicion 0 de arreglo (copia desde tope hasta 0)
        if (tope != -1) {
            //Copio objeto de la posicion tope
            arreglo2[tope]= arreglo1[tope];
            //Invocacion recursiva, apuntando al siguiente objeto
            cloneRecursive(arreglo1, arreglo2, tope-1);
        }
    }

    public String toString() {
        //Metodo que retorna una cadena con los objetos que contiene la pila
        String cad = "";
        //Verifica que la pila no este vacia
        if (this.esVacia()) {
            cad = "Pila vacia";
        } else {
            cad = "[";
            for (int i = 0; i <= this.tope; i++) {
                cad = cad + this.arreglo[i].toString();
                if (i != this.tope) { //En el ultimo no pone coma
                    cad = cad + ",";
                }
            }
            cad = cad + "]";
        }
        return cad;
    }
}
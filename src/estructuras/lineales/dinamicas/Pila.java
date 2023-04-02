package estructuras.lineales.dinamicas;

/************* Autores ***********
- Franco Benitez, Legajo FAI-3169
- Jamiro Zuñiga, Legajo FAI-3429
*/

public class Pila {
    
    private Nodo tope;

    //Constructor
    public Pila() {
        //Crea pila vacia, con tope null
        this.tope = null;
    }

    public boolean apilar(Object nuevoElemento) {
        /*Crea nuevo nodo tope con elemento obtenido por parametro 
        y se lo enlaza al nodo tope anterior*/
        Nodo nuevoTope = new Nodo(nuevoElemento, this.tope);
        
        //Se asigna el nuevo tope creado
        this.tope = nuevoTope;

        //Apilar exitoso
        return true;
    }

    public boolean desapilar() {
        //Metodo que borra el elemento del tope actual y usa su enlace como nuevo tope       
        boolean exito;
        //Si la pila esta vacia, entonces no se puede desapilar
        if (!this.esVacia()) {
            this.tope = this.tope.getEnlace();
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    public Object obtenerTope() {
        //Metodo que retorna el elemento del tope
        Object elemento;
        //Si la pila esta vacia, retorna null
        if (!this.esVacia()) {
           elemento = this.tope.getElemento();
        } else {
            elemento = null;
        }
        return elemento;
    }    

    public boolean esVacia() {
        //Metodo que retorna true si la pila esta vacia
        //Si el nodo tope es null, entonces la pila esta vacia
        return this.tope == null;
    }

    public void vaciar() {
        //Vacia la pila asignando null como tope, desenlazando asi todos los nodos
        this.tope = null;
    }

    public Pila clone() {
        /*Metodo recursivo que crea un clon de la pila ingresada en el llamado,
        usando cloneRecursive*/
        Pila clonPila = new Pila();
        //Genera un tope para el clon dentro de la recursividad, con sus respectivos enlaces
        clonPila.tope = cloneRecursive(this.tope);
        return clonPila;
    }

    private Nodo cloneRecursive(Nodo tope) {
        //Metodo recursivo que realiza la clonacion de una pila en base a su nodo tope
        //Retorna una copia del nodo tope con su nodo enlace tambien clonado recursivamente
        Nodo retorno;
        //Si el nodo tope es null, retorna null
        if (tope != null) {
            //Crea un nodo con el elemento de tope y con una copia de su enlace
            //Su enlace se copia y crea recursivamente
            retorno = new Nodo(tope.getElemento(), cloneRecursive(tope.getEnlace()));
        } else {
            retorno = null;
        }
        return retorno;
    }

    public String toString() {
        //Metodo que retorna un string con los objetos que contiene una pila dinamica
        String cadena = "";
        //Verifica que la pila no este vacia
        if (!this.esVacia()) {
            cadena = "[";
            cadena = toStringRecursive(this.tope, cadena);
            cadena = cadena + "]";
        } else {
            cadena = "Pila vacia";
        }
        return cadena;
    }

    private String toStringRecursive(Nodo enlace, String cadena) {
        //Metodo recursivo que genera una cadena con los elementos delos enlaces
        //Retorna la concatenacion de todos los elementos, con los mayores a la derecha
        if (enlace.getEnlace() != null) {
            //Por izquierda los elementos menores y por derecha los mayores
            cadena = toStringRecursive(enlace.getEnlace(), cadena) +","+ enlace.getElemento().toString();
        } else {
            //Llego al primer elemento, corta recursion y comienza a concatenar
            cadena = cadena + enlace.getElemento().toString();
        }
        return cadena;
    }

}

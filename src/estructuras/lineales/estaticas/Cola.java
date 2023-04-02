package estructuras.lineales.estaticas;

public class Cola {
    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    //Constructor
    public Cola() {
        //Genera cola vacia
        this.arreglo = new Object[TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object nuevoElem) {
        //Metodo que agrega un elemento al fin de la cola
        boolean exito;
        //Si esta llena, entonces no se puede realizar la carga
        if (!this.estaLlena()) {
            /*Asigno nuevo elemento al final, luego cambio fin por 
            el modulo de fin+1 respecto a TAMANIO*/
            this.arreglo[this.fin] = nuevoElem;
            this.fin = (this.fin + 1) % TAMANIO; 
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    public boolean estaLlena() {
        /*Metodo que verifica si una cola esta llena, retorna verdadero si una cola esta llena
        falso si no lo esta*/
        boolean llena;
        llena = (this.fin + 1) % TAMANIO == this.frente;
        return llena;
    }

    public boolean esVacia() {
        /*Modulo que verifica si una cola esta vacia, retorna verdadero si lo esta, falso si no lo esta*/
        boolean vacia;
        vacia = this.frente == this.fin;
        return vacia;
    }

    public boolean sacar() {
        /*Metodo que saca el elemento en la posicion frente y asigna un nuevo frente, retorna verdadero
        si se pudo sacar el elemento, retorna falso si no se pudo */
        boolean exito;
        if(!this.esVacia()) {
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % TAMANIO;
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    public Object obtenerFrente() {
        /*Retorna el elemento en la posicion del frente de la cola.
        Si la cola esta vacia, retorna null*/
        Object elemento;
        if (!this.esVacia()) {            
            elemento = this.arreglo[this.frente];            
        } else {
            elemento = null;
        }
        return elemento;
    }

    public void vaciar() {
        /*Metodo que vacia una cola*/
        while (!this.esVacia()) {
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % TAMANIO;
        }
    }

    public Cola clone() {
        /*Metodo que clona una cola, retorna puntero de clon generado*/
        Cola clon = new Cola();
        if (!this.esVacia()) {
            for (int i = 0; i < TAMANIO; i++) {
                clon.arreglo[i] = this.arreglo[i];
            }
            clon.frente = this.frente;
            clon.fin = this.fin;
        }
        return clon;
    }

    public String toString() {
        /*Metodo que retorna una cadena que representa el contenido de la cola*/
        String cad = "";
        if (!this.esVacia()) {
            int aux = this.frente;
            cad = "[";
            while (aux != this.fin) {
                cad = cad + this.arreglo[aux].toString();
                aux = (aux + 1) % TAMANIO;
                if (aux != this.fin) { //En el ultimo no pone coma
                    cad = cad + ",";
                }
            }
            cad = cad + "]";
        } else {
            cad = "La cola esta vacia.";
        }
        return cad;
    }
}
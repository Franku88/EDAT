package estructuras.jerarquicas.dinamicas;

public class NodoArbol {
    
    private Object elemento;
    private NodoArbol izquierdo;
    private NodoArbol derecho;
    
    public NodoArbol(Object elem, NodoArbol izq, NodoArbol der) {
        this.elemento = elem;
        this.izquierdo = izq;
        this.derecho = der;
    }

    //Getters
    public Object getElemento() {
        return this.elemento;
    }
    public NodoArbol getIzquierdo() {
        return this.izquierdo;
    }
    public NodoArbol getDerecho() {
        return this.derecho;
    }

    //Setters
    public void setElemento(Object elem) {
        this.elemento = elem;
    }
    public void setIzquierdo(NodoArbol izq) {
        this.izquierdo = izq;
    }
    public void setDerecho(NodoArbol der) {
        this.derecho = der;
    }
}

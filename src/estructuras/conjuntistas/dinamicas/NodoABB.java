package estructuras.conjuntistas.dinamicas;

public class NodoABB {
 
    private Object elemento;
    private NodoABB izquierdo;
    private NodoABB derecho;

    public NodoABB(Object elem, NodoABB izq, NodoABB der) {
        this.elemento = elem;
        this.izquierdo = izq;
        this.derecho = der;
    }

    public Object getElemento() {
        return this.elemento;
    }
    
    public void setElemento(Object elem) {
            this.elemento = elem;
    }

    public NodoABB getIzquierdo() {
        return this.izquierdo;
    }

    public void setIzquierdo(NodoABB izq) {
        this.izquierdo = izq;
    }

    public NodoABB getDerecho() {
        return this.derecho;
    }

    public void setDerecho(NodoABB der) {
        this.derecho = der;
    }
}
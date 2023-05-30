package estructuras.jerarquicas.dinamicas;

/************* Autores ***********
- Franco Fabian Benitez, Legajo FAI-3169
* Jamiro Zuñiga, Legajo FAI-3429
* Agustín Ezequiel Heredia, Legajo FAI-2876
*/

public class NodoGen {
    
    private Object elemento;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;
    
    public NodoGen(Object elem, NodoGen hijoIzq, NodoGen hermanoDer) {
        this.elemento = elem;
        this.hijoIzquierdo = hijoIzq;
        this.hermanoDerecho = hermanoDer;
    }
    
    //getters
    public Object getElem() {
        return elemento;
    }
    public NodoGen getHijoIzquierdo() {
        return hijoIzquierdo;
    }
    public NodoGen getHermanoDerecho() {
        return hermanoDerecho;
    }

    //setters
    public void setElem(Object elem) {
        this.elemento = elem;
    }
    public void setHijoIzquierdo(NodoGen hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }
    public void setHermanoDerecho(NodoGen hermanoDerecho) {
        this.hermanoDerecho = hermanoDerecho;
    }
}

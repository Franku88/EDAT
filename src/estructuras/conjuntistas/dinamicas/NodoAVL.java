package estructuras.conjuntistas.dinamicas;
@SuppressWarnings("rawtypes")

public class NodoAVL {
    
    private Comparable elemento;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

    public NodoAVL(Comparable elem) {
        this.elemento = elem;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0;
    }
    
    public NodoAVL(Comparable elem, NodoAVL izq, NodoAVL der) {
        this.elemento = elem;
        this.izquierdo = izq;
        this.derecho = der;
        this.recalcularAltura();
    }

    public Comparable getElemento() {
        return this.elemento;
    }
    
    public void setElemento(Comparable elem) {
            this.elemento = elem;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    public int balance() {
        //Modulo que calcula el balance de un nodoAVL
        int altIzq = -1;
        int altDer = -1; //Altura de null es -1
        if (this.izquierdo != null) {
            altIzq = this.izquierdo.getAltura();
        }
        if (this.derecho != null) {
            altDer = this.derecho.getAltura();
        }
        return (altIzq - altDer); 
    }

    public void recalcularAltura(){
        //Realiza el calculo de la altura de un nodo en base a la altura de sus hijos
        int altIzq = -1;
        int altDer = -1;
        //Si tiene HI calculo su altura
        if (this.izquierdo != null) { 
            altIzq = (this.izquierdo).altura;
        } 
        //Si tiene HD calculo su altura
        if (this.derecho != null) { 
            altDer = (this.derecho).altura;
        }
        //Establece la mayor altura encontrada + 1
        this.altura = Math.max(altIzq, altDer) + 1;
    }
}

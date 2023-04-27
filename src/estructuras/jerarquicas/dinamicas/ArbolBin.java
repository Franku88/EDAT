package estructuras.jerarquicas.dinamicas;

import estructuras.lineales.dinamicas.Nodo;

public class ArbolBin {
    
    //Atributos
    private NodoArbol raiz;

    //Constructor
    public ArbolBin() {
        this.raiz = null;
    }


    public boolean insertar(Object elemNuevo, Object elemPadre, boolean lado) {
        boolean exito = true;

        if (this.raiz == null) {
            //Si el arbol esta vacio, pone elemNuevo en la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //Si arbol no esta vacio, busca al padre
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);

            //Si padre existe y lugar no esta ocupado lo pone, si no da error
            if (nodoPadre != null) {
                if (!lado && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if(lado && nodoPadre.getDerecho() == null) {
                    nodoPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false;
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol nodo, Object buscado) {
        /*Metodo que busca el nodo contenedor del elemento buscado
        si no lo encuentra, retorna null*/
        NodoArbol resultado = null;
        if (nodo != null) {
            if (nodo.getElemento().equals(buscado)) {
                //Si el buscado es nodo, lo retorna
                resultado = nodo;
            } else {
                //Si no es el buscado, busca en HI
                resultado = obtenerNodo(nodo.getIzquierdo(),buscado);
                //Si no lo encuentra, busca en HD
                if (resultado == null) {
                    resultado = obtenerNodo(nodo.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object padre(Object elem) {
        //Metodo que retorna el elemento del nodo padre, del elemento nodo hijo
        Object elemPadre;
        elemPadre = padreAux(this.raiz, elem).getElemento();
        return elemPadre;
    } 

    private NodoArbol padreAux(NodoArbol nodo, Object buscado) {
        //Metodo que retorna nodo padre del nodo contenedor del elemento buscado
        //Si no se encuentra, retorna null
        NodoArbol padre = null;
        boolean esPadre = (nodo.getIzquierdo().getElemento() == buscado) || (nodo.getDerecho().getElemento() == buscado);
        if (esPadre) {
            padre = nodo;
        } else {
            //Si no es el padre buscado, busca en HI
            padre = padreAux(nodo.getIzquierdo(), buscado);
            //Si no lo encuentra, busca en HD
            if (padre == null) {
                padre = padreAux(nodo.getDerecho(), buscado);
            }
        }
        return padre;
    }

    public int altura() {
        //Metodo que retorna la altura de un arbol binario
        int alt = -1;
        if (this.raiz != null) { //Si su raiz es null, entonces la altura es -1
            alt = alturaAux(this.raiz, 0);
        }
        return alt;
    }

    public int alturaAux(NodoArbol nodo, int alt) {
        //Metodo que verifica la altura de un arbol binario
        if (nodo != null) { //Si el nodo a revisar es null, retorna la altura actual
            //Si no es null, verifica si tiene hijos
            NodoArbol nodoI = nodo.getIzquierdo(), nodoD = nodo.getDerecho(); 
            boolean sigue = nodoI != null && nodoD != null; //Si no existen hijos, no realiza mas llamados
            if (sigue) {
                int altD, altI;
                alt = alt + 1; //Si llega a este punto, es porque tiene al menos un hijo, aumenta altura
                //Verifica si cada hijo es padre de otros hijos y guarda su altura
                altI = alturaAux(nodoI, alt);
                altD = alturaAux(nodoD, alt);
                //Verifica cual hijo tiene mayor altura y lo retorna
                if (altI > altD) {
                    alt = altI;
                } else {
                    alt = altD;
                }
            }
        }
        return alt;
    } 

    public int nivel(Object elem) {
        //Metodo que retorna el nivel de un elemento
        //Retorna -1 si el elemento no existe en el arbol
        return nivelAux(this.raiz, elem, 0);
    }

    private int nivelAux(NodoArbol nodo, Object elem, int nivel) {
        //Metodo que retorna el nivel de un elemento encontrado
        int nivelR = -1; //Si no se encuentra o si el nodo es nulo, retorna -1
        if (nodo != null) {
            if (nodo.getElemento().equals(elem)){ //Si lo encuentra, retorna el nivel alcanzado
                nivelR = nivel;
            } else {
                nivelR = nivelAux(nodo.getIzquierdo(), elem, nivel+1);
                if (nivelR == -1) { //Si no se encuentra en HI, busca en el HD
                    nivelR = nivelAux(nodo.getDerecho(), elem, nivel+1);
                }
            }
        } 
        return nivelR;
    }

    public void vaciar() {
        this.raiz = null;
    }

    
}
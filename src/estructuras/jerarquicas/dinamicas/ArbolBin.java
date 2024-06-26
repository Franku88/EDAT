package estructuras.jerarquicas.dinamicas;

/************* Autores ***********
- Franco Fabian Benitez, Legajo FAI-3169
*/

import estructuras.lineales.dinamicas.Lista;
import estructuras.lineales.dinamicas.Cola;

public class ArbolBin {
    
    //Atributos
    private NodoArbol raiz;

    //Constructor
    public ArbolBin() {
        this.raiz = null;
    }

    /**
     * 
     * @param elemNuevo
     * @param elemPadre
     * @param lado I (Hijo izquierdo), D (Hijo derecho)
     * @return 
     */
    public boolean insertar(Object elemNuevo, Object elemPadre, char lado) {
        boolean exito = true;

        if (this.raiz == null) {
            //Si el arbol esta vacio, pone elemNuevo en la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //Si arbol no esta vacio, busca al padre
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);

            //Si padre existe y lugar no esta ocupado lo pone, si no da error
            if (nodoPadre != null) {
                if (lado == 'I' && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if(lado == 'D' && nodoPadre.getDerecho() == null) {
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

    public Object padre(Object hijo){
        Object padre = null;
        if (this.raiz != null){
            //Envio al padre null en caso de que este hijo sea la raiz (no tiene padre)
            padre = obtenerPadre(this.raiz, hijo, null); 
        }
        return padre; 
    }

    private Object obtenerPadre(NodoArbol nodo, Object hijo, Object padreAux){
        //Dado un elem (hijo) devuelve el valor del nodo padre
        Object ret = null; 

        if (nodo != null){ 
            if (nodo.getElemento().equals(hijo)){ //elemento encontrado, retorno el padre del parametro
                ret = padreAux;
            } else {
                ret = obtenerPadre(nodo.getIzquierdo(), hijo, nodo.getElemento()); //evaluo lado izq y guardando en padreAux el elem actual
                if (ret == null){ //si no se encontro evaluo el der
                    ret = obtenerPadre(nodo.getDerecho(), hijo, nodo.getElemento());
                }
            }
        }
        return ret;
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

    public ArbolBin clone() {
        ArbolBin clon = new ArbolBin();
        clon.raiz = cloneAux(this.raiz);
        return clon;    
    }

    private NodoArbol cloneAux(NodoArbol nodoO) {
        NodoArbol nodoC;
        if (nodoO != null) {
            NodoArbol hI,hD;
            hI = cloneAux(nodoO.getIzquierdo());
            hD = cloneAux(nodoO.getDerecho());
            nodoC = new NodoArbol(nodoO.getElemento(), hI, hD);
        } else {
            nodoC = null;
        }
        return nodoC;
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    public String toStringAux(NodoArbol nodo) {
        String cad = "";
        if (nodo != null) {
            NodoArbol hI, hD;
            hI = nodo.getIzquierdo();
            hD = nodo.getDerecho();

            cad = "\n ("+nodo.getElemento()+") -> ";
            //Concatena elementos de HI y HD
            //Verifica si HI es nulo
            if (hI != null) {
                cad = cad + " HI: "+hI.getElemento()+" ";
            } else {
                cad = cad + " HI: - ";
            }
            //Verifica si HD es nulo
            if (hD != null) {
                cad = cad + " HD: "+hD.getElemento()+" ";
            } else {
                cad = cad + " HD: - ";
            }
            //Concatena nodos hijos de HI y HD
            cad = cad + toStringAux(hI);
            cad = cad + toStringAux(hD);
        }
        return cad;
    }

    public Lista listarPreorden() {
        //Metodo que crea una lista con los elementos del arbol en preorden
        Lista preorden = new Lista();
        listarPreordenAux(this.raiz, preorden);
        return preorden;
    }

    private void listarPreordenAux(NodoArbol nodo, Lista list) {
        //Metodo que inserta primero la raiz, luego los subarboles izquierdos y por ultimo los subarboles derechos
        if (nodo != null) {
            list.insertar(nodo.getElemento(),list.longitud()+1);
            listarPreordenAux(nodo.getIzquierdo(),list);
            listarPreordenAux(nodo.getDerecho(),list);
        }
    }

    public Lista listarPosorden() {
        //Metodo que crea una lista con los elementos del arbol en posorden
        Lista posorden = new Lista();
        listarPosordenAux(this.raiz, posorden);
        return posorden;
    }

    private void listarPosordenAux(NodoArbol nodo, Lista list){
        //Metodo que inserta primero los subarboles izquierdos, luego los subarboles derechos y por ultimo la raiz de los subarboles
        if (nodo != null) {
            listarPosordenAux(nodo.getIzquierdo(), list);
            listarPosordenAux(nodo.getDerecho(), list);
            list.insertar(nodo.getElemento(), list.longitud()+1);
        }
    }

    public Lista listarInorden() {
        //Metodo que crea una lista con los elementos del arbol en inorden
        Lista inorden = new Lista();
        listarInordenAux(this.raiz, inorden);
        return inorden;
    }

    private void listarInordenAux(NodoArbol nodo, Lista list){
        //Metodo que inserta primero los subarboles izquierdos, luego la raiz del subarbol y por ultimo los subarboles derechos
        if (nodo != null) {
            listarInordenAux(nodo.getIzquierdo(), list);
            list.insertar(nodo.getElemento(), list.longitud()+1);
            listarInordenAux(nodo.getDerecho(), list);
        }
    }

    public Lista listarPorNiveles() {
        //Retorna lista del arbol en recorrido por niveles
        Lista porNivel = new Lista();
        if (this.raiz != null){//Si el arbol no esta vacio
            Cola colaAux = new Cola();//Guardara los nodos mientras los primeros se insertan
            colaAux.poner(this.raiz); //Pongo la raiz no nula
            NodoArbol nodoActual; //Declaro nodoActual para realizar insercion
            while (!colaAux.esVacia()){ 
                //Guardo nodo que se encuentra en la colaAux
                nodoActual = (NodoArbol) colaAux.obtenerFrente();
                //Saco nodo de cola que sera insertado, mientras guarda los siguientes en la cola
                colaAux.sacar();
                //Inserto nodoActual a la lista porNivel
                porNivel.insertar(nodoActual.getElemento(), porNivel.longitud()+1);
                //Pone los siguiente nodos en la cola, de izquierda a derecha
                NodoArbol hI = nodoActual.getIzquierdo();
                NodoArbol hD = nodoActual.getDerecho();
                if (hI != null){
                    colaAux.poner(hI);
                }
                if (hD != null){
                    colaAux.poner(hD);
                }
            }
        }
        return porNivel;
    }

    //Ejercicios apunte
    public Lista frontera(){
        //Metodo que retorna una lista con las hojas de un arbol, de izquierda a derecha
        Lista hojas = new Lista();
        if (!this.esVacio()) {
            fronteraAux(this.raiz, hojas);
        }
        return hojas;
    }

    public void fronteraAux(NodoArbol nodo, Lista hojas) {
        //Metodo que crea una lista con las hojas del arbol (nodos sin hijos)
        if (nodo != null) {
            NodoArbol hI = nodo.getIzquierdo(), hD = nodo.getDerecho(); //Asigna hijos de nodo
            boolean tieneHI = hI != null, tieneHD = hD != null; //Verifica si son nulos
            if (!tieneHI && !tieneHD) { //Si ambos hijos son nulos, entonces se inserta
                hojas.insertar(nodo.getElemento(), hojas.longitud()+1);
            } else { //Si alguno no es nulo
                if (tieneHI) { //Verifica los hijos de HI, si son hojas, los inserta primero
                    fronteraAux(hI, hojas);
                }
                if (tieneHD) { //Verifica los hijos de HD, si son hojas, los inserta luego de HI
                    fronteraAux(hD, hojas);
                }
            }
        }
    }

    public Lista listarAncestros(Object elem) {
        Lista ancestros = new Lista();
        if (!this.esVacio()) {
            listarAncestrosAux(this.raiz, ancestros, elem);
        }
        return ancestros;
    }   

    public boolean listarAncestrosAux(NodoArbol nodo, Lista list, Object elem) {
        //Metodo que retorna un boolean si se encuentra elem en nodo
        //Si encuentra el elemento en primera intancia, retorna el resultado
        //Si en las siguientes invocaciones lo encuentra, lista los elementos
        boolean encontrado = false;
        if (nodo != null) {
            Object nElem = nodo.getElemento();
            encontrado = (nElem.equals(elem));
            if (!encontrado) { //Si no lo encuentra, busca por HI
                encontrado = listarAncestrosAux(nodo.getIzquierdo(), list, elem);
                if (!encontrado) { //Si no lo encuentra, busca por HD
                    encontrado = listarAncestrosAux(nodo.getDerecho(), list, elem);
                    if (encontrado) { //Si lo encuentra, lista elementos
                        list.insertar(nElem, list.longitud()+1);
                    }
                } else { //Si lo encuentra, lista elementos
                    list.insertar(nElem, list.longitud()+1);
                }
            }
        }
        return encontrado;
    }

    public Lista listarDescendientes(Object elem) {
        Lista descendientes = new Lista();
        if (!this.esVacio()) {
            listarDescendientes(this.raiz, descendientes, elem, false);
        }
        return descendientes;
    }   

    public void listarDescendientes(NodoArbol nodo, Lista list, Object elem, boolean encontrado) {
        
        if (nodo != null) {
            Object nElem = nodo.getElemento();
            if (!encontrado) {
                encontrado = (nElem.equals(elem));
                listarDescendientes(nodo.getIzquierdo(), list, elem, encontrado);
                listarDescendientes(nodo.getDerecho(), list, elem, encontrado);
            } else {
                list.insertar(nElem, list.longitud()+1);
                listarDescendientes(nodo.getIzquierdo(), list, elem, encontrado);
                listarDescendientes(nodo.getDerecho(), list, elem, encontrado);
            }

        }
    }
}
package estructuras.conjuntistas.dinamicas;
import estructuras.lineales.dinamicas.*;

public class ArbolBB {

    private NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        //Inserta un elemento en el Arbol manteniendo su respectivo orden
        //Retorna verdadero si se pudo realizar la insercion
        boolean exito = true;
        if (!this.esVacio()) {
            exito = insertarAux(this.raiz, elem);
        } else {
            this.raiz = new NodoABB(elem, null, null);
        }
        return exito;
    }

    public boolean insertarAux(NodoABB nodo, Comparable elem) {
        //Metodo auxiliar que busca la posicion del nuevo nodo y lo inserta si no se encuentra
        //Retorna verdadero si se pudo insertar, falso si el elemento ya se encuentra en el arbol
        boolean exito = true;
        //Comparo elemento a insertar con elemento del nodo actual
        int comparacion = elem.compareTo(nodo.getElemento());
        //Si el elemento se encuentra
        if (comparacion == 0) { 
            exito = false; 
        } else {
            //Si el elemento es menor al de nodo
            if (comparacion < 0) {
                NodoABB izq = nodo.getIzquierdo();
                //Inserto elemento si no tiene HI
                if (izq == null){ 
                    nodo.setIzquierdo(new NodoABB(elem, null, null));
                } else { //Si tiene HI, realizo invocacion con el mismo
                    exito = insertarAux(izq, elem);
                }
            } else { //Si el elemento es mayor al del nodo
                NodoABB der = nodo.getDerecho();
                //Inserto elemento si no tiene HD
                if (der == null) { 
                    nodo.setDerecho(new NodoABB(elem, null, null));
                } else { //Si tiene HD, realizo invocacion con el mismo
                    exito = insertarAux(der, elem);
                }
            }
        }
        return exito;
    }

    public boolean esVacio() {
        //Metodo que retorna verdadero si el arbol esta vacio
        return this.raiz == null;
    }

    public void vaciar() {
        //Metodo que vacia el arbol actual
        this.raiz = null;
    }

    public boolean eliminar(Comparable elem) {
        //Metodo que remueve el elemento ingresado por parametro del arbol actual 
        return eliminarAux(this.raiz, null, elem);
    }

    private boolean eliminarAux(NodoABB nodo, NodoABB padre, Comparable elem) {
        //Metodo que elimina un elemento del arbol conservando el orden del mismo
        //Retorna verdadero si se elimina, falso si no se encuentra
        boolean exito = false;
        if (nodo != null) {
            //Hijos de nodo actual
            NodoABB izq = nodo.getIzquierdo();
            NodoABB der = nodo.getDerecho();
            //Comparo elem a eliminar con elemento del nodo actual
            int comparacion = elem.compareTo(nodo.getElemento());
            //Casos
            //Si se encontró el nodo que contiene elem
            if (comparacion == 0) {
                exito = true;
                //Si el elem a eliminar tiene dos hijos
                if (izq != null && der != null) { 
                    //Busco al elemento menor del subarbol derecho
                    Comparable menorElem = menorEnSubarbol(der);
                    //Elimino elemento encontrado
                    eliminarAux(der, nodo, menorElem);
                    //Reemplazo en nodo de elemento eliminado por el menor encontrado
                    nodo.setElemento(menorElem);
                } else {
                    //Si el elem a eliminar es hoja
                    if (izq == null && der == null) {
                        casoHoja(nodo, padre);
                    } else { //Si el elem a eliminar tiene solo un hijo
                        casoUnHijo(nodo, padre);
                    }
                }
            } else { //Si no se encuentra, se busca en sus hijos
                //Si elem es menor al del nodo
                if (comparacion < 0) {
                    //Busco por subarbol izquierdo
                    exito = eliminarAux(izq, nodo, elem); 
                } else { //Si elem es mayor al del nodo
                    //Busco por subarbol derecho
                    exito = eliminarAux(der, nodo, elem); 
                }
            }
        }
        return exito;
    }

    private void casoHoja(NodoABB nodo, NodoABB padre) {
        //Caso del metodo eliminar, elimina un nodo que es hoja.
        //Si el arbol tiene un solo elemento
        if (padre == null) {
            this.raiz = null;
        } else {
            boolean nodoEsMenor = (nodo.getElemento()).compareTo(padre.getElemento()) < 0;
            //Si el elemento de nodo es menor al de su padre, elimino HI de padre
            if (nodoEsMenor) {
                padre.setIzquierdo(null);
            } else { //Si elemento es mayor al de su padre, elimino HD de padre
                padre.setDerecho(null);
            }
        }
    }

    /****/
    private void casoUnHijo(NodoABB nodo, NodoABB padre) {
        //Caso del metodo eliminar, elimina nodo que tiene un solo hijo
        NodoABB izq = nodo.getIzquierdo();
        NodoABB der = nodo.getDerecho();
        //Si padre de nodo no es nulo, es decir, nodo no es raiz
        if (padre != null) {
            boolean nodoEsMenor = (nodo.getElemento()).compareTo(padre.getElemento()) < 0;
            if (der == null) { //Si solo tiene HI
                if (nodoEsMenor) { //Si elemNodo es menor al de su padre, coloco izq como HI
                    padre.setIzquierdo(izq);
                } else { //Si elemNodo es mayor al de su padre, coloco izq como HD
                    padre.setDerecho(izq);
                }
            } else { //Si solo tiene HD
                if (nodoEsMenor) { //Si elemNodo es menor al de su padre, coloco der como HI
                    padre.setIzquierdo(der);
                } else { //Si elemNodo es mayor al de su padre, coloco der como HD
                    padre.setDerecho(der);
                }
            }
        } else { //Si nodo es raiz, lo reemplazo por su hijo
            if (der == null) {
                this.raiz = izq;
            } else {
                this.raiz = der;
            }
        }
    }

    public Comparable minimoElem(){
        //Metodo que retorna el elemento mas chico del arbol
        Comparable menor;
        if (!this.esVacio()) {
            menor = menorEnSubarbol(this.raiz);
        } else {
            menor = null;
        }
        return menor;
    }

    private Comparable menorEnSubarbol(NodoABB nodo) {
        //Metodo que retorna el menor elemento de un subarbol
        Comparable menor; 
        NodoABB izq = nodo.getIzquierdo();
        //Si no tiene nodo izquierdo, entonces ya es el menor
        if (izq == null) {
            menor = nodo.getElemento();
        } else { //Si tiene nodo izquierdo, sigo buscando
            menor = menorEnSubarbol(izq);
        }
        return menor;
    }

    public Comparable maximoElem(){ 
        //Metodo que retorna el elemento mas grande del arbol
        Comparable mayor;
        if (!this.esVacio()) {
            mayor = mayorEnSubarbol(this.raiz);
        } else {
            mayor = null;
        }
        return mayor;
    }

    private Comparable mayorEnSubarbol(NodoABB nodo) {
        //Metodo que retorna el mayor elemento de un subarbol
        Comparable mayor;
        NodoABB der = nodo.getDerecho();
        //Si no tiene nodo derecho, entonces ya es el mayor
        if (der == null) {
            mayor = nodo.getElemento();
        } else { //Si tiene nodo derecho, sigo buscando
            mayor = mayorEnSubarbol(der);
        }
        return mayor;
    }

    public boolean pertenece(Comparable elem){
        //Retorna verdadero si elem se encuentra en el arbol
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoABB nodo, Comparable elem){
        //Metodo auxiliar que verifica si un elemento esta en el arbol
        boolean encontrado;
        //Si nodo no es nulo
        if (nodo != null){
            //Compara elem con elemento del nodo
            int comparacion = elem.compareTo(nodo.getElemento());
            if (comparacion == 0) { //Si son iguales
                encontrado = true;
            } else {
                //Si elem es menor al elemNodo, busco en subArbol izq
                if (comparacion < 0) {
                    encontrado = perteneceAux(nodo.getIzquierdo(), elem);
                } else { //Si elem es mayor a al elemNodo, busco en subArbol der
                    encontrado = perteneceAux(nodo.getDerecho(), elem);
                }
            }
        } else { //Si nodo es nulo, no se encuentra elem
            encontrado = false;
        }
        return encontrado;
    }

    public Lista listar(){ 
        //Retorna una lista de los elementos del arbol
        Lista list = new Lista();
        listarAux(this.raiz, list);
        return list;
    }

    private void listarAux(NodoABB nodo, Lista list){
        //Lista elementos del arbol de menor a mayor, realizando recorrido inorden inverso
        if (nodo != null) { 
            listarAux(nodo.getDerecho(), list);
            list.insertar(nodo.getElemento(), 1);
            listarAux(nodo.getIzquierdo(), list);
        }
    }

    public Lista listarRango(Comparable min, Comparable max){
        //Lista elementos del arbol de menor a mayor que se encuentran en el intervalo [min,max]
        Lista list = new Lista();
        listarRangoAux(this.raiz, min, max, list);
        return list;
    }

    private void listarRangoAux(NodoABB nodo, Comparable min, Comparable max, Lista list){
        //Inserta los elementos => min y <= max. Realizo insercion con recorrido inorden inverso (der, raiz, izq)
        //Si nodo no es nulo
        if (nodo != null) {
            //Elemento de nodo
            Comparable elemNodo = nodo.getElemento(); 
            //Comparaciones de elemNodo con los extremos min y max
            int comparacionMin = elemNodo.compareTo(min);
            int comparacionMax = elemNodo.compareTo(max) ;
            //Si elemNodo < max, recorro subArbol derecho
            if (comparacionMax < 0){
                listarRangoAux(nodo.getDerecho(), min, max, list); 
            }
            //Si min <= elemNodo <= max, listo elemento en posicion 1
            if (comparacionMin >= 0 && comparacionMax <= 0) {
                list.insertar(elemNodo, 1);
            }
            //Si min < elemNodo, recorro subArbol izquierdo
            if (comparacionMin > 0) {
                listarRangoAux(nodo.getIzquierdo(), min, max, list);
            }
        }
    }

    public String toString(){
        //Retorna un string que contiene una representacion del ArbolBB
        String cad;
        if (this.esVacio()){
            cad = "Arbol Vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoABB nodo){
        //Metodo auxiliar que concatena los elementos del arbol en un string
        String cad = "";
        //Si el nodo no es nulo
        if (nodo != null) {
            //Obtengo hijos de nodo
            NodoABB izq = nodo.getIzquierdo(); 
            NodoABB der = nodo.getDerecho();
            //Concateno elemento del nodo
            cad = cad +"("+ nodo.getElemento() + ") ->  ";
            //Si tiene HI, lo concateno
            if (izq != null) {
                cad = cad + "HI: " + izq.getElemento() + "    ";
            } else {
                cad = cad + "HI: -    ";
            }
            //Si tiene HD, lo concateno
            if (der != null) {
                cad = cad + "HD: " + der.getElemento() + "\n";
            } else {
                cad = cad + "HD: -\n";
            }
            //Continuo con HI e HD
            cad = cad + toStringAux(izq);
            cad = cad + toStringAux(der);
        }
        return cad;
    }

    public ArbolBB clone(){
        //Retorna un clon del arbol actual
        ArbolBB clon = new ArbolBB();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoABB cloneAux(NodoABB nodo){
        //Metodo auxiliar que copia nodos de un arbol
        NodoABB copia = null;
        //Si nodo no es nulo
        if (nodo != null) {
            /*Creo un nuevo nodo con el mismo elemento de nodo y enlazado recursivamente
            a los clones de HI e HD del nodo original*/
            copia = new NodoABB(nodo.getElemento(), cloneAux(nodo.getIzquierdo()), cloneAux(nodo.getDerecho()));
        }
        return copia;
    }
}
package estructuras.conjuntistas.dinamicas;
import estructuras.lineales.dinamicas.Lista; //Usado en métodos de listado
@SuppressWarnings({"rawtypes", "unchecked"})

public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        //Inserta un elem conservando el orden de el arbol 
        boolean exito = true;
        if (this.esVacio()){
            this.raiz = new NodoAVL(elem);
        } else {
            exito = insertarAux(this.raiz, elem, null);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodo, Comparable elem, NodoAVL padre) {
        //Metodo auxiliar que busca la posicion del nuevo nodo y lo inserta si no se encuentra
        //Retorna verdadero si se pudo insertar, falso si el elemento ya se encuentra en el arbol
        //padre: variable para balancear a nodo si fuera necesario
        boolean exito = true;
        //Comparo elemento a insertar con elemento del nodo actual
        int comparacion = elem.compareTo(nodo.getElemento());
        if (comparacion == 0) { //Si el elemento se encuentra
            exito = false; //error el elem ya existe en el arbol    
        } else {
            if (comparacion < 0) { //Si el elemento es menor al de nodo
                //Inserto elemento si no tiene HI
                if (nodo.getIzquierdo() == null) { 
                    nodo.setIzquierdo(new NodoAVL(elem, null, null));
                } else { //Si tiene HI, realizo invocacion con el mismo
                    exito = insertarAux(nodo.getIzquierdo(), elem, nodo);
                }
            } else { //Si el elemento es mayor al del nodo
                //Inserto elemento si no tiene HD
                if (nodo.getDerecho() == null) { 
                    nodo.setDerecho(new NodoAVL(elem, null, null));
                } else { //Si tiene HD, realizo invocacion con el mismo
                    exito = insertarAux(nodo.getDerecho(), elem, nodo);
                }
            }
        }
        if (exito) { //Si se inserto en alguna invocacion, verifico balanceo
            nodo.recalcularAltura(); //Recalculo altura 
            int balance = nodo.balance(); //Obtengo balance de nodo
            if (balance < -1 || balance > 1) { //Si esta desbalanceado
                balancear(nodo, padre);
                nodo.recalcularAltura();
            } 
        }   
        return exito;
    }
    
    public boolean esVacio() {
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

    private boolean eliminarAux(NodoAVL nodo, NodoAVL padre, Comparable elem) {
        //Metodo que elimina un elemento del arbol conservando el orden del mismo
        //Retorna verdadero si se elimina, falso si no se encuentra
        boolean exito = false;
        if (nodo != null) {
            //Hijos de nodo actual
            NodoAVL izq = nodo.getIzquierdo();
            NodoAVL der = nodo.getDerecho();
            //Comparo elem a eliminar con elemento del nodo actual
            int comparacion = elem.compareTo(nodo.getElemento());
            //Casos
            if (comparacion == 0) { //Si se encontró el nodo que contiene elem
                exito = true;
                if (izq != null && der != null) { //Si el elem a eliminar tiene dos hijos
                    casoDosHijos(nodo, padre);
                } else { 
                    if (izq == null && der == null) { //Si el elem a eliminar es hoja
                        casoHoja(nodo, padre);
                    } else { //Si el elem a eliminar tiene solo un hijo
                        casoUnHijo(nodo, padre);
                    }
                }
            } else { //Si no se encuentra, se busca en sus hijos
                if (comparacion < 0) { //Si elem es menor al del nodo
                    exito = eliminarAux(izq, nodo, elem); //Busco por subarbol izquierdo
                } else { //Si elem es mayor al del nodo
                    exito = eliminarAux(der, nodo, elem); //Busco por subarbol derecho
                }
            }
            if (exito) { //Si se elimino, verifico balanceo
                nodo.recalcularAltura(); //Recalculo altura 
                int balance = nodo.balance(); //Obtengo balance de nodo
                if (balance < -1 || balance > 1) { //Si esta desbalanceado
                    balancear(nodo, padre);
                    nodo.recalcularAltura();
                } 
            } 
        }
        return exito;
    }

    private void casoHoja(NodoAVL nodo, NodoAVL padre) {
        //Caso del metodo eliminar, elimina un nodo que es hoja.
        if (padre != null) { 
            //Si el elemento de nodo es menor al de su padre, elimino HI de padre
            if ((nodo.getElemento()).compareTo(padre.getElemento()) < 0) {
                padre.setIzquierdo(null);
            } else { //Si elemento es mayor al de su padre, elimino HD de padre
                padre.setDerecho(null);
            }
        } else { //Si nodo es raiz
            this.raiz = null;
        }
    }

    private void casoUnHijo(NodoAVL nodo, NodoAVL padre) {
        //Caso del metodo eliminar, elimina nodo que tiene un solo hijo
        //Precondicion: nodo tiene al menos un hijo no nulo
        NodoAVL hijo = nodo.getIzquierdo(); //Tomo HI
        if (hijo == null) { //Si HI es null, entonces HD no es null
            hijo = nodo.getDerecho();
        }
        if (padre != null) { //Si nodo no es raiz
            //Si elem nodo es menor al de su padre, coloco hijo como HI
            if ((nodo.getElemento()).compareTo(padre.getElemento()) < 0) {
                padre.setIzquierdo(hijo);
            } else { //Si elem nodo es mayor al de su padre, coloco hijo como HD
                padre.setDerecho(hijo);
            }
        } else { //Si nodo es raiz, lo reemplazo por su hijo
            this.raiz = hijo;
        }
    }

    private void casoDosHijos(NodoAVL nodo, NodoAVL padre) {
        //Caso del metodo eliminar, elimina nodo que tiene ambos hijos 
        //Elijo buscar nodo con elemento menor del subarbol derecho
        NodoAVL subarbol = nodo.getDerecho();
        NodoAVL candidato = menorEnSubarbol(subarbol);
        /* Otra opcion: buscar el mayor del subarbol izquierdo
        NodoAVL subarbol = nodo.getIzquierdo();
        NodoAVL candidato = mayorEnSubarbol(subarbol); */
        eliminarAux(subarbol, nodo, candidato.getElemento()); //Elimino candidato encontrado
        if (padre != null) { //Si nodo no es raiz
            if ((candidato.getElemento()).compareTo(padre.getElemento()) < 0) {
                padre.setIzquierdo(candidato); //Si candidato es menor a padre
            } else {
                padre.setDerecho(candidato); //Si candidato es mayor a padre
            }         
        } else { //Si nodo es raiz
            this.raiz = candidato; //Convierto candidato encontrado en raiz
        }
        //Establezco hijos del candidato
        candidato.setIzquierdo(nodo.getIzquierdo());
        candidato.setDerecho(nodo.getDerecho());
    }

    public Comparable minimoElem() {
        //Metodo que retorna el elemento mas chico del arbol
        Comparable menor;
        if (!this.esVacio()) {
            menor = menorEnSubarbol(this.raiz).getElemento();
        } else {
            menor = null;
        }
        return menor;
    }

    public Comparable maximoElem() { 
        //Metodo que retorna el elemento mas grande del arbol
        Comparable mayor;
        if (!this.esVacio()) {
            mayor = mayorEnSubarbol(this.raiz).getElemento();
        } else {
            mayor = null;
        }
        return mayor;
    }

    private NodoAVL menorEnSubarbol(NodoAVL nodo) {
        //Metodo que retorna referencia al Nodo con menor elemento de un subarbol
        NodoAVL izq = nodo.getIzquierdo();
        if (izq != null) { //Si tiene nodo izquierdo, sigo buscando
            nodo = menorEnSubarbol(izq);
        } //Si no tiene nodo izquierdo, entonces ya es el menor
        return nodo;
    }

    private NodoAVL mayorEnSubarbol(NodoAVL nodo) {
        //Metodo que retorna referencia al Nodo con mayor elemento de un subarbol
        NodoAVL der = nodo.getDerecho();
        if (der != null) { //Si tiene nodo derecho, sigo buscando
            nodo = mayorEnSubarbol(der);
        } //Si no tiene nodo derecho, entonces ya es el mayor
        return nodo;
    }

    private void balancear(NodoAVL nodo, NodoAVL padre) {
        /*Metodo aux que balancea el nodo con 4 casos
        padre: es el padre de nodo, usado para asignar a su hijo desbalanceado una vez termine el proceso
        precondicion: nodo no es vacio y su balance es 2 o -2*/
        NodoAVL aux;
        if (nodo.balance() < -1) { //Si subarbol con raiz nodo esta torcido a derecha
            if (nodo.getDerecho().balance() <= 0) { //Si HD esta torcido a la der (mismo lado que nodo)
                nodo = rotarIzquierda(nodo); //Rotacion simple a izq con pivote nodo
                if (padre == null) { //Caso especial, nodo es raiz
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    if ((nodo.getElemento()).compareTo(padre.getElemento()) > 0) { //Si nodo es mayor a padre
                        padre.setDerecho(nodo);
                    } else { //Si nodo es menor a padre
                        padre.setIzquierdo(nodo);
                    }
                    padre.recalcularAltura();
                }
            } else { //Si HD esta torcido a la izq (lado opuesto a nodo)
                aux = rotarDerecha(nodo.getDerecho()); //Rotacion simple a der con pivote HD
                nodo.setDerecho(aux);
                balancear(nodo, padre); //Balanceo nodo (paso recursivo con nuevo balance de HD)
            }
        } else { //Si subarbol con raiz nodo esta torcido a izquierda
            if (nodo.getIzquierdo().balance() >= 0) { //Si HI esta torcido a la izq (mismo lado que nodo)
                nodo = rotarDerecha(nodo); //Rotacion simple a der con pivote nodo
                if (padre == null) { //Caso especial, nodo es raiz
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    if ((nodo.getElemento().compareTo(padre.getElemento())) > 0) { //Si nodo es mayor a padre
                        padre.setDerecho(nodo);
                    } else { //Si nodo es menor a padre
                        padre.setIzquierdo(nodo);
                    }
                    padre.recalcularAltura();
                }
            } else { //Si HI esta torcido a la der
                aux = rotarIzquierda(nodo.getIzquierdo()); //Rotacion simple a izq con pivote HI
                nodo.setIzquierdo(aux);
                balancear(nodo, padre); //Balanceo nodo (paso recursivo con nuevo balance de HI)
            }
        }
    }

    private NodoAVL rotarIzquierda(NodoAVL pivote) {
        //Guardo hijo derecho del pivote
        NodoAVL hD = pivote.getDerecho();
        //Guardo hijo izquierdo del hijo derecho
        NodoAVL temp = hD.getIzquierdo();
        //Establezco pivote como hijo izquierdo de su hijo derecho
        hD.setIzquierdo(pivote);
        //Establezco hijo izquierdo del hijo derecho como hijo derecho del pivote
        pivote.setDerecho(temp);
        //Reestablezco alturas de pivote y hD
        pivote.recalcularAltura();
        hD.recalcularAltura();
        //Retorna nueva raiz de subarbol
        return hD;
    }

    private NodoAVL rotarDerecha(NodoAVL pivote) {
        //Guardo hijo izquierdo del pivote
        NodoAVL hI = pivote.getIzquierdo();
        //Guardo hijo derecho del hijo izquierdo
        NodoAVL temp = hI.getDerecho();
        //Establezco pivote como hijo derecho de su hijo izquierdo
        hI.setDerecho(pivote);
        //Establezco hijo derecho del hijo izquierdo como hijo izquierdo del pivote
        pivote.setIzquierdo(temp);
        //Reestablezco alturas de pivote y hI
        pivote.recalcularAltura();
        hI.recalcularAltura();
        //Retorna nueva raiz de subarbol
        return hI;
    }

    public boolean pertenece(Comparable elem) {
        //Retorna verdadero si elem se encuentra en el arbol
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        //Metodo auxiliar que verifica si un elemento esta en el arbol
        boolean encontrado;
        if (nodo != null) { //Si nodo no es nulo
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

    public Lista listar() { 
        //Retorna una lista de los elementos del arbol
        Lista list = new Lista();
        listarAux(this.raiz, list);
        return list;
    }

    private void listarAux(NodoAVL nodo, Lista list) {
        //Lista elementos del arbol de menor a mayor, realizando recorrido inorden inverso
        if (nodo != null) { 
            listarAux(nodo.getDerecho(), list);
            list.insertar(nodo.getElemento(), 1);
            listarAux(nodo.getIzquierdo(), list);
        }
    }

    public Lista listarRango(Comparable min, Comparable max) {
        //Lista elementos del arbol de menor a mayor que se encuentran en el intervalo [min,max]
        Lista list = new Lista();
        listarRangoAux(this.raiz, min, max, list);
        return list;
    }

    private void listarRangoAux(NodoAVL nodo, Comparable min, Comparable max, Lista list) {
        //Inserta los elementos => min y <= max. Realizo insercion con recorrido inorden inverso (der, raiz, izq)
        //Si nodo no es nulo
        if (nodo != null) {
            //Elemento de nodo
            Comparable elemNodo = nodo.getElemento(); 
            //Comparaciones de elemNodo con los extremos min y max
            int comparacionMin = elemNodo.compareTo(min);
            int comparacionMax = elemNodo.compareTo(max);
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
        //Retorna un string que contiene una representacion del ArbolAVL
        String cad;
        if (this.esVacio()){
            cad = "Arbol Vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoAVL nodo){
        //Metodo auxiliar que concatena los elementos del arbol en un string
        String cad = "";
        //Si el nodo no es nulo
        if (nodo != null) {
            //Obtengo hijos de nodo
            NodoAVL izq = nodo.getIzquierdo(); 
            NodoAVL der = nodo.getDerecho();
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

    public ArbolAVL clone() {
        //Retorna un clon del arbol actual
        ArbolAVL clon = new ArbolAVL();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVL cloneAux(NodoAVL nodo) {
        //Metodo auxiliar que copia nodos de un arbol
        NodoAVL copia = null;
        //Si nodo no es nulo
        if (nodo != null) {
            /*Creo un nuevo nodo con el mismo elemento de nodo y enlazado recursivamente
            a los clones de HI e HD del nodo original*/
            copia = new NodoAVL(nodo.getElemento(), cloneAux(nodo.getIzquierdo()), cloneAux(nodo.getDerecho()));
        }
        return copia;
    }
}
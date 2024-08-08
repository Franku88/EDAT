package estructuras.especifico.diccionario;
import estructuras.conjuntistas.dinamicas.ArbolAVL;
import estructuras.conjuntistas.dinamicas.NodoAVL;
import estructuras.lineales.dinamicas.Lista; //Usado en métodos de listado
@SuppressWarnings({"rawtypes", "unchecked"})

public class Diccionario {

    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        //Inserta un elem con clave y dato, conservando el orden de el arbol 
        boolean exito = true;
        if (this.esVacio()) {
            this.raiz = new NodoAVLDicc(clave, dato);
        } else {
            exito = insertarAux(this.raiz, clave, dato, null);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodo, Comparable clave, Object dato, NodoAVLDicc padre) {
        //Metodo auxiliar que busca la posicion del nuevo nodo y lo inserta si no se encuentra
        //Retorna verdadero si se pudo insertar, falso si la clave ya se encuentra en el arbol
        //padre: variable para balancear a nodo si fuera necesario
        boolean exito = true;
        //Comparo clave a insertar con clave del nodo actual
        int comparacion = clave.compareTo(nodo.getClave());
        if (comparacion == 0) { //Si la clave se encuentra
            exito = false; //error la clave ya existe en el arbol    
        } else {            
            if (comparacion < 0) { //Si la clave es menor al de nodo
                if (nodo.getIzquierdo() == null) { //Lo inserto si no tiene HI
                    nodo.setIzquierdo(new NodoAVLDicc(clave, dato, null, null));
                } else { //Si tiene HI, realizo invocacion con el mismo
                    exito = insertarAux(nodo.getIzquierdo(), clave, dato, nodo);
                }
            } else { //Si la clave es mayor al del nodo
                if (nodo.getDerecho() == null) { //Lo inserto si no tiene HD
                    nodo.setDerecho(new NodoAVLDicc(clave, dato, null, null));
                } else { //Si tiene HD, realizo invocacion con el mismo
                    exito = insertarAux(nodo.getDerecho(), clave, dato, nodo);
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
        //Retorna verdadero si el diccionario no tiene elementos
        return this.raiz == null;
    }

    public void vaciar() {
        //Metodo que vacia el arbol actual
        this.raiz = null;
    }

    public boolean eliminar(Comparable clave) {
        //Metodo que remueve el nodo con clave ingresado por parametro del arbol actual 
        return eliminarAux(this.raiz, null, clave);
    }

    private boolean eliminarAux(NodoAVLDicc nodo, NodoAVLDicc padre, Comparable clave) {
        //Metodo que elimina un elemento del arbol conservando el orden del mismo
        //Retorna verdadero si se elimina, falso si no se encuentra
        boolean exito = false;
        if (nodo != null) {
            //Hijos de nodo actual
            NodoAVLDicc izq = nodo.getIzquierdo();
            NodoAVLDicc der = nodo.getDerecho();
            //Comparo clave a eliminar con clave del nodo actual
            int comparacion = clave.compareTo(nodo.getClave());
            //Casos
            if (comparacion == 0) { //Si se encontró el nodo que contiene clave
                exito = true;
                if (izq != null && der != null) { //Si el nodo a eliminar tiene dos hijos
                    casoDosHijos(nodo, padre);
                } else {
                    if (izq == null && der == null) { //Si el nodo a eliminar es hoja
                        casoHoja(nodo, padre);
                    } else { //Si el elem a eliminar tiene solo un hijo
                        casoUnHijo(nodo, padre);
                    }
                }
            } else { //Si no se encuentra, se busca en sus hijos
                if (comparacion < 0) { //Si elem es menor al del nodo
                    exito = eliminarAux(izq, nodo, clave); //Busco por subarbol izquierdo
                } else { //Si elem es mayor al del nodo
                    exito = eliminarAux(der, nodo, clave); //Busco por subarbol derecho
                }
            }
            if (exito) { //Si se elimino, verifico balanceo
                nodo.recalcularAltura(); //Recalculo altura 
                int balance = nodo.balance(); //Veo balance de nodo
                if (balance < -1 || balance > 1) { //Si esta desbalanceado
                    balancear(nodo, padre);
                    nodo.recalcularAltura();
                } 
            } 
        }
        return exito;
    }

    private void casoHoja(NodoAVLDicc nodo, NodoAVLDicc padre) {
        //Caso del metodo eliminar, elimina un nodo que es hoja.
        if (padre != null) {
            //Si clave de nodo es menor al de su padre, elimino HI de padre
            if ((nodo.getClave()).compareTo(padre.getClave()) < 0) {
                padre.setIzquierdo(null);
            } else { //Si clave es mayor al de su padre, elimino HD de padre
                padre.setDerecho(null);
            }
        } else { //Si nodo es raiz
            this.raiz = null;
        }
    }

    private void casoUnHijo(NodoAVLDicc nodo, NodoAVLDicc padre) {
        //Caso del metodo eliminar, elimina nodo que tiene un solo hijo
        //Precondicion: nodo tiene al menos un hijo no nulo
        NodoAVLDicc hijo = nodo.getIzquierdo(); //Tomo HI
        if (hijo == null) { //Si HI es null, entonces HD no es null
            hijo = nodo.getDerecho();
        }
        if (padre != null) { //Si nodo no es raiz
            //Si clave nodo es menor al de su padre, coloco hijo como HI
            if ((nodo.getClave()).compareTo(padre.getClave()) < 0) {
                padre.setIzquierdo(hijo);
            } else { //Si clave nodo es mayor al de su padre, coloco hijo como HD
                padre.setDerecho(hijo);
            }
        } else { //Si nodo es raiz, lo reemplazo por su hijo
            this.raiz = hijo;
        }
    }

    private void casoDosHijos(NodoAVLDicc nodo, NodoAVLDicc padre) {
        //Caso del metodo eliminar, elimina nodo que tiene ambos hijos 
        //Elijo buscar nodo con clave menor del subarbol derecho
        NodoAVLDicc subarbol = nodo.getDerecho();
        NodoAVLDicc candidato = menorEnSubarbol(subarbol);
        /* Otra opcion: buscar el mayor del subarbol izquierdo
        NodoAVLDicc subarbol = nodo.getIzquierdo();
        NodoAVLDicc candidato = mayorEnSubarbol(subarbol); */
        eliminarAux(subarbol, nodo, candidato.getClave()); //Elimino candidato encontrado
        if (padre != null) { //Si nodo no es raiz
            if ((candidato.getClave()).compareTo(padre.getClave()) < 0) {
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
        //Metodo que retorna la clave mas chica del arbol
        Comparable menor;
        if (!this.esVacio()) {
            menor = menorEnSubarbol(this.raiz).getClave();
        } else {
            menor = null;
        }
        return menor;
    }

    public Comparable maximoElem() { 
        //Metodo que retorna la clave mas grande del arbol
        Comparable mayor;
        if (!this.esVacio()) {
            mayor = mayorEnSubarbol(this.raiz).getClave();
        } else {
            mayor = null;
        }
        return mayor;
    }

    private NodoAVLDicc menorEnSubarbol(NodoAVLDicc nodo) {
        //Metodo que retorna referencia al Nodo con menor clave de un subarbol
        NodoAVLDicc izq = nodo.getIzquierdo();
        if (izq != null) { //Si tiene nodo izquierdo, sigo buscando
            nodo = menorEnSubarbol(izq);
        } //Si no tiene nodo izquierdo, entonces ya es el menor
        return nodo;
    }

    private NodoAVLDicc mayorEnSubarbol(NodoAVLDicc nodo) {
        //Metodo que retorna referencia al Nodo con mayor clave de un subarbol
        NodoAVLDicc der = nodo.getDerecho();
        if (der != null) { //Si tiene nodo derecho, sigo buscando
            nodo = mayorEnSubarbol(der);
        } //Si no tiene nodo derecho, entonces ya es el mayor
        return nodo;
    }

    private void balancear(NodoAVLDicc nodo, NodoAVLDicc padre) {
        /*Metodo aux que balancea el nodo con 4 casos
        balance: variable con el balance de nodo
        padre: es el padre de nodo, usado para asignar a su hijo desbalanceado una vez termine el proceso
        precondicion: nodo no es vacio y balance es 2 o -2*/
        NodoAVLDicc aux;
        if (nodo.balance() < -1) { //Si subarbol con raiz nodo esta torcido a derecha
            if (nodo.getDerecho().balance() <= 0) { //Si HD esta torcido a la der (mismo lado que nodo)
                nodo = rotarIzquierda(nodo); //Roto HD a la izq, contrario al lado del padre
                if (padre == null) { //Caso especial, nodo a balancear
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    if ((nodo.getClave()).compareTo(padre.getClave()) > 0) { //Si nodo es mayor a padre
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
                nodo = rotarDerecha(nodo); //Rotacion simple a derecha con pivote nodo
                if (padre == null) { //Caso especial, nodo es raiz
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    if (nodo.getClave().compareTo(padre.getClave()) > 0) { //Si nodo es mayor a padre
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

    public NodoAVLDicc rotarIzquierda(NodoAVLDicc pivote) {
        //Guardo hijo derecho del pivote
        NodoAVLDicc hD = pivote.getDerecho();
        //Guardo hijo izquierdo del hijo derecho
        NodoAVLDicc temp = hD.getIzquierdo();
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

    public NodoAVLDicc rotarDerecha(NodoAVLDicc pivote) {
        //Guardo hijo izquierdo del pivote
        NodoAVLDicc hI = pivote.getIzquierdo();
        //Guardo hijo derecho del hijo izquierdo
        NodoAVLDicc temp = hI.getDerecho();
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

    public boolean pertenece(Comparable clave) {
        //Retorna verdadero si clave se encuentra en el arbol
        return perteneceAux(this.raiz, clave);
    }

    private boolean perteneceAux(NodoAVLDicc nodo, Comparable clave) {
        //Metodo auxiliar que verifica si una clave esta en el arbol
        boolean encontrado;
        if (nodo != null) { //Si nodo no es nulo
            //Compara clave con clave del nodo
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion == 0) { //Si son iguales
                encontrado = true;
            } else {
                if (comparacion < 0) { //Si elem es menor al elemNodo, busco en subArbol izq
                    encontrado = perteneceAux(nodo.getIzquierdo(), clave);
                } else { //Si elem es mayor a al elemNodo, busco en subArbol der
                    encontrado = perteneceAux(nodo.getDerecho(), clave);
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

    private void listarAux(NodoAVLDicc nodo, Lista list) {
        //Lista elementos del arbol de menor a mayor, realizando recorrido inorden inverso
        if (nodo != null) { 
            listarAux(nodo.getDerecho(), list);
            list.insertar(nodo.getClave(), 1);
            listarAux(nodo.getIzquierdo(), list);
        }
    }

    public Lista listarRango(Comparable min, Comparable max) {
        //Lista elementos del arbol de menor a mayor que se encuentran en el intervalo [min,max]
        Lista list = new Lista();
        listarRangoAux(this.raiz, min, max, list);
        return list;
    }

    private void listarRangoAux(NodoAVLDicc nodo, Comparable min, Comparable max, Lista list) {
        //Inserta las claves => min y <= max. Realizo insercion con recorrido inorden inverso (der, raiz, izq)
        if (nodo != null) { //Si nodo no es nulo
            Comparable claveNodo = nodo.getClave(); //Clave de nodo
            //Comparaciones de claveNodo con los extremos min y max
            int comparacionMin = claveNodo.compareTo(min);
            int comparacionMax = claveNodo.compareTo(max);
            if (comparacionMax < 0) { //Si claveNodo < max, recorro subArbol derecho
                listarRangoAux(nodo.getDerecho(), min, max, list); 
            }
            //Si min <= claveNodo <= max, listo elemento en posicion 1
            if (comparacionMin >= 0 && comparacionMax <= 0) {
                list.insertar(claveNodo, 1);
            }
            if (comparacionMin > 0) { //Si min < claveNodo, recorro subArbol izquierdo
                listarRangoAux(nodo.getIzquierdo(), min, max, list);
            }
        }
    }

    public String toString(){
        //Retorna un string que contiene una representacion del ArbolAVLDicc
        String cad;
        if (this.esVacio()){
            cad = "Arbol Vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoAVLDicc nodo){
        //Metodo auxiliar que concatena las claves del arbol en un string
        String cad = "";
        if (nodo != null) { //Si el nodo no es nulo
            //Obtengo hijos de nodo
            NodoAVLDicc izq = nodo.getIzquierdo(); 
            NodoAVLDicc der = nodo.getDerecho();
            //Concateno clave del nodo
            cad = cad +"("+ nodo.getClave() + ") ->  ";
            if (izq != null) { //Si tiene HI, lo concateno
                cad = cad + "HI: " + izq.getClave() + "    ";
            } else {
                cad = cad + "HI: -    ";
            }
            if (der != null) { //Si tiene HD, lo concateno
                cad = cad + "HD: " + der.getClave() + "\n";
            } else {
                cad = cad + "HD: -\n";
            }
            //Continuo con HI e HD
            cad = cad + toStringAux(izq);
            cad = cad + toStringAux(der);
        }
        return cad;
    }

    public Diccionario clone() {
        //Retorna un clon del diccionario actual
        Diccionario clon = new Diccionario();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVLDicc cloneAux(NodoAVLDicc nodo) {
        //Metodo auxiliar que copia nodos de un diccionario
        //El clon referencia a los datos del original, no los copia
        NodoAVLDicc copia = null;
        if (nodo != null) { //Si nodo no es nulo
            /*Creo un nuevo nodo con la misma clave de nodo y enlazado recursivamente
            a los clones de HI e HD del nodo original*/
            copia = new NodoAVLDicc(nodo.getClave(), nodo.getDato(), cloneAux(nodo.getIzquierdo()), cloneAux(nodo.getDerecho()));
        }
        return copia;
    }
    
}

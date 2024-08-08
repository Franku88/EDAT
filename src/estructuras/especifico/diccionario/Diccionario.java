package estructuras.especifico.diccionario;
import estructuras.conjuntistas.dinamicas.NodoAVL;
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

    private boolean insertarAux(NodoAVLDicc nodo, Comparable clave, Object dato, NodoAVLDicc padre){
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

    public Comparable minimoElem(){
        //Metodo que retorna la clave mas chica del arbol
        Comparable menor;
        if (!this.esVacio()) {
            menor = menorEnSubarbol(this.raiz).getClave();
        } else {
            menor = null;
        }
        return menor;
    }

    public Comparable maximoElem(){ 
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

    ///////////////////////////////////////
    private void balancear(NodoAVLDicc nodo, int balance, NodoAVLDicc padre) {
        /*Metodo aux que balancea el nodo con 4 casos
        balance: variable con el balance de nodo
        padre: es el padre de nodo, usado para asignar a su hijo desbalanceado una vez termine el proceso
        precondicion: nodo no es vacio y balance es 2 o -2*/
        NodoAVLDicc aux;
        if (balance < -1) { //Si subarbol con raiz nodo esta torcido a derecha
            int balanceHD = balance(nodo.getDerecho()); //Obtengo balance de HD
            if (balanceHD <= 0) { //Si HD esta torcido a la der
                nodo = rotarIzquierda(nodo); //Roto HD a la izq, contrario al lado del padre
                if (padre == null) { //Caso especial, nodo a balancear es raiz
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    int comparacion = (nodo.getClave()).compareTo(padre.getClave());
                    if (comparacion > 0) { //Si nodo es mayor a padre
                        padre.setDerecho(nodo);
                    } else { //Si nodo es menor a padre
                        padre.setIzquierdo(nodo);
                    }
                    padre.recalcularAltura();
                }
            } else { //Si HD esta torcido a la izq
                aux = rotarDerecha(nodo.getDerecho()); //Roto HD al mismo lado que el padre
                nodo.setDerecho(aux);
                balancear(nodo, balance, padre); //Balanceo a padre
            }
        } else { //Si subarbol con raiz nodo esta torcido a izq
            int balanceHI = balance(nodo.getIzquierdo()); //Obtengo balance de HI
            if (balanceHI >= 0) { //Si HI esta torcido a la izq
                nodo = rotarDerecha(nodo); //Roto HI a la der, contrario al lado del padre
                if (padre == null) { //Caso especial, nodo a balancear es raiz
                    this.raiz = nodo;
                } else {
                    //Comparo nodo con su padre
                    int comparacion = nodo.getClave().compareTo(padre.getClave());
                    if (comparacion > 0) { //Si nodo es mayor a padre
                        padre.setDerecho(nodo);
                    } else { //Si nodo es menor a padre
                        padre.setIzquierdo(nodo);
                    }
                    padre.recalcularAltura();
                }
            } else { //Si HI esta torcido a la der
                aux = rotarIzquierda(nodo.getIzquierdo()); //Roto HI al mismo lado que el padre
                nodo.setIzquierdo(aux);
                balancear(nodo, balance, padre); //Balanceo a padre
            }
        }
    }

    public NodoAVLDicc rotarIzquierda(NodoAVLDicc pivote){
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

    public NodoAVLDicc rotarDerecha(NodoAVLDicc pivote){
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

    
}

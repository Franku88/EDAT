package estructuras.especifico.diccionario;

import estructuras.conjuntistas.dinamicas.NodoAVL;

public class Diccionario {
    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        //Inserta un elem con clave y dato, conservando el orden de el arbol 
        boolean exito = true;
        if (this.esVacio()) {
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
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
        int comparacion = clave.compareTo(nodo.getClave()); //Comparo clave a insertar con clave del nodo actual
        if (comparacion == 0) { //Si la clave se encuentra
            exito = false; //error la clave ya existe en el arbol    
        } else {            
            if (comparacion < 0) { //Si la clave es menor al de nodo
                NodoAVLDicc izq = nodo.getIzquierdo();
                if (izq == null) { //Lo inserto si no tiene HI
                    nodo.setIzquierdo(new NodoAVLDicc(clave, dato, null, null));
                } else { //Si tiene HI, realizo invocacion con el mismo
                    exito = insertarAux(izq, clave, dato, nodo);
                }
            } else { //Si la clave es mayor al del nodo
                NodoAVLDicc der = nodo.getDerecho();
                if (der == null) { //Lo inserto si no tiene HD
                    nodo.setDerecho(new NodoAVLDicc(clave, dato, null, null));
                } else { //Si tiene HD, realizo invocacion con el mismo
                    exito = insertarAux(der, clave, dato, nodo);
                }
            }
        }        
        if (exito) { //Si se inserto en alguna invocacion, verifico balanceo
            nodo.recalcularAltura(); //Recalculo altura 
            int balance = balance(nodo); //Veo balance de nodo
            if (balance < -1 || balance > 1) { //Si esta desbalanceado
                balancear(nodo, balance, padre);
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

    private int balance(NodoAVLDicc nodo){
        //Modulo que calcula el balance de un nodoAVL
        int izq , der;
        if (nodo.getIzquierdo() != null) {
            izq = nodo.getIzquierdo().getAltura();
        } else { //Altura de null es -1
            izq = -1;
        }
        if (nodo.getDerecho() != null) {
            der = nodo.getDerecho().getAltura();
        } else { //Altura de null es -1
            der = -1;
        }
        return (izq - der); 
    }

    /***/
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

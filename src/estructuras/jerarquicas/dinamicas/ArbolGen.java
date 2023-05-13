package estructuras.jerarquicas.dinamicas;

import estructuras.lineales.dinamicas.Lista;

public class ArbolGen {
    
    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object nuevo, Object padre) {
        boolean exito = false;

        if (!this.esVacio()) {
            exito = insertarAux(this.raiz, nuevo, padre);
        } else {
            if (padre == null) { //Si el padre es null y Arbol esta vacio, se asigna nuevo como raiz
                this.raiz = new NodoGen(nuevo, null, null);
                exito = true;
            }
        }
        return exito;
    }

    private boolean insertarAux(NodoGen nodo, Object nuevo, Object padre) {
        boolean exito = false;
        if (nodo != null) {
            exito = nodo.getElem().equals(padre); //Verifica si nodo tiene elemento padre
            if (exito) { //Si tiene elemento padre, entonces se inserta nuevo como hijo 
                NodoGen aux = nodo.getHijoIzquierdo(); //Guarda el hijo izquierdo actual
                //Crea nuevo nodo con elemento nuevo y pone como hermano derecho al hijo aux
                nodo.setHijoIzquierdo(new NodoGen(nuevo, null, aux)); 
            } else { //Si no tiene el elemento padre, lo busca en su hermano derecho
                exito = insertarAux(nodo.getHermanoDerecho(), nuevo, padre);
                if (!exito) { //Si sus hermanos no tienen el elemento padre, lo busca en sus hijos
                    exito = insertarAux(nodo.getHijoIzquierdo(), nuevo, padre);
                }
            }
        }
        return exito;
    }

    public boolean pertenece(Object elem) {
        boolean existe = false;
        if (!this.esVacio()) {
            existe = perteneceAux(this.raiz, elem);
        }
        return existe;
    }

    private boolean perteneceAux(NodoGen nodo, Object elem){
        boolean existe = false;
        if (nodo != null) {
            existe = nodo.getElem().equals(elem); //Verifica si nodo tiene elem
            if (!existe) { //Si no tiene elem, verifica en sus hermanos
                existe = perteneceAux(nodo.getHermanoDerecho(), elem);
                if (!existe) { //Si sus hermanos no tienen elem, lo busca en sus hijos
                    existe = perteneceAux(nodo.getHijoIzquierdo(), elem);
                }
            }
        }
        return existe;
    }

    public Lista ancestros(Object elem) {
        Lista ancest = new Lista();
        if (!this.esVacio()) {
            ancestrosAux(this.raiz, elem, ancest);
        }
        return ancest;
    }

    private boolean ancestrosAux(NodoGen nodo, Object elem, Lista ancest) {
        boolean encontrado = false;
        if (nodo != null) {
            if (elem.equals(nodo.getElem())){ //si encuentro el elem
                encontrado = true;
            } else { //si no es igual busco en sus hijos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !encontrado) { //mientras hijo exista y elem no sea encontrado entonces
                    encontrado = ancestrosAux(hijo, elem, ancest);
                    if (encontrado){ //si fue encontrado inserto
                        ancest.insertar(nodo.getElem(), 1);
                    } else {
                        hijo = hijo.getHermanoDerecho(); //si no es encontrado me muevo a su hermano
                    }
                }
            }
        }
        return encontrado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }     

    public void vaciar() {
        this.raiz = null;
    }

    public int altura(){
        //devuelve la altura del arbol
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoGen nodo){
        int ret = -1; 
        if (nodo != null){ //si no es vacio
            NodoGen hijo = nodo.getHijoIzquierdo();
            int aux;
            while (hijo != null){ //mientras tenga hijos, calculo sus alturas
               aux = alturaAux(hijo);
                if (ret < aux){ //busco el mas grande
                    ret = aux;
                }
                hijo = hijo.getHermanoDerecho(); //me muevo a su hermano
            }
            ret++; //sumo la altura del nodo     
        }
        return ret;
    }

    public int nivel(Object elem) {
        return nivelAux(this.raiz, elem, 0);
    }

    private int nivelAux(NodoGen nodo, Object elem, int lvl){
        //metodo que retorna el nivel de elem 
        int ret = -1;
        if (nodo != null){ 
            if (nodo.getElem().equals(elem)){
                ret = lvl; //elem encontrado, ret lvl
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo(); //primer hijo
                boolean flag = false;
                while (hijo != null && !flag){ //recorro hijos hasta encontrar el elem
                    ret = nivelAux(hijo, elem, lvl + 1);
                    if (ret != -1){  //si es distinto entonces ya se encontro y dejo de recorrer
                        flag = true;
                    } else {
                        hijo = hijo.getHermanoDerecho(); //me muevo a su hermano
                    }
                }
            }
        }
        return ret;
    }

    public Object padre(Object elem){ 
        //metodo que busca al padre de elem (si no existe retorna null)
        return padreAux(this.raiz, elem, null);
    }

    private Object padreAux(NodoGen n, Object hijo, Object padre){
        //metodo aux busca al elem (hijo) en preorden, y si lo encuentro retorna al padre
        Object ret = null;
        if (n != null){
            if (n.getElem().equals(hijo)){
                ret = padre;
            } else {
                NodoGen hijoAux = n.getHijoIzquierdo();
                boolean flag = false;
                while (!flag && hijoAux != null){
                    ret = padreAux(hijoAux, hijo, n.getElem());
                    if (ret != null){
                        flag = true;
                    } else {
                        hijoAux = hijoAux.getHermanoDerecho();
                    }
                }
            }
        }
        return ret;
    }



}

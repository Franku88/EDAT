package estructuras.jerarquicas.dinamicas;

import estructuras.lineales.dinamicas.Lista;
import estructuras.lineales.dinamicas.Cola;

public class ArbolGen {
    
    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object elem, Object padre){
        //Inserta elem en el arbol
        boolean exito = true;
        if (this.raiz == null){ //Insertar en arbol vacio
            this.raiz = new NodoGen(elem, null, null);
        } else {
            NodoGen aux = obtenerNodo(this.raiz, padre); //busco al padre
            if (aux == null){
                exito = false; //No encontro al padre
            } else {
                NodoGen nuevo = new NodoGen(elem, null, aux.getHijoIzquierdo()); //Creo el nuevo nodo con elem
                aux.setHijoIzquierdo(nuevo); //lo pongo como primer hijo 
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen nodo, Object elem){
        //Metodo que busca la primera aparicion del nodo que contiene a elem
        NodoGen ret = null; 

        if (nodo != null){
            if (nodo.getElem() == elem){
                ret = nodo;
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo(); //guardo el primer hijo
                boolean flag = false;

                while (!flag && hijo != null){ //si no ha sido encontrado y no se terminaron sus hijos
                    ret = obtenerNodo(hijo, elem);
                    if (ret != null){ //Nodo encontrado, salgo de la repetitiva
                        flag = true;
                    } else {
                        hijo = hijo.getHermanoDerecho(); //me muevo a su hermano
                    }
                } 
                
            }
        }
        return ret;
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

    public String toString(){
        //Crea un string del arbol actual
        String ret; 
        if (this.raiz == null){
            ret = "Arbol Vacio";
        } else {
            ret = toStringAux(this.raiz);
        }
        return ret;
    }

    private String toStringAux(NodoGen nodo){ 
        /*Metodo aux toString, concatena el nodo actual y sus hijos, 
        luego lo hace recursivamente con sus hijos*/
        String ret = "";
        if (nodo != null){
            NodoGen hijo = nodo.getHijoIzquierdo(); //Guardo hijo ext izq
            ret = ret + nodo.getElem()+ ")  ->  ";
            
            if (hijo == null){ //Si no tiene hijos
                ret = ret + "[Hoja]";
            } else {
                while (hijo != null){  //Si tiene hijos
                    ret = ret + hijo.getElem(); //Concateno hijo
                    hijo = hijo.getHermanoDerecho(); //Siguiente hijo
                    if (hijo != null){
                        ret = ret + ", ";
                    }
                }
                hijo = nodo.getHijoIzquierdo(); //Vuelvo al primer hijo
                //Concatena hijos de sus hijos recursivamente
                while (hijo != null){
                    ret = ret + "\n"+toStringAux(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return ret;
    }

    public Lista listarPreorden(){
        Lista list = new Lista();
        preordenAux(this.raiz, list);
        return list;
    }

    private void preordenAux(NodoGen nodo, Lista list) {
        if (nodo != null) {
            list.insertar(nodo.getElem(), list.longitud()+1);
            preordenAux(nodo.getHijoIzquierdo(), list);
            preordenAux(nodo.getHermanoDerecho(), list);
        }   
    }

    public Lista listarInorden(){
        Lista list = new Lista();
        inordenAux(this.raiz, list);
        return list;
    }

    private void inordenAux(NodoGen nodo, Lista list) {
        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();

            //Si tiene hijo, lo lista
            if (hijo != null) { 
                inordenAux(hijo, list);
                hijo = hijo.getHermanoDerecho();
            }

            //Luego de listar el primer hijo, lista el elemento
            list.insertar(nodo.getElem(),list.longitud()+1);

            //Luego lista los demas hijos
            while (hijo != null) {
                inordenAux(hijo, list);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }
    public Lista listarPosorden(){
        Lista list = new Lista();
        posordenAux(this.raiz, list);
        return list;
    }

    private void posordenAux(NodoGen nodo, Lista list) {
        if (nodo != null) {
            posordenAux(nodo.getHijoIzquierdo(), list);
            list.insertar(nodo.getElem(), list.longitud()+1);
            posordenAux(nodo.getHermanoDerecho(), list);
        }   
    }

    public Lista listarPorNiveles(){
        //Lista arbol en recorrido por niveles
        Lista list = new Lista();
        if (this.raiz != null){
            Cola q = new Cola(); //Cola aux para guardar nodos
            NodoGen nodoActual, hijo;
            int index = 1;

            q.poner(this.raiz); //pongo la raiz
            while (!q.esVacia()){
                nodoActual = (NodoGen) q.obtenerFrente(); //obtengo el nodo del frente
                list.insertar(nodoActual.getElem(), index); //Inserto nodoActual
                index++; //Aumento posicion en lista
                q.sacar(); //Saco nodo recien listado

                hijo = nodoActual.getHijoIzquierdo(); //Obtengo hijo izq del nodo listado
                while(hijo != null){
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho(); //Pongo demas hijos en la cola
                }
            }
        }
        return list;
    }

}
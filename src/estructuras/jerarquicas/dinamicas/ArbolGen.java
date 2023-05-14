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
                NodoGen hijo = nodo.getHijoIzquierdo(); //Guardo el primer hijo
                boolean flag = false;

                while (!flag && hijo != null){ //Si no ha sido encontrado y no se terminaron sus hijos
                    ret = obtenerNodo(hijo, elem);
                    if (ret != null){ //Nodo encontrado, salgo de la repetitiva
                        flag = true;
                    } else {
                        hijo = hijo.getHermanoDerecho(); //Me muevo a su hermano
                    }
                } 
                
            }
        }
        return ret;
    } 

    public boolean pertenece(Object elem) {
        //Metodo que verifica si un elemento pertenece al arbol generico
        boolean existe = false;
        if (!this.esVacio()) {
            existe = perteneceAux(this.raiz, elem);
        }
        return existe;
    }

    private boolean perteneceAux(NodoGen nodo, Object elem){
        //Metodo auxiliar de pertenece
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

    public boolean esVacio() {
        //Metodo que retorna verdadero si el arbol esta vacio
        return this.raiz == null;
    }     

    public void vaciar() {
        //Metodo que vacia un arbol generico
        this.raiz = null;
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

    public int altura(){
        //Metodo que retorna la altura del arbol
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoGen nodo){
        //Metodo auxiliar de altura
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
        //Metodo que retorna el nivel de elem ingresado
        return nivelAux(this.raiz, elem, 0);
    }

    private int nivelAux(NodoGen nodo, Object elem, int lvl){
        //Metodo auxiliar de nivel
        int ret = -1;
        if (nodo != null){ 
            if (nodo.getElem().equals(elem)){
                ret = lvl; //elem encontrado
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

    public Lista ancestros(Object elem) {
        //Metodo que retorna una lista con los ancestros de un elemento ingresado
        Lista ancest = new Lista();
        if (!this.esVacio()) {
            ancestrosAux(this.raiz, elem, ancest);
        }
        return ancest;
    }

    private boolean ancestrosAux(NodoGen nodo, Object elem, Lista ancest) {
        //Metodo auxiliar de ancestros
        boolean encontrado = false;
        if (nodo != null) {
            if (elem.equals(nodo.getElem())){
                encontrado = true;
            } else { //Si no contiene elem, busca en sus hijos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !encontrado) { //Mientras hijo exista y elem no sea encontrado entonces
                    encontrado = ancestrosAux(hijo, elem, ancest);
                    if (encontrado){ //Si fue encontrado inserto
                        ancest.insertar(nodo.getElem(), 1);
                    } else {
                        hijo = hijo.getHermanoDerecho(); //Si no es encontrado me muevo a su hermano
                    }
                }
            }
        }
        return encontrado;
    }

    public ArbolGen clone() {
        //Metodo que clona el arbol actual
        ArbolGen clon = new ArbolGen();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoGen cloneAux(NodoGen nodo){
        //Metodo auxiliar de clone
        NodoGen nClon;
        if (nodo != null) {
            //Crea una copia del nodo con su hijo y hermano, recursivamente
            nClon = new NodoGen(nodo.getElem(), cloneAux(nodo.getHijoIzquierdo()), cloneAux(nodo.getHermanoDerecho()));
        } else {
            nClon = null;
        }
        return nClon;
    }

    public Lista listarPreorden(){
        //Crea una lista en preorden del arbol actual
        Lista list = new Lista();
        preordenAux(this.raiz, list);
        return list;
    }

    private void preordenAux(NodoGen nodo, Lista list) {
        //Metodo auxiliar de listarPreorden
        if (nodo != null) {
            list.insertar(nodo.getElem(), list.longitud()+1);
            preordenAux(nodo.getHijoIzquierdo(), list);
            preordenAux(nodo.getHermanoDerecho(), list);
        }   
    }

    public Lista listarInorden(){
        //Crea una lista en inorden del arbol actual
        Lista list = new Lista();
        inordenAux(this.raiz, list);
        return list;
    }

    private void inordenAux(NodoGen nodo, Lista list) {
        //Metodo auxiliar de listarInorden
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
        //Crea una lista en posorden del arbol actual
        Lista list = new Lista();
        posordenAux(this.raiz, list);
        return list;
    }

    private void posordenAux(NodoGen nodo, Lista list) {
        //Metodo auxiliar de listarPosorden
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

    public String toString(){
        //Crea un string del arbol actual
        String cadena; 
        if (this.raiz == null){
            cadena = "Arbol Vacio";
        } else {
            cadena = toStringAux(this.raiz);
        }
        return cadena;
    }

    private String toStringAux(NodoGen nodo){ 
        /*Metodo aux toString, concatena el nodo actual y sus hijos, 
        luego lo hace recursivamente con sus hijos*/
        String cadena = "";
        if (nodo != null){
            NodoGen hijo = nodo.getHijoIzquierdo(); //Guardo hijo ext izq
            cadena = cadena + nodo.getElem()+ ")  ->  ";
            
            if (hijo == null){ //Si no tiene hijos
                cadena = cadena + "[Hoja]";
            } else {
                while (hijo != null){  //Si tiene hijos
                    cadena = cadena + hijo.getElem(); //Concateno hijo
                    hijo = hijo.getHermanoDerecho(); //Siguiente hijo
                    if (hijo != null){
                        cadena = cadena + ", ";
                    }
                }
                hijo = nodo.getHijoIzquierdo(); //Vuelvo al primer hijo
                //Concatena hijos de sus hijos recursivamente
                while (hijo != null){
                    cadena = cadena + "\n"+toStringAux(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return cadena;
    }

    public Lista frontera(){
        //Metodo que retorna una lista con las hojas del arbol actual
        Lista list = new Lista();
        fronteraAux(this.raiz, list);
        return list;
    }

    private void fronteraAux(NodoGen nodo, Lista list){
        //Metodo auxiliar de frontera
        if (nodo != null){
            NodoGen hijo = nodo.getHijoIzquierdo();
            if (hijo == null){ //Si no tiene hijos, lo inserto en list
                list.insertar(nodo.getElem(), 1);
            } else { //Si tiene hijos, invoco recurisvamente con los mismos
                while (hijo != null){
                    fronteraAux(hijo, list);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    //Ejercicios del TPO
    public boolean sonFrontera(Lista unaLista) {
        /*Metodo que verifica si los elementos de la lista ingresada son hojas en el arbol actual,
        unaLista no puede contener elementos repetidos, si los tiene, entonces la lista quedara no vacia
        al final de la ejecucion y retornara falso*/
        boolean esFrontera;
        if (unaLista.esVacia()){
            if (this.raiz == null){ 
                esFrontera = true; //Si arbol actual y unaLista son vacios
            } else {
                esFrontera = false; //Si unaLista esta vacia y el arbol actual no lo esta
            }
        } else {
            Lista list = unaLista.clone(); //Clono lista para no modificar original
            sonFronteraAux(this.raiz, list); //Llamado recursivo
            esFrontera = list.esVacia(); //Si list se vacio, entonces todos sus elementos eran frontera
        }
        return esFrontera;
    }
    
    private void sonFronteraAux(NodoGen nodo, Lista list) {
        //Metodo que busca cada hoja y elimina de la lista list si es encontrada
        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            if (hijo == null) { //Si nodo es hoja (no tiene HI)
                int pos = list.localizar(nodo.getElem()); //Buscar posición en la lista
                if (pos > 0) {
                    list.eliminar(pos); //Eliminarlo de la lista
                }
            } else {
                //Paso recursivo con sus hijos, si son hojas, los busca en la lista y elimina
                while (hijo != null && !list.esVacia()) { 
                    sonFronteraAux(hijo, list);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public boolean equals(ArbolGen unArbol) { 
        //Metodo que verifica si dos arboles son iguales
        boolean iguales = equalsAux(this.raiz, unArbol.raiz);
        return iguales;
    }

    private boolean equalsAux(NodoGen nodo1, NodoGen nodo2) {
        /*Metodo auxiliar que verifica si los elementos de dos nodos son iguales,
        si lo son, verifica sus hermanos e hijos recursivamente*/
        boolean iguales;
        if (nodo1 != null && nodo2 != null) { //Si ninguno es null, verifica si contienen el mismo elemento
            iguales = nodo1.getElem().equals(nodo2.getElem());
            if (iguales) { //Si son iguales sus elementos, verifica sus hermanos
                iguales = equalsAux(nodo1.getHermanoDerecho(), nodo2.getHermanoDerecho());
                if (iguales) { //Si son iguales los elementos de los hermanos, verifica sus hijos
                    iguales = equalsAux(nodo1.getHijoIzquierdo(), nodo2.getHijoIzquierdo());
                }
            }
        } else { //Si alguno de los dos es null, verifica que ambos lo sean
            iguales = nodo1 == null && nodo2 == null;
        }
        return iguales;
    }



}
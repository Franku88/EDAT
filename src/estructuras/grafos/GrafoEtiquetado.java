package estructuras.grafos;
import estructuras.lineales.dinamicas.Lista;
import estructuras.lineales.dinamicas.Cola;

public class GrafoEtiquetado {
    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object elem) {
        boolean noExiste = !existeVertice(elem);
        if (noExiste) { //Si no existe, lo inserta al principio
            this.inicio = new NodoVert(elem, this.inicio);
        }
        return noExiste; //Si no existe, entonces se inserta con exito
    }

    private NodoVert ubicarVertice(Object buscado) {
        //Busca y retorna vertice con elemento buscado del grafo
        //Si no se encuentra, retorna null
        NodoVert aux = this.inicio;
        while(aux != null && !buscado.equals(aux.getElemento())) {
            aux = aux.getSigVertice(); //Para comparar en la siguiente iteración
        }
        return aux;
    }

    public boolean existeVertice(Object buscado) {
        //Retorna falso si el vertice no es ubicado (ubicarVertice retorna null)
        return (ubicarVertice(buscado) != null);
    }

    public boolean eliminarVertice(Object buscado) {
        //Metodo que elimina vertice con elemento buscado
        //Retorna verdadero si es eliminado, falso en caso de no encontrarlo
        return eliminarVerticeAux(this.inicio, null, buscado);
    }

    private boolean eliminarVerticeAux(NodoVert nodo, NodoVert anterior, Object buscado) {
        //Metodo auxiliar para eliminar un vertice y sus respectivos arcos
        //Retorna verdadero si es eliminado, falso si no se encuentra el elemento buscado
        boolean exito = false;
        if (nodo != null) { //Si es null, entonces anterior tiene al ultimo vertice del grafo
            exito = buscado.equals(nodo.getElemento()); //Compara elemento buscado con el de nodo
            if (exito) { //Si se encuentra el elemento
                eliminarArcos(nodo); //Elimina todos los arcos enlazados al nodo
                if (anterior == null) { //Si anterior es null, entonces nodo es inicio
                    this.inicio = nodo.getSigVertice();
                } else { //Si no, enlazo anterior vertice al siguiente vertice del eliminado
                    anterior.setSigVertice(nodo.getSigVertice());
                }
            } else { //Si no se encuentra, paso recursivo con siguiente vertice
                exito = eliminarVerticeAux(nodo.getSigVertice(), nodo, buscado);
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen , Object destino, double etiqueta) {
        /*Metodo que inserta un arco con elementos origen y destino ingresado, 
        ademas de la etiqueta del arco*/
        boolean exito = false;
        NodoVert aux = this.inicio, nodoOrig = null, nodoDest = null;
        //No uso obtenerVertice ya que recorreria dos veces los vertices en el peor de los casos
        while (aux != null && (nodoOrig == null || nodoDest == null)) { //Hasta encontrar ambos nodos o llegar al ultimo vertice
            if (origen.equals(aux.getElemento())) { //Compara con origen
                nodoOrig = aux;
            } else { //Si no es origen
                if (destino.equals(aux.getElemento())) { //Compara con destino
                    nodoDest = aux;
                }
            }
            aux = aux.getSigVertice(); //Referencio al siguiente vertice
        }
        exito = (nodoOrig != null && nodoDest != null); //Si encontro ambos vertices
        if (exito) { //Conecta ambos arcos (igual etiqueta pero ambos casos de orgen/destino)
            conectarAdy(nodoOrig, nodoDest, etiqueta);
            conectarAdy(nodoDest, nodoOrig, etiqueta);
        }
        return exito;
    }

    private void conectarAdy(NodoVert origen, NodoVert destino, double etiqueta){
        //Conecta un nodo origen con nodo destino, Precondicion: no deben ser nulos
        //Reemplaza el primer adyacente de origen con el ingresado, enlaza el antiguo primer al nuevo
        NodoAdy nuevo = new NodoAdy(destino, origen.getPrimerAdyacente(), etiqueta);
        origen.setPrimerAdyacente(nuevo);
    }

    public void eliminarArcos(NodoVert nodo) {
        //Metodo que elimina todos los arcos enlazados a nodo (como inicio y destino)
        //Precondicion: nodo no es null
        NodoAdy ady = nodo.getPrimerAdyacente();
        while (ady != null) {
            eliminarArcoAux(ady.getVertice(), nodo.getElemento()); //Elimina arco inverso (desde destino a inicio)
            ady = ady.getSigAdyacente(); //Siguiente arco
        }
        nodo.setPrimerAdyacente(null); //Desenlaza todos los adyacentes desde nodo
    }

    public boolean eliminarArco(Object origen, Object destino) {
        //Metodo que elimina el arco (arcos) de origen a destino (y destino a origen)
        //Retorna falso si origen no es encontrado o si no existe arco entre ambos
        /*Se busca origen, luego destino es buscado en adyacentes de origen, al buscar 
        en los adyacentes se verifica la existencia o no del arco sin necesidad de buscar
        a vertice destino*/
        boolean exito = false;
        NodoVert nodoOrig = ubicarVertice(origen);
        if (nodoOrig != null) { //Si encuentra origen, busco en sus adyacentes al destino
            NodoAdy ady = nodoOrig.getPrimerAdyacente(), anterior = null;
            NodoVert nodoDest = null;
            //No uso ubicarArco pues necesito referencia al anterior (ubicarArco no mantiene dicho valor)
            while (!exito && ady != null) { //Busco destino en cada ady
                exito = destino.equals(ady.getVertice().getElemento());
                if (exito) { //Si se encuentra vertice destino
                    nodoDest = ady.getVertice();
                } else { //Si no se encuentra vertice, siguiente vertice
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                }
            }
            if (exito) { //Si estan conectados, elimino arco (ambos sentidos)
                pisarArco(nodoOrig, anterior, ady); //Reemplazo referencia de arco desde el origen
                eliminarArcoAux(nodoDest, origen); //Elimino arco desde el destino
            }
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert nodo, Object elem) {
        //Busca y elimina arco desde nodo a elem, retorna verdadero si es eliminado
        NodoAdy anterior = null, aux = nodo.getPrimerAdyacente();
        boolean exito = false;
        while (!exito && aux != null) {
            exito = elem.equals(aux.getVertice().getElemento());
            if (!exito) { //Si adyacente aux actual no enlaza a elem
                anterior = aux;
                aux = aux.getSigAdyacente();
            }
        }
        if (exito) { //Si se encontro arco
            pisarArco(nodo, anterior, aux); 
        }
        return exito;
    }

    private void pisarArco(NodoVert nodo, NodoAdy anterior, NodoAdy eliminado) {
        //Metodo auxiliar que reemplaza la referencia al nodoAdy eliminado
        //Preconidiciones: nodo y eliminado no son nulos
        if (anterior == null) { //Caso en que eliminado sea primer ady
            nodo.setPrimerAdyacente(eliminado.getSigAdyacente());
        } else {
            anterior.setSigAdyacente(eliminado.getSigAdyacente());
        }
    }

    public boolean existeArco(Object origen, Object destino) {
        /*Metodo que retorna verdadero si existe arco desde un vertice con 
        elemento origen hasta vertice con elemento destino*/
        NodoVert nodoOrig = ubicarVertice(origen); //Busca origen
        return (ubicarArco(nodoOrig, destino) != null);
    }

    private NodoAdy ubicarArco(NodoVert origen, Object destino) {
        //Retorna arco referenciado en vertice origen y con vertice de elemento destino
        //Precondicion: origen no es null
        NodoAdy ady = null;
        boolean encontrado = false;
        if (origen != null) {
            ady = origen.getPrimerAdyacente();
            while (ady != null && !encontrado) { //Busco destino en cada ady
                encontrado = destino.equals(ady.getVertice().getElemento());
                if (!encontrado) { //Si no se encuentra arco con destino
                    ady = ady.getSigAdyacente(); //Busca al siguiente
                }
            }
        }
        return ady;
    }

    public boolean esVacio(){
        return this.inicio == null;
    }

    public void vaciar(){
        this.inicio = null;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        NodoVert aux = this.inicio; 
        NodoVert auxOrig = null;
        NodoVert auxDest = null;
        //Busco ambos nodos
        while (aux != null && (auxOrig == null || auxDest == null)) {
            if (origen.equals(aux.getElemento())) {
                auxOrig = aux;
            } else {
                if (destino.equals(aux.getElemento())) {
                    auxDest = aux;
                }
            } 
            aux = aux.getSigVertice();
        }
        if (auxOrig != null && auxDest != null) { //Si fueron encontrados, busco un camino
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxOrig, destino, visitados);
        }
        return exito;
    }

    public boolean existeCaminoAux(NodoVert nodo, Object dest, Lista vis){
        //Metodo auxiliar, recorre los caminos desde nodo hasta encontrar a dest
        //Almacena en vis los arcos ya recorridos
        boolean exito = false;
        if (nodo != null){
            if (dest.equals(nodo.getElemento())) { //Camino encontrado
                exito = true;
            } else {
                //Si no es el camino, verifico si hay camino entre nodo y dest
                vis.insertar(nodo.getElemento(), vis.longitud()+1);
                NodoAdy ady = nodo.getPrimerAdyacente();
                //Busco en todos sus adyacentes hasta encontrar dest
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElemento()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    
    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camMin = new Lista();
        NodoVert aux = this.inicio; 
        NodoVert auxOrig = null;
        NodoVert auxDest = null;
        while (aux != null && (auxOrig == null || auxDest == null)) { //Busco ambos nodos
            if (origen.equals(aux.getElemento())) {
                auxOrig = aux;
            } else {
                if (destino.equals(aux.getElemento())) {
                    auxDest = aux;
                }
            }
            aux = aux.getSigVertice();
        }
        if (auxOrig != null && auxDest != null) { //Fueron encontrados, busco un camino
            Lista camActual = new Lista();
            camMin = caminoCortoAux(auxOrig, destino, camMin, camActual);
        }
        return camMin;
    }

    /*Modificaciones realizadas*/
    private Lista caminoCortoAux(NodoVert nodo, Object destino, Lista camMin, Lista camActual) {
        if (nodo != null) {
            Object elem = nodo.getElemento();
            //Inserta nodo actual
            camActual.insertar(elem, camActual.longitud()+1);
            //Si encuentra destino
            if (elem.equals(destino)) {
                //Verifica que camActual es menor a camMin
                if (camMin.esVacia() || (camMin.longitud() > camActual.longitud())) {
                    //Clona camActual para no modificar la referencia
                    camMin = camActual.clone();
                }
            } else {
                NodoAdy ady = nodo.getPrimerAdyacente();
                NodoVert auxVert = null;
                //Recorro sus adyacentes 
                while (ady != null) {
                    auxVert = ady.getVertice();
                    //Si no fue visitado en este camino, entonces lo visita
                    if (camActual.localizar(auxVert.getElemento()) < 0) { 
                        camMin = caminoCortoAux(auxVert, destino, camMin, camActual); 
                    }
                    //Verifica siguiente arco
                    ady = ady.getSigAdyacente();
                }
            }
            //Quita nodo actual ya que puede haber mas caminos que lo visiten
            camActual.eliminar(camActual.longitud());
        }
        return camMin;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista camMax = new Lista();
        NodoVert aux = this.inicio; 
        NodoVert auxOrig = null;
        NodoVert auxDest = null;
        while (aux != null && (auxOrig == null || auxDest == null)) { //Busco ambos nodos
            if (origen.equals(aux.getElemento())) {
                auxOrig = aux;
            } else {
                if (destino.equals(aux.getElemento())) {
                    auxDest = aux;
                }
            }
            aux = aux.getSigVertice();
        }
        if (auxOrig != null && auxDest != null) { //si ambos fueron encontrados, busco un camino
            Lista camActual = new Lista();
            camMax = caminoLargoAux(auxOrig, destino, camMax, camActual);
        }
        return camMax;
    }

    private Lista caminoLargoAux(NodoVert nodo, Object destino, Lista camMax, Lista camActual) {
         if (nodo != null) {
            Object elem = nodo.getElemento();
            //Inserta nodo actual
            camActual.insertar(elem, camActual.longitud()+1);
            //Si encuentra destino
            if (elem.equals(destino)) {
                //Verifica que camActual es mayor a camMax
                if (camMax.longitud() < camActual.longitud()) {
                    //Clona camActual para no modificar la referencia
                    camMax = camActual.clone(); 
                }
            } else {
                NodoAdy ady = nodo.getPrimerAdyacente();
                NodoVert auxVert = null;
                //Recorro sus adyacentes
                while (ady != null) {
                    auxVert = ady.getVertice();
                    //Si no fue visitado en este camino, entonces lo visita
                    if (camActual.localizar(auxVert.getElemento()) < 0) {
                        camMax = caminoLargoAux(auxVert, destino, camMax, camActual); 
                    }
                    //Verifica siguiente arco
                    ady = ady.getSigAdyacente();
                }
            }
            //Quita nodo actual ya que puede haber mas caminos que lo visiten
            camActual.eliminar(camActual.longitud());
        }
        return camMax;
    }

    public Lista listarEnProfundidad() {
        //Visita cada vertice y listaEnProfundidad cada adyacente
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) { 
            //Si el vertice no fue visitado
            if (visitados.localizar(aux.getElemento()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados) {
        //Lista cada nodo adyacente al nodo vertice ingresado si no fueron listados
        if (nodo != null) {
            //Visita nodo
            visitados.insertar(nodo.getElemento(), visitados.longitud()+1);
            NodoAdy ady = nodo.getPrimerAdyacente();
            NodoVert auxVert = null;
            //Visita a los adyacentes que no fueron visitados
            while (ady != null) {
                auxVert = ady.getVertice();
                if (visitados.localizar(auxVert.getElemento()) < 0) {
                    listarEnProfundidadAux(auxVert, visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) { 
            //Si el vertice no fue visitado
            if (visitados.localizar(aux.getElemento()) < 0) {
                anchuraDesde(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void anchuraDesde(NodoVert inicio, Lista visitados) {
        Cola q = new Cola();
        visitados.insertar(inicio.getElemento(), visitados.longitud()+1);
        q.poner(inicio);
        while (!q.esVacia()) {
            NodoVert aux = (NodoVert) q.obtenerFrente();
            q.sacar();
            NodoAdy ady = aux.getPrimerAdyacente();
            while (ady != null) {
                NodoVert auxVert = ady.getVertice();
                //Si fue visitado
                if (visitados.localizar(auxVert.getElemento()) < 0) {
                    //Visito y recorro por anchura
                    visitados.insertar(auxVert.getElemento(), visitados.longitud()+1);
                    q.poner(auxVert);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }
    
    public String toString(){
        String cad;
        if (this.esVacio()) {
            cad = "Grafo vacio";
        } else {
            cad = toStringAux(this.inicio);
        }
        return cad;
    }

    private String toStringAux(NodoVert nodo) {
        //Retorna una cadena con el contenido del grafo, nodo no debe ser vacio
        String ret = "";
        NodoAdy ady = nodo.getPrimerAdyacente();
        //Concatena vertice
        ret = "+ ("+nodo.getElemento()+"): \n       ";
        //Concatena todos sus adyacentes
        if (ady != null) {
            while (ady != null){
                ret = ret + "->  "+ady.getVertice().getElemento()+": "+ady.getEtiqueta()+"\n       ";
                ady = ady.getSigAdyacente();
            }
        } else {
            ret = ret + "------ \n";
        }
        //Concatena siguiente vertice
        nodo = nodo.getSigVertice();
        if (nodo != null){
            ret = ret + "\n "+toStringAux(nodo);
        }
        return ret;
    }

    /*Clone */

}
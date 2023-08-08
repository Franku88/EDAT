package estructuras.grafos;
import estructuras.lineales.dinamicas.*; 

public class GrafoEtiquetado {

    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }
    
    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        //Verifica si existe
        NodoVert aux = ubicarVertice(elem);
        //Si no es encontrado, lo inserta al principio
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        //Busca y retorna vertice con elemento buscado
        NodoVert aux = this.inicio;
        while (aux != null && !buscado.equals(aux.getElemento())) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        NodoVert anterior = null;
        boolean exito = false;
        while (aux != null && !buscado.equals(aux.getElemento())) {
            anterior = aux;
            aux = aux.getSigVertice();
        }
        if (aux != null) {
            NodoAdy ady = aux.getPrimerAdyacente();
            while (ady != null) {
                eliminarArcoAux(ady.getVertice(), buscado);
                ady = ady.getSigAdyacente();
            }
            //Si anterior es null, entonces buscado se encuentra al principio
            if (anterior == null) {
                this.inicio = this.inicio.getSigVertice();
            } else {
                anterior.setSigVertice(aux.getSigVertice());
            }
            exito = true;
        }
        return exito;
    }

    public boolean insertarArco(Object origen , Object destino, double etiqueta) {
        boolean exito = false;
        NodoVert aux = this.inicio;
        NodoVert auxOrig = null;
        NodoVert auxDest = null;

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
        if (auxOrig != null && auxDest != null) {
            conectarAdy(auxOrig, auxDest, etiqueta);
            conectarAdy(auxDest, auxOrig, etiqueta);
            exito = true;
        }
        return exito;
    }

    private void conectarAdy(NodoVert origen, NodoVert destino, double etiqueta){
        //Conecta un nodo origen con nodo destino, Precondicion: no deben ser nulos
        NodoAdy nuevo = new NodoAdy(destino, origen.getPrimerAdyacente(), etiqueta);
        origen.setPrimerAdyacente(nuevo);
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert auxOrig = ubicarVertice(origen);
        
        //Si encuentra origen, busco en sus adyacentes al destino
        if (auxOrig != null) {
            NodoAdy ady = auxOrig.getPrimerAdyacente();
            NodoAdy anterior = null;
            NodoVert auxDest = null;

            //Busco destino en cada ady
            while (!exito && ady != null){
                if (destino.equals(ady.getVertice().getElemento())){
                    auxDest = ady.getVertice();
                    exito = true;
                } else {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                }
            }

            //Si estan conectados, elimino arcos
            if (exito) {
                //Elimino arco desde el origen
                if (anterior == null){ //caso especial es el primer ady
                    auxOrig.setPrimerAdyacente(ady.getSigAdyacente());
                } else {
                    anterior.setSigAdyacente(ady.getSigAdyacente());
                }
                //Elimino arco desde el destino
                eliminarArcoAux(auxDest, origen);
            }
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert vert, Object elem) {
        //Busca y elimina arco desde vert a elem, retorna verdadero si es eliminado
        NodoAdy anterior  = null;
        NodoAdy aux = vert.getPrimerAdyacente();
        boolean exito = false;
        while (!exito && aux != null) {
            if (elem.equals(aux.getVertice().getElemento())) {
                exito = true;
            } else {
                anterior = aux;
                aux = aux.getSigAdyacente();
            }
        }
        if (exito) {
            if (anterior == null) {
                vert.setPrimerAdyacente(aux.getSigAdyacente());
            } else {
                anterior.setSigAdyacente(aux.getSigAdyacente());
            }
        }
        return exito;
    }

    public boolean existeVertice(Object buscado) {
        NodoVert aux = ubicarVertice(buscado);
        return aux != null;
    }

    public boolean existeArco(Object origen, Object destino) {
        NodoVert auxOrig = ubicarVertice(origen);
        boolean exito = false;
        if (null != auxOrig) { //Si encuentra origen, busco en sus adyacentes al destino
            NodoAdy ady = auxOrig.getPrimerAdyacente();
            //Busco destino en cada ady
            while (!exito && ady != null) {
                exito = destino.equals(ady.getVertice().getElemento());
                if (!exito) {
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
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

    public boolean esVacio(){
        return this.inicio == null;
    }

    public void vaciar(){
        this.inicio = null;
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

}
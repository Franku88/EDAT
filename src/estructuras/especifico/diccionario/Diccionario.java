package estructuras.especifico.diccionario;
import estructuras.conjuntistas.dinamicas.NodoAVL;

public class Diccionario {
    private NodoAVL raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato){
        //inserta un elem conservando el orden del arbol 
        boolean verif = true;

        if (this.raiz == null){
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
        } else {
            verif = insertarAux(this.raiz, clave, dato, null);
        }
        return verif;
    }

    private boolean insertarAux(NodoAVLDicc n, Comparable elem, Object dato, NodoAVLDicc padreAux){
        //metodo aux que inserta un elem en un arbol no vacio, devuelve false si el elem ya existe
        // padreAux: variable para poder balancear a n (si es necesario)
        boolean flag = true;

        if (n != null){
            NodoAVLDicc izq = n.getIzquierdo(), der = n.getDerecho(); 

            if (elem.compareTo(n.getClave()) == 0){ 
                flag = false; //error el elem ya existe en el arbol
                
            } else if (elem.compareTo(n.getClave()) > 0) { //si el elem es mayor que la raiz voy al HD
                if (der == null){ //si n no tiene HD inserto
                    n.setDerecho(new NodoAVLDicc(elem, dato, null, null));
                } else { //paso recursivo con subarbol der
                    flag = insertarAux(der, elem, dato, n);
                }
            } else { //si el elem es menor que la raiz voy al HI
                if (izq == null){ //si n no tiene HI inserto
                    n.setIzquierdo(new NodoAVLDicc(elem, dato, null, null));
                } else { //Paso recursivo con subarbol izq
                    flag = insertarAux(izq, elem, dato, n);
                }
            }

            if (flag){ //si fue insertado verifico si hay desbalance
                n.recalcularAltura();
                int balance = balance(n); //veo el balance de n

                if (balance < -1 || balance > 1){ //si esta desbalanceado
                    balancear(balance, n, padreAux);
                    n.recalcularAltura();
                } 
            }                     
        }   
        return flag;
    }
}

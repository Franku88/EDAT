package estructuras.jerarquicas.testing;

import estructuras.jerarquicas.dinamicas.ArbolGen;
import estructuras.lineales.dinamicas.Lista;

/************* Autores ***********
- Franco Fabian Benitez, Legajo FAI-3169
* Jamiro Zuñiga, Legajo FAI-3429
* Agustín Ezequiel Heredia, Legajo FAI-2876
*/

public class TestSonFrontera {

    public static final String sErr = "ERROR", sOk = "OK";

    public static void main(String[] args) {
        ArbolGen arbol = new ArbolGen(), clon = new ArbolGen();
        Lista list = new Lista();
        
        System.out.println("****************************************************************");
        System.out.println("*              Test Metodo sonFrontera y equals                *");
        System.out.println("****************************************************************");
        System.out.println("");
        
        llenarArbol(arbol); 

        System.out.println("           Árbol a Testear:             ");
        System.out.println(" "
        + "\n                                A                        "
        + "\n                                |                        "
        + "\n                D ------------- C ---------- B           "
        + "\n                |                            |           "
        + "\n            +---+---+                    +---+---+       "
        + "\n            |   |   |                    |   |   |       "
        + "\n            J   I   H                    G   F   E       "
        + "\n                    |                            |       " 
        + "\n                 ---+---                     ----+---    "
        + "\n                 |     |                     |   |   |   "
        + "\n                 O     L                     N   M   K   "
        );
        System.out.println();
        System.out.println("Datos de arbol: \n"+arbol.toString());
        System.out.println();

        System.out.println("Una lista con todos las hojas del arbol:");
        llenarLista(list, "JIOLCGFNMK");
        System.out.println("\tLista: "+list.toString());        
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************"); 

        System.out.println("Lista con dos hojas hermanas del arbol:");
        list.vaciar();
        llenarLista(list, "GF");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Lista con dos hojas hermanas invertidas del arbol:");
        list.vaciar();
        llenarLista(list, "IJ");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");
        
        System.out.println("Arbol no vacio y lista vacia");
        list.vaciar();
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");
        
        System.out.println("Arbol vacio, lista con elementos:");
        arbol.vaciar();
        llenarLista(list, "ABC");
        System.out.println("\tArbol: "+arbol.toString());
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Arbol vacio y lista vacia");
        list.vaciar();
        System.out.println("\tArbol: "+arbol.toString());
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Arbol y lista con una sola hoja del arbol:");
        llenarArbol(arbol);
        llenarLista(list, "O");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");
        
        System.out.println("Lista con un elemento que no pertenece al arbol");
        list.vaciar();
        llenarLista(list, "Z");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Lista con elementos y solo uno de ellos es hoja");
        list.vaciar();
        llenarLista(list, "ZWCY");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Lista que no tiene todas las hojas del arbol");
        list.vaciar();
        llenarLista(list, "OLN");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");

        System.out.println("Lista con solo un elemento que no es hoja:");
        list.vaciar();
        llenarLista(list, "D");
        System.out.println("\tLista: "+list.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");  
        
        System.out.println("Lista con raiz del arbol: ");
        list.vaciar();
        llenarLista(list, "A");
        System.out.println("\tLista: "+list.toString());        
        System.out.println("\tSe espera ERROR: "+((arbol.sonFrontera(list)) ? sOk : sErr));
        System.out.println("****************************************************************");
        
        System.out.println("Arbol con solo un elemento y lista con el mismo");
        list.vaciar();
        arbol.vaciar();
        arbol.insertar('A', 0); 
        llenarLista(list, "A");
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tLista: \n"+list.toString());
        System.out.println("\tSe espera OK: "+((arbol.sonFrontera(list)) ? sOk : sErr)); 
        System.out.println("****************************************************************");           

        System.out.println("Comparo arbol y clon vacio");
        arbol.vaciar();
        llenarArbol(arbol);
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************"); 

        System.out.println("Comparo arbol y su clon");
        clon = arbol.clone();
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera OK: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************");

        System.out.println("Comparo arbol y clon modificado (Se agrega hijo A al nodo A)");
        clon.insertar('A','A');
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************"); 

        System.out.println("Comparo arbol y clon modificado (Se agrega hijo E al nodo F)");
        clon.insertar('E','F');
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************"); 

        System.out.println("Comparo arbol vacio y clon modificado");
        arbol.vaciar();
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera ERROR: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************"); 

        System.out.println("Comparo arbol vacio y clon vacio");
        arbol.vaciar();
        clon.vaciar();
        System.out.println("\tArbol: \n"+arbol.toString());
        System.out.println("\tClon: \n"+clon.toString());
        System.out.println("\tSe espera OK: "+((arbol.equals(clon)) ? sOk : sErr)); 
        System.out.println("****************************************************************");
    }

    public static void llenarArbol(ArbolGen a){
        //Método para llenar el arbol con elementos de prueba
        //Raiz
        a.insertar('A', 0);
        //Hijos de A
        a.insertar('B', 'A');
        a.insertar('C', 'A');
        a.insertar('D', 'A');
        //Hijos de B
        a.insertar('E', 'B');
        a.insertar('F', 'B');
        a.insertar('G', 'B');
        //Hijos de D
        a.insertar('H', 'D');
        a.insertar('I', 'D');
        a.insertar('J', 'D');
        //Hijos de E
        a.insertar('K', 'E');
        a.insertar('M', 'E');
        a.insertar('N', 'E');
        //Hijos de H
        a.insertar('L', 'H');
        a.insertar('O', 'H');
    }

    public static void llenarLista(Lista list, String cad){
        //Metodo que carga una litas con los caracteres de un string
        int pos, limite = cad.length();
        for (pos = 0; pos < limite; pos++){
            list.insertar(cad.charAt(pos), 1);
        }
    }
}
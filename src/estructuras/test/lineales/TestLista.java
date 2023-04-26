package estructuras.test.lineales;

import estructuras.lineales.dinamicas.Lista;

public class TestLista {

    /**
     * @autor Benitez, Franco Fabian FAI-3169
     * 
     */
    
    static String sOk = "OK", sError = "ERROR";
    public static void main(String[] args) {
        //Main
        System.out.println("//////////////////////// \t Test Lista \t ////////////////////////");
        Lista l1 = new Lista(), clon;
        System.out.println("Lista creada.                          \t\t " + sOk + "\t");
        System.out.println("Verifica si es vacia. Espera true.     \t\t " + ((l1.esVacia()) ? sOk : sError) + "\t");
        System.out.println("Verifica longitud. Espera 0            \t\t " + ((l1.getLongitud() == 0) ? sOk : sError) + "\t");
        System.out.println("Inserta 1. Espera |1|.          \t\t " + ((l1.insertar(1,1) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Inserta 2. Espera |1,2|.        \t\t " + ((l1.insertar(2,2) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Inserta 3. Espera |1,2,3|.      \t\t " + ((l1.insertar(3,3) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Inserta 4. Espera |1,2,3,4|.    \t\t " + ((l1.insertar(4,4) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Inserta 5. Espera |1,2,3,4,5|.   \t\t " + ((l1.insertar(5,5) ? sOk : sError)) + "\t" + l1.toString());
        l1.invertir();
        System.out.println("Invierto lista. Espera |5,4,3,2,1|.\t " + "\t\t" + l1.toString());
        l1.invertir();
        System.out.println("Invierto lista. Espera |1,2,3,4,5|.\t " + "\t\t" + l1.toString());
        System.out.println("Inserta x en posicion 3. Espera |1,2,x,3,4,5|.   " + ((l1.insertar("x",3) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Inserta x en posicion 1. Espera |x,1,2,x,3,4,5|. " + ((l1.insertar("x",1) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Elimina posicion 1. Espera |1,2,x,3,4,5|.\t " + ((l1.eliminar(1) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Elimina posicion 3. Espera |1,2,3,4,5|.\t\t " + ((l1.eliminar(3) ? sOk : sError)) + "\t" + l1.toString());
        System.out.println("Clono lista.                         \t\t " );
        clon = l1.clone();
        System.out.println("Inserta en posicion erronea. Espera falso.\t " + ((l1.eliminar(7) ? sError : sOk)) + "\t" + l1.toString());
        System.out.println("Recupero elemento de posicion 1. Espera 1.\t\t " + l1.recuperar(1));
        System.out.println("Recupero elemento de posicion 3. Espera 3.\t\t " + l1.recuperar(3));
        System.out.println("Recupero elemento de posicion 5. Espera 5.\t\t " + l1.recuperar(5));
        System.out.println("Elimino posicion 1. Espera |2,3,4,5|      \t " + (l1.eliminar(1)?sOk:sError)+ "\t" + l1.toString());
        System.out.println("Elimino posicion 1. Espera |3,4,5|        \t " + (l1.eliminar(1)?sOk:sError)+ "\t" + l1.toString());
        System.out.println("Elimino posicion 3. Espera |3,4|          \t " + (l1.eliminar(3)?sOk:sError)+ "\t" + l1.toString());
        System.out.println("Elimino posicion 2. Espera |3|            \t " + (l1.eliminar(2)?sOk:sError)+ "\t" + l1.toString());
        System.out.println("Elimino posicion 1. Espera ||             \t " + (l1.eliminar(1)?sOk:sError)+ "\t" + l1.toString());
        System.out.println("Verifico clon. Espera |1,2,3,4,5|         \t\t" + clon.toString());
        System.out.println("Localizo posicion de 3. Espera 3.         \t\t" + clon.localizar(3));
        System.out.println("Localizo posicion de 1. Espera 1.         \t\t" + clon.localizar(1));
        System.out.println("Localizo posicion de 5. Espera 5.         \t\t" + clon.localizar(5));
        System.out.println("Localizo posicion de 0. Espera -1.        \t\t" + clon.localizar(0));
        System.out.println("Localizo en lista vacia. Espera -1.       \t\t" + l1.localizar(0));           
    }

}
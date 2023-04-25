package estructuras.test.lineales;

import estructuras.lineales.dinamicas.Lista;
import estructuras.lineales.dinamicas.Pila;
import java.util.Scanner;

public class PruebaLista {
 
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean seguir = true;
            char opcion;
            Lista l1, l2, l3;
            menu();
            while (seguir) {
                System.out.print("Ingrese una opcion: ");
                opcion = sc.next().charAt(0);
                switch (opcion) {
                    case 'a':
                        l1 = new Lista();
                        l2 = new Lista();
                        l3 = new Lista();
                        //Cargo l1
                        l1.insertar(6,1);
                        l1.insertar(4,1);
                        l1.insertar(2,1);
                        //Cargo l2
                        l2.insertar(7,1);
                        l2.insertar(6,1);
                        l2.insertar(1,1);
                        l2.insertar(5,1);
                        System.out.println("Concateno "+l1.toString()+" y "+l2.toString());
                        l3 = concatenar(l1,l2);
                        System.out.println("Resultado: "+l3.toString());
                        break;
                    case 'b':
                        l1 = new Lista();
                        //l1.insertar(9,1);
                        l1.insertar(6,1);
                        l1.insertar(5,1);
                        l1.insertar(0,1);
                        l1.insertar(5,1);
                        l1.insertar(6,1);
                        l1.insertar(9,1);
                        l1.insertar(0,1);
                        l1.insertar(5,1);
                        l1.insertar(6,1);
                        l1.insertar(9,1);
                        System.out.println("Compruebo si la lista "+l1.toString()+" tiene la forma cad0cad0cad*.");
                        System.out.println("La lista "+ (comprobar(l1)?"cumple la propiedad.":"no cumple la propiedad."));
                        break;
                    case 'c': 
                        l1 = new Lista();
                        //l2 = new Lista();
                        l1.insertar(6,1);
                        l1.insertar(4,1);
                        l1.insertar(2,1);
                        l1.insertar(7,1);
                        l1.insertar(6,1);
                        l1.insertar(1,1);
                        l1.insertar(5,1);
                        System.out.println("La lista "+l1.toString()+" invertida es: "+invertir(l1).toString());
                        break;
                    case 'd':
                        System.out.println("!Hasta luego¡");
                        seguir = false;
                        break;
                    default:
                        System.out.println("--- Opcion invalida ---");
                        break;
                }
            }
        }
    }


    public static void menu() {
        //Metodo que imprime un menu por pantalla
        System.out.println("\t\t\t --- Menu --- \t\t\t");
        System.out.println("a) Concatenar");
        System.out.println("b) Comprobar");
        System.out.println("c) Invertir");
        System.out.println("d) Salir");
        System.out.println("\t\t\t ------------ \t\t\t");
    }


    public static Lista concatenar(Lista l1, Lista l2) {
        //Metodo que concatena l1 a l2 en una lista nueva
        Lista listaC = new Lista();
        //Concatena l1 en listaC
        concatenarR(listaC,l1,1,1);
        //Concatena l2 en listaC, desde donde quedo en la invocacion anterior
        concatenarR(listaC,l2,l1.getLongitud()+1,1);
        return listaC;
    }

    public static void concatenarR(Lista listaC, Lista listaO, int pC, int pO) {
        //Metodo que concatena recursivamente elementos de listaO al final de listaC
        //listaC donde se va a realizar la concatenacion
        //listaO desde donde se van a copiar los elementos
        //pC apunta a posicion donde insertar en la listaC
        //pO apunta a posicion donde recuperar de la listaO
        Object nuevo = listaO.recuperar(pO);
        if (listaO.getLongitud() == pO) {
            listaC.insertar(nuevo,pC);
        } else {
            listaC.insertar(nuevo,pC);
            concatenarR(listaC, listaO, pC+1, pO+1);
        }
    }

    public static boolean comprobar(Lista list) {
        //Metodo que compruba si una lista tiene la forma cad0cad0cadI
        boolean comp = false;
        int lim = list.localizar(0);
        /*Como es de la forma cad0cad0cadI, entonces la posicion del primer 0
        depende de la longitud de list,es decir 3*longitud(list)+2*/
        if (lim == ((list.getLongitud()-2)/3)+1) {
            if (lim != 1) {
                int i = 1, j = list.getLongitud();
                comp = true;
                while (i < lim && comp) {
                    Object elem = list.recuperar(i);
                    comp = elem == list.recuperar(i+lim) && elem == list.recuperar(j);
                    i++;
                    j--;
                }
            } else {
                comp = list.recuperar(1) == list.recuperar(2);;
            }
        }
        return comp;
    }

    public static Lista invertir(Lista listO) {
        Lista listI = new Lista();
        if (listO.getLongitud() > 0) {
            Pila pile = new Pila();
            invertirR(listI, listO, pile, 1);
        }
        return listI;
    }

    public static void invertirR(Lista listI, Lista listO, Pila pile, int pos) {
        Object elem = listO.recuperar(pos);
        if (pos == listO.getLongitud()) {
            listI.insertar(elem,listI.getLongitud()+1);
        } else {
            pile.apilar(elem);
            invertirR(listI, listO, pile, pos+1);
            listI.insertar(pile.obtenerTope(),listI.getLongitud()+1);
            pile.desapilar();
        }
    }
}

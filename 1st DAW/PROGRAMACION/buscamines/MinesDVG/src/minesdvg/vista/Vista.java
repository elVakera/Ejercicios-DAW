package minesdvg.vista;

/**
 * Classe encarregada de mostrar les dades i missatges tractades per la classe Model y Controlador
 */
public class Vista {
    /**
     * mostra el tauler introduit com a parametre donant-li un format
     * @param tauler array del camp que es vol mostrar
     */
    public static void monstrarCampDeMines(char [][] tauler){
        int tamanyFil = tauler.length, tamanyCol = tauler[0].length;

        //numero de columnes
        System.out.printf("\t%s", " ");
        for (int i = 1; i < tamanyCol - 1; i++){
            System.out.printf("\t%d", i);
        }
        System.out.println();

        //mostrar tauler y lletra de fila
        for (int i = 1; i < tamanyFil - 1; i++){
            System.out.printf("\t%c", (char)(i + 'A')-1);
           for (int j = 1; j < tamanyCol - 1; j++){
                System.out.printf("\t%c", tauler[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Printa per pantalla el missatge introduit com a parametre
     * @param msg missatge que es vol mostrar
     */
    public static void mostrarMisatge(String msg){
        System.out.println(msg);
    }
}
//java -jar .\nombreDeArchivo.jar
//-encoding utf8 -docencoding utf8 -charset utf8
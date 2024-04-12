~~~ java
package minesdvg.controlador;
import minesdvg.model.Model;
import minesdvg.vista.Vista;
import java.util.Scanner;


/**
 * Clase del joc encarregada de controlar les accions del jugador agafades per teclat
 */
public class Controlador {

    //scn variable global privada de tipus Scanner per detectar les accions del jugador
    private static final Scanner SCN = new Scanner(System.in);

    /**
     * Funcio encarregada de les eleccions el jugador
     */
    public static void jugar() throws Exception{
        /**
         * Constants locals
         * TREPITJAR nom de la jugada per desvelar casella
         * BANDERA nom de la jugada per colocar una bandera
         * ACABAR nom de la jugada per acabar de jugar
         * FACIL opcio del tauler 8 x 8 & 10 bombes
         * MIG opcio del tauler 16 x 16 & 40 bombes
         * DIFICIL opcio del tauler 16 x 30 & 99 bombes
         * SORTIR opcio per acabar el joc
         */
        final char TREPITJAR = 'T', BANDERA = 'B', ACABAR = 'A',
                   FACIL = '1', MIG = '2', DIFICIL = '3', SORTIR = 'S';

        /**
         * variables locals
         * files numero de files del tauler
         * columnes numero de columnes del tauler
         * bombes numero de bombes al tauler
         * coordenades posicio desitjada del tauler
         * op opcio de dificultat i jugada
         */
        String coordenades;
        int files, columnes, bombes, c, f;
        char op;
        boolean lvlOK;

        //Menu dificultat
        do{
            Vista.mostrarMisatge("Quin nivell vols escullir?\n" +
                                      "1 - 8x8 (10 bombes)\n" +
                                      "2 - 16x16 (40 bombes)\n" +
                                      "3 - 16x30 (99 bombes)\n" +
                                      "S - Sortir del programa");

            op = SCN.nextLine().toUpperCase().trim().charAt(0);

            switch (op){
                case FACIL:
                    files = 8;
                    columnes = 8;
                    bombes = 10;
                    //mostrar misatge inicial
                    Vista.mostrarMisatge("S'ha generat un tauler de " + files + " files X "
                                            + columnes + " columnes amb " + bombes + " bombes");

                    //inicia partida amb files, columnes i bombes
                    Model.inicialitzarJoc(files, columnes, bombes);
                    lvlOK = true;
                    break;
                case MIG:
                    files = 16;
                    columnes = 16;
                    bombes = 40;
                    //mostrar misatge inicial
                    Vista.mostrarMisatge("S'ha generat un tauler de " + files + " files X "
                                            + columnes + " columnes amb " + bombes + " bombes");

                    //inicia partida amb files, columnes i bombes
                    Model.inicialitzarJoc(files, columnes, bombes);
                    lvlOK = true;
                    break;
                case DIFICIL:
                    files = 16;
                    columnes = 30;
                    bombes = 99;
                    //mostrar misatge inicial
                    Vista.mostrarMisatge("S'ha generat un tauler de " + files + " files X "
                                            + columnes + " columnes amb " + bombes + " bombes");

                    //inicia partida amb files, columnes i bombes
                    Model.inicialitzarJoc(files, columnes, bombes);
                    lvlOK = true;
                    break;
                case SORTIR:
                    Vista.mostrarMisatge("ADEU !");
                    System.exit(0);
                    lvlOK = true;
                    break;
                default:
                    Vista.mostrarMisatge("ERROR al introduir l'opcio");
                    lvlOK = false;
            }
        }while(!lvlOK);

        //Partida en progres
        do{
            Vista.mostrarMisatge("Que vols fer? ((T)repitjar, (B)andera, (A)cabar)");
            op = SCN.nextLine().toUpperCase().trim().charAt(0);     //opcio de jugada

            switch (op){
                //revelar casella
                case TREPITJAR:
                    do {
                        Vista.mostrarMisatge("Quina casella vols trepitjar? (ex: A1)");
                        coordenades = SCN.nextLine();

                    }while (!coordenades.substring(0,0).matches("[A-Z]") && !coordenades.substring(1).matches("[0-9]+"));

                    f = coordenades.toUpperCase().charAt(0) - 'A' + 1;
                    c = Integer.parseInt(coordenades.substring(1));
                    Model.trepitjar(f, c);
                    break;
                //colocar bandera
                case BANDERA:
                    do {
                        Vista.mostrarMisatge("Quina casella vols marcar? (ex: A1)");
                        coordenades = SCN.nextLine();

                    }while (!coordenades.substring(0,0).matches("[A-Z]") && !coordenades.substring(1).matches("[0-9]+"));

                    f = coordenades.toUpperCase().charAt(0) - 'A' + 1;
                    c = Integer.parseInt(coordenades.substring(1));
                    Model.posarBandera(f, c);
                    break;
                //acabar partida
                case ACABAR:
                    Vista.mostrarMisatge("GAME OVER!");
                    break;

                default:
                    Vista.mostrarMisatge("Error al intoduir les dades, prova introuint una de les seguents lletres (T, B, A)");
                    //Model.comprovarSiElJocSHaAcabat();
            }
        }while(!Model.comprovarSiElJocSHaAcabat());
        Thread.sleep(10000);
    }
}
~~~~ java

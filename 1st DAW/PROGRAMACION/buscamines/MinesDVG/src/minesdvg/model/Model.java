package minesdvg.model;
import minesdvg.vista.Vista;

/**
 * Classe encarregada de el tractament de les dades del joc agafant aquestes onades per la classe Controlador
 */
public class Model {
    /**
     * variables constants globals privades
     * FILES tamany del tauler en eix X
     * COLUMNES tamany del tauler en eix Y
     * BOOMBES numero de bombes al tauler de joc
     */
    private static int FILES, COLUMNES, BOMBES;
    /**
     * variables globals privades
     * jocFinalitzat variable per finalizras o cuntinuar el joc
     * campDeMinesOcult tauler am l'informacio per jugar
     * campDeMinesVisible tauler de joc
     */
    private static boolean jocFinalitzat = true;
    private static char[][] campDeMinesOcult, campDeMinesVisible;
    //Metodes Privats
    /**
     * genera un camp de mines de medides determinades omplenant les caselles amb un caracter per diferenciar els posibles camps
     * @param tauler array que conforma el tauler que es vol generar
     * @param caracter variable que conte el caracter que diferencia els camps
     */
    private static void inicialitzarCampDeMines(char [][] tauler, char caracter) {
        //variables locals
        int tamanyFil = tauler.length, tamanyCol = tauler[0].length;

        //omple el camp de mines
        for (int i = 0; i < tamanyFil; i++){
            for (int j = 0; j < tamanyCol; j++){
                tauler[i][j] = caracter;            //col"loca el caracter a la casella
            }
        }
    }
    /**
     * comprova si el camp esta complert correctament
     * @return si s'ha omplert correctament o no
     */
    private static boolean comprovarSiHaGuanyat(){
        boolean win = true;
        for(int i = 1; i < campDeMinesOcult.length - 1; i++){
            for (int j = 1; j < campDeMinesOcult[0].length - 1; j++){
                if(!((campDeMinesVisible[i][j] == 'B' && campDeMinesOcult[i][j] == '☼') || (campDeMinesVisible[i][j] == campDeMinesOcult[i][j]))){
                    win = false;
                }
            }
        }
        return win;
    }
    /**
     * Mostra la posicio de les bombes, les banderes mal colocades i el progres completat del camp
     */
    private static void mostrarSolucio(){
        //recorre el tauler jugable
        for (int i = 1; i < campDeMinesOcult.length - 1; i++){
            for (int j = 1; j < campDeMinesOcult[0].length - 1; j++){
                //mostra si hi ha bomba
                if(campDeMinesOcult[i][j] == '☼'){
                    campDeMinesVisible[i][j] = campDeMinesOcult[i][j];
                }
                //mostra si hi ha una bandera mal col·locada
                if(campDeMinesVisible[i][j] == 'B' && campDeMinesOcult[i][j] != '☼'){
                    campDeMinesVisible[i][j] = '*';
                }
            }
        }
        Vista.monstrarCampDeMines(campDeMinesVisible);
    }
    /**
     * comprova si la casella es trepitjable i la revela, comprova les caselles del seu voltant i es truca a si mateixa
     * amb una coordenada del seu voltant. Si l casella es diferent a 0 mostra el numero i si es un 0 mostra el voltant
     * mentres no sigui una bomba
     * @param fila coordenada del tauler  en l'eix X
     * @param columna coordenada del tauler en l'eix Y
     */
    private static void trepitjarRecursivament(int fila, int columna){
        if(campDeMinesVisible[fila][columna] == '·'){
           campDeMinesVisible[fila][columna] = campDeMinesOcult[fila][columna];

            for(int i = fila - 1; i <= fila + 1; i++){
                for(int j = columna - 1; j <= columna + 1; j++){
                    if(campDeMinesOcult[i][j] == '0'){
                        trepitjarRecursivament(i, j);
                    }
                }
            }
            if(campDeMinesOcult[fila][columna] != '0'){
                campDeMinesVisible[fila][columna] = campDeMinesOcult[fila][columna];
            }else {
                for(int i = fila - 1; i <= fila +1; i++){
                    for(int j = columna - 1; j <= columna +1; j++){
                        if(campDeMinesOcult[i][j] != '☼'){
                            campDeMinesVisible[i][j] = campDeMinesOcult[i][j];
                        }
                    }
                }
            }
        }
    }
    //Metodes Publics
    /**
     * Genera el camp de mines i el tauler de joc segons el numero de files, columnes i bombes que rep com a parametres.
     * Primer inicialitza les variables i els camps necessaris, genera els camps, coloca les bombes aleatoriament,
     * genera la graella numerica per les bombes i mostra el camp de joc
     * @param f numero de files
     * @param c numero de columnes
     * @param m numero de bombes
     */
    public static void inicialitzarJoc(int f, int c, int m) {
        //variables constants locals
        final char VISIBLE = '·', OCULT = ' ';

        //inicialitzar variables
        FILES = f + 2;
        COLUMNES = c + 2;
        BOMBES = m;
        campDeMinesOcult = new char[FILES][COLUMNES];
        campDeMinesVisible = new char[FILES][COLUMNES];

        //crida de metodes
        inicialitzarCampDeMines(campDeMinesOcult, OCULT);       //crea camp amb solucio
        inicialitzarCampDeMines(campDeMinesVisible, VISIBLE);   //crea camp de joc
        posarBombesAleatoriament(campDeMinesOcult);             //coloca bombes al tauler aleatoriament
        comptarQuantesBombes(campDeMinesOcult);                 //coloca els numeros corresponents al voltant de les bombes

        //mostrar els camps
        //Vista.mostrarMisatge("\nCamp Ocult");
        //Vista.monstrarCampDeMines(campDeMinesOcult);
        //Vista.mostrarMisatge("\nCamp Visible");
        Vista.monstrarCampDeMines(campDeMinesVisible);
    }
    /**
     * Revela la casella seleccionada pels parametres fila i columna, mostra missatge si ja s'ha trepitjat o
     * si hi ha una bandera, si hi ha una bomba mostra els errors, bombes, estat del camp i acaba el joc sino revela la
     * casella i comprova si s'ha guanyat
     * @param fila valor coordenada X del tauler
     * @param columna volor de la coordenada Y del tauler
     */
    public static void trepitjar(int fila, int columna){
        //comprova si s'ha trepitjar
        if(campDeMinesVisible[fila][columna] != '·'){
            //comprova si hi ha bandera
            if(campDeMinesVisible[fila][columna] == 'B'){
                Vista.mostrarMisatge("Aquesta casella conte una bandera");
            }else {
                Vista.mostrarMisatge("Aquesta casella ja s'ha trepitjat");
            }
        }
        //comprovar si hi ha una mina
        if(campDeMinesOcult[fila][columna] == '☼'){
            Vista.mostrarMisatge("BOMBA!");
            mostrarSolucio();                           //mostrar les mines i les vanderes mal colocades
            jocFinalitzat = true;                       //GAME OVER

        }else{
            //revelar casella
            trepitjarRecursivament(fila,columna);           //revelar caselles
            //Vista.monstrarCampDeMines(campDeMinesOcult);
            Vista.monstrarCampDeMines(campDeMinesVisible);  //mostra camp actualitzat
            jocFinalitzat = false;
        }
        //comprova si ha guanyat
        if(comprovarSiHaGuanyat()){
            Vista.mostrarMisatge("Has guanyat");
            mostrarSolucio();
            jocFinalitzat = true;
        }
    }
    /**
     * Coloca una bandera en la casella determinada pels parametres rebuts fila i columna o la treu s'hi ha una,
     * en cas d'una casella ja revelada mostra missatge i finalment comprova si s'ha guanyat
     * @param fila valor de la coordenada X del tauler
     * @param columna valor de la coordenada Y del tauler
     */
    public static void posarBandera(int fila, int columna){

        //comprova si la casella ha estat trepitjada o no
        if(campDeMinesVisible[fila][columna] != '·'){
            Vista.mostrarMisatge("Casella trepitjada");
        }
        //comprova si hi ha una bandera o no
        if(campDeMinesVisible[fila][columna] == '·' || campDeMinesVisible[fila][columna] == 'B'){
            if(campDeMinesVisible[fila][columna] == 'B'){
                campDeMinesVisible[fila][columna] = '·';    //treu la bandera
            }
            else{
                campDeMinesVisible[fila][columna] = 'B';
            }
        }
        //comprova si ha guanyat
        if(comprovarSiHaGuanyat()){
            Vista.mostrarMisatge("Has guanyat");
            mostrarSolucio();
            jocFinalitzat = true;
        }else {
            jocFinalitzat = false;
            //Vista.monstrarCampDeMines(campDeMinesOcult);
            Vista.monstrarCampDeMines(campDeMinesVisible);      //mostra camp actualitzat
        }
    }
    /**
     * Comprova l'estat del joc
     * @return si s'ha acabat el joc o no
     */
    public static boolean comprovarSiElJocSHaAcabat(){
        if(jocFinalitzat){
            return true;
        }else {
            jocFinalitzat = true;
            return false;
        }
    }
    /**
     * Coloca aleatoriament les bombes dins del tauler de joc jugable fins acabar el total de BOMBES,
     * en cas de colocar una bomba en una casella que en conte una repeteix la randomitzacio
     * @param taulerO tauler que contindra les bombes
     */
    public static void posarBombesAleatoriament(char [][] taulerO) {
        //variables locals
        int tamanyFil = taulerO.length, tamanyCol = taulerO[0].length;
        int posiI, posiJ;

        //Col·loca de manera random les bombes al tauler
        for(int i = 0; i < BOMBES; i++){
            //coordenades del tauler jugable
            posiI = (int)(Math.random() * ((tamanyFil - 1) - 1) + 1);
            posiJ = (int)(Math.random() * ((tamanyCol - 1) - 1) + 1);

            //si no hi ha bomba la col·loca sino intenta un altre cop
            if(taulerO[posiI][posiJ] != '☼'){
                taulerO[posiI][posiJ] = '☼';    //posa bomba
            }else {
                i--;                            //refa l'intent
            }
        }
    }
    /**
     * Calcula per cada casella el numero de bombes que te al voltant i en coloca el seu valor numeric
     * @param taulerO que conte les bombes i volem numerar
     */
    public static void comptarQuantesBombes(char[][] taulerO) {
        int tamanyFil = taulerO.length, tamanyCol = taulerO[0].length;
        //recorre el tauler jugable
        for (int i = 1; i < tamanyFil - 1; i++){
            for (int j = 1; j < tamanyCol - 1; j++){
                int detectaBombes = 0;
                //detecta i suma la quantitat de bombes que hi ha al voltant seu
                if(taulerO[i][j] != '☼') {
                    if (taulerO[i - 1][j - 1] == '☼') {     //esq a
                        detectaBombes++;
                    }
                    if (taulerO[i - 1][j] == '☼') {         //a
                        detectaBombes++;
                    }
                    if (taulerO[i - 1][j + 1] == '☼') {     //dre a
                        detectaBombes++;
                    }
                    if (taulerO[i][j - 1] == '☼') {         //esq
                        detectaBombes++;
                    }
                    if (taulerO[i][j + 1] == '☼') {         //dre
                        detectaBombes++;
                    }
                    if (taulerO[i + 1][j - 1] == '☼') {     //esq b
                        detectaBombes++;
                    }
                    if (taulerO[i + 1][j] == '☼') {         //b
                        detectaBombes++;
                    }
                    if (taulerO[i + 1][j + 1] == '☼') {     //dre b
                        detectaBombes++;
                    }
                    taulerO[i][j] = (char) (detectaBombes + '0');   //col·loca el total de bombes detectades
                }
            }
        }
    }
}

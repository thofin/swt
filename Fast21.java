/**
 * Ein Kommandozeilenspiel, bei dem der Spieler durch würfeln (1-6) versuchen muss, so nah wie möglich an die Zahl 21 zu kommen.
 * Als Gegenspieler dient der Computer, welcher ebenfalls versucht, dass Ziel zu erreichen.
 *
 * @version 0.2
 * @author Thomas Finnern
 */

package de.familie_finnern.fast21;

import java.util.Random;
import java.util.Scanner;

public class Fast21 {

    private int summeSpieler, summeComputer;
    //public final int maximalErreichbareZahl;
    int maximalErreichbareZahl = 21;
    private String status;
    private boolean spielende, spielerFertig, computerFertig;

    /*
    public Fast21() {
        this.maximalErreichbareZahl = 21;
    }
    */

    public final void variablenZuruecksetzen() {
        spielerFertig = false;
        computerFertig = false;
        summeSpieler = 0;
        summeComputer = 0;
        status = "";
    }
    
    public final void pausiere() {
        int zeitZumWarten = 500;
        try {
            Thread.sleep(zeitZumWarten);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } 
    }
    
    public final void zeigeUeberschrift() {
        if (summeSpieler == 0) {
            System.out.println("\n|  Summe   |  Summe  | Nochmal würfeln? |");
            System.out.println("| Computer | Spieler |      (j/n)       |");
            System.out.println("+----------+---------+------------------+");
        }
    }

    public final void zeigeFrageWuerfeln() {
         System.out.print("Würfeln? (j/n) ");
    }
    
    public final char leseBenutzerkommando() {
        Scanner in = new Scanner(System.in, "UTF-8");
        return in.nextLine().charAt(0);
    }
    
    public final void setzeSpielerFertig() {
        spielerFertig = true;
    }
    
    public final void setzeSpielende() {
        spielende = true;
    }
    
    public final void zeigeSpielnamen() {
        System.out.println("================== Fast21 ==================\n");
    }
    
    public final void zeigeLeerzeile() {
        System.out.println();
    }
    
    public final void zeigeNeuesSpiel() {
        if (!entscheidungWeitereRundeSpielen()) {
            zeigeLeerzeile();
        }
        System.out.print(zeigeStatus() + " - Neues Spiel? (j/n) ");
    }
    
    public final void summiereSpieler() {
        summeSpieler += wuerfeleZahl();
    }

    public final void summiereComputer() {
        if (!entscheidungComputer()) {
            if (!pruefeObMaximaleZahl()) {
                summeComputer += wuerfeleZahl();
            }
        }
        else {
            computerFertig = true;
        }
    }

    public final Random generiereZufallszahl() {
        return new Random();
    }
    
    public final int wuerfeleZahl() {
        final int anzahlWerte = 6;
        final int zahlGroesserAlsNull = 1;
        return generiereZufallszahl().nextInt(anzahlWerte) + zahlGroesserAlsNull;
    }
     
    public final boolean entscheidungWeitereRundeSpielen() {
        return !(pruefeObMaximaleZahl() || pruefeObFertig() || pruefeObComputerGewinnt());
    }
   
    public final boolean pruefeObOberhalbEntscheidungszahl(final int vergleichsWert) {
       return summeComputer > vergleichsWert;
    }
    
    public final boolean pruefeObComputerGewinnt() {
       return summeComputer > summeSpieler && spielerFertig;
    }    

    public final boolean pruefeObMaximaleZahl() {
       return summeComputer > maximalErreichbareZahl || summeSpieler > maximalErreichbareZahl;
    }   
   
    public final boolean pruefeObFertig() {
       return spielerFertig && computerFertig;
    } 
   
    public final boolean vergleicheZufallszahl() {
        int anzahlWerte = 2;
        int zahlGroesserAlsNull = 1;
        int untererWert = 1;
        return generiereZufallszahl().nextInt(anzahlWerte) + zahlGroesserAlsNull == untererWert;
    }
   
    public final boolean entscheidungComputer() {
        final int minimaleEntscheidungszahl = 16;
        return vergleicheZufallszahl() && pruefeObOberhalbEntscheidungszahl(minimaleEntscheidungszahl);
    }

    public final void zeigeErgebnis() {
        String statusComputer;
        String statusSpieler = String.format("% 4d", summeSpieler) + "    |        ";
        if (computerFertig) {
            statusComputer = "| fertig    |";
            System.out.print(statusComputer + statusSpieler);
        }
        else if (!spielerFertig) {
            statusComputer = String.format("% 5d", summeComputer);
            System.out.print("|" + statusComputer + "     | " + statusSpieler);
        }
        else {
            statusComputer = String.format("% 5d", summeComputer);
            System.out.println("|" + statusComputer + "     | " + statusSpieler);
        }
     }

    public final String zeigeStatus() {
        String spielbeginn = "Herzlich willkommen!";
        String gewonnen = "Du hast gewonnen!";
        String verloren = "Du hast verloren!";
        String unentschieden = "Unentschieden!";
        if (summeSpieler > maximalErreichbareZahl && summeComputer > maximalErreichbareZahl) {
            status = unentschieden;
        }
        else if (summeSpieler > maximalErreichbareZahl && summeComputer <= maximalErreichbareZahl) {
            status = verloren;
        }
        else if (summeSpieler <= maximalErreichbareZahl && summeComputer > maximalErreichbareZahl) {
            status = gewonnen;
        }
        else if (summeSpieler <= maximalErreichbareZahl && summeComputer <= maximalErreichbareZahl) {
            if (summeComputer == 0) {
                status = spielbeginn;
            }
            else if (summeSpieler > summeComputer) {
                status = gewonnen;
            }
            else if (summeSpieler < summeComputer) {
                status = verloren; 
            }
            else if (summeSpieler == summeComputer) {
                status = unentschieden;
            }
        }
     return status;
    }
    
    public final void starteMenue() {
        do {
            zeigeNeuesSpiel();
             switch (leseBenutzerkommando()) {
                case 'j':
                variablenZuruecksetzen();
                zeigeFrageWuerfeln();
                do {
                     switch (leseBenutzerkommando()) {
                        case 'j':
                            zeigeUeberschrift();
                            summiereSpieler();
                            summiereComputer();
                            zeigeErgebnis();
                        break;
                        case 'n':
                            zeigeUeberschrift();
                            setzeSpielerFertig();
                            while (entscheidungWeitereRundeSpielen()) {
                                summiereComputer();
                                pausiere();
                                zeigeErgebnis();
                            }
                            break;
                        default:
                            break;
                        }

                    }
                    while(entscheidungWeitereRundeSpielen());
                    break;
                    case 'n':
                        setzeSpielende();
                    break;
                default:
                    break;
            }
        }
        while(!spielende);
    }

    public final void starteSpiel() {
        zeigeSpielnamen();
        starteMenue();
    }

    public static void main(String[] args) {
        Fast21 neu = new Fast21();
        neu.starteSpiel();
    }
}
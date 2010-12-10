import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;



public class EssaGo
{

    /**
     * Haupteinstiegspunkt der Anwendung.
     * @param asArgs Die KonsolenParameter
     */
    public static void main(String[] asArgs)
    {
        Calendar oCalendar = new GregorianCalendar();
        System.out.print(oCalendar.get(Calendar.DAY_OF_MONTH) + ".");
        System.out.print(oCalendar.get(Calendar.MONTH) + ".");
        System.out.print(oCalendar.get(Calendar.YEAR) + " - ");
        System.out.print(oCalendar.get(Calendar.HOUR) + ":");
        System.out.print(oCalendar.get(Calendar.MINUTE) + ":");
        System.out.print(oCalendar.get(Calendar.SECOND) + ",");
        System.out.println(oCalendar.get(Calendar.MILLISECOND));
        String[] asTest = { "Interspar", "Döner", "Mensa", "Mäki", "Mama", "Oma"};
        
        int[] anzahl = new int[asTest.length];
        
        for (int i = 0; i < 100; i++)
        {
            int iRandom = new Random().nextInt(asTest.length);
//            System.out.println(asTest[iRandom]);
            anzahl[iRandom]++;
        }
        
        for (int i = 0; i < anzahl.length; i++)
        {
            System.out.println(anzahl[i] + ". " + asTest[i]);
        }
    }
}

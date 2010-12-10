import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;
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
        
        PriorityQueue<Location> location = new PriorityQueue<Location>();

        
        for (String item : asTest) {
        	        	       	
			location.add(new Location(item,new Random().nextInt(100)));
		}
             
        while(!location.isEmpty()){
        	Location place = location.remove();
        	System.out.println(place._priority+" "+place._location);
        
        }
    }
    
    
    
}

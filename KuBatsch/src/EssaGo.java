import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EssaGo
{

    /**
     * Haupteinstiegspunkt der Anwendung.
     * @param args Die KonsolenParameter
     */
    public static void main(String[] args)
    {
        // printing log
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.printf("Running EssaGo on %s%n",
                formatter.format(new Date()));

        // possible locations
        Location[] locations = { new Location("Interspar"),
                new Location("Döner"), new Location("Mensa"),
                new Location("McDreck"), new Location("Mama"),
                new Location("Oma") };

        // chose a random location
        int locationIndex = new Random().nextInt(locations.length);

        System.out.println("Do essa go:");
        System.out.println(locations[locationIndex]);
    }

}

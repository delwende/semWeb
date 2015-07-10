package de.recipeminer;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.lang.Deprecated;


public class ChefkochAPICrawler extends AbstractCrawler {
    @Deprecated

    /*
     *"Now acknowledged by Ruby Model."
     *Georg, 18. Jan. 12
    */

    public final static int bufferCapGesamtRezeptListe = 2000000;
    public final static int bufferCapEinzelRezept = 10000;
    public final static int ping = 80;
    public final static String standardSuche2 = "http://api.chefkoch.de/api/1.0/api-recipe.php?ID=1715651280334684&divisor=0&limit=1";

    private int bufferSize = bufferCapGesamtRezeptListe;

    public String crawl() throws IOException
       /*
       * deprecated
       */ {
        URL u = new URL(standardSuche2);
        return crawl(u);
    }

    public String crawl(URL u) throws IOException {
        String output = "";
        URLConnection connection = u.openConnection();
        InputStream in = (InputStream) u.openStream();
        //Versuch, die maximale Anzahl von read-durchläufen zu ermitteln
        int available = in.available();
        int maximum = bufferSize / available;
        if (Control.debuggingmode == 1) System.out.println("Maximum = " + maximum);
        byte[] b;

        maximum++;
        /*
        * es werden mehrere Durchläufe gebraucht, um den runtergeladenen inputbuffer komplett in string umzuwandeln
        */
        for (int i = 1; i <= maximum; i++) {

            b = new byte[available];
            try {
                in.mark(in.read(b));
            } catch (EOFException e) {
            }

            String s = new String(b);
            output = output + s;
        }
        in.close();
        return output;
    }

    public String crawl(String s) throws IOException {
        if (Control.debuggingmode == 1) System.out.println(s);
        return crawl(new URL(s));
    }

    public String crawlForDetailedRecipe(String showid) throws IOException, MalformedURLException {

        URL u = new URL("http://api.chefkoch.de/api/1.0/api-recipe.php?ID=" + showid + "&divisor=0&limit=1");

        this.setBufferSize(bufferCapEinzelRezept);

        String s = crawl(u);

        return s;

    }

    public void setBufferSize(int buff) {
        this.bufferSize = buff;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    //Hilfs-Methode, warten für n millisekunden
    public static void waitmilli(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        }
        while (t1 - t0 < 1);
    }
}
	
	


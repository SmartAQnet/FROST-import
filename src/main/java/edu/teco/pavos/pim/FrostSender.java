package edu.teco.pavos.pim;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Sends Data to the FROST-Server.
 */
public final class FrostSender {

    /**
     * Default constructor
     */
    private FrostSender() { }
    
    /**
     * Sends the given JsonObject to the FROST-Server.
     * @param surl is the url to which information has to be sent.
     * @param json contains the information to send.
     * @param errors that occur
     */
    public static void sendToFrostServer(final String surl, final String json, ArrayList<String> errors) {
    	
    	try {
    		
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            URL url = new URL(surl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setFixedLengthStreamingMode(bytes.length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Content-Encoding", "charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.connect();
            
            try (DataOutputStream dos = new DataOutputStream(http.getOutputStream())) {
            	
            	dos.write(bytes);
            	BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			    in.close();
			    
            } catch (IOException e) {
            	
            	errors.add(e.getLocalizedMessage());
            	errors.add("JSON: " + json);
            	System.out.println(e.getLocalizedMessage());
            	
            }
            
        } catch (IOException e) {
        	
        	errors.add(e.getLocalizedMessage());
        	errors.add("JSON: " + json);
        	System.out.println(e.getLocalizedMessage());
        	
        }
    	
    }

}

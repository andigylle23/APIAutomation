package readFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class AddingTags {

	public static final String saccess_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imt5cmllQGNvZGVyZXB1YmxpcS5jb20ifQ._MPb4Y19i19SbitYNDN3uZi2hqZ5bwEbMz2UsiUVFlM";
	public static final String homeUrl = "Web Address";
	public static final String COMMA_DELIMETER = ",";
	public static final int TAG_ID = 0;
	public static final int PRODUCT_PROPERTY = 1;
	public static final int PRODUCT_PROPERTY_VALUE = 2;
	public static final int USER_PROPERTY = 3;
	public static final int USER_PROPERTY_VALUE = 4;
	public static final int USER_PROPERTY_META = 5;
	
	
public static void createTags(String fileName){
		
		BufferedReader br = null;
		
		try{
			
			String line = "";
			br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			
			while((line = br.readLine()) != null){
				String[] tokens = line.split(COMMA_DELIMETER);
				
					if(tokens.length > 0){
				   	URL url = new URL(homeUrl + "tags?");
			        Map<String,Object> params = new LinkedHashMap<>();
			        params.put("access_token", saccess_token);     		
			        params.put("product_property", tokens[PRODUCT_PROPERTY]);
			        params.put("product_property_value", tokens[PRODUCT_PROPERTY_VALUE]);
			        params.put("user_property", tokens[USER_PROPERTY]);
			        params.put("user_property_value", tokens[USER_PROPERTY_VALUE]);

			        StringBuilder postData = new StringBuilder();
			        for (Map.Entry<String,Object> param : params.entrySet()) {
			            if (postData.length() != 0) postData.append('&');
			            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			            postData.append('=');
			            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			        }
			        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			        conn.setRequestMethod("POST");
			        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			        conn.setDoOutput(true);
			        conn.getOutputStream().write(postDataBytes);

			        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			        for ( int c = in.read(); c != -1; c = in.read() )
			        	System.out.print((char)c);		
			}
		}

			
		} catch (Exception e){
			System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            	System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
			
		}
	}
}

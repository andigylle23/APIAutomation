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
import org.json.JSONObject;


public class FindProduct {
	public static final String access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJlcXVlc3RzZW5kaW5nQG1haWxpbmF0b3IuY29tIn0.YuBX1cNdw-CcmMzdCqjrpxKNrHhtZVvEU7AN9ZGqJY4";
	public static final String homeUrl = "WebSiteHere";
	public static final String COMMA_DELIMETER = ",";
	public static final int PRODUCT_GENDER = 0;
	public static final int PRODUCT_CATEGORY = 1;
	public static final int PRODUCT_SUBCAT = 2;
	public static final int PRODUCTID = 3;
	public static final int PRODUCT_TAG1 =4;
	public static final int PRODUCT_TAG2 = 5;
	public static final int PRODUCT_TAG3 = 6; 
	public static final int PRODUCT_TAG4 = 7;
	public static final int PRODUCT_TAG5 = 8;
	
	
	public static void readCsvFile(String fileName){
		
		BufferedReader br = null;
		
		try{
			
			String line = "";
			br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			
			while((line = br.readLine()) != null){
				
				if(line !=null){
				String[] tokens = line.split(COMMA_DELIMETER);
				
					if(tokens.length > 0){
				   	URL url = new URL(homeUrl + "tag");
			        Map<String,Object> params = new LinkedHashMap<>();
			        params.put("access_token", access_token);     		
			        params.put("product_id",tokens[PRODUCTID]);
			        params.put("tag", tokens[PRODUCT_TAG4]);
			        
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
					System.out.print("\nProductID: " + tokens[PRODUCTID]);
			        for ( int c = in.read(); c != -1; c = in.read() )
			        	System.out.print((char)c);		
			}
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
	
	
	public static void updateProdCsvFile(String fileName){
		
		BufferedReader br = null;
		
		try{
			
			String line = "";
			br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			
			while((line = br.readLine()) != null){
				String[] tokens = line.split(COMMA_DELIMETER);
				
					if(tokens.length > 0){
				   	URL url = new URL(homeUrl + "products/" + tokens[PRODUCTID] + "?access_token=" + access_token);
			        Map<String,Object> params = new LinkedHashMap<>();
			        params.put("categories", "2");

			        StringBuilder postData = new StringBuilder();
			        for (Map.Entry<String,Object> param : params.entrySet()) {
			            if (postData.length() != 0) postData.append('&');
			            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			            postData.append('=');
			            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			        }
			        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			        conn.setRequestMethod("PUT");
			        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			        conn.setDoOutput(true);
			        conn.getOutputStream().write(postDataBytes);
			        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			   		String inputLine;
			   		StringBuffer response = new StringBuffer();
				   		while((inputLine = in.readLine())!=null){
				   		response.append(inputLine);
				   		}
			   		in.close();
			   		JSONObject json = new JSONObject(response.toString());
			   		if(json.has("info")){
			   			System.out.println(json.getString("item_sku"));
			   			System.out.println(json.getString("_id"));
			   		}
					
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

package readFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class TagProduct {

	public static final String daccess_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJlcXVlc3RzZW5kaW5nQG1haWxpbmF0b3IuY29tIn0.YuBX1cNdw-CcmMzdCqjrpxKNrHhtZVvEU7AN9ZGqJY4";
	public static final String saccess_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imt5cmllQGNvZGVyZXB1YmxpcS5jb20ifQ._MPb4Y19i19SbitYNDN3uZi2hqZ5bwEbMz2UsiUVFlM";
	public static final String homeUrl = "HomePageHere";
	public static final String COMMA_DELIMETER = ",";
	public static final int PRODUCT_NAME = 0;
	public static final int PRODUCT_ITEM = 1;
	public static final int PRODUCT_ID = 2;

	
	public static void searchCsvFile(String fileName){

		BufferedReader br = null;

		try{
			
			String line = "";
			br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			
			while((line = br.readLine()) != null){
				String[] tokens = line.split(COMMA_DELIMETER);
				
					if(tokens.length > 0){
				   	URL url = new URL(homeUrl + "search?access_token="+daccess_token+"&keywords="+tokens[PRODUCT_NAME]);
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			        conn.setRequestMethod("GET");
			        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					String inputLine;
					StringBuffer response = new StringBuffer();
					while((inputLine = in.readLine())!=null){
						response.append(inputLine);
					}
						in.close();
						
						JSONObject json = new JSONObject(response.toString());
						JSONArray arrProd = json.getJSONArray("products");
						
						
						for(int i = 0; i < arrProd.length(); i++){
							if(arrProd.getJSONObject(i).has("_id")){
//								String item_sku = arrProd.getJSONObject(i).getString("item_sku");
//								if(item_sku.equals(tokens[PRODUCT_ITEM])){
								System.out.println(arrProd.getJSONObject(i).getString("_id"));
//								String parentage = arrProd.getJSONObject(i).getString("parentage");
//									System.out.println("Parent: " +arrProd.getJSONObject(i).getString("_id"));
//									ProductProperty prod = new ProductProperty();
//									prod.setId(arrProd.getJSONObject(i).getString("_id"));
//									prod.setItem_name(arrProd.getJSONObject(i).getString("item_name"));
//									prod.setImage(arrProd.getJSONObject(i).getString("images"));
//									prod.setParentage(arrProd.getJSONObject(i).getString("parentage"));
//									prod.setChildren(arrProd.getJSONObject(i).getString("children"));
//									Gson gson = new Gson();
//									System.out.println(gson.toJson(prod));
//								
//								if(item_sku.equals(tokens[PRODUCT_ITEM])){
////										System.out.println( "\nURL ITEM_SKU: " +arrProd.getJSONObject(i).getString("item_sku") + "\nPRODUCT_ID: " + arrProd.getJSONObject(i).getString("_id"));
////										System.out.println("\nCSV ITEM_ID : " +tokens[PRODUCT_ITEM] + "\nURL ITEM_SKU: " +arrProd.getJSONObject(i).getString("item_sku") + "\nPRODUCT_ID: " + arrProd.getJSONObject(i).getString("_id"));	
//										System.out.println(arrProd.getJSONObject(i).getString("_id"));
//										arrProd = json.getJSONArray("children");
//										String children = arrProd.getJSONObject(i).getString("_id");
//											if(children.equals(tokens[PRODUCT_ITEM])){
//												System.out.println(arrProd.getJSONObject(i).getString("_id"));
//											}
//										
//									}
								}else{
								
								System.out.println("\nItemId: "+ arrProd.getJSONObject(i).getString("item_sku")+ "\nPRODUCT_ID is not found");
							}
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

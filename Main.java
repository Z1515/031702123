import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.FileReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			File file1 = new File(args[0]);
			//FileWriter file2 = new FileWriter(args[1]);
			FileOutputStream fos = new FileOutputStream(args[1]);
//          File file1 = new File("1.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "utf-8"));
			//BufferedWriter out = new BufferedWriter(file2);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//          File file2= new File("2.txt");
//          BufferedWriter out = new BufferedWriter(new FileWriter(file2));
			String line = null;
			String[] information = new String[2000];
			int i=0;
            while((line = in.readLine()) != null){
            	information[i] = line;
            	i++;
            }
            int length = i;
            for(i=0;i<length;i++) {
            	String str = information[i];
            	//获取、切除难度级别
            	String level = str.substring(0, str.indexOf("!"));
            	str = str.substring(str.indexOf("!")+1);
            	//获取、切除名字
            	String name = str.substring(0, str.indexOf(","));
            	str = str.substring(str.indexOf(",")+1);
            	//获取、切除电话
            	String phone = informationResolution.get_phone(str);
            	str = str.replaceAll(phone, "");
            	if(level.equalsIgnoreCase("1")) {
            		List<Map<String,String>> table = informationResolution.addressResolution1(str);
            		String strf="{\"姓名\":"+"\""+name+"\""+","+"\"电话\":"+"\""+phone+"\""+","+"\"地址\":["+"\""+table.get(0).get("province")+"\""+","+"\""+table.get(0).get("city")+"\""+","+"\""+table.get(0).get("county")+"\""+","+"\""+table.get(0).get("town")+"\""+","+"\""+table.get(0).get("village")+"\"]}";
            		//out.write(strf);
            		//out.newLine();
            		osw.write(strf);
            		osw.flush();
            		osw.append("\r\n");
            	}
            	if(level.equalsIgnoreCase("2")) {
            		List<Map<String,String>> table = informationResolution.addressResolution2(str);
            		String strf="{\"姓名\":"+"\""+name+"\""+","+"\"电话\":"+"\""+phone+"\""+","+"\"地址\":["+"\""+table.get(0).get("province")+"\""+","+"\""+table.get(0).get("city")+"\""+","+"\""+table.get(0).get("county")+"\""+","+"\""+table.get(0).get("town")+"\""+","+"\""+table.get(0).get("road")+"\""+","+"\""+table.get(0).get("num")+"\""+","+"\""+table.get(0).get("village")+"\"]}";
            		//out.write(strf);
            		//out.newLine();
            		osw.write(strf);
            		osw.flush();
            		osw.append("\r\n");
            	}
            }
            in.close();
            //out.close();
            osw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class informationResolution {
	//获取、切除名字
	public static String get_phone(String str) {
		String phone="";
		Pattern pattern = Pattern.compile("(13[0-9]|14[57]|15[012356789]|18[056789])\\d{8}");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			phone=matcher.group();
		}
		return phone;
	}
	//难度级别1
	public static List<Map<String,String>> addressResolution1(String str) {
		//正则表达式解析地址ַ
	    String regex="((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
	    Matcher m=Pattern.compile(regex).matcher(str);
	    String province=null,city=null,county=null,town=null,village=null;
	    List<Map<String,String>> table=new ArrayList<Map<String,String>>();
	    Map<String,String> row=null;
	    while(m.find()){
	    	row=new LinkedHashMap<String,String>();
	        province=m.group("province");
	        row.put("province", province==null?"":province.trim());
	        city=m.group("city");
	        row.put("city", city==null?"":city.trim());
	        county=m.group("county");
	        row.put("county", county==null?"":county.trim());
	        town=m.group("town");
	        row.put("town", town==null?"":town.trim());
	        village=m.group("village");
	        row.put("village", village==null?"":village.trim());
	        table.add(row);
	    }
	    return table;
	        
	}	
	  
	//难度级别2
	public static List<Map<String,String>> addressResolution2(String str) {
	//正则表达式解析地址
	String regex = "((?<province>[^省]+省|.+市|自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇|.+街|.+乡)?(?<road>[^路]+街|.*?巷|.*?路)?(?<num>[^号]+门牌号|.*?号)?(?<village>.*)";
	Matcher m=Pattern.compile(regex).matcher(str);
	String province=null,city=null,county=null,town=null,village=null,road=null,num=null;
	List<Map<String,String>> table=new ArrayList<Map<String,String>>();
	Map<String,String> row=null;
	   	while(m.find()){
	   		row=new LinkedHashMap<String,String>();
	        province=m.group("province");
	        row.put("province", province==null?"":province.trim());
	        city=m.group("city");
	        row.put("city", city==null?"":city.trim());
	        county=m.group("county");
	        row.put("county", county==null?"":county.trim());
	        town=m.group("town");
	        row.put("town", town==null?"":town.trim());
	        road=m.group("road");
	        row.put("road", road==null?"":road.trim());
	        num=m.group("num");
	        row.put("num", num==null?"":num.trim());
	        village=m.group("village");
	        row.put("village", village==null?"":village.trim());
	        table.add(row);
	   	}
	    return table;
	}
}
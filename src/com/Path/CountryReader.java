package com.Path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryReader{
    private  String countryFile;
    public CountryReader(String file,String countryFile){
        this.countryFile = countryFile;
    }

    public static Map<String,List<String>> getPathWithCountry(String countryFile)throws Exception{
        Map<String,List<String>> countryMap = new HashMap<String,List<String>>();
        try{
            BufferedReader fr = new BufferedReader(new FileReader(countryFile));
            String line ="";
            while((line=fr.readLine())!=null){
                String path[] = line.split(",");
                List<String> list = new ArrayList<String>();
                if(countryMap.get(path[1])!=null){
                    list = countryMap.get(path[1]);
                    list.add(path[0]);
                }else{
                    list.add(path[0]);
                }
                countryMap.put(path[1],list);
            }
        }catch(Exception e){
            throw new Exception("Database country File not found");
        }
        return countryMap;
    }
    public static String getCountry(String city,Map<String,List<String>> countryMap){
        for(String country: countryMap.keySet()){
            if(country.equals(city) || countryMap.get(country).contains(city))
                return country;
        }
        return null;
    }
    public static void printWithCountry(Map<Integer, ArrayList<String>> root, Map<String, List<String>> countryMap, boolean isallPath, Map<String, Integer> costTable){
        String r = "";
        String prev = "";
        double totalCost = 0.0;
        for(Integer list : root.keySet()){
            for(String item : root.get(list)) {
                prev = prev.concat("->"+item);
                if(costTable.containsKey(prev)){
                    totalCost += (double)costTable.get(prev);
                    prev = "->"+item;
                }
                r = r.concat("->" + item + "[" + getCountry(item, countryMap) + "]");
            }
            r = r.concat("\r\n"+"Total Cost:"+totalCost);
            System.out.println(r);
            totalCost = 0.0;prev = "";r= "";
            if(!isallPath) break;
        }
    }
}

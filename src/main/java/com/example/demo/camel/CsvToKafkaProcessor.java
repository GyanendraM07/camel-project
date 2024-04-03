package com.example.demo.camel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvToKafkaProcessor implements Processor {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void process(Exchange exchange) throws Exception {
        System.err.println("Inside converter start ");
        String csvFileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        System.out.println("CSV File Name: " + csvFileName);
        RulesLoader ruleLoader = new RulesLoader();
         Map<String, String> rules= ruleLoader.getRulesForCSV(csvFileName);
        // Get CSV records from exchange
        List<List<String>> csvRecords = exchange.getIn().getBody(List.class);

        if (csvRecords != null && !csvRecords.isEmpty()) {
            // Get CSV headers (keys)
            List<String> headers = csvRecords.remove(0); // Remove and store the header

            // Prepare list to hold key-value maps
            List<Map<String, String>> keyValueList = new ArrayList<>();
            boolean isValidData =true;
            // Parse CSV rows into key-value maps
            outerLoop:
            for (List<String> record : csvRecords) {
            	Map<String, String> keyValueMap = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    String key = headers.get(i);
                    String value = record.get(i);
                    String regexRule =rules.get(key);
                    if(regexRule!=null) {
                    	boolean isMatch = value.matches(regexRule);
                    	if(!isMatch) {
                    		isValidData =false;
                    		System.out.println("Mismatch found in file " + csvFileName + " for key " + key);
                    		 break outerLoop;
                    	}
                    }
                    keyValueMap.put(key, value);
                }
                keyValueList.add(keyValueMap);
            }

            // Serialize the list of key-value maps to JSON
            String jsonData = objectMapper.writeValueAsString(keyValueList);
            // Set the JSON data as the body of the exchange
            if(isValidData) {
            	exchange.getIn().setHeader("isValidData", isValidData);
            	exchange.getIn().setBody(jsonData);
            }
            else {
            	exchange.getIn().setHeader("isValidData", isValidData);
            	exchange.getIn().setBody("File is Errect Column is blank or  null");
            }
        }
    }
    
//    // Method to check regex pattern for a specific column in a given CSV file
//    public boolean isValidColumnValue(String fileName, String columnName, String value) {
//        Map<String, String> columnRules = validationRules.get(fileName);
//        if (columnRules != null && columnRules.containsKey(columnName)) {
//            String regex = columnRules.get(columnName);
//            return value.matches(regex);
//        }
//        // If no specific rule found, consider it valid
//        return true;
//    }
}

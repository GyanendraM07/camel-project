package com.example.demo.camel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class RulesLoader {

    private final Map<String, Map<String, String>> rules;

    public RulesLoader() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("validation.json");
        this.rules = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, Map<String, String>>>() {});
    }

    public String getRegexForColumn(String fileName, String columnName) {
        Map<String, String> fileRules = rules.get(fileName);
        if (fileRules != null) {
            return fileRules.get(columnName);
        }
        return null; // Handle missing rules or columns
    }
    
    public Map<String, String> getRulesForCSV(String fileName) {
        Map<String, String> fileRules = rules.get(fileName);
        return fileRules; // Handle missing rules or columns
    }
    
    // Method to check regex pattern for a specific column in a given CSV file
//    public boolean isValidColumnValue(String fileName, String columnName, String value) {
//        Map<String, String> columnRules = validationRules.get(fileName);
//        if (columnRules != null && columnRules.containsKey(columnName)) {
//            String regex = columnRules.get(columnName);
//            return value.matches(regex);
//        }
//        // If no specific rule found, consider it valid
//        return true;
//    }
    

    public static void main(String[] args) throws IOException {
        //RulesLoader rulesLoader = new RulesLoader("validation.json");
        String fileName = "bill_of_materials";
        String columnName = "code";
      //  String regex = rulesLoader.getRegexForColumn(fileName, columnName);
       // String regex = "^(?!null$|^\\s*$).+";
        String valueToCheck = "hellow";

    //    boolean isMatch = valueToCheck.matches(regex);

      //  System.out.println("Regex for column '" + columnName + "': " + regex+"isMatch:"+isMatch);
    }
}


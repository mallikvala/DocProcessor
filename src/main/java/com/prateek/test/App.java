package com.prateek.test;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {

    public static String checked = "☒";
    public static String unchecked = "☐";
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("/Users/dyb203/Experiment/del/example2.docx");
        byte[] byteData = Files.readAllBytes(path);


        XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(byteData));
        final List<XWPFTable> allTables = doc.getTables();
        final XWPFTable mainTable = allTables.get(1);

        final Map<String, Boolean> stringBooleanMap = generalCheckBoxIterator(mainTable.getRow(0).getCell(1).getText().replace(" ",""));


        final String lineOfBusiness = mainTable.getRow(1).getCell(1).getText();
        final Map<String, Boolean> lobMap = generalCheckBoxIterator(lineOfBusiness.replace(" ", ""));
        System.out.println(lineOfBusiness);

        final String projectorSubmitter = mainTable.getRow(2).getCell(1).getText();
        System.out.println(projectorSubmitter);

        final String dateOfSubmission = mainTable.getRow(3).getCell(1).getText();
        System.out.println(dateOfSubmission);

        final String projectName = mainTable.getRow(4).getCell(1).getText();
        System.out.println(projectName);

        final String providerName = mainTable.getRow(5).getCell(1).getText();
        System.out.println(providerName);

        final String totalClaims = mainTable.getRow(6).getCell(1).getText();
        System.out.println(totalClaims);

        final String projectFinancialImpact = mainTable.getRow(7).getCell(1).getText();
        System.out.println(projectFinancialImpact);

        final String interestDue = mainTable.getRow(8).getCell(1).getText();
        System.out.println(interestDue);

        final String multiple = mainTable.getRow(9).getCell(1).getText();
        System.out.println(multiple);

        final String multiple2 = mainTable.getRow(10).getCell(1).getText();
        System.out.println(multiple2);

    }

    public static Map<String, Boolean> checkBoxIterator(String s, int textLength){
        Map<String, Boolean> selectedMap = new HashMap<>();
        final ListIterator<Character> healthPlanListIterator = s.replace(" ", "").chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList()).listIterator();

        while(healthPlanListIterator.hasNext()){
            Boolean selected = healthPlanListIterator.next().equals(checked.charAt(0));
           StringBuilder stringBuilder = new StringBuilder();

            IntStream.range(0,textLength).forEach(value -> {
                stringBuilder.append(healthPlanListIterator.next());
            });
            selectedMap.put(stringBuilder.toString(), selected);
            System.out.println(stringBuilder.toString() + " is checked :"+ selected);
        }
        return selectedMap;
    }

    public static Map<String, Boolean> generalCheckBoxIterator(String s){
        Map<String, Boolean> selectedMap = new HashMap<>();
        int index = 0;
        while(s.indexOf(checked,index) < s.length() && s.indexOf(unchecked,index) < s.length()){
            int checkIndex = index;
            int nextCheckIndex;
            if(s.indexOf(checked, index+1)< 0 && s.indexOf(unchecked, index+1)< 0){
                nextCheckIndex = s.length();
            } else if(s.indexOf(checked, index+1)< 0){
                nextCheckIndex=s.indexOf(unchecked, index+1);
            }else if (s.indexOf(unchecked, index+1)< 0){
                nextCheckIndex=s.indexOf(checked, index+1);
            }else{
                nextCheckIndex = (s.indexOf(checked, index+1)<s.indexOf(unchecked,index+1))?s.indexOf(checked, index+1):s.indexOf(unchecked, index+1);
            }
            selectedMap.put(s.substring(checkIndex+1, nextCheckIndex),s.charAt(index)==checked.charAt(0));
            System.out.print(s.charAt(index)==checked.charAt(0));
            System.out.println(s.substring(checkIndex+1, nextCheckIndex));
            if(nextCheckIndex == s.length()) break;
            index = nextCheckIndex;
        }
        return selectedMap;
    }
}

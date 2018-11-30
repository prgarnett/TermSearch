/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.prgarnett.termsearch;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author philip
 */
public class SearchPDFNGrams
{
    private final String dirPath;
    private final String savePath;
    private String term;
    private FindAllFiles findFiles;
    private final Map<String, Integer> termCountsPDF;
    private final Map<String, Integer> termCountsTXT;
    
    public SearchPDFNGrams(String dirPath, String savePath, String term)
    {
        this.dirPath = dirPath;
        this.savePath = savePath;
        this.term = term;
        this.termCountsPDF = new HashMap<>();
        this.termCountsTXT = new HashMap<>();
    }    
    
    public void locateFilesInDir()
    {
        this.findFiles = new FindAllFiles(dirPath);
        
        findFiles.search();
    }
    
    public void process()
    {
        System.out.println("Searching for " + term);
        findFiles.getList().stream().filter((aFile) -> (aFile.endsWith(".txt"))).forEachOrdered((aFile) ->
        {
            try
            {
                List<String> lines = Files.readAllLines(Paths.get(aFile), StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder(1024);
                
                lines.forEach((line) -> {
                    sb.append(line);
                });
                
                String text = sb.toString();
                text = StringUtils.lowerCase(text);
                text = StringUtils.replace(text, "\n", " ");
                text = StringUtils.replace(text, "\r", " ");
                text = StringUtils.replace(text, "\f", " ");
                text = StringUtils.replace(text, "\t", " ");
                text = StringUtils.replace(text, "  ", " ");
                text = StringUtils.replace(text, "   ", " ");
                
                term = StringUtils.lowerCase(term);
                
                int countString = StringUtils.countMatches(text, term);
                
                String paperTile = aFile.substring(aFile.indexOf("/")+1,aFile.lastIndexOf("."));
                
                termCountsTXT.put(paperTile, countString);
            }
            catch (IOException e)
            {
                
            }
        });
        
        this.saveTermCounts();
    }
        
    private void saveTermCounts()
    {
        System.out.println("Saving csv file.");
        try (PrintStream out = new PrintStream(new BufferedOutputStream( new FileOutputStream(savePath + "/result_"+term+"_PDF.csv"))))
        {
            out.println("Paper\t" + term);
            
            termCountsPDF.entrySet().forEach((pairs) -> {
                out.println(pairs.getKey() + "\t" + pairs.getValue());
            });
            
            out.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
        
        try (PrintStream out = new PrintStream(new BufferedOutputStream( new FileOutputStream(savePath + "/result_"+term+"_TXT.csv"))))
        {
            out.println("Paper\t" + term);
            
            termCountsTXT.entrySet().forEach((pairs) -> {
                out.println(pairs.getKey() + "\t" + pairs.getValue());
            });
            
            out.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
}

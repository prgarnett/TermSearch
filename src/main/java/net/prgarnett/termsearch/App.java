/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.prgarnett.termsearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author philipgarnett
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if(args[0].equals("WordSearch"))
        {
            try{
                List<String> lines = Files.readAllLines(Paths.get(args[3]), StandardCharsets.UTF_8);
                
                lines.forEach((aLine) -> {
                    SearchPDFNGrams search = new SearchPDFNGrams(args[1], args[2], aLine.toLowerCase().trim());
                    search.locateFilesInDir();
                    search.process();
                });
            }
            catch (IOException e)
            {
                
            }
        }
    }
    
}

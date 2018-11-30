/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.prgarnett.termsearch;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author philipgarnett
 */
public class CleanTXTFile {
    private final String aFile;
    private final String saveName;
    
    public CleanTXTFile(String aFile, String saveName)
    {
        this.aFile = aFile;
        this.saveName = saveName;
    }
    
    public void cleanFile()
    {
        try
        {
            System.out.println("Processing: " + aFile);
            
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

            System.out.println("Saving txt file.");

            try (PrintStream out = new PrintStream(new BufferedOutputStream( new FileOutputStream(saveName)))) {
                out.println(text);
            }

        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

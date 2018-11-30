/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.prgarnett.termsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philip
 */
public class FindAllFiles
{
    private final List<String> files, dirs;
    
    public FindAllFiles(String startPath)
    {
        this.dirs = new ArrayList<>();
        this.files = new ArrayList<>();
        this.dirs.add(startPath);
    }
    
    public void search()
    {
        System.out.println("Searching for Files");
        while(!this.dirs.isEmpty())
        {
            String temp = this.dirs.get(0);
            this.dirs.remove(0);
            this.searchPath(temp);
        }
//        this.dumpList();
    }
    
    public void searchPath(String path)
    {
        if(path.endsWith("/"))
        {
            path = path.substring(0, path.length()-1);
        }
        File dir = new File(path);//path of dir
            
        if(dir.isDirectory())//check its a dir
        {
            String[] contence = dir.list();
            
            for (String contence1 : contence)
            {
                File newFile = new File(path + "/" + contence1);
                if (newFile.isDirectory())
                {
                    this.dirs.add(path + "/" + contence1);
                }
                else
                {
                    this.files.add(path + "/" + contence1);
                }
            }
        }
    }
    
    public List<String> getList()
    {
        return files;
    }
    
    public void dumpList()
    {
        this.files.stream().forEach((fname) ->
        {
            System.out.println(fname);
        });
    }
}

package logging.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import logging.Interface.ILogger;

public class LoggerImpl implements ILogger{

	private final File logDir;
	public LoggerImpl(File logDir) {
		this.logDir = logDir;
		this.logDir.mkdirs();
		System.out.println("Dossier de Log crée "+logDir.getAbsolutePath());
	}

	@Override
	public File newRobotFile(String id) {
		File f =new File(logDir, id+".txt");
		Path path = Paths.get(f.getAbsolutePath());
		try {
		  Files.deleteIfExists(path);
		} catch (DirectoryNotEmptyException dnee) {
		  System.err.println("Le repertoire " + path + " n'est pas vide");
		} catch (IOException ioe) {
		  System.err.println("Impossible de supprimer " + path + " : " + ioe);
		}
		File file =new File(logDir, id+".txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
		}
		return f;
		
	}

	@Override
	public void addLine( File f, String line) {
		try{
			
		}catch(Exception e){}
		
		try {
			FileWriter ffw=new FileWriter(f,true);
			ffw.write(line);  // écrire une ligne dans le fichier resultat.txt
			ffw.write("\n"); // forcer le passage à la ligne
			ffw.close(); // 
		} catch (FileNotFoundException e) {
			System.err.println("An error happened with the file, nothing will be logged.");
			
		} catch (IOException e) {
			System.err.println("An error happened with the file, nothing will be logged.");
			
		}
		
	}

	

	

}

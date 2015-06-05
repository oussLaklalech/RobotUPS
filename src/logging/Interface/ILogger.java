package logging.Interface;

import java.io.File;

public interface ILogger {
    
	 public File newRobotFile(String id);
	public void addLine(File f, String line);
}

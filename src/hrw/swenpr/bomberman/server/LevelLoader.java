package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.common.rfc.Level;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Level loader to get all available levels.
 * 
 * @deprecated Should not be used anymore. The {@link ServerModel} handles this internally and 
 * provides a function, with better performance, for getting all available levels.
 * 
 * @author Marco Egger
 */
public class LevelLoader {
	
	/**
	 * Returns an ArrayList that contains all available levels.
	 * 
	 * @param dir the directory to search for the level files 
	 * @return {@code ArrayList<Level>} the levels
	 */
	public static ArrayList<Level> getAvailableLevels(File dir) {
		ArrayList<File> xmlFiles = getAllXmlFiles(dir);
		ArrayList<Level> levels = new ArrayList<Level>();
		SAXBuilder builder = new SAXBuilder();
		
		for(File file : xmlFiles) {
			try {
				// parse file
				Document doc = (Document) builder.build(file);
				
				if(isLevelXml(doc)) {
					Level level = new Level(file.getName(), getLevelSize(doc));
					levels.add(level);
				}
				
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return levels;
	}
	
	
	/**
	 * Reads the given {@code Document} to determine the size.
	 * 
	 * @param doc of the XML file
	 * @return {@link Point} the size
	 */
	private static Point getLevelSize(Document doc) {
		Element root = doc.getRootElement();
		Element node = root.getChild("size");
		
		Point size = new Point(Integer.parseInt(node.getChildText("x")), Integer.parseInt(node.getChildText("y")));
		
		return size;
	}
	
	/**
	 * @param file the XML file
	 * @return true when level file
	 */
	private static boolean isLevelXml(Document doc) {
		if (doc.getRootElement().getName().equals("level"))
			return true;
		
		return false;
	}
	
	
	/**
	 * Searches for all XML-Files in dir and all subdirs.
	 * 
	 * @param dir
	 * @return all xml files
	 */
	private static ArrayList<File> getAllXmlFiles(File dir) {
		ArrayList<File> list = new ArrayList<File>();
        // get all the files from a directory
        File[] fList = dir.listFiles();
 
        for (File file : fList){
            if (file.isFile()){
            	String extension = getFileExtension(file);
            	if(extension.equals("xml")) 
            		list.add(file);
            } 
            else {
            	if (file.isDirectory()) {
            		ArrayList<File> tmp = getAllXmlFiles(file);
            		for(int i = 0; i < tmp.size(); i++) {
            			list.add(tmp.get(i));
            		}
            	}
            }
        }
        
        return list;
	}
	
	
	
	/**
	 * @param file
	 * @return the file extension without .
	 */
	private static String getFileExtension(File file) {
    	int i = file.getAbsolutePath().lastIndexOf(".");
    	
    	if(i > 0)
    		return file.getAbsolutePath().substring(i + 1).toLowerCase();
    	
    	return "";
    }

}

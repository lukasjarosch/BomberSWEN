package hrw.swenpr.bomberman.common.rfc;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p> C <= S</p>
 * {@code LevelFile} is sent from the server to the clients, when the game starts with {@link GameStart} message, so every client can display the level.
 * 
 * @author Marco Egger
 */
public class LevelFile extends Header implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private byte[] filebytes;
	
	/**
	 * Creates instance and reads in the given level file.
	 * 
	 * @param file
	 */
	public LevelFile(File file) {
		setType(MessageType.LEVEL_FILE);
		
		try {
			readLevelFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the containing level file to a file.
	 * 
	 * @param file the file instance with a valid filename to write to
	 * @throws IOException throws exception if unable to write file, when software has no write access).
	 */
	public void writeLevelFile(File file) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(filebytes);
		} finally {
			// close stream even with exception
			if(out != null)
				out.close();
		}
	}
	
	/**
	 * Reads in the given file and converts it to byte array.
	 * 
	 * @param file the file to read in
	 * @throws IOException throws exception when the file doesn't exist.
	 */
	private void readLevelFile(File file) throws IOException {
		BufferedInputStream in = null;
		try {
			// create stream of pointing on file
			in = new BufferedInputStream(new FileInputStream(file));
			// for appending bytes, because the size of the array is not known
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// needed buffer and count for detecting end of file
			byte[] buffer = new byte[4096];
			int count = 0;
			
			// read in size of buffer (or less)
			while((count = in.read(buffer)) >= 0) {
				// append read bytes to ByteArrayOutputStream
				out.write(buffer, 0, count);
			}
			
			// when all bytes of file are read, create one byte[] and store it in class field
			filebytes = out.toByteArray();
		} finally {
			// close stream even with exception
			if(in != null)
				in.close();
		}
	}
}

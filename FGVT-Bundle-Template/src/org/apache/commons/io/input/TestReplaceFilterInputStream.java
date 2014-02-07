package org.apache.commons.io.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestReplaceFilterInputStream {
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		//Create pom files.
	
		//first create POM file
		Map<byte[], byte[]> replacements = new HashMap<byte[], byte[]>();
		ReplaceFilterInputStream in = new ReplaceFilterInputStream(
				new FileInputStream(new File("resources/converge/pom.xml")),
				replacements);
		FileOutputStream out = new FileOutputStream("test.xml");
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len != -1) {
		    out.write(buffer, 0, len);
		    len = in.read(buffer);
		}
		
	}

}

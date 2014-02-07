/**
 * 
 */
package de.htwsaar.fgvt.types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

/**
 * This class contains all data which is needed by a creation template.
 * 
 * 
 * @author mfuenfrocken
 * 
 */
public class DataContext {

	/**
	 * Data which describes the user/developer of the new project.
	 */
	private UserData userData = new UserData();
	
	/**
	 * Project specific data.
	 */
	private ProjectData projectData = new ProjectData();

	/**
	 * Data which is bundle/OSGi specific.
	 */
	private BundleData bundleData = new BundleData();

	/**
	 * This c-tor will create a new context. It also tries to initialize
	 * the context with meaningfull default data. Therefor it will 
	 * read a properties file and set those data.<br>
	 * <b>Note:</b> The various data providers are initialized by their c-tors
	 * before those defaults are loaded, so they will overwrite their default values! 
	 * 
	 */
	public DataContext() {
		this.readGitconfiguration(new File(this.userData.getHomeDirectory() + "/.gitconfig"));
		Properties defaultValues = new Properties();
		URL entry = Platform.getBundle("FGVT-Bundle-Template")
				.getEntry("/resources/converge/defaults.txt");
		try {
			defaultValues.load(new FileInputStream(FileLocator.resolve(entry).getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bundleData.setCategory(defaultValues.getProperty("bundle.category", ""));
		this.projectData.setDescription(defaultValues.getProperty("project.description", ""));		
	}

	/**
	 * 
	 * @return The data object for bundle related information.
	 */
	public BundleData getBundleData() {
		return this.bundleData;
	}

	/**
	 * 
	 * @return The data object for project related data.
	 */
	public ProjectData getProjectData() {
		return projectData;
	}

	/**
	 * 
	 * @return The data object for user/developer related data.
	 */
	public UserData getUserData() {
		return this.userData;
	}

	/**
	 * Read the git configuration file '.gitconfig' and obtain the relevant
	 * data, like user-mail. This data is directly set to the {@link #userData}
	 * member, so it will overwrite any default values or changes made before
	 * calling this method.
	 * 
	 * @param _gitconfig
	 *            The configuration file.
	 */
	private void readGitconfiguration(File _gitconfig) {
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					_gitconfig));
			line = reader.readLine();
			boolean inUserSection = false;
			while (null != line) {
				if (line.contains("[user]")) {
					inUserSection = true;
				}
				if (inUserSection) {
					if (line.contains("mail")) {
						this.userData.setMail(line.split("=")[1].trim());
					}
					if (line.contains("name")) {
						this.userData.setId(line.split("=")[1].trim());
					}
				}
				line = reader.readLine();
			}
			;
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

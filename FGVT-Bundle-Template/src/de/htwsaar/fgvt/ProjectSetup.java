/**
 * 
 */
package de.htwsaar.fgvt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.m2e.core.MavenPlugin;
import org.osgi.framework.Bundle;

/**
 * @author mfuenfrocken
 *
 */
public class ProjectSetup {

	public static final String MAVEN2_NATURE = "org.eclipse.m2e.core.maven2Nature";
	private IProject project;
	private HashMap<byte[], byte[]> replacements;

	public ProjectSetup(IProject _project, HashMap<byte[], byte[]> _hashMap) {
		this.project = _project;
		this.replacements = _hashMap;
	}
	
	private void initializeReplacements() {
		this.replacements.put(
				"$PROJECTNAME$".getBytes(),
				this.project.getProject().getName().getBytes());		
	}

	/**
	 * Create the eclipse project. This will also create the top 
	 * level directory in the workspace.
	 * This will also open the project, so that its resources may be 
	 * modified in subsequent steps.
	 * 
	 * @throws CoreException 
	 */
	private void createOpenProject() throws CoreException {
		this.project.create(null);
		this.project.open(null);
	}
	
	private void initializeMavenProject() throws FileNotFoundException, CoreException, IOException {
		//Create pom files.
		Bundle bundle = Platform.getBundle("FGVT-Bundle-Template");
		IFile pomFile = this.project.getFile("pom.xml");
		URL entry = bundle.getEntry("/resources/converge/pom.xml");
		
		//first create POM file
		System.out.println("Creating pom file");
//		try {
////			ReplaceFilterInputStream replaceStream = new ReplaceFilterInputStream(
////					new FileInputStream(new File(FileLocator.toFileURL(entry).getFile())),
////					replacements);
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(FileLocator.toFileURL(entry).getFile())));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
//			String line = reader.readLine();
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			while( null != line) {
//				for(byte[] key : this.replacements.keySet()){
//					String keyString = new String(key);
//					if( line.contains(keyString)) {
//						line.replace(keyString, new String(this.replacements.get(key)));
//					}
//				}
//				
//				bos.write((line + "\n").getBytes());
//				line = reader.readLine();
//			}
//			reader.close();
//			
//			pomFile.create(			
//					new ByteArrayInputStream(bos.toByteArray()),
//					true,
//					null);
//			System.out.println("POM created.");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
		
		try {
			System.out.println("Creating folders");
			//Create necessary/recommended paths
			this.project.getFolder("src").create(false, false, null);
			this.project.getFolder("src/main").create(false, false, null);
			this.project.getFolder("src/test").create(false, false, null);
			this.project.getFolder("src/main/java").create(false, false, null);
			this.project.getFolder("src/main/resources").create(false, false, null);
			this.project.getFolder("src/main/config").create(false, false, null);
			this.project.getFolder("src/test/java").create(false, false, null);
			this.project.getFolder("src/test/resources").create(false, false, null);
			System.out.println("Folders created.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		try {
			System.out.println("Creating maven nature");
			IProjectDescription description = this.project.getDescription();
			MavenPlugin.getProjectConfigurationManager().addMavenBuilder(
					this.project,
					description,
					null);
			this.project.setDescription(description, null);
			System.out.println("Maven nature created");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

//		try {
//			System.out.println("Creating classpath");
//			IJavaProject jp = JavaCore.create(this.project);
//			IClasspathEntry cpe = JavaCore.newSourceEntry(
//					this.project.getFolder("src/main/java").getFullPath());
//			IClasspathEntry[] rawClasspath = jp.getRawClasspath();
//			IClasspathEntry[] newClasspath = new IClasspathEntry[rawClasspath.length + 1];
//			System.arraycopy(
//					rawClasspath,
//					0,
//					newClasspath,
//					0, 
//					rawClasspath.length);
//			newClasspath[newClasspath.length-1] = cpe;
//			jp.setRawClasspath(newClasspath, null);
//			System.out.println("Classpath created");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
		
//		IWorkspaceDescription workspaceDescription = this.project.getWorkspace().getDescription();
//		workspaceDescription.setAutoBuilding(true);
//		IProjectNatureDescriptor natureDescriptor = this.project.getWorkspace().getNatureDescriptor(MAVEN2_NATURE);
		
		

		

	}
	
	/**
	 * This class will perform the setup of the given project. 
	 */
	public void setup() {		
		try {			
			this.createOpenProject();
			this.initializeReplacements();
			this.initializeMavenProject();	
		} catch (CoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

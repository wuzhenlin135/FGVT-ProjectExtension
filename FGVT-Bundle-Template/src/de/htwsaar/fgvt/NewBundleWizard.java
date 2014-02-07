/**
 * 
 */
package de.htwsaar.fgvt;

import java.util.HashMap;

import org.apache.commons.io.input.ReplaceFilterInputStream;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import de.htwsaar.fgvt.types.DataContext;

/**
 * Main class for the wizard. 
 * This class will create the several pages of the wizard, group them 
 * and pass the data which has been collected by them in a {@link DataContext} object
 * to the {@link ProjectSetup} class. The later one will perform all necessary initalization
 * steps.
 * 
 * @author mfuenfrocken
 * 
 */
public class NewBundleWizard extends Wizard implements INewWizard {

	/**
	 * The first page, which will collect general information.
	 */
	private WizardNewProjectCreationPage pageOne;
	
	/**
	 * This page will collect information about the developer.
	 */
	private DeveloperInformationPage pageTwo;
	
	/**
	 * Indicates, if the wizard has all necessary data and if
	 * the user may now use the 'Finish' button.
	 */
	private boolean isFinished = false;
	
	/**
	 * This context stores all retrieved data in a central place.
	 */
	private DataContext dataContext = new DataContext();

	/**
	 * Create new wizard.
	 */
	public NewBundleWizard() {
		this.setWindowTitle("CONVERGE OSGi Bundle");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
			//Nothing to do here.	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IProject projectHandle = this.pageOne.getProjectHandle();		
		ProjectSetup converge = new ProjectSetup(
				projectHandle,
				this.createDataMap());
		converge.setup();		
		return this.isFinished;
	}

	@Override
	public void addPages() {
		super.addPages();

		this.pageOne = new WizardNewProjectCreationPage(
				"CONVERGE OSGi Bundle");
		this.pageOne.setTitle("CONVERGE OSGi Bundle");
		this.pageOne.setDescription("Creating a template OSGi Bundle for CONVERGE.");

		this.pageTwo = new DeveloperInformationPage(this.dataContext);
			
		this.addPage(this.pageOne);
		this.addPage(this.pageTwo);
		
	}

	@Override
	public IWizardPage getNextPage(IWizardPage _currentPage) {
		if( _currentPage == this.pageOne) {
			//after page one, the wizard can be finished, as 
			//page 2 contains default values.
			this.isFinished = true;
			return this.pageTwo;
		}		
		return super.getNextPage(_currentPage);
	}

	/**
	 * This data map contains mappings from tags to specific values.
	 * If the {@link ReplaceFilterInputStream} class detects one of those
	 * tags in its input it will be replaced by the appropriate mapping.<bt>
	 * This is used in {@link ProjectSetup} to modify the template pom.xml file.
	 * 
	 * @return A Map with an mapping of tags to actual values. (Or an empty map,
	 *  if non mapping is specified.)
	 */
	private HashMap<byte[], byte[]> createDataMap() {
		HashMap<byte[], byte[]> map = new HashMap<byte[], byte[]>();
		map.put(
				"$PROJECTDESCRIPTION$".getBytes(),
				this.dataContext.getProjectData().getDescription().getBytes());
		map.put(
				"$DEVELOPERID$".getBytes(),
				this.dataContext.getUserData().getId().getBytes());
		map.put(
				"$DEVELOPERNAME$".getBytes(),
				this.dataContext.getUserData().getFullName().getBytes());
		map.put(
				"$DEVELOPERMAIL$".getBytes(),
				this.dataContext.getUserData().getMail().getBytes());
		map.put(
				"$BUNDLE.CATEGORY$".getBytes(),
				this.dataContext.getBundleData().getCategory().getBytes());		
		return map; 
	}
	
}

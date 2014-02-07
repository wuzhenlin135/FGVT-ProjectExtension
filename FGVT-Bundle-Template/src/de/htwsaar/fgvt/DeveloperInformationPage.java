/**
 * 
 */
package de.htwsaar.fgvt;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.htwsaar.fgvt.types.DataContext;

/**
 * @author mfuenfrocken
 * 
 */
public class DeveloperInformationPage extends WizardPage {

	private Composite container;
	private Text txtBundleCategory;
	private Text txtDescription;
	private Text txtDeveloperID;
	private Text txtDeveloperName;
	private Text txtDeveloperMail;
	private DataContext context;

	public DeveloperInformationPage(DataContext _context) {
		super("OSGi Bundle");
		this.setTitle("OSGi Bundle");
		this.setDescription("OSGi Bundle control information");
		this.context = _context;
	}

	
	private void addTextField(Composite _parent) {
		this.container = new Composite(_parent, SWT.NONE);
		GridLayout layout = new GridLayout();
	    container.setLayout(layout);
		Label label = new Label(this.container, SWT.NONE);
		label.setText("Developer-ID:");
		
		this.txtDeveloperID = new Text(this.container, SWT.BORDER | SWT.SINGLE);
		txtDeveloperID.setText(context.getUserData().getId());

		txtDeveloperID.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (!txtDeveloperID.getText().isEmpty()) {
					setPageComplete(true);
					context.getUserData().setId(txtDeveloperID.getText());
				}
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txtDeveloperID.setLayoutData(gd);
	}
	
	private void addDeveloperMail(Composite _parent) {
		this.container = new Composite(_parent, SWT.NONE);
		GridLayout layout = new GridLayout();
	    container.setLayout(layout);
		Label label = new Label(this.container, SWT.NONE);
		label.setText("Developer-Mail");
		this.txtDeveloperMail = new Text(this.container, SWT.BORDER | SWT.SINGLE);
		txtDeveloperMail.setText(this.context.getUserData().getMail());

		txtDeveloperMail.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (!txtDeveloperMail.getText().isEmpty()) {
					setPageComplete(true);
					context.getUserData().setMail(txtDeveloperMail.getText());
				}
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txtDeveloperMail.setLayoutData(gd);
	}

	private void addDeveloperName(Composite _parent) {
		this.container = new Composite(_parent, SWT.NONE);
		GridLayout layout = new GridLayout();
	    container.setLayout(layout);
		Label label = new Label(this.container, SWT.NONE);
		label.setText("Developer-Name");
		this.txtDeveloperName = new Text(this.container, SWT.BORDER | SWT.SINGLE);
		txtDeveloperName.setText(this.context.getUserData().getFullName());

		txtDeveloperName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (!txtDeveloperName.getText().isEmpty()) {
					setPageComplete(true);
					context.getUserData().setFullName(txtDeveloperName.getText());
				}
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txtDeveloperName.setLayoutData(gd);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(Composite _parent) {
		this.container = new Composite(_parent, SWT.NONE);
		GridLayout layout = new GridLayout();
	    container.setLayout(layout);
		Label label = new Label(this.container, SWT.NONE);
		label.setText("Bundle-Category");
		this.txtBundleCategory = new Text(this.container, SWT.BORDER | SWT.SINGLE);
		txtBundleCategory.setText(this.context.getBundleData().getCategory());

		txtBundleCategory.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (!txtBundleCategory.getText().isEmpty()) {
					setPageComplete(true);
					context.getBundleData().setCategory(txtBundleCategory.getText());
				}
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txtBundleCategory.setLayoutData(gd);
		
		
		Label label2 = new Label(this.container, SWT.NONE);
		label2.setText("Description");
		this.txtDescription = new Text(this.container, SWT.BORDER | SWT.SINGLE);
		txtDescription.setText(this.context.getProjectData().getDescription());

		txtDescription.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (!txtDescription.getText().isEmpty()) {
					setPageComplete(true);
					context.getProjectData().setDescription(txtDescription.getText());
				}
			}
		});
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		txtDescription.setLayoutData(gd2);
		
		
		
		this.addTextField(this.container);
		this.addDeveloperName(this.container);
		this.addDeveloperMail(this.container);
		this.setControl(this.container);
	}

}

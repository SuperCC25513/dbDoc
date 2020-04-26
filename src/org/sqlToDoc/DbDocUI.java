package org.sqlToDoc;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.sqlToDoc.util.FileUtil;
import org.sqlToDoc.util.FreeMarkerUtils;

/** 
 * Description: ����db doc�ĵ��Ľ���
 * 
 * @author jiujiya
 * @version 1.0 
 */
public class DbDocUI {
	
	/** ������ */
	private Shell shell;
	
	// �ĵ�ģ��
	Combo styleList;
	// �ĵ�����
	Text projectText;
	// �ĵ�·��
	Text pathText;
	// ���ݿ�IP
	Text dbIpText;
	// �˿ں�
	Text dbPortText;
	// �û���
	Text dbUserText;
	// ����
	Text dbPsText;
	// ����
	Text dbNameText;
	// ����bean
	DbDocBean bean;
	// ���ɰ�ť
	Button createButton;
	// �򿪰�ť
	Button openButton;

	public static void main(String[] args) {
		DbDocBean bean = new DbDocBean();
		String basePath = System.getProperty("user.dir");
		bean.ftlBasePath = basePath + "\\templet\\";
		bean.pathText = basePath + "\\doc\\";
		List<String> files= FileUtil.getFileList(bean.ftlBasePath);
		bean.styleList = new String[files.size()];
		for (int i = 0; i < bean.styleList.length; i++) {
			bean.styleList[i] = FileUtil.getFileName(files.get(i));
		}
		new DbDocUI(bean).show();
	}

	public DbDocUI(DbDocBean bean) {
		this.bean = bean;
		
		shell = new Shell(192);
		shell.setText("���ݿ��ĵ����ɹ���");
		shell.setLayout(null);

		addView();

		// �����������С
		shell.setSize(430, 400);
		centerWindow(shell);
	}

	/**
	 * ��ӽ���
	 * @throws Exception 
	 */
	private void addView(){
		int y = 10;
		Label titleLabel = new Label(shell, 0);
		titleLabel.setText("��ܰ��ʾ��ѡ����Ŀ�����Զ������ĵ�");
		titleLabel.setFont(new Font(titleLabel.getDisplay(), "����", 8, SWT.UNDERLINE_LINK));
		titleLabel.setBounds(20, y+3, 300, 20);

		y += 30;
		Label styleLabel = new Label(shell, 0);
		styleLabel.setText("�ĵ�ģ�壺");
		styleLabel.setBounds(20, y+3, 70, 28);
		styleList = new Combo(shell, SWT.READ_ONLY);
		styleList.setBounds(100, y, 220, 28);
		styleList.setItems(bean.styleList);
		styleList.select(0);
		Label styleCustom = new Label(shell, 0);
		styleCustom.setText("�Զ���ģ�壿");
		styleCustom.setFont(new Font(titleLabel.getDisplay(), "����", 8, SWT.NORMAL));
		styleCustom.setBounds(328, y+7, 90, 28);
		
		styleCustom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File(bean.ftlBasePath));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		y += 40;
		Label projectLabel = new Label(shell, 0);
		projectLabel.setText("�ĵ����ƣ�");
		projectLabel.setBounds(20, y+3, 70, 28);
		projectText = new Text(shell, 2048);
		projectText.setBounds(100, y, 300, 28);
		projectText.setText(bean.projectText);

		y += 40;
		Label pathLabel = new Label(shell, 0);
		pathLabel.setText("�ĵ�·����");
		pathLabel.setBounds(20, y+3, 70, 28);
		pathText = new Text(shell, 2048);
		pathText.setBounds(100, y, 300, 28);
		pathText.setText(bean.pathText);

		y += 40;
		Label dbIpLabel = new Label(shell, 0);
		dbIpLabel.setText("���ݿ�IP��");
		dbIpLabel.setBounds(20, y+3, 70, 28);
		dbIpText = new Text(shell, 2048);
		dbIpText.setBounds(100, y, 140, 28);
		dbIpText.setText(bean.dbIpText);
		
		Label dbPortLabel = new Label(shell, 0);
		dbPortLabel.setText("�˿ںţ�");
		dbPortLabel.setBounds(260, y+3, 70, 28);
		dbPortText = new Text(shell, 2048);
		dbPortText.setBounds(330, y, 70, 28);
		dbPortText.setText(bean.dbPortText);

		y += 40;
		Label dbUserLabel = new Label(shell, 0);
		dbUserLabel.setText("�û�����");
		dbUserLabel.setBounds(20 + 12, y+3, 60, 28);
		dbUserText = new Text(shell, 2048);
		dbUserText.setBounds(100, y, 115, 28);
		dbUserText.setText(bean.dbUserText);
		
		Label dbPsLabel = new Label(shell, 0);
		dbPsLabel.setText("���룺");
		dbPsLabel.setBounds(235, y+3, 50, 28);
		dbPsText = new Text(shell, 2048);
		dbPsText.setBounds(285, y, 115, 28);
		dbPsText.setText(bean.dbPsText);
		
		y += 40;
		Label dbNameLabel = new Label(shell, 0);
		dbNameLabel.setText("���ݿ�����");
		dbNameLabel.setBounds(20, y+3, 70, 28);
		dbNameText = new Text(shell, 2048);
		dbNameText.setBounds(100, y, 300, 28);
		dbNameText.setText(bean.dbNameText);

		y += 60;
		createButton = new Button(shell, 0);
		createButton.setText("ȷ������");
		createButton.setBounds(70, y, 120, 28);
		
		openButton = new Button(shell, 0);
		openButton.setText("���ĵ�·��");
		openButton.setBounds(220, y, 120, 28);
		openButton.setVisible(false);
		
		createButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				createDoc();
			}
		});
		
		openButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(pathText.getText()));
				} catch (IOException e) {
					error(shell, e);
					e.printStackTrace();
				}
			}
		});
		
		// ������ݿ����Ʋ�Ϊ�գ��Զ�����
		if(!bean.dbNameText.equals("")) {
			createDoc();
		}
	}
	
	/**
	 * �����ĵ�
	 * @throws IOException 
	 */
	public void createDoc() {
		createButton.setEnabled(false);
		createButton.setText("�ĵ�������");
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	try {
					// ��ʼ�����ݿ�����
					DBHelper.driverName = bean.driverClassName;
					DBHelper.dbname = dbNameText.getText();
					DBHelper.user = dbUserText.getText();
					DBHelper.password = dbPsText.getText();
					DBHelper.url = "jdbc:mysql://" + dbIpText.getText() + ":" + dbPortText.getText() + "/" 
									+ dbNameText.getText() + "?characterEncoding=utf8&useSSL=false";
					// ��ʼ��ģ������  
			    	FreeMarkerUtils.initFreeMarker(bean.ftlBasePath);
			    	/** ģ����������Ҫ������Map */
			    	Map<String,Object> templateData = new HashMap<String, Object>();
			    	templateData.put("projectName", projectText.getText());
			    	templateData.put("tables", DBHelper.getTableAndColumns());
			    	// ����word�ļ�
			    	if("".equals(pathText.getText().trim())) {
			    		throw new RuntimeException("�ĵ�·������Ϊ��");
			    	}
			    	String fileName = pathText.getText() + projectText.getText() + ".doc";
			    	FileUtil.addDirs(fileName);
			    	FreeMarkerUtils.crateFile(templateData, styleList.getText(), fileName, true);
					openButton.setVisible(true);
					createButton.setText("��������");
				} catch (Exception e) {
					e.printStackTrace();
					createButton.setText("ȷ������");
					error(shell, e);
				} finally {
					createButton.setEnabled(true);
				}
		    }
		});
		
	}

	public void show() {
		shell.open();
		Display display = shell.getDisplay();
		while (!(shell.isDisposed()))
			if (!(display.readAndDispatch()))
				display.sleep();
	}
	
	public void close() {
		shell.dispose();
	}
	
	/**
	 * @param shell
	 * @param e
	 */
	public static void error(Shell shell, Exception e) {
		error(shell, e.getMessage());
	}
	
	/**
	 * @param shell
	 * @param message
	 */
	public static void error(Shell shell, String message) {
		MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
        dialog.setText("��ܰ��ʾ");
        dialog.setMessage(message);
        dialog.open();
	}
	
	/**
	 * �������
	 * @param shell
	 */
	public static void centerWindow(Shell shell) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scmSize = toolkit.getScreenSize();
		shell.setLocation(scmSize.width / 2 - shell.getSize().x / 2, scmSize.height / 2 - shell.getSize().y / 2);
	}
}
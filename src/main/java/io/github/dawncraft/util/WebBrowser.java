package io.github.dawncraft.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 网页浏览器主程序
 *
 * @author QingChenW
 * 
 * 如何使用：WebBrowser webBrowser = new WebBrowser("维基百科", "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
 */
public class WebBrowser extends JFrame implements HyperlinkListener, ActionListener
{
	String htmlSource;
	private ArrayList history = new ArrayList();
	private int historyIndex;
	
	JWindow window = new JWindow(WebBrowser.this);
	JToolBar jBar = new JToolBar();
	JTextField jurl = new JTextField(50);
	JEditorPane jEditorPane = new JEditorPane();
	JScrollPane jScrollPane = new JScrollPane(jEditorPane);
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	JButton picBack = new JButton("后退");
	JButton picRefresh = new JButton("刷新");
	JLabel label = new JLabel("地址");
	JButton picGo = new JButton("转向");
	Box adress = Box.createHorizontalBox();
	
	public WebBrowser(String title, String url)
	{
		title += "   浏览器作者:wc";
		setTitle(title);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e) {}
	
		Container contentPane = getContentPane();
		jScrollPane.setPreferredSize(new Dimension(100,500));
		contentPane.add(jScrollPane, BorderLayout.SOUTH);
		jEditorPane.addHyperlinkListener(this);
	
		jBar.add(picBack);
		jBar.add(picRefresh);
		jBar.addSeparator();
		adress.add(label);
		adress.add(jurl);
		adress.add(picGo);
		jBar.add(adress);
		contentPane.add(jBar,BorderLayout.NORTH);
	
		picBack.addActionListener(this);
		picRefresh.addActionListener(this);
		jurl.addActionListener(this);
		picGo.addActionListener(this);
	
		pack();
		setVisible(true);
		
		loadWebPage(url);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String url = "";
		if(e.getSource() == picGo || e.getSource() == jurl || e.getSource() == picRefresh)
		{
			url = jurl.getText();
			loadWebPage(url);
		}
		else if(e.getSource() == picBack)
		{
			if(historyIndex > 0) historyIndex--;
			url = (String) history.get(historyIndex);
			loadWebPage(url);
		}
	}
	
	public void hyperlinkUpdate(HyperlinkEvent e)
	{
		try
		{
			if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				jEditorPane.setPage(e.getURL());
		}
		catch(Exception ex)
		{
			ex.printStackTrace(System.err);
		}
	}
	
	public void loadWebPage(String url)
	{
		if(url.length() > 0)
		{
			if(!url.startsWith("http://")) url = "http://" + url;
			try
			{
				jurl.setText(url);
				jEditorPane.setPage(url);
				jEditorPane.setEditable(false);
				jEditorPane.revalidate();
				history.add(url);
				historyIndex = history.size() - 1;
			}
			catch(Exception ex)
			{
				jEditorPane.setText("无法打开该网页，请检查网络连接\n错误:" + ex);
			}
		}
		else
		{
			jEditorPane.setText("未输入网址");
		}
	}
}

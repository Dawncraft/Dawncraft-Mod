package io.github.dawncraft.util;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * 网页浏览器主程序
 * <br>请勿使用此类，这只是个纪念品</br>
 *
 * @version v1.0
 * @author QingChenW
 *
 *         如何使用：WebBrowserV1 webBrowser = new WebBrowserV1("我的世界中文维基百科",
 *         "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
 */
public class WebBrowserV1 extends JFrame implements HyperlinkListener, ActionListener
{
    String htmlSource;
    private ArrayList history = new ArrayList();
    private int historyIndex;

    JWindow window = new JWindow(this);
    JToolBar jBar = new JToolBar();
    JTextField jurl = new JTextField(50);
    JEditorPane jEditorPane = new JEditorPane();
    JScrollPane jScrollPane = new JScrollPane(this.jEditorPane);

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    JButton picBack = new JButton("后退");
    JButton picRefresh = new JButton("刷新");
    JLabel label = new JLabel("地址");
    JButton picGo = new JButton("转向");
    Box adress = Box.createHorizontalBox();

    public WebBrowserV1(String title, String url)
    {
        this.setTitle(title + "   浏览器作者:wc");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e)
        {
        }

        Container contentPane = this.getContentPane();
        this.jScrollPane.setPreferredSize(new Dimension(100, 500));
        contentPane.add(this.jScrollPane, BorderLayout.SOUTH);
        this.jEditorPane.addHyperlinkListener(this);

        this.jBar.add(this.picBack);
        this.jBar.add(this.picRefresh);
        this.jBar.addSeparator();
        this.adress.add(this.label);
        this.adress.add(this.jurl);
        this.adress.add(this.picGo);
        this.jBar.add(this.adress);
        contentPane.add(this.jBar, BorderLayout.NORTH);

        this.picBack.addActionListener(this);
        this.picRefresh.addActionListener(this);
        this.jurl.addActionListener(this);
        this.picGo.addActionListener(this);

        this.pack();
        this.setVisible(true);

        this.loadWebPage(url);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String url = "";
        if (e.getSource() == this.picGo || e.getSource() == this.jurl || e.getSource() == this.picRefresh)
        {
            url = this.jurl.getText();
            this.loadWebPage(url);
        } else if (e.getSource() == this.picBack)
        {
            if (this.historyIndex > 0)
                this.historyIndex--;
            url = (String) this.history.get(this.historyIndex);
            this.loadWebPage(url);
        }
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e)
    {
        try
        {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                this.jEditorPane.setPage(e.getURL());
        } catch (Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    public void loadWebPage(String url)
    {
        if (url.length() > 0)
        {
            if (!url.startsWith("http://"))
                url = "http://" + url;
            try
            {
                this.jurl.setText(url);
                this.jEditorPane.setPage(url);
                this.jEditorPane.setEditable(false);
                this.jEditorPane.revalidate();
                this.history.add(url);
                this.historyIndex = this.history.size() - 1;
            } catch (Exception ex)
            {
                this.jEditorPane.setText("无法打开该网页，请检查网络连接\n错误:" + ex);
            }
        } else
            this.jEditorPane.setText("未输入网址");
    }
}

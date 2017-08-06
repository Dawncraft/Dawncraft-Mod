package io.github.dawncraft.util;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 网页浏览器主程序
 * <br>请勿使用此类，这玩意根本用不了</br>
 * 如何使用：WebBrowserV2 webBrowser = new WebBrowserV2("我的世界中文维基百科", "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
 *
 * @version v2.0
 * @author QingChenW
 */
public class WebBrowserV2 extends Region
{
    final WebView browser;
    final WebEngine webEngine;
    
    public WebBrowserV2(String title, String url)
    {
        this.browser = new WebView();
        this.webEngine = this.browser.getEngine();
        this.getStyleClass().add("browser");
        this.webEngine.load(url);
        this.getChildren().add(this.browser);

        this.webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>()
        {
            @Override
            public WebEngine call(PopupFeatures config)
            {
                return null;
            }
        });
        
        Stage stage = new Stage();
        stage.setTitle(title + "   浏览器作者:wc");
        stage.setScene(new Scene(this, 800, 480, Color.web("#666970")));
        stage.show();
    }
    
    private Node createSpacer()
    {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override
    protected void layoutChildren()
    {
        this.layoutInArea(this.browser, 0, 0, this.getWidth(), this.getHeight(), 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        return 800;
    }

    @Override
    protected double computePrefHeight(double width)
    {
        return 480;
    }
}

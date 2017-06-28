package net.msdh.console.gui;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class MediaTab extends JPanel {

    public MediaTab() {
        JChartTest demo = new JChartTest("test1","test2");
        demo.setVisible(true);
        this.add(demo);
    }
}

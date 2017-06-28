package net.msdh.console.gui;

import net.msdh.h3d.Home3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class ManagementTab extends JPanel {

    private Home3D home3D;
    private JComboBox visualObjects;


    public ManagementTab() {
      this.setLayout(null);
      this.setPreferredSize(new Dimension(410, 50));
      home3D = new Home3D("Resources/Objects/myhouse.obj","Resources/Textures/",700,500);
      this.add(home3D.getCanvas3D());
      DefaultComboBoxModel objName = new DefaultComboBoxModel();

      Hashtable table = home3D.getObjectsName();
      for(Enumeration e = table.keys() ; e.hasMoreElements() ;) {
        Object key = e.nextElement();
        objName.addElement(key);
      }

      visualObjects = new JComboBox(objName);
      visualObjects.setSelectedIndex(0);

      final JLabel headerLabel = new JLabel("", JLabel.CENTER);
      headerLabel.setText("Control in action: JComboBox");
      headerLabel.setBounds(701,23,100,300);

      visualObjects.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
	      JComboBox jcmbType = (JComboBox) e.getSource();
	      String cmbType = (String) jcmbType.getSelectedItem();
          headerLabel.setText(cmbType);
          home3D.setUnvisible(cmbType);
        }
      });

      visualObjects.setBounds(701,0,300,20);
      this.add(headerLabel);
      this.add(visualObjects);
    }
}

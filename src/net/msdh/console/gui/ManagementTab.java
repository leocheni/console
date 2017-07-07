package net.msdh.console.gui;

import net.msdh.h3d.Home3D;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
   // private JComboBox visualObjects;


    public ManagementTab() {
      this.setLayout(null);
      //this.setPreferredSize(new Dimension(410, 50));
      home3D = new Home3D("F:\\dev\\projects\\lab\\dh\\console\\Resources\\Objects","F:\\dev\\projects\\lab\\dh\\console\\Resources\\Textures",900,600);
      //home3D.setObject("myhouse");
      //home3D.setObject("myhouse_v4.2i");
      //home3D.setObject("myhouse_v4.2i3");
      home3D.setObject("myhouse_v4.2_3DHome");


      home3D.create();
      this.add(home3D.getCanvas3D());
   //   DefaultComboBoxModel objName = new DefaultComboBoxModel();

      Hashtable table = home3D.getObjectsName();


      int i=0;
      for(Enumeration e = table.keys() ; e.hasMoreElements() ;) {
        Object key = e.nextElement();
        //objName.addElement(key);
        JCheckBox shapeBox = new JCheckBox(key.toString());
        shapeBox.setName(key.toString());
        shapeBox.addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
              System.out.println(e.getStateChange());
              if(e.getStateChange()==1){
                home3D.setUnvisible(((JCheckBox)e.getItem()).getName());
                System.out.println(((JCheckBox)e.getItem()).getName());
              }
              else{
                home3D.setVisible(((JCheckBox)e.getItem()).getName());
                System.out.println(((JCheckBox)e.getItem()).getName());
              }
           }
        });

        System.out.println(shapeBox.getName());
        shapeBox.setBounds(900,i,300,20);
        this.add(shapeBox);
      //  this.repaint();
        i=i+22;
      }

      this.repaint();

//      visualObjects.setBounds(701,0,300,20);
//      this.add(headerLabel);
//      this.add(visualObjects);
    }

    public Home3D getHome3D(){
      return home3D;
    }
}

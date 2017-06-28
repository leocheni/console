package net.msdh.console.utils;

import net.msdh.console.Console;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

  public static ImageIcon createImageIcon(String path) {

    ImageIcon icon = null;
    try{
      Image img = ImageIO.read(new FileImageInputStream(new File(path)));
      icon = new ImageIcon(img);
    }
    catch (IOException ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return icon;
  }
}

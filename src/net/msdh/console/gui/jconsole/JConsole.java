package net.msdh.console.gui.jconsole;

import net.msdh.console.Console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.sound.sampled.DataLine;
import javax.swing.*;

import static java.awt.event.KeyEvent.*;


/**
 * Class implementing a Swing-based text console
 * 
 * Principles:
 * - provides a fixed number of rows and columns, but can be resized
 * - each cell can have its own foreground and background colour
 * - The main font determines the grid size
 * 
 * @author Mike Anderson
 * 
 */
//public class JConsole extends JPanel implements HierarchyListener {
//public class JConsole extends JComponent implements HierarchyListener {
//public class JConsole extends JComponent{ //implements HierarchyListener {
public class JConsole extends JPanel implements KeyListener, MouseListener{ //implements HierarchyListener {

	private static final long serialVersionUID = 3571518591759968333L;

	private static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	private static final Color DEFAULT_BACKGROUND = Color.BLACK;
	private static final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 18);

    public int height;
    public int width;

	private ConsoleData data = new ConsoleData();

	private int fontWidth;
	private int fontHeight;
	private int fontYOffset;

    private int indexCommand;

	private boolean cursorVisible = false;
	//private boolean cursorBlinkOn = true;
	//private boolean cursorInverted = true;
    private boolean endScrol = false;
    private int scrollPoz;
    private boolean incomplitComand = false;

	private Font mainFont = null;
	private Font currentFont = null;
	private Color currentForeground = DEFAULT_FOREGROUND;
	private Color currentBackground = DEFAULT_BACKGROUND;

    private String commandLine;
    private ConsoleAction action;


	public JConsole(int columns, int rows) {
		setMainFont(DEFAULT_FONT);
		setFont(mainFont);
		init(columns, rows);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.commandLine = "";
        scrollPoz = 0;
        indexCommand = 0;
	}

	/**
	 * Sets the main font of the console, which is used to determine the size of
	 * characters
	 * 
	 * @param font
	 */
	public void setMainFont(Font font) {

	  mainFont = font;
	  FontRenderContext fontRenderContext = new FontRenderContext(mainFont.getTransform(), false, false);
	  Rectangle2D charBounds = mainFont.getStringBounds("X",fontRenderContext);
	  fontWidth = (int) charBounds.getWidth();
	  fontHeight = (int) charBounds.getHeight();
	  fontYOffset = -(int) charBounds.getMinY();

	  setPreferredSize(new Dimension(data.columns * fontWidth, data.rows * fontHeight));
	  repaint();

	}

	public void setRows(int rows) {
	  resize(this.data.columns, rows);
	}

	public void setFont(Font f) {
	  currentFont = f;
	}

	public void setCursorVisible(boolean visible) {
	  cursorVisible = visible;
	}

	public int getRows() {
	  return data.rows;
	}

	public void setColumns(int columns) {
	  resize(columns, this.data.rows);
	}

	public int getColumns() {
	  return data.columns;
	}

	public int getFontWidth() {
	  return fontWidth;
	}

	public int getFontHeight() {
	  return fontHeight;
	}

	/**
	 * Fires a repaint event on a specified rectangle of characters in the
	 * console
	 */
	public void repaintArea(int column, int row, int width, int height) {
	  int fw = getFontWidth();
	  int fh = getFontHeight();
	  repaint(column * fw, row * fh, width * fw, height * fh);
	}

	/**
	 * Initialises the console to a specified size
	 */
	protected void init(int columns, int rows) {
	  data.init(columns, rows);
      height = rows * fontHeight;
      width = columns * fontWidth;
	  setPreferredSize(new Dimension(width,height));
	}

	public void resize(int columns, int rows) {
		//throw new UnsupportedOperationException();
	}

	public void clear() {
	  clearArea(0, 0, data.columns, data.rows);
	}

	public void resetCursor() {
	 // repaintArea(cursorX, cursorY, 0, 0);
	  data.cursorX = 0;
	  data.cursorY = 0;
	//  repaintArea(cursorX, cursorY, 0, 0);
	}

	public void clearScreen() {
	  clear();
	  resetCursor();
	}

	private void clearArea(int column, int row, int width, int height) {
	  data.fillArea(' ', currentForeground, currentBackground, currentFont, column, row, width, height);
	  repaintArea(0, 0, width, height);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		Rectangle r = g.getClipBounds();

		// AffineTransform textTransform=new AffineTransform();
		// textTransform.scale(fontWidth, fontHeight);
		// g.setTransform(textTransform);

		// calculate x and y range to redraw
		int x1 = (int) (r.getMinX() / fontWidth);
		int x2 = (int) (r.getMaxX() / fontWidth) + 1;
		int y1 = (int) (r.getMinY() / fontWidth);
		int y2 = (int) (r.getMaxY() / fontWidth) + 1;

	//	int curX = getCursorX();
	//	int curY = getCursorY();

		for (int j = Math.max(0, y1); j < Math.min(y2, data.rows); j++) {
			int offset = j * data.columns;
			int start = Math.max(x1, 0);
			int end = Math.min(x2, data.columns);

			while (start < end) {
				Color nfg = data.foreground[offset + start];
				Color nbg = data.background[offset + start];
				Font nf = data.font[offset + start];

				// index of ending position
				int i = start + 1;

//				if ((j == curY) && (start == curX)) {
//					if (cursorVisible && cursorBlinkOn && cursorInverted) {
//						// swap foreground and background colours
//						Color t = nfg;
//						nfg = nbg;
//						nbg = t;
//					}
//				}
 //               else {
					// detect run
//					while ((i < end) && (!((j == curY) && (i == curX))) && (nfg == data.foreground[offset + i])
//							&& (nbg == data.background[offset + i]) && (nf == data.font[offset + i])){
//						i++;
//					}
//				}

                while ((i < end)&&(nfg == data.foreground[offset + i])&&(nbg == data.background[offset + i])&&(nf == data.font[offset + i])){
				  i++;
                }

				// set font
				g.setFont(nf);

				// draw background
				g.setBackground(nbg);
				g.clearRect(fontWidth * start, j * fontHeight, fontWidth * (i - start), fontHeight);

				// draw chars up to this point
				g.setColor(nfg);
				for (int k=start; k<i; k++) { 
				  g.drawChars(data.text, offset + k, 1, k * fontWidth, j * fontHeight + fontYOffset);
				}
				start = i;
			}
		}
	}

	public void setCursorPos(int column, int row) {
		if ((column < 0) || (column >= data.columns))
			throw new Error("Invalid X cursor position: " + column);
		if ((row < 0) || (row >= data.rows))
			throw new Error("Invalid Y cursor position: " + row);

		data.cursorX = column;
		data.cursorY = row;
	}

	public int getCursorX() {
		return data.cursorX;
	}

	public int getCursorY() {
		return data.cursorY;
	}

	public void setForeground(Color c) {
		currentForeground = c;
	}

	public void setBackground(Color c) {
		currentBackground = c;
	}

	public Color getForeground() {
		return currentForeground;
	}

	public Color getBackground() {
		return currentBackground;
	}

	public char getCharAt(int column, int row) {
		return data.getCharAt(column, row);
	}

	public Color getForegroundAt(int column, int row) {
		return data.getForegroundAt(column, row);
	}

	public Color getBackgroundAt(int column, int row) {
		return data.getBackgroundAt(column, row);
	}

	public Font getFontAt(int column, int row) {
		return data.getFontAt(column, row);
	}

	/**
	 * Redirects System.out to this console by calling System.setOut
	 */
	public void captureStdOut() {
	  PrintStream ps = new PrintStream(System.out) {
		public void println(String x) {
	      writeln(x);
		}
	  };
	  System.setOut(ps);
	}

//    public void captureStdIn() {
//
//        InputStream is = new InputStream() {
//            @Override
//            public int read() throws IOException {
//              return System.in.read();
//            }
//        };
//        System.setIn(is);
//	}

	private void moveCursor() {
      data.moveCursor();
      repaint();
    }

    private void removeCursor(){
      data.removeCursor();
      repaint();
    }

	public void writeln(String line) {
		write(line+'\n');
        data.addLine(line+'\n');
	}

    public void writeln(String line,Color foreGround, Color backGround) {
		write(line+'\n',foreGround,backGround);
        data.addLine(line+'\n');
	}

	public void write(String line, Color foreGround, Color backGround) {
	  Color foreTemp = currentForeground;
	  Color backTemp = currentBackground;
	  setForeground(foreGround);
	  setBackground(backGround);
	  write(line);
	  setForeground(foreTemp);
	  setBackground(backTemp);
      data.addLine(line);
	}

    public void write(int x, int y, String line, Color foreGround, Color backGround) {
	  Color foreTemp = currentForeground;
	  Color backTemp = currentBackground;
	  setForeground(foreGround);
	  setBackground(backGround);
	  write(line);
	  setForeground(foreTemp);
	  setBackground(backTemp);
      data.addLine(line);
	}

	public void write(String string) {
	  for (int i = 0; i < string.length(); i++) {
		char c = string.charAt(i);
		write(c);
	  }
	}

    public void write(int x, int y, String string) {
      setCursorPos(x,y);
	  for (int i = 0; i < string.length(); i++) {
		char c = string.charAt(i);
		write(c);
	  }
	}

    public void write(char c) {
	  //data.setDataAt(cursorX, cursorY, c, currentForeground,currentBackground, currentFont);
      data.setDataAt(c, currentForeground,currentBackground, currentFont);
	  repaint();
	}

//    public void write(int x , int y, char c) {
//		//data.setDataAt(x, y, c, currentForeground,currentBackground, currentFont);
//        setCursorPos(x,y);
//        data.setDataAt(c, currentForeground,currentBackground, currentFont);
//		//moveCursor(c);
//        repaint();
//	}

//	public void fillArea(char c, Color fg, Color bg, int column, int row,int width, int height) {
//		data.fillArea(c, fg, bg, currentFont, column, row, width, height);
//		repaintArea(column, row, width, height);
//	}

//    public void lineDown(){
//     //  if(data.cursorY == data.rows){
//         data.moveLineDown();
//         repaint();
//     //  }
//    }

//    public void lineUp(){
//       data.moveLineUp();
//       repaint();
//    }

    public void delChar(){
      data.delChar();
      repaint();
    }

    public void setFocus(){
      this.requestFocus();
    }

    public void addConsoleListener(ConsoleAction action){
      this.action = action;
    }


    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
      int size = data.lines.size();

      switch(e.getKeyCode()){
        case VK_SHIFT:{
          break;
        }
        case VK_BACK_SPACE:{
          this.delChar();
          break;
        }
        case VK_DOWN:{
          if(!incomplitComand){
            if((0<=indexCommand)&&(indexCommand<data.commands.size())){
              int y=getCursorY();
              String tmp = data.commands.get(indexCommand); //kopiruem iz vektora vo vremennuu stroku

              data.clearLine(y);
              write(0,y,tmp);
              commandLine = tmp;
              indexCommand++;
            }
          }
          break;
        }
        case VK_UP:{
          if(!incomplitComand){ //esli komanda uzhe nachata vihodim
            if(indexCommand>0){ //esli konec vectora to vihodim
              int y=getCursorY();
              String tmp=data.commands.get(indexCommand-1); //kopiruem iz vektora vo vremennuu stroku
              data.clearLine(y);
              write(0,y,tmp);
              commandLine = tmp;
              indexCommand--;
            }
          }
          break;
        }
        case VK_PAGE_UP:{
          endScrol=false;
          if(scrollPoz<(size-data.rows)){
            data.scroll(-1);
            int index = size-(data.rows+scrollPoz);
            ConsoleLine cl = data.lines.get(index);
            data.setLine(0,0,cl);
            repaint();
            scrollPoz++;
          }
          break;
        }
        case VK_PAGE_DOWN:{
          if(!endScrol){
            if(scrollPoz!=0){
              data.scroll(1);

              int index = size-scrollPoz;
              ConsoleLine cl = data.lines.get(index);
              data.setLine(0,data.rows-2,cl);
              repaint();
              scrollPoz--;
            }
            else{
              endScrol=true;
              //waddch(body,'#');
            }
          }
          break;
        }
        case VK_LEFT:{
          this.removeCursor();
          break;
        }
        case VK_RIGHT:{
          this.moveCursor();
          break;
        }
        default:{
          if(e.getKeyChar()=='\n'){
            this.write(e.getKeyChar());
            incomplitComand = false;
            data.addLine(commandLine+'\n');
            data.addCommand(commandLine);
            indexCommand = data.commands.size();
            if(action!=null){
              action.onLine(commandLine);
            }
            commandLine = "";
          }
          else{
            incomplitComand = true;
            commandLine = commandLine + e.getKeyChar();
            this.write(e.getKeyChar());
          }
          break;
        }
      }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
      System.out.println("mouse clicked, focus get");
      this.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}

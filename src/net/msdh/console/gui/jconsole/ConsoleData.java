package net.msdh.console.gui.jconsole;

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

import javax.sound.sampled.DataLine;
import java.awt.*;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.Vector;

/**
 * Class used for storing console data
 * 
 * @author Mike
 */
public final class ConsoleData {

    public static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BACKGROUND = Color.BLACK;
	public static final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 18);

    public Vector<String> commands;
    public Vector<ConsoleLine> lines;

    public int commandsSize;
    public int linesSize;


	private int capacity = 0;
	public int rows;
	public int columns;
	public Color[] background;
	public Color[] foreground;
	public Font[] font;
	public char[] text;

    public int cursorX;
	public int cursorY;

    private int scrollCount;

	ConsoleData() {
      commands = new Vector<String>();
      lines = new Vector<ConsoleLine>();
      cursorX = 0;
	  cursorY = 0;
      scrollCount = 0;
      commandsSize = 5;
      linesSize = 40;
	}

	private void ensureCapacity(int minCapacity) {
		if(capacity >= minCapacity){
		  return;
        }

		char[] newText = new char[minCapacity];
		Color[] newBackground = new Color[minCapacity];
		Color[] newForeground = new Color[minCapacity];
		Font[] newFont = new Font[minCapacity];

		int size = rows * columns;

		if(size > 0){
		  System.arraycopy(text, 0, newText, 0, size);
		  System.arraycopy(foreground, 0, newForeground, 0, size);
		  System.arraycopy(background, 0, newBackground, 0, size);
		  System.arraycopy(font, 0, newFont, 0, size);
		}

		text = newText;
		foreground = newForeground;
		background = newBackground;
		font = newFont;
		capacity = minCapacity;
	}

	void init(int columns, int rows) {
		ensureCapacity(rows * columns);

        Arrays.fill(background, DEFAULT_BACKGROUND);
        Arrays.fill(foreground, DEFAULT_FOREGROUND);
        Arrays.fill(font, DEFAULT_FONT);
        Arrays.fill(text, ' ');

		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * Sets a single character position
	 */
	public void setDataAt(char c, Color fg, Color bg,Font f){

	  int pos = cursorX + cursorY * columns;
	  text[pos] = c;
	  foreground[pos] = fg;
	  background[pos] = bg;
	  font[pos] = f;
      moveCursor(c);

      if(cursorY==rows){
       // System.out.println("mldx: " + cursorX + " mldy: " + cursorY);
        //moveLineDown();
        scroll(1);
      }
	}

    /**
	 * Sets a single character position
	 */
	public void setCharAt(char c, Color fg, Color bg,Font f){

	  int pos = cursorX + cursorY * columns;
	  text[pos] = c;
	  foreground[pos] = fg;
	  background[pos] = bg;
	  font[pos] = f;
	}


    private void moveCursor(char c) {
		switch (c) {
		case '\n':
			cursorY++;
			cursorX = 0;

			break;
		default:
			cursorX++;
			if(cursorX >= columns) {
			  cursorX = 0;
			  cursorY++;
			}
			break;
		}
        //System.out.println("mc x: " + cursorX + " mc y: " + cursorY);
	}


    public void moveCursor() {
	  cursorX++;
	  if(cursorX >= columns) {
	    cursorX = 0;
	    cursorY++;
	  }
      System.out.println("mc x: " + cursorX + " mc y: " + cursorY);
    }

    public void removeCursor() {
	  cursorX--;
	  if(cursorX < 0) {
        if(cursorY>0){
          cursorX = (columns-1);
	      cursorY--;
        }
        else{
         cursorX = 0;
        }
	  }
      System.out.println("mc x: " + cursorX + " mc y: " + cursorY);
	}


	public char getCharAt(int column, int row) {
		int offset = column + row * columns;
		return text[offset];
	}

	public Color getForegroundAt(int column, int row) {
		int offset = column + row * columns;
		return foreground[offset];
	}

	public Color getBackgroundAt(int column, int row) {
		int offset = column + row * columns;
		return background[offset];
	}

	public Font getFontAt(int column, int row) {
		int offset = column + row * columns;
		return font[offset];
	}

	public void fillArea(char c, Color fg, Color bg, Font f, int column,int row, int width, int height) {

		for (int q = Math.max(0, row); q < Math.min(row + height, rows); q++) {
			for (int p = Math.max(0, column); p < Math.min(column + width, columns); p++) {
				int offset = p + q * columns;
				text[offset] = c;
				foreground[offset] = fg;
				background[offset] = bg;
				font[offset] = f;
			}
		}

	}

    public void scroll(int i){
      if(i==1){    //скроллинг вверх
//          char[] zeroLine  = new char[columns];                //создаем пустую строку
//          Color[] zeroBG = new Color[columns];
//          Color[] zeroFG = new Color[columns];
//          Font[] zeroF = new Font[columns];
//
//          Arrays.fill(zeroLine,' ');                          //инициализируем пустую строку
//          Arrays.fill(zeroBG,DEFAULT_BACKGROUND);
//          Arrays.fill(zeroFG,DEFAULT_FOREGROUND);
//          Arrays.fill(zeroF,DEFAULT_FONT);

          char[] temp = new char[capacity-columns];          //создаем временный массив-буфер размером на одну строку меньше для сдвига экрана
          Color[] tempBG = new Color[capacity-columns];
          Color[] tempFG = new Color[capacity-columns];
          Font[] tempF = new Font[capacity-columns];

          System.arraycopy(text,columns,temp,0,capacity-columns);  //копируем текущий экран без верхней строки во временный массив
          System.arraycopy(temp,0,text,0,temp.length);             //копируем временный массив начиная от верхка основного массива

          System.arraycopy(background,columns,tempBG,0,capacity-columns);  //копируем текущий экран без верхней строки во временный массив
          System.arraycopy(tempBG,0,background,0,tempBG.length);             //копируем временный массив начиная от верхка основного массива

          System.arraycopy(foreground,columns,tempFG,0,capacity-columns);  //копируем текущий экран без верхней строки во временный массив
          System.arraycopy(tempFG,0,foreground,0,tempFG.length);             //копируем временный массив начиная от верхка основного массива

          System.arraycopy(font,columns,tempF,0,capacity-columns);  //копируем текущий экран без верхней строки во временный массив
          System.arraycopy(tempF,0,font,0,tempF.length);             //копируем временный массив начиная от верхка основного массива


//          System.arraycopy(zeroLine,0,text,(capacity-columns),columns);  //забиваем самую нижнюю строку
//          System.arraycopy(zeroBG,0,background,(capacity-columns),columns);
//          System.arraycopy(zeroFG,0,foreground,(capacity-columns),columns);
//          System.arraycopy(zeroF,0,font,(capacity-columns),columns);

          clearLine(rows-1);

          cursorY--;
      }
      else{
        scrollCount++;
        char[] temp = new char[capacity-columns];          //создаем временный массив-буфер размером на одну строку меньше для сдвига экрана
        System.arraycopy(text,0,temp,0,capacity-columns);  //копируем текущий экран без нижней строки во временный массив
        System.arraycopy(temp,0,text,columns,temp.length);             //копируем временный массив начиная от второй строки основного массива
        cursorY++;
      }
    }

    public void clearLine(int y){

       char[] zeroLine  = new char[columns];                //создаем пустую строку
       Color[] zeroBG = new Color[columns];
       Color[] zeroFG = new Color[columns];
       Font[] zeroF = new Font[columns];

       Arrays.fill(zeroLine,' ');                          //инициализируем пустую строку
       Arrays.fill(zeroBG,DEFAULT_BACKGROUND);
       Arrays.fill(zeroFG,DEFAULT_FOREGROUND);
       Arrays.fill(zeroF,DEFAULT_FONT);


       System.arraycopy(zeroLine,0,text,y * columns,columns);  //забиваем самую нижнюю строку
       System.arraycopy(zeroBG,0,background,y * columns,columns);
       System.arraycopy(zeroFG,0,foreground,y * columns,columns);
       System.arraycopy(zeroF,0,font,y * columns,columns);

    }

    public void addLine(String line){
        if(lines.size()>=linesSize){
          lines.remove(0);
        }
        //char[] line = new char[columns];                     //создаем массивы под одну временную строку
        Color[] bgLine = new Color[line.length()];
        Color[] fgLine = new Color[line.length()];
        Font[] fLine = new Font[line.length()];

        Arrays.fill(bgLine,DEFAULT_BACKGROUND);
        Arrays.fill(fgLine,DEFAULT_FOREGROUND);
        Arrays.fill(fLine,DEFAULT_FONT);

        ConsoleLine cl = new ConsoleLine(line.toCharArray(),bgLine,fgLine,fLine);
        lines.add(cl);
    }

    public void addCommand(String command){
      if(commands.size()>=commandsSize){
        commands.remove(0);
      }
      commands.add(command);

    }

    public void setLine(int x, int y, ConsoleLine cl){
       System.arraycopy(cl.line,0,text,x + y * columns,cl.line.length);  //заменяем верхнюю строку строкой из буфера
       System.arraycopy(cl.background,0,background,x + y * columns,cl.background.length);
       System.arraycopy(cl.foreground,0,foreground,x + y * columns,cl.foreground.length);
       System.arraycopy(cl.font,0,font,x + y * columns,cl.font.length);
    }

//    public void moveLineUp(){
//       if(lines.size()==0){
//         return;
//       }
//
//       ConsoleLine cl = lines.get(lines.size()-1);
//       lines.remove(lines.size()-1);
//
//       scrollCount++;
//
//       char[] temp = new char[capacity-columns];          //создаем временный массив-буфер размером на одну строку меньше для сдвига экрана
//       System.arraycopy(text,0,temp,0,capacity-columns);  //копируем текущий экран без нижней строки во временный массив
//       System.arraycopy(temp,0,text,columns,temp.length);             //копируем временный массив начиная от второй строки основного массива
//
//
//       System.arraycopy(cl.line,0,text,0,columns);  //заменяем верхнюю строку строкой из буфера
//       System.arraycopy(cl.background,0,background,0,columns);
//       System.arraycopy(cl.foreground,0,foreground,0,columns);
//       System.arraycopy(cl.font,0,font,0,columns);
//
//       cursorY++;
//       System.out.println("Y: " + cursorY);
//    }

//    public void moveLineDown(){
//
//        //if(scrollCount!=0){
//        char[] line = new char[columns];                     //создаем массивы под одну временную строку
//        Color[] bgLine = new Color[columns];
//        Color[] fgLine = new Color[columns];
//        Font[] fLine = new Font[columns];
//
//        System.arraycopy(text,0,line,0,columns);             //копируем верхнюю сроку массива во временную строку
//        System.arraycopy(foreground,0,fgLine,0,columns);
//        System.arraycopy(background,0,bgLine,0,columns);
//        System.arraycopy(font,0,fLine,0,columns);
//
//        lines.add(new ConsoleLine(line,bgLine,fgLine,fLine));  //сохраняем временную строку в вектоор буфера
//
//        char[] zeroLine  = new char[columns];                //создаем пустую строку
//        Color[] zeroBG = new Color[columns];
//        Color[] zeroFG = new Color[columns];
//        Font[] zeroF = new Font[columns];
//
//        Arrays.fill(zeroLine,' ');                          //инициализируем пустую строку
//        Arrays.fill(zeroBG,DEFAULT_BACKGROUND);
//        Arrays.fill(zeroFG,DEFAULT_FOREGROUND);
//        Arrays.fill(zeroF,DEFAULT_FONT);
//
//        char[] temp = new char[capacity-columns];          //создаем временный массив-буфер размером на одну строку меньше для сдвига экрана
//
//        System.arraycopy(text,columns,temp,0,capacity-columns);  //копируем текущий экран без верхней строки во временный массив
//        System.arraycopy(temp,0,text,0,temp.length);             //копируем временный массив начиная от верхка основного массива
//
//        System.arraycopy(zeroLine,0,text,(capacity-columns),columns);  //забиваем самую нижнюю строку
//        System.arraycopy(zeroBG,0,background,(capacity-columns),columns);
//        System.arraycopy(zeroFG,0,foreground,(capacity-columns),columns);
//        System.arraycopy(zeroF,0,font,(capacity-columns),columns);
//
//        cursorY--;
//        System.out.println("Y: " + cursorY);
//
//        //scrollCount--;
//
//        //}
//    }

    public void delChar(){
        removeCursor();
        setCharAt(' ',DEFAULT_FOREGROUND,DEFAULT_BACKGROUND,DEFAULT_FONT);
    }
}
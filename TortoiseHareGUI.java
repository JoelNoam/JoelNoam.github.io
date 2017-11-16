import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;

class TortoiseHareGUI extends JFrame implements ActionListener, Runnable { 
  
    static java.util.Random rd = new java.util.Random();
  
    private MyPanel panel = new MyPanel();
    private JButton drawRace = new JButton("Race");
    private Object command; 
  
    static int tWins = 0, hWins = 0, ties = 0;
    static int END = 70; // won't fit if race is too long
    
    public TortoiseHareGUI() { 
        setTitle("The Tortoise and the Hare");
        setLayout(new FlowLayout ());
        add(drawRace);
        panel.setBackground(Color.pink);
        panel.setPreferredSize(new Dimension(10*END + 200, 300));
        add(panel);
        drawRace.addActionListener(this);
        setSize(10*END + 300, 400); 
        setVisible(true); 
        setResizable(false);
        getContentPane().setBackground(Color.yellow); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
     
    private void drawRace(Graphics g) {   
        g.setColor(Color.black);
        
        for (int i=0; i < 10*END; i+=10) // draw racetrack
            g.drawRect(x+i,y,10,10);
        for (int i=0; i < 10*END; i+=10)
            g.drawRect(x+i,y+20,10,10);
        
        for (int i=10; i <= END; i+=10) // label racetrack distances
            g.drawString(Integer.toString(i), x + 10*(i-1), y-10);
        
        
        tortoise = moveTortoise(tortoise);
        g.fillRect(x + (tortoise-1) * 10, y, 10, 10);
        
        hare = moveHare(hare);
        g.fillRect(x + (hare-1) * 10, y+20, 10, 10);
      
        g.drawString("Tortoise: " + tortoise, x, y-20);
        g.drawString("Hare: " + hare, x, y+60);
        
        if (tortoise == hare && hare >= END) {
            g.drawString("Tie.", 10*END, y-20);
            g.drawString("Tie.", 10*END, y+60);
            ties++;
        }
        else if (tortoise >= END && tortoise > hare) {
            g.drawString("Tortoise wins.",700,y+60);
            tWins++;
        }
        else if (hare >= END && hare > tortoise) {
            g.drawString("Hare wins.",700,y+60);
            hWins++;
        }
        
        g.drawString("TOTAL WINS", 10*END, y+80);
        g.drawString(("Tortoise: " + tWins + "  Hare: " + hWins),10*END,y+100);
    }
    
    private static int moveTortoise(int tortoise) {
        int i = Math.abs(rd.nextInt()) % 10 + 1;
        switch (i) {
                case 1:  
                case 2:  
                case 3:  
                case 4:
                case 5:  tortoise += 3;
                         break;
                case 6:
                case 7:  tortoise -= 6;
                         break;
                case 8:  
                case 9:
                case 10: tortoise++;
                         break;
            }
        if (tortoise < 1)
            tortoise = 1;
        return tortoise;
    }
    private static int moveHare(int hare) {
        int i = Math.abs(rd.nextInt()) % 10 + 1;
        switch (i) {
                case 1:  
                case 2:  break;
                case 3:  
                case 4:  hare += 9;
                         break;
                case 5:  hare -= 12;
                         break;
                case 6:
                case 7:  
                case 8:  hare++;
                         break;
                case 9:
                case 10: hare -= 2;;
                         break;
            }
        if (hare < 1)
            hare = 1;
        return hare;
    }
     
    int tortoise=1, hare=1;
    int x = 100, y = 100;
    
    public void actionPerformed(ActionEvent e) {
        command = e.getSource();
        (new Thread(this)).start();   // needed for animation;
    }
    public void run() {            //for animation;
         
        tortoise = hare = 1; // reset before
        x = y = 100;          // each race
        
        for (int i = 0; tortoise < END && hare < END; i++) { // running the race
            panel.repaint();
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {}
        }
    }
    class MyPanel extends JPanel{
        public void paintComponent(Graphics g) {
            super.paintComponent(g);         
            if (command == drawRace) {
                g.drawString("BANG !!!!!",200,30);
                g.drawString("AND THEY'RE OFF !!!!!",200,50);
                
                drawRace(g);
            }
        }
    }
    
    
    public static void main(String[]args) { 
        new TortoiseHareGUI(); 
    } 
} 

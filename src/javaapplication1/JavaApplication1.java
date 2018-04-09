/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import net.java.games.input.Component;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static  org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        JavaApplication1 work = new JavaApplication1();
        
        work.start();
        
    }
    
    public void start() {
try {
    createWindow();
initGL();
render();} catch (Exception e) {
e.printStackTrace();}
}
    
    public void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
        
        Display.create();
    }
    
    public void initGL() throws Exception{
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        glMatrixMode(GL_PROJECTION); 
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render() {
while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
    try{
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();  
        glColor3f(1.0f,1.0f,0.0f);
        glPointSize(1);
        glBegin(GL_POINTS);
        File file = new File("coordinates.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner sc = new Scanner(br);
        while (sc.hasNext()){
            String hold = sc.nextLine();
            char c = hold.charAt(0);
            hold = hold.substring(1);
            hold = hold.replaceAll(",", " ");
            Scanner b = new Scanner(hold);
            if (c==('l')){
                lines((float)b.nextInt(), (float)b.nextInt(),(float)b.nextInt(),(float)b.nextInt());
            } else if (c=='c'){
                circles((float)b.nextInt(), b.nextInt(), b.nextInt());
            } else {
                ellipses((float)b.nextInt(), (float)b.nextInt(),(float)b.nextInt(),(float)b.nextInt());
            }
        }
        glEnd();
        Display.update();
        Display.sync(60);
        }catch(Exception e){
        }
}
Display.destroy
();        
}
    
    private void ellipses(float centerx, float centery, float x, float y){
        glColor3f(0.0f, 1.0f, 0.0f);
        for (int i = 0; i < 360; i++){
            float rad = i * (3.14159265f/180.0f);
            glVertex2f(centerx +(float)Math.cos(rad)*x, centery +(float)Math.sin(rad)*y);
        }
    }
    
    private void circles(float centerx, float centery, float radius){
        glColor3f(0.0f, 0.0f, 1.0f);
        for (int i = 0; i < 360; i++){
            float rad = i * (3.14159265f/180.0f);
            glVertex2f(centerx +(float)Math.cos(rad)*radius, centery +(float)Math.sin(rad)*radius);
        }
    }
    
    private void lines(float fx, float fy, float sx, float sy){
        glColor3f(1.0f, 0.0f, 0.0f);
        if (fx > sx){
            float hold = sx;
            float hold2 = sy;
            sx = fx;
            sy = fy;
            fx = hold;
            fy = hold2;
        }
        float dx = sx - fx;
        float dy = sy - fy;
        boolean moreup = Math.abs(dy) > Math.abs(dx);
        boolean one = Math.abs(dy) == Math.abs(dx);
        boolean negative = sy < fy;
        
        float d = 2*(dy) - dx;
        float incr = 2*(dy);
        float incu = 2*(dx);
        
        float incur = 2*(dx-dy);
        float x = fx;
        float y = fy;
        while (x != sx){
            if (negative && moreup && !one){
                incur = 2*(dy-dx);
                if (d < -1){
                x++;
                y--;
                d = d -(incur);
                glVertex2f (x, y);
            } else {
                y--;
                d = d - incu;
                glVertex2f (x, y);
            }
            } else if (negative && !moreup&& !one){
                if (d < -1){
                x++;
                y--;
                d = d +(incur);
                glVertex2f (x, y);
            } else {
                x++;
                d = d + incr;
                glVertex2f (x, y);
            }
            } else if (!negative && moreup&& !one){
                incur = 2*(dx-dy);
                if (d > -1){
                    x++;
                y++;
                d = d +(incur);
                glVertex2f (x, y);
            } else {
                    
                y++;
                d = d + incu;
                glVertex2f (x, y);
            }
            } else if (!negative && !moreup&& !one) {
                if (d > -1){
                incur = 2*(dy-dx);
                x++;
                y++;
                d = d +(incur);
                glVertex2f (x, y);
            } else {
                x++;
                d = d + incr;
                glVertex2f (x, y);
            }
            } else if (!negative && one){
                x++;
                y++;
                glVertex2f (x, y);
            } else {
                x++;
                y--;
                glVertex2f (x, y);
            }
        }
    }
    
}

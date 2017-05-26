
import threads_01_02.HelloRunnable;
import threads_01_02.HelloThread;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
    @author splat
 */
public class Threads_01_02 {
    public static void main(String[] args)
    {        
    //Create a thread using a class that implements runnable
    (new Thread(new HelloRunnable())).start();

    //Create a thread using a class that extends Thread
    (new HelloThread()).start();
    //Create a runnable object
    Runnable r1 = new Runnable()
    {
        @Override
        public void run()
        {
        //perform some work inside the thread
        System.out.println("Hello from "+
            Thread.currentThread().getName()
            + " NOT USING LAMBDA");
        }  
    };
    //Create a runnable object using lambda notation
    /*Runnable r2 = () -> System.out.println("Hello from "
            + Thread.currentThread().getName()+" USING LAMBDA")
            */
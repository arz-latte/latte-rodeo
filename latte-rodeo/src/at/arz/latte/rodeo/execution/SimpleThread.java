package at.arz.latte.rodeo.execution;

import java.util.logging.Logger;

/**
 * Klasse Simplethread
 * 
 * Abstrakte Klasse - implementiert Interface Runnable
 * 
 * @author Mario Rodler, Hutterer Bernhard, Stephan D�Costa
 */
public abstract class SimpleThread implements Runnable {
    private static final Logger logger = Logger.getLogger( SimpleThread.class.getName() );
    private boolean isRunning = false;
    private boolean keepRunning = false;
    private Thread myThread;

    public static void sleep( long ms ) {
        try {
            Thread.sleep( ms );
        } catch ( InterruptedException ie ) {
            // ignore
        }
    }

    public boolean isRunning() {
        if ( !isRunning ) {
            return false;
        }
        if ( myThread != null ) {
            return myThread.isAlive();
        }
        return false;
    }

    public abstract boolean loop();

    public final void run() {
        logger.finest( "run " + this.getClass().getName() );
        initialize();
        try {
            while ( keepRunning() ) {
                if ( !loop() ) {
                    break;
                }
            }
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
        cleanup();
        logger.finest( "finished " + this.getClass().getName() );
    }

    public void start() {
        logger.finest( "starting " + this.getClass().getName() );
        if ( !isRunning ) {
            keepRunning = true;
            myThread = new Thread( this );
            myThread.start();
            isRunning = true;
        }
    }

    public void stop( boolean wait ) {
        logger.finest( "stop " + this.getClass().getName() );
        keepRunning = false;
        try {
            synchronized ( this ) {
                notifyAll();
            }
            if ( wait ) {
                myThread.join();
            }
        } catch ( InterruptedException ie ) {
            // ignore
        }
    }

    protected void cleanup() {
        // ignore
    }

    protected void initialize() {
        // ignore
    }

    protected boolean keepRunning() {
        return keepRunning;
    }

    protected void resume() {
        logger.finest( "resume " + this.getClass().getName() );
        synchronized ( this ) {
            notifyAll();
        }
    }

    protected void suspend() {
        logger.finest( "suspend " + this.getClass().getName() );
        try {
            synchronized ( this ) {
                wait();
            }
        } catch ( InterruptedException ie ) {
            ie.printStackTrace();
        }
    }
}

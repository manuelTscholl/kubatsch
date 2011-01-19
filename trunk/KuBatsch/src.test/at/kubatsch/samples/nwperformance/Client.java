/**
 * This file is part of KuBatsch.
 *   created on: 03.01.2011
 *   filename: Client.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.nwperformance;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.samples.uiperformance.PerformanceRunner;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class Client extends JFrame implements WindowListener, Runnable
{
    private ClientPanel _panel;
    private boolean     _running;
    private Thread      _thread;
    private PlayerInfo _nfo;

    /**
     * Gets the running.
     * @return the running
     */
    public synchronized boolean isRunning()
    {
        return _running;
    }

    /**
     * Sets the running.
     * @param running the running to set
     */
    public synchronized void setRunning(boolean running)
    {
        _running = running;
    }

    /**
     * Initializes a new instance of the {@link Client} class.
     */
    public Client()
    {
        _nfo = new PlayerInfo();
        setSize(800, 800);
        setTitle("Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.NORTH);
        
        // slider
        final JSlider positionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        positionSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                _nfo.setPosition(positionSlider.getValue() / 100.0);
                try
                {
                    _output.writeObject(_nfo);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(positionSlider);
        
        // name
        final JTextField name = new JTextField();
        name.setSize(300, 25);
        name.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _nfo.setName(name.getText());
            }
        });
        panel.add(name);
        
        // client panel
        _panel = new ClientPanel();
        _panel.setNfos(new PlayerInfo[0]);
        add(_panel, BorderLayout.CENTER);
    }
    
    public void start()
    {
        setRunning(true);
        _thread = new Thread(this);
        _thread.run();
    }

    @Override
    public void windowOpened(WindowEvent e)
    {}

    @Override
    public void windowClosing(WindowEvent e)
    {
        setRunning(false);
    }

    @Override
    public void windowClosed(WindowEvent e)
    {}

    @Override
    public void windowIconified(WindowEvent e)
    {}

    @Override
    public void windowDeiconified(WindowEvent e)
    {}

    @Override
    public void windowActivated(WindowEvent e)
    {}

    @Override
    public void windowDeactivated(WindowEvent e)
    {}

    private Socket             _socket;
    private ObjectInputStream  _input;
    private ObjectOutputStream _output;

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            _socket = new Socket("localhost", 25000);
            _output = new ObjectOutputStream(_socket.getOutputStream());
            _input = new ObjectInputStream(_socket.getInputStream());

            while (isRunning())
            {
                Object data;
                try
                {
                    data = _input.readObject();
                    if (data instanceof PlayerInfo[])
                    {
                        _panel.setNfos((PlayerInfo[]) data);
                    }
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (_socket != null)
                try
                {
                    _socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }
    
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
        }
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Client runner = new Client();
                runner.setVisible(true);
                runner.start();
            }
        });
    }
}

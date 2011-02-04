/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: AudioController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import at.kubatsch.util.StreamUtils;

import de.quippy.javamod.main.gui.PlayThread;
import de.quippy.javamod.main.gui.PlayThreadEventListener;
import de.quippy.javamod.mixer.Mixer;
import de.quippy.javamod.mixer.dsp.AudioProcessor;
import de.quippy.javamod.multimedia.MultimediaContainer;
import de.quippy.javamod.multimedia.MultimediaContainerManager;
import de.quippy.javamod.system.Helpers;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class AudioController implements PlayThreadEventListener
{
    private static final String[]  AUDIO_FILES   = { "alloy-run.xm",
            "credits-screen.xm", "flash.xm", "mortal-kombat.xm", "remember.xm" };

    private static final URL       TICK_FILE     = getResource("tick.wav");
    private static final String    RESOURCES_DIR = "files";
    private List<URL>              _files;
    private MultimediaContainer    _currentContainer;
    private int                    _currentIndex;
    private PlayThread             _playerThread;
    private AudioProcessor         audioProcessor;

    private float                  _effectsVolume;
    private float                  _backgroundVolume;
    private boolean                _isAudioEnabled;

    static
    {
        try
        {
            Helpers.registerAllClasses();
        }
        catch (ClassNotFoundException ex)
        {
        }
    }

    private static AudioController _instance;

    public static AudioController getInstance()
    {
        if (_instance == null)
            _instance = new AudioController();
        return _instance;
    }

    /**
     * Initializes a new instance of the {@link ModPlayer} class.
     */
    private AudioController()
    {
        loadPlaylist();
        this.audioProcessor = new AudioProcessor(2048, 70);
        MultimediaContainerManager.configureContainer(new Properties());
        _effectsVolume = 1;
        _backgroundVolume = 1;
    }

    /**
     * Gets the isAudioEnabled.
     * @return the isAudioEnabled
     */
    public boolean isAudioEnabled()
    {
        return _isAudioEnabled;
    }

    /**
     * Sets the isAudioEnabled.
     * @param isAudioEnabled the isAudioEnabled to set
     */
    public void setAudioEnabled(boolean isAudioEnabled)
    {
        _isAudioEnabled = isAudioEnabled;
    }

    /**
     * Gets the volume of the background music.
     * @return the volume
     */
    public float getBackgroundVolume()
    {
        return _backgroundVolume;
    }

    /**
     * Sets the volume of the background music.
     * @param volume the volume to set
     */
    public void setBackgroundVolume(float volume)
    {
        _backgroundVolume = Math.max(0, Math.min(1, volume));
        if (_playerThread != null)
        {
            _playerThread.getCurrentMixer().setVolume(_backgroundVolume);
        }
    }

    /**
     * Gets the volume of the sound effects.
     * @return the volume of the sound effects
     */
    public float getEffectsVolume()
    {
        return _effectsVolume;
    }

    /**
     * Sets the volume of the sound effects.
     * @param effectsVolume the volume of the sound effects
     */
    public void setEffectsVolume(float effectsVolume)
    {
        _effectsVolume = effectsVolume;
    }

    public void playTick()
    {
        if (!_isAudioEnabled)
            return;
        new WavePlayer(TICK_FILE, _effectsVolume).start();
    }

    /**
     * Loads the list from the jar file.
     */
    private void loadPlaylist()
    {
        _files = new ArrayList<URL>();
        for (String file : AUDIO_FILES)
        {
            URL uri = getResource(file);
            _files.add(uri);
        }
        _currentIndex = new Random().nextInt(_files.size());
        updateContainer(false);
    }

    /**
     * Updates the container needed for playing.
     * @param start true if start playing, otherwise false.
     */
    public void updateContainer(boolean start)
    {
        if (!_isAudioEnabled)
            return;
        try
        {
            _currentContainer = MultimediaContainerManager
                    .getMultimediaContainer(_files.get(_currentIndex));
            if (start)
                doStartPlaying();
        }
        catch (UnsupportedAudioFileException e)
        {
        }
    }

    /**
     * Starts playing the next song in the playlist.
     */
    public void next()
    {
        _currentIndex++;
        if (_currentIndex >= AUDIO_FILES.length)
        {
            _currentIndex = 0;
        }
        updateContainer(true);
    }

    /**
     * Starts the player.
     */
    private void doStartPlaying()
    {
        if (_currentContainer != null)
        {
            doStopPlaying();
            Mixer mixer = createNewMixer();
            if (mixer != null)
            {
                _playerThread = new PlayThread(mixer, null, this);
                _playerThread.start();
            }
        }
    }

    /**
     * Creates the mixer required for playing.
     * @return the mixer.
     */
    private Mixer createNewMixer()
    {
        Mixer mixer = _currentContainer.createNewMixer();
        if (mixer != null)
        {
            mixer.setAudioProcessor(audioProcessor);
            mixer.setVolume(_backgroundVolume);
        }
        return mixer;
    }

    /**
     * Stopps playing the current song.
     */
    private void doStopPlaying()
    {
        if (_playerThread != null)
        {
            _playerThread.stopMod();
            _playerThread = null;
        }
    }

    /**
     * Loads a resource as a URL.
     * @param resource The filename of the resource.
     * @return The URL for accessing the resource.
     */
    private static URL getResource(String resource)
    {
        String full = String.format("%s/%s", RESOURCES_DIR, resource);
        return AudioController.class.getResource(full);
    }

    /**
     * @see de.quippy.javamod.main.gui.PlayThreadEventListener#playThreadEventOccured(de.quippy.javamod.main.gui.PlayThread)
     */
    @Override
    public void playThreadEventOccured(PlayThread thread)
    {
        // finished?
        if (!thread.isRunning() && thread.getHasFinishedNormaly())
        {
            next();
        }
    }

    /**
     * Starts Playing
     */
    public void start()
    {
        updateContainer(true);
    }

    private static class WavePlayer extends Thread
    {
        private URL   _file;
        private float _volume;

        /**
         * Initializes a new instance of the {@link AudioController.WavePlayer}
         * class.
         */
        public WavePlayer(URL file, float volume)
        {
            _file = file;
            _volume = volume;
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run()
        {
            try
            {
            
                final Clip clip = AudioSystem.getClip();
                final AudioInputStream inputStream = AudioSystem
                        .getAudioInputStream(_file);
                clip.addLineListener(new LineListener()
                {
                    
                    @Override
                    public void update(LineEvent event)
                    {
                        if(event.getType() == LineEvent.Type.STOP)
                        {
                            clip.close();
                            StreamUtils.close(inputStream);
                        }                        
                    }
                });
                clip.open(inputStream);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float)(Math.log(_volume)/Math.log(10.0)*20.0);
                gainControl.setValue(dB);
                
                clip.start();
                
            }
            catch (Exception e)
            {
            }
        }
    }
}

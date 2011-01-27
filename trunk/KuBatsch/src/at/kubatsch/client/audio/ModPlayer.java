/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: ModPlayer.java
 *   project: KuBatsch
 */
package at.kubatsch.client.audio;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.quippy.javamod.main.gui.PlayThread;
import de.quippy.javamod.main.gui.PlayThreadEventListener;
import de.quippy.javamod.mixer.Mixer;
import de.quippy.javamod.mixer.dsp.AudioProcessor;
import de.quippy.javamod.multimedia.MultimediaContainer;
import de.quippy.javamod.multimedia.MultimediaContainerManager;
import de.quippy.javamod.system.Helpers;

/**
 * This class encapsules the playing of MOD files.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ModPlayer implements PlayThreadEventListener
{
    private static final String[] AUDIO_FILES   = { "alloy-run.xm",
            "credits-screen.xm", "flash.xm", "mortal-kombat.xm", "remember.xm" };
    private static final String   RESOURCES_DIR = "files";
    private List<URL>             _files;
    private MultimediaContainer   _currentContainer;
    private int                   _currentIndex;
    private PlayThread            _playerThread;
    private AudioProcessor        audioProcessor;

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

    /**
     * Initializes a new instance of the {@link ModPlayer} class.
     */
    public ModPlayer()
    {
        loadPlaylist();
        this.audioProcessor = new AudioProcessor(2048, 70);
        MultimediaContainerManager.configureContainer(new Properties());
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
            mixer.setVolume(1);
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
        return ModPlayer.class.getResource(full);
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
}

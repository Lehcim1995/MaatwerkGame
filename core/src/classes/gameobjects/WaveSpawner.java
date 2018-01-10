package classes.gameobjects;

import classes.Wave;
import classes.managers.GameManager;
import com.badlogic.gdx.math.Vector2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class WaveSpawner
{
    private List<Wave> waves;
    private Wave currentWave;
    private boolean repeat;
    private GameManager gameManager;
    private float timeBetweenWaves;
    private float time;

    private boolean isDone;

    public WaveSpawner(
            List<Wave> waves,
            boolean repeat,
            GameManager gameManager)
    {
        this.waves = waves;
        this.repeat = repeat;
        this.gameManager = gameManager;

        isDone = false;
    }

    public List<Wave> getWaves()
    {
        return waves;
    }

    public void setWaves(List<Wave> waves)
    {
        this.waves = waves;
    }

    public void addWave(Wave wave)
    {
        if (waves == null)
        {
            waves = new ArrayList<>();
        }

        waves.add(wave);
    }

    public boolean isRepeat()
    {
        return repeat;
    }

    public void setRepeat(boolean repeat)
    {
        this.repeat = repeat;
    }

    public void update(float deltaTime) throws Exception
    {
        if (timeBetweenWaves <= 0)
        {
            throw new InvalidParameterException("Time between waves cannot be 0 or less");
        }

        if (repeat)
        {
            isDone = false;
        }

        if (!isDone)
        {
            time += deltaTime;
        }

        if (time > timeBetweenWaves)
        {
            time = 0;

            spawnWave(nextWave());
            isDone = true;
        }
    }

    private void spawnWave(Wave wave)
    {
        switch (wave.getSpawnType())
        {
            case Round:
                int spawn = 10;
                float radius = 500;
                float angle = 360 / spawn;
                Vector2 middle = new Vector2(0, 0);

                for (int i = 0; i < spawn; i++)
                {
                    // TODO refactor
                    // Make a vector with a radius
                    Vector2 pos = new Vector2(radius, 0);
                    pos.rotate(angle * i);
                    pos.add(middle);

                    gameManager.createEnemy(pos, angle * i);
                }
                break;
            case Square:
                throw new NotImplementedException();
            case Triangle:
                throw new NotImplementedException();
        }
    }

    private Wave nextWave() throws Exception
    {
        if (currentWave == null)
        {
            if (waves == null || waves.size() == 0)
            {
                throw new Exception("No waves found");
                // maybe throw exception
            }
            currentWave = waves.get(0);
        }
        else
        {
            // Stuff
            int index = waves.indexOf(currentWave) + 1;
            index = index % waves.size();

            currentWave = waves.get(index);
        }

        return currentWave;
    }


    public void setTimeBetweenWaves(float timeBetweenWaves)
    {
        this.timeBetweenWaves = timeBetweenWaves;
    }
}

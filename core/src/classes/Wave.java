package classes;


import classes.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    private List<GameObject> waveShips;
    private float delay;
    private float duration;
    private SpawnType spawnType;

    public Wave(
            List<GameObject> waveShips,
            float delay,
            float duration)
    {
        this.waveShips = waveShips;
        this.delay = delay;
        this.duration = duration;
        this.spawnType = SpawnType.Round;
    }

    public Wave(
            List<GameObject> waveShips,
            float delay,
            float duration,
            SpawnType spawnType)
    {
        this.waveShips = waveShips;
        this.delay = delay;
        this.duration = duration;
        this.spawnType = spawnType;
    }

    public List<GameObject> getWaveShips()
    {
        return waveShips;
    }

    public void setWaveShips(List<GameObject> waveShips)
    {
        this.waveShips = waveShips;
    }

    public void addWaveShip(GameObject ship)
    {
        if (this.waveShips == null)
        {
            this.waveShips = new ArrayList<>();
        }

        this.waveShips.add(ship);
    }

    public void addWaveShips(List<GameObject> waveShips)
    {
        if (this.waveShips == null)
        {
            this.waveShips = new ArrayList<>();
        }

        this.waveShips.addAll(waveShips);
    }

    public float getDelay()
    {
        return delay;
    }

    public void setDelay(float delay)
    {
        this.delay = delay;
    }

    public float getDuration()
    {
        return duration;
    }

    public void setDuration(float duration)
    {
        this.duration = duration;
    }

    public SpawnType getSpawnType()
    {
        return spawnType;
    }

    public void setSpawnType(SpawnType spawnType)
    {
        this.spawnType = spawnType;
    }

    public enum SpawnType
    {
        Round,
        Square,
        Triangle
    }
}

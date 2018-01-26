package classes.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import interfaces.IGameLobby;
import interfaces.IGameManager;
import interfaces.IGameObject;

import java.util.ArrayList;
import java.util.List;

public class OnlineManager2 implements IGameManager
{

    private GameManager gameManager;
    private IGameLobby gameLobby;
    private String playerName;
    private float onlineUpdateRate = 1 / 30f;
    private float onlineUpdateTimer = 0;
    private List<IGameObject> serverGameObjects;

    public OnlineManager2(
            GameManager gameManager,
            IGameLobby gameLobby,
            String playerName)
    {
        this.gameLobby = gameLobby;
        this.playerName = playerName;
        this.gameManager = gameManager;
        serverGameObjects = new ArrayList<>();
    }

    @Override
    public IGameObject createObject(IGameObject object)
    {
        return null;
    }

    @Override
    public void deleteObject(IGameObject object)
    {

    }

    @Override
    public void update(float deltaTime)
    {
        onlineUpdateTimer += deltaTime;
        if (onlineUpdateTimer > onlineUpdateRate)
        {
            onlineUpdateTimer -= onlineUpdateRate;

            // TODO update
        }
    }

    @Override
    public void draw(Batch batch)
    {
        // TODO do this online?
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer)
    {
        // TODO do this online?
    }

    @Override
    public List<IGameObject> getObjects()
    {
        return serverGameObjects;
    }
}

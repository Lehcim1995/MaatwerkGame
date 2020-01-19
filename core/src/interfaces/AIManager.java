package interfaces;

import classes.gameobjects.GameObject;

public interface AIManager
{
    void SetTarget(GameObject go);

    void SetSelf(GameObject go);

    void Move();
}

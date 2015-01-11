package team044.Units;


import team044.*;

import battlecode.common.*;
import team044.Units.Rushers.LauncherRusher;

public class Launcher extends Unit
{
    public Launcher(RobotController rc)
    {
        super(rc);
        // override supers range
        range = 24;

        nav.setAvoidTowers(false);
        nav.setAvoidHQ(false);
    }

    public void collectData() throws GameActionException
    {
        super.collectData();
        // collect our data
        nearByEnemies = rc.senseNearbyRobots(range, opponent);
        MapLocation[] enemyTower = rc.senseEnemyTowerLocations();
        if (enemyTower.length > 0)
        {
            target = enemyTower[0];
        }
        else
        {
            target = rc.senseEnemyHQLocation();
        }
    }

    public void handleMessages() throws GameActionException
    {
        super.handleMessages();

        Utilities.handleMessageCounter(rc, Messaging.NumbOfLaunchersOdd.ordinal(), Messaging.NumbOfLaunchersEven.ordinal());
    }

    public boolean takeNextStep() throws GameActionException
    {
        if (nearByEnemies.length > 0)
        {
            return false;
        }
        else if (Utilities.nearEnemyTower(rc))
        {
            return false;
        }
        else
        {
            return nav.takeNextStep(target);
        }
    }

    public boolean fight() throws GameActionException
    {
        return fighter.launcherAttack(nearByEnemies);
    }

    public Unit getNewStrategy(Unit current) throws GameActionException
    {
        if (rc.readBroadcast(Messaging.RushEnemyBase.ordinal()) == 1)
        {
            return new LauncherRusher(rc);
        }
        return current;
    }

    public boolean carryOutAbility() throws GameActionException
    {
        return false;
    }
}

package team044.Units;

import battlecode.world.Util;
import team044.*;

import battlecode.common.*;

public class Miner extends Unit
{
    MapLocation target;
    boolean mineToOurHQ = true;

    public Miner(RobotController rc)
    {
        super(rc);

        rc.setIndicatorString(0, "Miner to our HQ");
        if (rc.getID() % 4 == 0)
        {
            rc.setIndicatorString(0, "Miner to enemyHQ");
            mineToOurHQ = false;
        }
    }

    public void collectData() throws GameActionException
    {
        // collect our data
        super.collectData();

        if (rc.senseOre(rc.getLocation()) < 5)
        {
            //target = Utilities.getBestMiningSpot(rc);
            target = Utilities.greedyBestMiningSpot(rc);

            if (target == rc.getLocation())
            {
                if (mineToOurHQ)
                {
                    target = ourHQ;
                }
                else
                {
                    target = enemyHQ;
                }
            }

            if (rc.getLocation().distanceSquaredTo(ourHQ) < 35)
            {
                mineToOurHQ = false;
            }
        }

    }

    public void handleMessages() throws GameActionException
    {
        super.handleMessages();

        Utilities.handleMessageCounter(rc, Messaging.NumbOfMinersOdd.ordinal(), Messaging.NumbOfMinersEven.ordinal());
    }

    public boolean takeNextStep() throws GameActionException
    {
        if (target == null)
        {
            return false;
        }

        return nav.takeNextStep(target, true, false);
        //return nav.badMovement(target);
    }

    public boolean fight() throws GameActionException
    {
        return fighter.basicFightMicro(nearByEnemies);
    }

    public Unit getNewStrategy(Unit current) throws GameActionException
    {
        return current;
    }

    public boolean carryOutAbility() throws GameActionException
    {
        if (rc.isCoreReady() && rc.canMine() && rc.senseOre(rc.getLocation()) >= 1)
        {
            rc.mine();
            return true;
        }

        return false;
    }
}

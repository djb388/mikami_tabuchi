package _teamoffense.Structures;

import battlecode.common.*;
import _teamoffense.Messaging;
import _teamoffense.Structure;
import _teamoffense.Utilities;

public class Helipad extends Structure
{
    int numbOfDrones;
    public Helipad(RobotController rc)
    {
        super(rc);
    }

    public void collectData() throws GameActionException
    {
        // collect our data
        super.collectData();
        numbOfDrones = rc.readBroadcast(Messaging.NumbOfDrones.ordinal());
    }

    public boolean carryOutAbility() throws GameActionException
    {
        // start by only keeping up at most one drone at a time
        if (numbOfDrones == 0 && Utilities.spawnUnit(RobotType.DRONE, rc))
        {
            return true;
        }
        return false;
    }
}

package alsid.model.game;

import alsid.model.asset.Asset;
import alsid.model.asset.Property;
import alsid.model.asset.Railroad;
import alsid.model.asset.Utility;
import alsid.model.space.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class for board. This keeps track of each of the spaces.
 */
public class Board
{
    private ArrayList <Space>   spaces;
    private int                 nPlayerCount;

    public static final int		SPACE_COUNT = 32,

                             	START = 0,
                        		COMMUNITY_SERVICE = 8,
                             	FREE_PARKING = 16,
                        		JAIL = 24,

                                PROPERTY = 1,
                                UTILITY = 2,
                                RAILROAD = 3;

    /**
     * Constructor for the board.
     * @param nPlayerCount Number of players in this game
     */
    public Board(int nPlayerCount)
    {
        spaces = new ArrayList<>();
        this.nPlayerCount = nPlayerCount;
        initSpaces();
    }

    /**
     * Initializes all of the spaces.
     */
    private void initSpaces()
    {
        //initializing all properties

        Asset andrew    = new Property ("Andrew", 1, 60, 50, new double[]{2, 10, 30, 90, 160, 250},2.5, nPlayerCount);
        Asset razon     = new Property ("Razon", 1, 60, 50, new double[]{4, 20, 60, 180, 320, 450},3, nPlayerCount);

        Asset connon    = new Property ("Connon", 2, 100, 50, new double[]{6, 30, 90, 270, 400, 550},3.5, nPlayerCount);
        Asset yuchengco = new Property ("Yuch", 2, 100, 50, new double[]{6, 30, 90, 270, 400, 550},4, nPlayerCount);
        Asset lshall    = new Property ("LS Hall", 2, 120, 50, new double[]{6, 40, 100, 300, 450, 600},4, nPlayerCount);

        Asset gokongwei = new Property ("Goks", 3, 140, 100, new double[]{10, 50, 150, 450, 625, 750},4.5, nPlayerCount);
        Asset miguel    = new Property ("Miguel", 3, 140, 100, new double[]{10, 50, 150, 450, 625, 750},4.5, nPlayerCount);
        Asset stc       = new Property ("STC", 3, 160, 100, new double[]{12, 60, 180, 500, 700, 900},5, nPlayerCount);

        Asset velasco   = new Property ("Velasco", 4, 180, 100, new double[]{14, 70, 200, 550, 750, 950},5, nPlayerCount);
        Asset henry     = new Property ("Henry", 4, 180, 100, new double[]{14, 70, 200, 550, 750, 950},5.5, nPlayerCount);
        Asset bloemen   = new Property ("Bloemen", 4, 200, 100, new double[]{16, 80, 220, 600, 800, 1000},5.5, nPlayerCount);

        Asset joseph    = new Property ("Joseph", 5, 220, 150, new double[]{18, 90, 250, 700, 875, 1050},6, nPlayerCount);
        Asset william   = new Property ("William", 5, 220, 150, new double[]{18, 90, 250, 700, 875, 1050},6, nPlayerCount);
        Asset faculty   = new Property ("Faculty", 5, 240, 150, new double[]{20, 100, 300, 750, 925, 1100},6.5, nPlayerCount);

        Asset agno      = new Property ("Agno", 6, 260, 150, new double[]{22, 110, 330, 800, 975, 1150},6.5, nPlayerCount);
        Asset pericos   = new Property ("Pericos", 6, 260, 150, new double[]{22, 110, 330, 800, 975, 1150},7, nPlayerCount);

        Asset mutien    = new Property ("Mutien", 7, 300, 200, new double[]{26, 130, 390, 900, 1100, 1275},7, nPlayerCount);
        Asset salikneta = new Property ("Salikneta", 7, 320, 200, new double[]{28, 130, 450, 1000, 1200, 1400},8, nPlayerCount);

        //initializing railroads

        Asset vwalk     = new Railroad("Velasco Walk");
        Asset sjwalk    = new Railroad("SJ Walk");
        Asset warpzone  = new Railroad("Warpzone");

        //initializing utilities

        Asset wifi  = new Utility("Wi-Fi");
        Asset water = new Utility("Water");

        //initializing tax spaces

        IncomeTaxSpace incomeTax = new IncomeTaxSpace();
        LuxuryTaxSpace luxuryTax = new LuxuryTaxSpace();

        //initializing chance spaces

        ChanceSpace chanceA = new ChanceSpace();
        ChanceSpace chanceB = new ChanceSpace();
        ChanceSpace chanceC = new ChanceSpace();

        //initializing four corners

        NoEventSpace start = new NoEventSpace("CADS");
        NoEventSpace freeParking = new NoEventSpace("FREE PARKING");
        CommunityServiceSpace church = new CommunityServiceSpace();
        NoEventSpace sdfo = new NoEventSpace("SDFO");

        spaces.addAll(Arrays.asList(start, andrew, razon, connon, yuchengco, lshall, gokongwei, miguel,
                freeParking, stc, velasco, henry, bloemen, joseph, william, faculty,
                church, agno, pericos, mutien, salikneta, vwalk, sjwalk, warpzone,
                sdfo, wifi, water, incomeTax, luxuryTax, chanceA, chanceB, chanceC));
    }

    /**
     * Getter for the spaces in the board.
     * @return ArrayList of Spaces
     */
    public ArrayList<Space> getSpaces()
    {
        return spaces;
    }

    /**
     * Shuffles the board, keeping the four corner spaces intact.
     */
    public void shuffle()
    {
        ArrayList <Space> spaceTemp = new ArrayList<>();

        //temporarily store 4 corners to another ArrayList first
        for (int i = 0; i < 4; i++)
        {
            spaceTemp.add(spaces.remove((i * 8) - i));
        }

        Collections.shuffle(spaces);

        for (int i = 0; i < 4; i++)
        {
            spaces.add(i * 8, spaceTemp.remove(0));
        }

        for (int i = 0; i <= 31; i++)
        {
            spaces.get(i).setPosition(i);
        }
    }

    /**
     * Returns all spaces that match the given constant.
     * @param spaceType Type of space.
     * @return ArrayList of spaces that match the given type.
     */
    public ArrayList<Space> filterSpaceType(int spaceType)
    {
        ArrayList <Space> filteredSpaces = new ArrayList<>();

        switch (spaceType)
        {
            case Board.PROPERTY:
            {
                spaces.forEach(space -> {
                    if (space instanceof Property)
                    {
                        filteredSpaces.add(space);
                    }
                });
            } break;

            case Board.UTILITY:
            {
                spaces.forEach(space -> {
                    if (space instanceof Utility)
                    {
                        filteredSpaces.add(space);
                    }
                });
            } break;

            case Board.RAILROAD:
            {
                spaces.forEach(space -> {
                    if (space instanceof Railroad)
                    {
                        filteredSpaces.add(space);
                    }
                });
            } break;
        }

        return filteredSpaces;
    }

    /**
     * Returns the position of the nearest utility that the <code>player</code> can move to.
     * @param player Player to reference.
     * @return Index of utility in board.
     */
    public int getNearestUtility(Player player)
    {
        int i = (player.getPosition() + 1) % spaces.size() ;
        while (i != player.getPosition() - 1
                && !(spaces.get(i) instanceof Utility))
        {
            i = (i + 1) % spaces.size();
        }

        if (!(spaces.get(i) instanceof Utility))
        {
            return -1;
        }
        return i;
    }

    /**
     * Returns the position of the nearest railroad that the <code>player</code> can move to.
     * @param player Player to reference.
     * @return Index of railroad in board.
     */
    public int getNearestRailroad(Player player)
    {
        int i = (player.getPosition() + 1) % spaces.size() ;
        while (i != player.getPosition() - 1
                && !(spaces.get(i) instanceof Railroad))
        {
            i = (i + 1) % spaces.size();
        }

        if (!(spaces.get(i) instanceof Railroad))
        {
            return -1;
        }
        return i;
    }
}
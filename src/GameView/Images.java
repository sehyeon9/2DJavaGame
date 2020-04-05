package GameView;

import java.awt.image.BufferedImage;

public class Images {

    private BufferedImage player;
    private BufferedImage boss;
    private BufferedImage wall01;
    private BufferedImage wall02;
    private BufferedImage blackWall;
    private BufferedImage keyButton;
    private BufferedImage laserFaceDown;
    private BufferedImage laserFaceUp;
    private BufferedImage marbleSwitch;
    private BufferedImage marbleSwitchSpace;
    private BufferedImage treasureChest;
    private BufferedImage key;
    private BufferedImage unlockedChest;
    private BufferedImage floor;
    private BufferedImage wall03;
    private BufferedImage wall04;
    private BufferedImage bird;
    private BufferedImage statue;
    private BufferedImage portal;
    private BufferedImage leftHeart;
    private BufferedImage rightHeart;
    private BufferedImage jar;
    private BufferedImage silverSquareWall;
    private BufferedImage silverSquare;
    private BufferedImage voidSpace;
    private BufferedImage lockedDoor;
    
    private BufferedImage laserFacingLeft;
    private BufferedImage laserFacingRight;
    
    //images for non-dungeons
    private BufferedImage botLeftOfTree;
    private BufferedImage botRightOfTree;
    //these two dungeon entrance parts should be 40x40
    private BufferedImage bottomLeftOfDungeonEntrance;
    private BufferedImage bottomRightOfDungeonEntrance;
    private BufferedImage bush;
    private BufferedImage dirt;
    //20x40
    private BufferedImage doorToDungeon;
    private BufferedImage flower;
    private BufferedImage grass;
    private BufferedImage hole;
    private BufferedImage partRightAboveDoorToDungeon;
    private BufferedImage sand;
    private BufferedImage topLeftOfDungeonEntrance;
    private BufferedImage topLeftOfTree;
    private BufferedImage topPartOfDungeonEntrance;
    private BufferedImage topRightOfDungeonEntrance;
    private BufferedImage topRightOfTree;
    private BufferedImage wall05;
    //20x40
    private BufferedImage weirdStatue;
    
    /** specifically for map 6 (where the player first spawns when starting game) */
    private BufferedImage topLeftCorner;
    private BufferedImage topRightCorner;
    private BufferedImage botLeftCorner;
    private BufferedImage botRightCorner;
    private BufferedImage topWall;
    private BufferedImage leftWall;
    private BufferedImage rightWall;
    private BufferedImage bottomWall;
    //house should be 80x80
    private BufferedImage house;
    private BufferedImage yard;
    private BufferedImage fence;

    /**
     * The various forms of our player for animations
     */
    private BufferedImage playerStandingLeft;
    private BufferedImage playerStandingRight;
    private BufferedImage playerRunningLeft;
    private BufferedImage playerRunningRight;
    private BufferedImage playerRunningUpState1;
    private BufferedImage playerRunningUpState2;
    private BufferedImage playerRunningDownState1;
    private BufferedImage playerRunningDownState2;
    

    /** this one for the most part should be add-ons
     * HP Recovery amount:
     * red potion - one half heart
     * orange potion - one full heart
     * white potion - full recovery
     */
    private BufferedImage redPotion;
    private BufferedImage orangePotion;
    private BufferedImage whitePotion;

    /** TBD there is no Mana functionality as of yet
     * MP Recovery amount:
     * blue potion - 25
     * blue elixir - half of total
     * power elixir - full recovery
     */
    private BufferedImage bluePotion;
    private BufferedImage manaElixir;
    private BufferedImage powerElixir;

    /**
     * Weapon Damage output:
     * sabre - 30
     * soul singer - 65
     * royal katana - 88 (8 is a lucky number in Japan meaning prosperity)
     */
    private BufferedImage sabre;
    private BufferedImage soulSinger;
    private BufferedImage royalKatana;

    /** if we want armor, we need to change current implementation of health
     * Armor Damage reduction:
     * dragon robe - 20
     * bloody mail - 50
     * ancient battle suit - 80
     * genesis - 120
     */
    private BufferedImage dragonRobe;
    private BufferedImage bloodySuit;
    private BufferedImage ancientBattleMail;
    private BufferedImage genesis;
    
    //TODO i could refactor this and separate each into different methods resembling the img directory structure
    public Images() {
        player = ImageLoader.loadImage("./img/Player/LinkStandingLeft.png");
        //need to get a new keyButton image
        keyButton = ImageLoader.loadImage("./img/Dungeon/keybutton.png");
        leftHeart = ImageLoader.loadImage("./img/heartLeft.png");
        rightHeart = ImageLoader.loadImage("./img/heartRight.png");
        
        createPlayerSpawnMap();
        createPlayerForms();
        createWeapons();
        createArmors();
        createPotions();
        createWorld();
        createDungeonImages();
    }

    public BufferedImage getWall01() {
        return wall01;
    }

    //this should be the corner walls (but currently is not)
    public BufferedImage getWall02() {
        return wall02;
    }

    public BufferedImage getBlackWall() {
        return blackWall;
    }

    public BufferedImage getKeyButton() {
        return keyButton;
    }

    public BufferedImage getLaserFaceDown() {
        return laserFaceDown;
    }

    public BufferedImage getLaserFaceUp() {
        return laserFaceUp;
    }

    public BufferedImage getMarbleSwitch() {
        return marbleSwitch;
    }

    public BufferedImage getMarbleSwitchSpace() {
        return marbleSwitchSpace;
    }

    public BufferedImage getTreasureChest() {
        return treasureChest;
    }
    
    public BufferedImage getKey() {
        return key;
    }
    
    public BufferedImage getUnlockedChest() {
        return unlockedChest;
    }
    
    public BufferedImage getFloor() {
        return floor;
    }
    
    public BufferedImage getWall03() {
        return wall03;
    }
    
    public BufferedImage getWall04() {
        return wall04;
    }
    
    public BufferedImage getBird() {
        return bird;
    }
    
    public BufferedImage getStatue() {
        return statue;
    }
    
    public BufferedImage getPortal() {
        return portal;
    }
    
    public BufferedImage getLeftHeart() {
        return leftHeart;
    }
    
    public BufferedImage getRightHeart() {
        return rightHeart;
    }
    
    public BufferedImage getJar() {
        return jar;
    }
    
    public BufferedImage getSilverSquareWall() {
        return silverSquareWall;
    }
    
    public BufferedImage getSilverSquare() {
        return silverSquare;
    }
    
    public BufferedImage getVoidSpace() {
        return voidSpace;
    }
    
    public BufferedImage getLockedDoor() {
        return lockedDoor;
    }
    
    private void createPlayerSpawnMap() {
        topLeftCorner = ImageLoader.loadImage("./img/World/PlayerSpawn/topLeftCorner.png");
        topRightCorner = ImageLoader.loadImage("./img/World/PlayerSpawn/topRightCorner.png");
        botLeftCorner = ImageLoader.loadImage("./img/World/PlayerSpawn/botLeftCorner.png");
        botRightCorner = ImageLoader.loadImage("./img/World/PlayerSpawn/botRightCorner.png");
        topWall = ImageLoader.loadImage("./img/World/PlayerSpawn/topWall.png");
        leftWall = ImageLoader.loadImage("./img/World/PlayerSpawn/leftWall.png");
        rightWall = ImageLoader.loadImage("./img/World/PlayerSpawn/rightWall.png");
        bottomWall = ImageLoader.loadImage("./img/World/PlayerSpawn/bottomWall.png");
        house = ImageLoader.loadImage("./img/World/PlayerSpawn/house.png");
        yard = ImageLoader.loadImage("./img/World/PlayerSpawn/yard.png");
        fence = ImageLoader.loadImage("./img/World/PlayerSpawn/fence.png");
    }
    
    private void createDungeonImages() {
        boss = ImageLoader.loadImage("./img/Dungeon/boss.png");
        jar = ImageLoader.loadImage("./img/Dungeon/jar.png");
        voidSpace = ImageLoader.loadImage("./img/Dungeon/voidspace.png");
        treasureChest = ImageLoader.loadImage("./img/Dungeon/treasurechest.png");
        key = ImageLoader.loadImage("./img/Dungeon/key.png");
        unlockedChest = ImageLoader.loadImage("./img/Dungeon/unlockedChest.png");
        floor = ImageLoader.loadImage("./img/Dungeon/floor.png");
        bird = ImageLoader.loadImage("./img/Dungeon/bird.png");
        statue = ImageLoader.loadImage("./img/Dungeon/statue.png");
        createSquares();
        createMarbles();
        createWalls();
        createWallLasers();
        createDoors();
    }
    
    private void createSquares() {
        blackWall = ImageLoader.loadImage("./img/Dungeon/blackwall.png");
        silverSquareWall = ImageLoader.loadImage("./img/Dungeon/silversquarewall.png");
        silverSquare = ImageLoader.loadImage("./img/Dungeon/silversquarepressed.png");
    }
    
    private void createMarbles() {
        marbleSwitch = ImageLoader.loadImage("./img/Dungeon/marbleswitch.png");
        marbleSwitchSpace = ImageLoader.loadImage("./img/Dungeon/marbleswitchspace.png");
    }
    
    private void createWalls() {
        wall01 = ImageLoader.loadImage("./img/RegularBlueWall/wall01.png");
        wall02 = ImageLoader.loadImage("./img/RegularBlueWall/wall02.png");
        wall03 = ImageLoader.loadImage("./img/RegularBlueWall/wall03.png");
        wall04 = ImageLoader.loadImage("./img/RegularBlueWall/wall04.png");
    }
    
    private void createWallLasers() {
        laserFacingLeft = ImageLoader.loadImage("./img/WallLaser/LaserFaceLeft.png");
        laserFacingRight = ImageLoader.loadImage("./img/WallLaser/LaserFaceRight.png");
        laserFaceDown = ImageLoader.loadImage("./img/WallLaser/laserfacedown.png");
        laserFaceUp = ImageLoader.loadImage("./img/WallLaser/laserfaceup.png");
    }
    
    private void createDoors() {
        lockedDoor = ImageLoader.loadImage("./img/Dungeon/doortoboss.png");
        portal = ImageLoader.loadImage("./img/Dungeon/portal1.png");
    }
    
    private void createPlayerForms() {
        playerStandingLeft = ImageLoader.loadImage("./img/Player/LinkStandingLeft.png");
        playerStandingRight = ImageLoader.loadImage("./img/Player/LinkStandingRight.png");
        playerRunningLeft = ImageLoader.loadImage("./img/Player/LinkRunningLeft.png");
        playerRunningRight = ImageLoader.loadImage("./img/Player/LinkRunningRight.png");
        playerRunningUpState1 = ImageLoader.loadImage("./img/Player/LinkRunningUpState1.png");
        playerRunningUpState2 = ImageLoader.loadImage("./img/Player/LinkRunningUpState2.png");
        playerRunningDownState1 = ImageLoader.loadImage("./img/Player/LinkRunningDownState1.png");
        playerRunningDownState2 = ImageLoader.loadImage("./img/Player/LinkRunningDownState2.png");
    }
    
    private void createWeapons() {
        sabre = ImageLoader.loadImage("./img/Item/Weapon/sabre.png");
        soulSinger = ImageLoader.loadImage("./img/Item/Weapon/soulsinger.png");
        royalKatana = ImageLoader.loadImage("./img/Item/Weapon/royalkatana.png");
    }
    
    private void createArmors() {
        dragonRobe = ImageLoader.loadImage("./img/Item/Armor/dragonrobe.png");
        bloodySuit = ImageLoader.loadImage("./img/Item/Armor/bloodysuit.png");
        ancientBattleMail = ImageLoader.loadImage("./img/Item/Armor/ancientbattlemail.png");
        genesis = ImageLoader.loadImage("./img/Item/Armor/genesis.png");
    }
    
    private void createPotions() {
        redPotion = ImageLoader.loadImage("./img/Item/Potion/Health/redpotion.png");
        orangePotion = ImageLoader.loadImage("./img/Item/Potion/Health/orangepotion.png");
        whitePotion = ImageLoader.loadImage("./img/Item/Potion/Health/whitepotion.png");

        bluePotion = ImageLoader.loadImage("./img/Item/Potion/Mana/bluepotion.png");
        manaElixir = ImageLoader.loadImage("./img/Item/Potion/Mana/manaelixir.png");
        powerElixir = ImageLoader.loadImage("./img/Item/Potion/Mana/powerelixir.png");
    }
    
    private void createWorld() {
        botLeftOfTree = ImageLoader.loadImage("./img/World/botLeftOfTree.png");
        botRightOfTree = ImageLoader.loadImage("./img/World/botRightOfTree.png");
        //these two dungeon entrance parts should be 60x60
        bottomLeftOfDungeonEntrance = ImageLoader.loadImage("./img/World/bottomLeftOfDungeonEntrance.png");
        bottomRightOfDungeonEntrance = ImageLoader.loadImage("./img/World/bottomRightOfDungeonEntrance.png");
        bush = ImageLoader.loadImage("./img/World/bush.png");
        dirt = ImageLoader.loadImage("./img/World/dirt.png");
        //40x60
        doorToDungeon = ImageLoader.loadImage("./img/World/doorToDungeon.png");
        flower = ImageLoader.loadImage("./img/World/flower.png");
        grass = ImageLoader.loadImage("./img/World/grass.png");
        hole = ImageLoader.loadImage("./img/World/hole.png");
        partRightAboveDoorToDungeon = ImageLoader.loadImage("./img/World/PartRightAboveDoorToDungeon.png");
        sand = ImageLoader.loadImage("./img/World/sand.png");
        topLeftOfDungeonEntrance = ImageLoader.loadImage("./img/World/topLeftOfDungeonEntrance.png");
        topLeftOfTree = ImageLoader.loadImage("./img/World/topLeftOfTree.png");
        topPartOfDungeonEntrance = ImageLoader.loadImage("./img/World/topPartOfDungeonEntrance.png");
        topRightOfDungeonEntrance = ImageLoader.loadImage("./img/World/topRightOfDungeonEntrance.png");
        topRightOfTree = ImageLoader.loadImage("./img/World/topRightOfTree.png");
        wall05 = ImageLoader.loadImage("./img/World/wall05.png");
        weirdStatue = ImageLoader.loadImage("./img/World/weirdStatue.png");
    }

    public BufferedImage getPlayer() {
        return player;
    }
    
    public BufferedImage getRedPotion() {
        return redPotion;
    }
    
    public BufferedImage getOrangePotion() {
        return orangePotion;
    }
    
    public BufferedImage getWhitePotion() {
        return whitePotion;
    }
    
    public BufferedImage getBluePotion() {
        return bluePotion;
    }
    
    public BufferedImage getManaElixir() {
        return manaElixir;
    }
    
    public BufferedImage getPowerElixir() {
        return powerElixir;
    }
    
    public BufferedImage getSabre() {
        return sabre;
    }
    
    public BufferedImage getSoulSinger() {
        return soulSinger;
    }
    
    public BufferedImage getRoyalKatana() {
        return royalKatana;
    }
    
    public BufferedImage getDragonRobe() {
        return dragonRobe;
    }
    
    public BufferedImage getBloodySuit() {
        return bloodySuit;
    }
    
    public BufferedImage getAncientBattleMail() {
        return ancientBattleMail;
    }
    
    public BufferedImage getGenesis() {
        return genesis;
    }
    
    public BufferedImage getBoss() {
        return boss;
    }

    public BufferedImage getPlayerStandingLeft() {
        return playerStandingLeft;
    }

    public BufferedImage getPlayerStandingRight() {
        return playerStandingRight;
    }

    public BufferedImage getPlayerRunningLeft() {
        return playerRunningLeft;
    }

    public BufferedImage getPlayerRunningRight() {
        return playerRunningRight;
    }

    public BufferedImage getPlayerRunningUpState1() {
        return playerRunningUpState1;
    }

    public BufferedImage getPlayerRunningUpState2() {
        return playerRunningUpState2;
    }

    public BufferedImage getPlayerRunningDownState1() {
        return playerRunningDownState1;
    }

    public BufferedImage getPlayerRunningDownState2() {
        return playerRunningDownState2;
    }

    public BufferedImage getLaserFacingLeft() {
        return laserFacingLeft;
    }

    public BufferedImage getLaserFacingRight() {
        return laserFacingRight;
    }

    public BufferedImage getBotLeftOfTree() {
        return botLeftOfTree;
    }

    public BufferedImage getBotRightOfTree() {
        return botRightOfTree;
    }

    public BufferedImage getBottomLeftOfDungeonEntrance() {
        return bottomLeftOfDungeonEntrance;
    }

    public BufferedImage getBottomRightOfDungeonEntrance() {
        return bottomRightOfDungeonEntrance;
    }

    public BufferedImage getBush() {
        return bush;
    }

    public BufferedImage getDoorToDungeon() {
        return doorToDungeon;
    }

    public BufferedImage getFlower() {
        return flower;
    }

    public BufferedImage getGrass() {
        return grass;
    }

    public BufferedImage getHole() {
        return hole;
    }

    public BufferedImage getPartRightAboveDoorToDungeon() {
        return partRightAboveDoorToDungeon;
    }

    public BufferedImage getSand() {
        return sand;
    }

    public BufferedImage getTopLeftOfDungeonEntrance() {
        return topLeftOfDungeonEntrance;
    }

    public BufferedImage getTopLeftOfTree() {
        return topLeftOfTree;
    }

    public BufferedImage getTopPartOfDungeonEntrance() {
        return topPartOfDungeonEntrance;
    }

    public BufferedImage getTopRightOfDungeonEntrance() {
        return topRightOfDungeonEntrance;
    }

    public BufferedImage getTopRightOfTree() {
        return topRightOfTree;
    }

    public BufferedImage getWeirdStatue() {
        return weirdStatue;
    }

    public BufferedImage getDirt() {
        return dirt;
    }

    public BufferedImage getWall05() {
        return wall05;
    }

    public BufferedImage getTopLeftCorner() {
        return topLeftCorner;
    }

    public BufferedImage getTopRightCorner() {
        return topRightCorner;
    }

    public BufferedImage getBotLeftCorner() {
        return botLeftCorner;
    }

    public BufferedImage getBotRightCorner() {
        return botRightCorner;
    }

    public BufferedImage getTopWall() {
        return topWall;
    }

    public BufferedImage getLeftWall() {
        return leftWall;
    }

    public BufferedImage getRightWall() {
        return rightWall;
    }

    public BufferedImage getBottomWall() {
        return bottomWall;
    }

    public BufferedImage getHouse() {
        return house;
    }

    public BufferedImage getYard() {
        return yard;
    }

    public BufferedImage getFence() {
        return fence;
    }
}

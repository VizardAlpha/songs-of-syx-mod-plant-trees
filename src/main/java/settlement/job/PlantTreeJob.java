package settlement.job;

import game.GAME;
import init.resources.RESOURCE;
import init.sound.SOUND;
import init.sound.SoundSettlement;
import init.sprite.SPRITES;
import settlement.entity.humanoid.Humanoid;
import settlement.main.SETT;
import settlement.tilemap.floor.Floors;
import settlement.tilemap.terrain.Terrain;
import snake2d.SPRITE_RENDERER;
import snake2d.util.sprite.SPRITE;
import view.tool.PlacableMulti;

import static game.faction.FResources.RTYPE.CONSTRUCTION;
import static settlement.main.SETT.TERRAIN;

public class PlantTreeJob extends JobBuild {
    public final static CharSequence NAME = "Plant Tree";
    public final static CharSequence DESC = "Plants trees";

    private final PlacableMulti placer;

    private final int performTime;

    /**
     * There's some magic where on construction the job gets added into {@link Job#all}.
     * This will be accessible by the settlements {@link JOBS}. So it can be processed as any other job by the game.
     */
    public PlantTreeJob(SPRITE icon, int performTime, int minFertilityPercentage, RESOURCE resource, int resAmount) {
        super(resource, resAmount, false, NAME, DESC, icon);

        this.placer = new PlantTreePlacer(
                this,
                icon,
                minFertilityPercentage,
                resource,
                resAmount);

        this.performTime = performTime;
    }

    @Override
    void renderAbove(SPRITE_RENDERER r, int x, int y, int mask, int tx, int ty) {
        SPRITES.cons().ICO.tile.render(r, x, y);
    }

    /**
     * Plants the actual tree on a tile
     */
    @Override
    protected boolean construct(int tx, int ty) {
        GAME.player().res().inc(res, CONSTRUCTION ,resAmount);
        TERRAIN().TREES.SMALL.placeFixed(tx, ty);

        return false;
    }

    @Override
    protected SoundSettlement.Sound constructSound() {
        return SOUND.sett().action.dig;
    }

    @Override
    public PlacableMulti placer() {
        return placer;
    }

    @Override
    public Terrain.TerrainTile becomes(int tx, int ty) {
        return TERRAIN().TREES.SMALL;
    }
    @Override
    protected double constructionTime(Humanoid skill) {
        return performTime;
    }

    @Override
    protected CharSequence problem(int tx, int ty, boolean overwrite) {
        CharSequence problem = super.problem(tx, ty, overwrite);

        if (problem != null) {
            return problem;
        }

        Floors.Floor floor = SETT.FLOOR().getter.get(tx, ty);

        // only plant on grass
        if (floor != null && !floor.isGrass){
            return "Trees can only grow on soil";
        }

        return null;
    }
}

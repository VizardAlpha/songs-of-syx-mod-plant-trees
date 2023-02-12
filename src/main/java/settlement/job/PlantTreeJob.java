package settlement.job;

import game.GAME;
import init.resources.RESOURCE;
import init.sound.SOUND;
import init.sound.SoundSettlement;
import init.sprite.SPRITES;
import settlement.entity.humanoid.Humanoid;
import settlement.tilemap.Terrain;
import snake2d.SPRITE_RENDERER;
import snake2d.util.sprite.SPRITE;
import view.tool.PlacableMulti;

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

    @Override
    protected boolean construct(int tx, int ty) {
        GAME.player().res().outConstruction.inc(res, resAmount);
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
}

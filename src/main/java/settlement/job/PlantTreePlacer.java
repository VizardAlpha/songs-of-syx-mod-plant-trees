package settlement.job;

import com.github.argon.sos.planttree.log.Loggers;
import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.util.FertilityUtil;
import init.resources.RESOURCE;
import settlement.main.SETT;
import settlement.tilemap.terrain.*;
import settlement.tilemap.terrain.TNothing;
import settlement.tilemap.terrain.Terrain;
import snake2d.util.datatypes.AREA;
import snake2d.util.sprite.SPRITE;
import util.gui.misc.GBox;
import view.main.VIEW;
import view.tool.PLACER_TYPE;
import view.tool.PlacableMulti;

import static settlement.main.SETT.JOBS;
import static settlement.main.SETT.*;

/**
 * Takes care of placing {@link PlantTreeJob}s in a selected area.
 */
public class PlantTreePlacer extends PlacableMulti {

    /**
     * To plant trees on
     */
    private final int minFertilityPercentage;

    private final PlantTreeJob plantTreeJob;

    private final static Logger log = Loggers.getLogger(PlantTreePlacer.class);

    private final RESOURCE resource;

    /**
     * For showing total planting resource cost
     */
    private final int resAmount;

    public PlantTreePlacer(PlantTreeJob plantTreeJob, SPRITE icon, int minFertilityPercentage, RESOURCE resource, int resAmount) {
        super(PlantTreeJob.NAME,
             "Trees can only be planted on tiles with minimal " + minFertilityPercentage + "% fertility",
                icon);
        this.minFertilityPercentage = minFertilityPercentage;
        this.plantTreeJob = plantTreeJob;
        this.resource = resource;
        this.resAmount = resAmount;
    }

    /**
     * For a given tile in the area selection
     *
     * @return null or error message
     */
    @Override
    public CharSequence isPlacable(int tx, int ty, AREA area, PLACER_TYPE type) {
        // is plantable on terrain type?
        Terrain.TerrainTile terrain = SETT.TERRAIN().get(tx, ty);
        log.trace("Terrain: %s(%s)", terrain.getClass().getSimpleName(), terrain.name());

        //check job problems
        CharSequence msg = plantTreeJob.problem(tx, ty, Job.overwrite);
        if (msg != null) {
            return msg;
        }

        log.trace("Terrain: %s", terrain.getClass().getSimpleName());

        // check terrain
        if (!(terrain instanceof TNothing)
            && !(terrain instanceof TBush)
            && !(terrain instanceof TGrowable)
            && !(terrain instanceof TRock)
            && !(terrain instanceof TMushroom)
            && !(terrain instanceof TFlower)
            && !(terrain instanceof TDecor)
        ) {
            return "No terrain for planting a tree.";
        }

        // tile has minimal fertility?
        int fertilityPercentage = (int) (GROUND().MAP.get(tx, ty).farm * 100);
        log.trace("Fertility (%d,%d): %s%%", tx, ty, fertilityPercentage);
        if (fertilityPercentage < minFertilityPercentage) {
            return "Fertility " + fertilityPercentage + "% is lower than the required " + minFertilityPercentage + "%.";
        }

        return null;
    }

    /**
     * Shows an info box under the selection area
     */
    @Override
    public void placeInfo(GBox b, int okTiles, AREA area) {
        super.placeInfo(b, okTiles, area);

        if (okTiles > 0) {
            int fertilityAvgArea = (int) (FertilityUtil.calcAvg(area) * 100);
            VIEW.hoverBox().add(VIEW.hoverBox().text()
                .add("Fertility: ").add(fertilityAvgArea).add("% ")
                .add("Trees: ").add(okTiles).add(" ")
                .add(resource.name.toString()).add(": ").add((long) okTiles * resAmount));
        }
    }

    /**
     * Places a {@link PlantTreeJob} on a given tile
     */
    @Override
    public void place(int tx, int ty, AREA area, PLACER_TYPE type) {
        if (!IN_BOUNDS(tx, ty)) {
            return;
        }

        int jobPos = tx + ty * TWIDTH;
        Job presentJob = JOBS().getter.get(jobPos);

        // there's already a plant tree job?
        if (presentJob == plantTreeJob) {
            if (!JOBS().planMode.is() && JOBS().state.is(tx, ty, StateManager.State.DORMANT)) {
                JOBS().state.set(StateManager.State.RESERVABLE, JOBS().getter.get(jobPos));
            }

            return;
        }

        // cancel other jobs on the same tile
        if (presentJob != null) {
            presentJob.cancel(tx, ty);
            PlacerDelete.place(tx, ty);
        }

        plantTreeJob.init(tx, ty);
        JOBS().set(plantTreeJob.index, tx, ty);

        if (!JOBS().planMode.is()) {
            JOBS().state.set(StateManager.State.RESERVABLE, JOBS().getter.get(jobPos));
        }else {
            JOBS().state.set(StateManager.State.DORMANT, JOBS().getter.get(jobPos));
        }
    }
}

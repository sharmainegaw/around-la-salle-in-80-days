package alsid.model.chance;

import alsid.model.asset.Asset;

/**
 * Interface for objects whose effects apply to Assets.
 */
public interface AssetApplicable {

    /**
     * Applys the effect of this object to the given asset.
     * @param asset Asset to affect.
     * @return String message of action/event.
     */
    String useEffect(Asset asset);

}

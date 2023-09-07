package mekanism.api.reactor;


public interface IReactorBlock {
    boolean isFrame();

    void setReactor(IFusionReactor reactor);

    IFusionReactor getReactor();

}

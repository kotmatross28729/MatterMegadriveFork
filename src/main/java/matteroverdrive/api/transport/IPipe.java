package matteroverdrive.api.transport;

import java.util.List;

public interface IPipe<T extends IPipeNetwork> {
    T getNetwork();

    void setNetwork(T network);

    void onNeighborBlockChange();

    List<IPipe<T>> getConnections();
}

package matteroverdrive.machines;

public abstract class MachineComponentAbstract<T extends MOTileEntityMachine> implements IMachineComponent<T> {
    protected final T machine;

    public MachineComponentAbstract(T machine) {
        this.machine = machine;
    }

    public T getMachine() {
        return machine;
    }
}

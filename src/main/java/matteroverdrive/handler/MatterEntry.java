package matteroverdrive.handler;

import matteroverdrive.api.matter.IMatterEntry;

import java.io.Serializable;

public class MatterEntry implements Serializable, IMatterEntry {
    private byte type;
    private int matter;
    private String name;
    private boolean calculated;

    public MatterEntry(String entry, int matter, byte type) {
        this.name = entry;
        this.type = type;
        this.matter = matter;
    }

    @Override
    public int getMatter() {
        return matter;
    }

    @Override
    public void setMatter(int matter) {
        this.matter = matter;
    }

    public boolean isBlock() {
        return type == 2;
    }

    public boolean isItem() {
        return this.type == 1;
    }

    public byte getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    @Override
    public boolean getCalculated() {
        return calculated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof MatterEntry)) return false;
        MatterEntry entry = (MatterEntry) o;
        return entry.name.equals(name) && entry.matter == matter && entry.calculated == calculated && entry.type == type;
    }

}

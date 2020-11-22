package com.connormahaffey.GiantTrees;

public class FileDbTreesWrapper {

    private final int id;
    private final String owner;
    private final int x;
    private final int y;
    private final int z;

    public FileDbTreesWrapper(final int id, final String owner, final int x, final int y, final int z) {
        this.id = id;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

}

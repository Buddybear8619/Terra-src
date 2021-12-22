package com.dfsek.terra.api.util.vector;

import com.dfsek.terra.api.util.Rotation;


/**
 * oh yeah
 */
public class Vector2Int {
    private static final Vector2Int ZERO = new Vector2Int(0, 0);
    private static final Vector2Int UNIT = new Vector2Int(0, 1);
    private final int x;
    private final int z;
    
    protected Vector2Int(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    public static Vector2Int zero() {
        return ZERO;
    }
    
    public static Vector2Int unit() {
        return UNIT;
    }
    
    public static Vector2Int of(int x, int z) {
        return new Vector2Int(x, z);
    }
    
    public int getX() {
        return x;
    }
    
    public int getZ() {
        return z;
    }
    
    public Vector3Int toVector3(int y) {
        return new Vector3Int(x, y, z);
    }
    
    public Mutable mutable() {
        return new Mutable(x, z);
    }
    
    public Vector2Int rotate(Rotation rotation) {
        return switch(rotation) {
            case CW_90 -> of(z, -x);
            case CCW_90 -> of(-z, x);
            case CW_180 -> of(-x, -z);
            default -> this;
        };
    }
    
    public static class Mutable {
        private int x, z;
        
        protected Mutable(int x, int z) {
            this.x = x;
            this.z = z;
        }
        
        public int getZ() {
            return z;
        }
        
        public void setZ(int z) {
            this.z = z;
        }
        
        public int getX() {
            return x;
        }
        
        public void setX(int x) {
            this.x = x;
        }
        
        public Vector2Int immutable() {
            return new Vector2Int(x, z);
        }
    }
}
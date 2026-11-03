package team.creative.littletiles.client.render.cache.buffer;

import java.nio.ByteBuffer;

public interface ChunkBufferDownloader {
    
    public ByteBuffer downloaded();
    
    public boolean hasFacingSupport();
    
    public ByteBuffer downloaded(int facing);
    
    public static class SimpleChunkBufferDownloader implements ChunkBufferDownloader {
        
        public ByteBuffer buffer;
        
        public ByteBuffer downloaded() {
            return buffer;
        }
        
        public boolean hasFacingSupport() {
            return false;
        }
        
        public ByteBuffer downloaded(int facing) {
            throw new UnsupportedOperationException();
        }
        
    }
    
}

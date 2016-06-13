package player;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mrlukashem on 13.06.16.
 */
public interface EternityExtractor {
    boolean jumpToNextSample();
    void seekTo(long timeUs);
    void selectTrack(int index);
    void setDataSource(String path) throws IOException;
    int readSampleData(ByteBuffer byteBuffer, int offset);
    int readNextSample(ByteBuffer byteBuffer);
}

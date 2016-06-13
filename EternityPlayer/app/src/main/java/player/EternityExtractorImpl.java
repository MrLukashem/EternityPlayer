package player;

import android.media.MediaExtractor;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mrlukashem on 13.06.16.
 */
public class EternityExtractorImpl implements EternityExtractor {
    protected EternityExtractorImpl() {
        mExtractor = new MediaExtractor();
    }

    protected MediaExtractor mExtractor;

    protected ByteBuffer mDataBuffer;

    protected int mDataBufferSize = 0;

    protected int mCurrentOffset = 0;

    protected void feedDataBuffer(int capacity) {

    }

    public EternityExtractor newInstance() {
        return new EternityExtractorImpl();
    }

    public EternityExtractor newInstance(String path) throws IOException {
        EternityExtractor extractor = new EternityExtractorImpl();
        extractor.setDataSource(path);

        return extractor;
    }

    @Override
    public boolean jumpToNextSample() {
        return false;
    }

    @Override
    public void seekTo(long timeUs) {
        mExtractor.seekTo(timeUs, MediaExtractor.SEEK_TO_NEXT_SYNC);
    }

    @Override
    public void selectTrack(int index) {
        mExtractor.selectTrack(index);
    }

    @Override
    public void setDataSource(String path) throws IOException {
        mExtractor.setDataSource(path);
    }

    @Override
    public int readSampleData(ByteBuffer byteBuffer, int offset) {
        return mExtractor.readSampleData(byteBuffer, offset);
    }

    @Override
    public int readNextSample(ByteBuffer byteBuffer) {
        int inputBufferSize = byteBuffer.array().length;
        if (mDataBufferSize - mCurrentOffset >= inputBufferSize) {
            byteBuffer.put(mDataBuffer.array(), mCurrentOffset, inputBufferSize);
            feedDataBuffer(inputBufferSize);
        } else {
            int bytesReads = mExtractor.readSampleData(byteBuffer, mCurrentOffset);
        }

        return 0;
    }
}

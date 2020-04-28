package lejos.internal.io;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a wrapper for NativeFile, which operates always on the same NativeFile-Object
 */
public class NativeFileManager {
    private static final List<NativeFile> files = new LinkedList<>();
    private final NativeFile nf;

    /**
     * Find a NativeFile object and open the associated file/device
     * for native access, if the Object does not exist, create it.
     *
     * @param fname the name of the file to open
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws FileNotFoundException
     */
    public NativeFileManager(String fname, int flags, int mode) throws FileNotFoundException {
        this(fname);
        if(!nf.isOpened()) {
            open(flags, mode);
        }
    }

    public NativeFileManager(String fname) {
        synchronized (files) {
            NativeFile[] matchingFiles = files
                    .stream().filter(d -> d.getName().equals(fname))
                    .toArray(NativeFile[]::new);
            if (matchingFiles.length > 1) {
                throw new IllegalStateException("More than one device matches this name," +
                        " this is a programming error and should not happen");
            } else if (matchingFiles.length == 1) {
                nf = matchingFiles[0];
            } else {
                nf = new NativeFile(fname);
                files.add(nf);
            }
        }
    }

        /**
     * Open the file
     * for native access.
     *
     * @param flags Linux style file access flags
     * @param mode  Linux style file access mode
     * @throws FileNotFoundException
     */
    public void open(int flags, int mode) throws FileNotFoundException {
        nf.open(nf.getName(), flags, mode);
    }

        /**
     * Attempt to read the requested number of bytes from the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int read(byte[] buf, int len) {
        return nf.read(buf, len);
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset the offset within buf to take data from for the write
     * @param len    number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int write(byte[] buf, int offset, int len) {
        return nf.write(buf, offset, len);
    }

    /**
     * Attempt to read the requested number of byte from the associated file.
     *
     * @param buf    location to store the read bytes
     * @param offset offset with buf to start storing the read bytes
     * @param len    number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int read(byte[] buf, int offset, int len) {
        return nf.read(buf, offset, len);
    }

    /**
     * Attempt to write the requested number of bytes to the associated file.
     *
     * @param buf location to store the read bytes
     * @param len number of bytes to attempt to read
     * @return number of bytes read or -1 if there is an error
     */
    public int write(byte[] buf, int len) {
        return nf.write(buf, len);
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf byte array containing the ioctl parameters if any
     * @return Linux style ioctl return
     */
    public int ioctl(int req, byte[] buf) {
        return nf.ioctl(req, buf);
    }

    /**
     * Perform a Linux style ioctl operation on the associated file.
     *
     * @param req ioctl operation to be performed
     * @param buf pointer to ioctl parameters
     * @return Linux style ioctl return
     */
    public int ioctl(int req, Pointer buf) {
        return nf.ioctl(req, buf);
    }

    /**
     * Close the associated file
     *
     * @return Linux style return
     */
    public int close() {
        return nf.close();
    }

    /**
     * Map a portion of the associated file into memory and return a pointer
     * that can be used to access that memory.
     *
     * @param len   size of the region to map
     * @param prot  protection for the memory region
     * @param flags Linux mmap flags
     * @param off   offset within the file for the start of the region
     * @return a pointer that can be used to access the mapped data
     */
    public Pointer mmap(long len, int prot, int flags, long off) {
        return nf.mmap(len, prot, flags, off);
    }

}

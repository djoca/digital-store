package net.j33r.digitalstore.domain.store;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The {@link FileRepository} interface defines the methods to read a file from
 * a repository.
 *
 * @author joses
 *
 */
public interface FileRepository {

    /**
     * Read a file and send it to an OutputStream.
     * 
     * @param out
     *            the OutputStream where the file will be sent.
     * @param filename
     *            the file name
     * @throws IOException
     */
    public void read(OutputStream out, String filename) throws IOException;
}

package net.j33r.digitalstore.infra.file.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import net.j33r.digitalstore.domain.store.FileRepository;

/**
 * The {@link ClassLoaderFileRepository} is a {@link FileRepository} that retrieve
 * files from the class loader.
 *
 * @author joses
 *
 */
@Component
public class ClassLoaderFileRepository implements FileRepository {

    @Override
    public void read(final OutputStream out, final String filename) throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("product-files/" + filename)) {
            IOUtils.copyLarge(inputStream, out);
        }
    }

}

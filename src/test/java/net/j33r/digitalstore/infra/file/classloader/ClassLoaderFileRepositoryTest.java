package net.j33r.digitalstore.infra.file.classloader;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

public class ClassLoaderFileRepositoryTest {

    @Test
    public void loadFile() throws Exception {
        final ClassLoaderFileRepository repository = new ClassLoaderFileRepository();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        repository.read(out, "atlas.jpg");
        Assert.assertEquals(282588, out.size());
    }

    @Test
    public void loadUnexistentFile() throws Exception {
        try {
            final ClassLoaderFileRepository repository = new ClassLoaderFileRepository();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            repository.read(out, "no_file.jpg");
            Assert.fail("Should throw IOException");
        } catch (final NullPointerException e) {
            // Expected exception. Nothing to do here
        }
    }
}

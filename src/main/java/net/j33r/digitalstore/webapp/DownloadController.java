package net.j33r.digitalstore.webapp;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import net.j33r.digitalstore.domain.DigitalStoreApplicationService;
import net.j33r.digitalstore.domain.store.Product;

/**
 * Controller class used for downloading product files.
 *
 * @author joses
 */
@Controller
@AllArgsConstructor
public class DownloadController {

    private final DigitalStoreApplicationService applicationService;

    @GetMapping("/download/{productId}")
    public void download(final HttpServletResponse response, @PathVariable final Long productId) throws IOException {
        final Product product = applicationService.retrieveProduct(productId);
        response.setHeader("Content-length", "" + product.getFileSize());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + product.getFileContent() + "\"");

        final OutputStream out = response.getOutputStream();
        applicationService.download(out, productId);
    }
}

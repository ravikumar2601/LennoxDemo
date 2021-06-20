package assertion.partsAndSupplies;

import assertion.AbstractAssertion;
import page.partsAndSupplies.CompressorsPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CompressorsPageAssertion extends AbstractAssertion<CompressorsPage> {

    public CompressorsPageAssertion assertAtCompressorPage(List<String> description) {
        assertThat(basePage.getDescription()).as("Verify that user at compressor Page ").isEqualTo(description);
        return this;
    }

    public CompressorsPageAssertion assertIfProductDetailsShowsCorrectly(List<String> expectedDetails) {
        assertThat(basePage.getProductDetailsPageValues()).as("Verify that Collect all the product details which are highlighted and compare it with the details  ").isEqualTo(expectedDetails);
        return this;
    }
}
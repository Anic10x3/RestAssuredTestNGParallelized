package utils;

import com.aventstack.extentreports.Status;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.testng.Reporter;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ExtentRestAssuredFilter implements Filter{

    private static final Set<String> SENSITIVE_HEADERS =
            new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    static {
        SENSITIVE_HEADERS.addAll(Arrays.asList(
                "x-api-key",
                "authorization",
                "cookie",
                "set-cookie"
        ));
    }

    private static String maskValue(String value) {
        if (value == null || value.isEmpty()) return "********";
        // show only last 4 chars
        int keep = Math.min(4, value.length());
        String tail = value.substring(value.length() - keep);
        return "********" + tail;
    }
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        StringBuilder log = new StringBuilder();
        log.append("<b>Request:</b><br>")
                .append(requestSpec.getMethod()).append(" ").append(requestSpec.getURI()).append("<br>");
        if (requestSpec.getHeaders() != null) {
            String filterHeaders = requestSpec.getHeaders().asList().stream().map(h -> {
                String name = h.getName();
                String val = h.getValue();
                if(SENSITIVE_HEADERS.contains(name)) {
                    return name + ": " + maskValue(val); // Mask API key
                } else {
                    return name + ": " + val;
                }
            }).collect(Collectors.joining((", ")));
            log.append("<b>Headers:</b> ").append(filterHeaders).append("<br>");
        }
        if (requestSpec.getBody() != null) {
            log.append("<b>Body:</b> ").append((String) requestSpec.getBody()).append("<br>");
        }

        log.append("<br><b>Response:</b><br>")
                .append("<b>Status Code:</b> ").append(response.getStatusCode()).append("<br>")
                .append("<b>Body:</b> ").append(response.getBody().asPrettyString()).append("<br>");

        String currentTestName = Reporter.getCurrentTestResult().getName();

       /* if (ExtentTestManager.getTest(currentTestName) != null) {
            ExtentTestManager.getTest(currentTestName).log(Status.INFO, log.toString());
        }*/

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.INFO, log.toString());
        }

        return response;
    }
}

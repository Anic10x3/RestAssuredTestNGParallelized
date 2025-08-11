package utils;

import com.aventstack.extentreports.Status;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.testng.Reporter;

public class ExtentRestAssuredFilter implements Filter{
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        StringBuilder log = new StringBuilder();
        log.append("<b>Request:</b><br>")
                .append(requestSpec.getMethod()).append(" ").append(requestSpec.getURI()).append("<br>");
        if (requestSpec.getHeaders() != null) {
            log.append("<b>Headers:</b> ").append(requestSpec.getHeaders()).append("<br>");
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

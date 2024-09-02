package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

//@CucumberOptions(
//        dryRun = true,
//        features = "src/test/resources/features",
//        glue = {"com.github.manjunathprabhakar.stepdefs"},
//        plugin = {"summary","pretty","json:target/cucumber.json"},
//        tags = "@mf"
//)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void bfc(){
        System.out.println("TestNGTestRunner.bfc");
    }

}

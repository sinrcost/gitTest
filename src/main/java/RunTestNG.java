import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxiufang on 2020/4/17 16:44
 */
public class RunTestNG {

    public static void main(String[] args) throws InterruptedException {

        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<String>();
        suites.add("D:\\Program Files\\IDEA\\workSpace\\AIapiTest\\testng.xml");
        testNG.setTestSuites(suites);
        testNG.run();



    }



}

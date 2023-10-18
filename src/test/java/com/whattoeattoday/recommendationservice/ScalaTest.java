package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.scala.IntegrateScala;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class ScalaTest {
    @Test
    public void TestScalaDemo() {
        IntegrateScala integrateScala = new IntegrateScala();
        integrateScala.integrateScala();
    }
}

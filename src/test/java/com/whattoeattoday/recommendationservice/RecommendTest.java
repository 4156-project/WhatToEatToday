package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnItemRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnSimilarUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.service.impl.GetRecommendationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/28/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecommendTest {

    @InjectMocks
    public GetRecommendationServiceImpl getRecommendationServiceImpl;

    @Value("${dataproc.project-id}")
    private String projectId;

    @Value("${dataproc.region}")
    private String region;

    @Value("${dataproc.cluster-name}")
    private String clusterName;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test01RecommendOnUser() {
        GetRecommendationOnUserRequest request = new GetRecommendationOnUserRequest();
        request.setUserId("1");
        request.setCategoryName("movies");
        request.setFieldNameList(new ArrayList<String>(){{add("genre"); add("rating");}});
        request.setRankTopSize(10);

        // mock static method in GetRecommendationServiceImpl.class
        MockedStatic<GetRecommendationServiceImpl> getRecommendationServiceMockedStatic = Mockito.mockStatic(GetRecommendationServiceImpl.class);
        getRecommendationServiceMockedStatic.when(()-> GetRecommendationServiceImpl
                .callDataProc(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(new ArrayList<String>(){{add("1");add("2");add("3");}});

        // mock return of database
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("item_id", "1");
        list.add(map);
        Mockito.when(jdbcTemplate.queryForList(Mockito.any())).thenReturn(list);

        BaseResponse<List<String>> recommendationOnUser = getRecommendationServiceImpl.getRecommendationOnUser(request);
        Assert.assertTrue(recommendationOnUser.isSuccess());
    }

    @Test
    public void test02RecommendOnItem() {
        GetRecommendationOnItemRequest request = new GetRecommendationOnItemRequest();
        request.setTargetId("1");
        request.setCategoryName("movies");
        request.setFieldNameList(new ArrayList<String>(){{add("genre"); add("rating");}});
        request.setRankTopSize(10);

        // mock return of database
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("item_id", "1");
        list.add(map);
        Mockito.when(jdbcTemplate.queryForList(Mockito.any())).thenReturn(list);

        BaseResponse<List<String>> recommendationOnItem = getRecommendationServiceImpl.getRecommendationOnItem(request);
        Assert.assertTrue(recommendationOnItem.isSuccess());
    }
}

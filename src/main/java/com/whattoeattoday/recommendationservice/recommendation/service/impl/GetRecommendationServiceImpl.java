package com.whattoeattoday.recommendationservice.recommendation.service.impl;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.dataproc.v1.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnSimilarUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnItemRequest;
import com.whattoeattoday.recommendationservice.recommendation.service.GetRecommendationService;
import com.whattoeattoday.recommendationservice.user.model.User;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;
import com.whattoeattoday.recommendationservice.user.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@Slf4j
@ConfigurationProperties(prefix = "dataproc")
@Service
public class GetRecommendationServiceImpl implements GetRecommendationService {

    @Value("${dataproc.project-id}")
    private String projectId;

    @Value("${dataproc.region}")
    private String region;

    @Value("${dataproc.cluster-name}")
    private String clusterName;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserService userService;

    @Resource
    private TableService tableService;

    @Override
    public BaseResponse<List<String>> getRecommendationOnUser(GetRecommendationOnUserRequest request) {

        String mainClass = "com.whattoeattoday.RecommendOnUser";
        String jarFileUris = "gs://4156-recommend-job/Recommend-1.0-SNAPSHOT.jar";

        PageInfo pageInfo = PageInfo.builder()
                .pageNo(1)
                .pageSize(100)
                .build();

        StringBuilder querySql = new StringBuilder(String.format("SELECT item_id FROM collection "));
        querySql.append(String.format("WHERE user_id = '%s' AND category_name = '%s' ", request.getUserId(), request.getCategoryName()));
        int offset = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
        querySql.append(String.format("LIMIT %s offset %s;", pageInfo.getPageSize(), offset));
        List<Map<String, Object>> res;
        res = jdbcTemplate.queryForList(querySql.toString());
        StringBuilder itemIds = new StringBuilder();
        for (Map<String, Object> map : res) {
            itemIds.append(String.valueOf(map.get("item_id")));
            itemIds.append(",");
        }
        itemIds.deleteCharAt(itemIds.length()-1);
        String itemIdsStr = itemIds.toString();

        String tableName = request.getCategoryName();
        List<String> fieldNameArr = request.getFieldNameList();
        StringBuilder fieldNameSb = new StringBuilder();
        for (String fieldName : fieldNameArr) {
            fieldNameSb.append(fieldName);
            fieldNameSb.append(",");
        }
        fieldNameSb.deleteCharAt(fieldNameSb.length()-1);

        String[] argsArr = new String[]{
            itemIdsStr,
            tableName,
            fieldNameSb.toString(),
            String.valueOf(request.getRankTopSize())};

        List<String> resultList = callDataProc(region, projectId, clusterName, argsArr, mainClass, jarFileUris);
        return BaseResponse.with(Status.SUCCESS, resultList);
    }

    @Override
    public BaseResponse<List<String>> getRecommendationOnItem(GetRecommendationOnItemRequest request) {

        String mainClass = "com.whattoeattoday.RecommendOnItem";
        String jarFileUris = "gs://4156-recommend-job/Recommend-1.0-SNAPSHOT.jar";

        String tableName = request.getCategoryName();
        List<String> fieldNameArr = request.getFieldNameList();
        StringBuilder fieldNameSb = new StringBuilder();
        for (String fieldName : fieldNameArr) {
            fieldNameSb.append(fieldName);
            fieldNameSb.append(",");
        }
        fieldNameSb.deleteCharAt(fieldNameSb.length()-1);

        String[] argsArr = new String[]{
                tableName,
                fieldNameSb.toString(),
                request.getTargetId(),
                String.valueOf(request.getRankTopSize())};

        List<String> resultList = callDataProc(region, projectId, clusterName, argsArr, mainClass, jarFileUris);
        return BaseResponse.with(Status.SUCCESS, resultList);
    }

    @Override
    public BaseResponse<List<String>> recommendOnSimilarUser(GetRecommendationOnSimilarUserRequest request) {
        UserVerifyRequest userVerifyRequest = new UserVerifyRequest();
        userVerifyRequest.setUsername(request.getUsername());
        userVerifyRequest.setPassword(request.getPassword());
        userVerifyRequest.setCategory(request.getCategory());
        BaseResponse response = userService.userVerify(userVerifyRequest);
        if (!response.getCode().equals(Status.SUCCESS)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Invalid Input!");
        }
        User user = userService.getUserByUsername(request.getUsername());
        Set<Long> baseCollection = user.getCollection();
        // get all user's collections
        QueryRowRequest queryRowRequest = QueryRowRequest.builder().build();
        queryRowRequest.setTableName("user");
        queryRowRequest.setConditionField("category");
        queryRowRequest.setConditionValue("food");
        queryRowRequest.setFieldNames(new ArrayList<>(Arrays.asList("username")));
        queryRowRequest.setPageInfo(PageInfo.builder().pageNo(1).pageSize(100).build());
        PageInfo queryResponse = tableService.query(queryRowRequest);
        List<String> resultList = new ArrayList<>();
        for (Map<String, Object> oneUser : queryResponse.getPageData()) {
            String username = String.valueOf(oneUser.get("username"));
            User curUser = userService.getUserByUsername(username);
            Set<Long> curCollection = curUser.getCollection();
            double similarScore = getJaccardScore(baseCollection, curCollection);
            if (similarScore > 0.7) {
                Set<Long> difference = new HashSet<>(curCollection);
                difference.removeAll(baseCollection);
                for (Long item : difference) {
                    if (resultList.size() >= request.getRankTopSize()) {
                        break;
                    }
                    resultList.add(Long.toString(item));
                }
            }
        }
        return BaseResponse.with(Status.SUCCESS, resultList);
    }

    /**
     * calculate Jaccard similarity = |set1 ∩ set2|/|set1 ∪ set2|
     * @param set1
     * @param set2
     * @return
     */
    private double getJaccardScore(Set<Long> set1, Set<Long> set2) {
        Set<Long> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Long> union = new HashSet<>(set1);
        union.addAll(set2);
        double jaccardScore = (double) intersection.size() / union.size();
        return jaccardScore;
    }

    public static List<String> callDataProc(String region, String projectId, String clusterName, String[] argsArr, String mainClass, String jarFileUris) {
        // Configure the settings for the job controller client.
        String myEndpoint = String.format("%s-dataproc.googleapis.com:443", region);
        JobControllerSettings jobControllerSettings =
                null;
        try {
            jobControllerSettings = JobControllerSettings.newBuilder().setEndpoint(myEndpoint).build();
        } catch (IOException e) {
            return null;
        }

        List<String> resultList = null;

        // Create a job controller client with the configured settings. Using a try-with-resources
        // closes the client,
        // but this can also be done manually with the .close() method.
        try (JobControllerClient jobControllerClient =
                     JobControllerClient.create(jobControllerSettings)) {

            // Configure cluster placement for the job.
            JobPlacement jobPlacement = JobPlacement.newBuilder().setClusterName(clusterName).build();

            List<String> args = Arrays.asList(argsArr);
            // Configure Spark job settings.
            SparkJob sparkJob =
                    SparkJob.newBuilder()
                            .setMainClass(mainClass)
                            .addJarFileUris(jarFileUris)
                            .addAllArgs(args)
                            .build();

            Job job = Job.newBuilder().setPlacement(jobPlacement).setSparkJob(sparkJob).build();

            // Submit an asynchronous request to execute the job.
            OperationFuture<Job, JobMetadata> submitJobAsOperationAsyncRequest =
                    jobControllerClient.submitJobAsOperationAsync(projectId, region, job);

            Job response = submitJobAsOperationAsyncRequest.get();

            // Print output from Google Cloud Storage.
            Matcher matches =
                    Pattern.compile("gs://(.*?)/(.*)").matcher(response.getDriverOutputResourceUri());
            matches.matches();

            Storage storage = StorageOptions.getDefaultInstance().getService();
            Blob blob = storage.get(matches.group(1), String.format("%s.000000000", matches.group(2)));

            String dataprocLog = new String(blob.getContent());
            log.info("Dataproc job finished successfully: {}", dataprocLog);

            String[] dataprocLogLines = dataprocLog.split("\\r?\\n");
            String resultStr = dataprocLogLines[dataprocLogLines.length-1];
            String[] resultArr = resultStr.split(",");
            resultList = Arrays.asList(resultArr);
            return resultList;
        } catch (Exception ignored) {
            return null;
        }
    }
}

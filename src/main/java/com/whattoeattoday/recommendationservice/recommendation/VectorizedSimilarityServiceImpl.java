package com.whattoeattoday.recommendationservice.recommendation;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.dataproc.v1.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@Slf4j
@ConfigurationProperties(prefix = "dataproc")
@Service
public class VectorizedSimilarityServiceImpl implements VectorizedSimilarityService{

    @Value("${dataproc.project-id}")
    private String projectId;

    @Value("${dataproc.region}")
    private String region;

    @Value("${dataproc.cluster-name}")
    private String clusterName;

    @Override
    public BaseResponse<List<Row>> getVectorizedSimilarityRank(GetVectorizedSimilarityRankRequest request) {
        // TODO Param Validation
        return null;
    }

    @Override
    public BaseResponse<List<String>> getVectorizedSimilarityRankOnMultiField(GetVectorizedSimilarityRankOnMultiFieldRequest request) throws IOException, ExecutionException, InterruptedException {
        // TODO Param Check
        // TODO Double Type Unsupported
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

        String myEndpoint = String.format("%s-dataproc.googleapis.com:443", region);

        // Configure the settings for the job controller client.
        JobControllerSettings jobControllerSettings =
                JobControllerSettings.newBuilder().setEndpoint(myEndpoint).build();

        List<String> resultList = null;

        // Create a job controller client with the configured settings. Using a try-with-resources
        // closes the client,
        // but this can also be done manually with the .close() method.
        try (JobControllerClient jobControllerClient =
                     JobControllerClient.create(jobControllerSettings)) {

            // Configure cluster placement for the job.
            JobPlacement jobPlacement = JobPlacement.newBuilder().setClusterName(clusterName).build();

            String[] argsArr = new String[]{
                    tableName,
                    fieldNameSb.toString(),
                    request.getTargetId(),
                    String.valueOf(request.getRankTopSize())};
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
            return BaseResponse.with(Status.SUCCESS, resultList);
        } catch (Exception ignored) {}

        return BaseResponse.with(Status.FAILURE, "Dataproc Error");
    }
}

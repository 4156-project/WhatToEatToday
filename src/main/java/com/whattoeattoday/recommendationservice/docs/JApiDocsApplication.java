package com.whattoeattoday.recommendationservice.docs;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;

/**
 * Reference: https://japidocs.agilestudio.cn/#/
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/17/23
 */
public class JApiDocsApplication {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        DocsConfig config = new DocsConfig();
        // root project path
        config.setProjectPath(projectPath);
        // project name
        config.setProjectName("RecommendationService");
        // api version
        config.setApiVersion("V1.0");
        // api docs target path
        config.setDocsPath(projectPath);
        // auto generate
        config.setAutoGenerate(Boolean.TRUE);
        // output type
        config.addPlugin(new MarkdownDocPlugin());
        // execute to generate
        Docs.buildHtmlDocs(config);
    }
}

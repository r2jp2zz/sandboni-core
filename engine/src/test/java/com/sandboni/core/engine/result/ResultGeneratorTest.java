package com.sandboni.core.engine.result;

import com.sandboni.core.engine.*;
import org.junit.Assert;
import org.junit.Test;

public class ResultGeneratorTest {

    private Arguments getArguments(String fromChangeId, boolean selectiveMode, boolean runAllExternalTests, Stage stage) {
        return Arguments.builder()
            .fromChangeId(fromChangeId)
            .toChangeId("999eee")
            .repository(".")
            .runSelectiveMode(selectiveMode)
            .stage(stage.name())
            .runAllExternalTests(runAllExternalTests).build();
    }

    private Processor getProcessor(String fromChangeId, boolean runSelective, boolean runAllExternal, Stage stage)  {
        return new ProcessorBuilder().with(procBuilder->{
                procBuilder.arguments = getArguments(fromChangeId, runSelective, runAllExternal, stage);
                procBuilder.gitDetector = new ChangeDetectorResultMock();
            }).build();
    }

    @Test
    public void testResultWhenEmptyContext(){
        Processor processor = getProcessor("0", false, false, Stage.BUILD);

        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.NONE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyCnfgContext(){
        Processor processor = getProcessor("1", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyJavaContext(){
        Processor processor = getProcessor("2", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyNonSupportedFileContext(){
        Processor processor = getProcessor("5", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyNonSupportedFileContextRunSelectiveTrue(){
        Processor processor = getProcessor("5", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBothBuildCnfgAndJavaContext(){
        Processor processor = getProcessor("3", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBothBuildCnfgAndJavaContextRunSelectiveTrue(){
        Processor processor = getProcessor("3", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBothBuildCnfgAndNonSupportedFileContext(){
        Processor processor = getProcessor("6", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBothBuildCnfgAndNonSupportedFileContextRunSelectiveTrue(){
        Processor processor = getProcessor("6", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBuildCnfgJavaAndNonSupportedFileContext(){
        Processor processor = getProcessor("7", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBuildCnfgJavaAndNonSupportedFileContextRunSelectiveTrue(){
        Processor processor = getProcessor("7", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenJavaAndNonSupportedFileContext(){
        Processor processor = getProcessor("8", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenJavaAndNonSupportedFileContextRunSelectiveTrue(){
        Processor processor = getProcessor("8", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testWhenCnfgVVersionChangeAndJavaContext(){
        Processor processor = getProcessor("4", false, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenEmptyContextAndRunSelectiveModeIsTrue(){
        Processor processor = getProcessor("0", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.NONE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyCnfgContextAndRunSelectiveModeIsTrue(){
        Processor processor = getProcessor("1", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenOnlyJavaContextAndRunSelectiveModeIsTrue(){
        Processor processor = getProcessor("2", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenBothBuildCnfgAndJavaContextAndRunSelectiveModeIsTrue(){
        Processor processor = getProcessor("2", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenRunAllExternalTestsIsTrueDuringBuildStage() {
        Processor processor = getProcessor("2", true, true, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.ALL_EXTERNAL_UNIT);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.SELECTIVE, res.getFilterIndicator());
    }

    @Test
    public void testResultWhenRunAllExternalTestsIsTrueDuringIntegrationStage() {
        Processor processor = getProcessor("2", true, true, Stage.INTEGRATION);
        Result res = processor.getResultGenerator().generate(ResultContent.ALL_EXTERNAL_CUCUMBER);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(FilterIndicator.ALL_EXTERNAL, res.getFilterIndicator());
    }

    @Test
    public void testResultGetFilterIndicator() {
        Processor processor = getProcessor("2", true, false, Stage.BUILD);
        FilterIndicator indicator = processor.getResultGenerator().getFilterIndicator();
        Assert.assertEquals(FilterIndicator.SELECTIVE, indicator);
    }

    @Test
    public void testResultAllReachableEdges () {
        Processor processor = getProcessor("2", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.ALL_REACHABLE_EDGES);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testResultJiraRelatedTests () {
        Processor processor = getProcessor("2", true, false, Stage.BUILD);
        Result res = processor.getResultGenerator().generate(ResultContent.JIRA_RELATED_TESTS);
        Assert.assertTrue(res.isSuccess());
    }
}
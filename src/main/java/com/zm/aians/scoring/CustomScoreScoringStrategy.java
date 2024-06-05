package com.zm.aians.scoring;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zm.aians.model.dto.question.QuestionContentDTO;
import com.zm.aians.model.entity.App;
import com.zm.aians.model.entity.Question;
import com.zm.aians.model.entity.ScoringResult;
import com.zm.aians.model.entity.UserAnswer;
import com.zm.aians.model.vo.QuestionVO;
import com.zm.aians.service.QuestionService;
import com.zm.aians.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义
 */
@ScoringStrategyConfig(appType = 0,scoringStrategy = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;


    @Override
    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        Long appId = app.getId();
        //1.根据id查询到题目和题目结果信息（按分数降序排序）
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContentList = questionVO.getQuestionContent();
        //2.统计用户的总得分
        int totalScore = 0;
        for (int i = 0; i < choice.size(); i++) {
            String userChoice = choice.get(i);
            QuestionContentDTO questionContent = questionContentList.get(i);
            for(QuestionContentDTO.Option option : questionContent.getOptions()){
                if(userChoice.equals(option.getKey())){
                    totalScore += option.getScore();
                    break;
                }
            }
        }

        //3.遍历得分结果，找到第一个用户分数大于得分范围的结果，作为最终结果
        int finalTotalScore = totalScore;
        ScoringResult scoringResult = scoringResultList.stream()
                .filter(scoringResult1 -> finalTotalScore >= scoringResult1.getResultScoreRange())
                .findFirst()
                .orElse(null);

        //4.构造返回值，填充答案对象的属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setResultId(scoringResult.getId());
        userAnswer.setResultScore(totalScore);
        userAnswer.setResultName(scoringResult.getResultName());
        userAnswer.setResultPicture(scoringResult.getResultPicture());
        userAnswer.setResultDesc(scoringResult.getResultDesc());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setAppType(app.getAppType());
        userAnswer.setAppId(appId);
        userAnswer.setChoices(choice.toString());
        userAnswer.setUserId(app.getUserId());
        return userAnswer;
    }
}

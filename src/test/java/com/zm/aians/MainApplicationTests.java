package com.zm.aians;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 主类测试
 *

 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private ClientV4 clientV4;


    @Test
    void contextLoads() {
        //初始化客户端
        ClientV4 client = new ClientV4.Builder("b764bd12fa39a2b94292fb140d0456f9.CwTGZPvDjvTkTXWz").build();
        //构建请求
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);
        //请求id可以自己创建，也可以自动生成，类比数据库主键
        //String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                //false代表回答全部生成才返回，true代表生成一个字返回一个字
                .stream(Boolean.FALSE)
                //异步不会立马返回回答，而是返回id，回头要根据id去查询
        //        .invokeMethod(Constants.invokeMethodAsync)
                .messages(messages)
                .invokeMethod(Constants.invokeMethod)
        //        .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.getData().getChoices().get(0));

    }

}

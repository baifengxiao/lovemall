package cn.sc.love.gpt;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;

/**
 * @Author yupengtao
 * @Date 2023/6/8 18:49
 **/
public class Demo1 {
    public static void main(String[] args) {

        OpenAiService service = new OpenAiService(Constants.OPENAI_TOKEN,10000);
        CompletionRequest request = CompletionRequest.builder().model("text-davince-003")    //设置使用的模型
                .prompt("请帮我写一首情诗，送给我的陈姓女朋友")   //提示
                .temperature(0D)      //采样的波动性
                .maxTokens(1000)    //输入输出的多少
                .topP(1D)           //情绪采样。【0，1】：从悲观到乐观
                .frequencyPenalty(0D)       //频率处罚系数
                .presencePenalty(0D)    //重复处罚系数
                .build();


        List<CompletionChoice> choices = service.createCompletion(request).getChoices();
        System.out.println(choices);
        choices.forEach(System.out::println);
    }
}

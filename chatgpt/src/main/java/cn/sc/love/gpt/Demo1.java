package cn.sc.love.gpt;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

/**
 * @Author yupengtao
 * @Date 2023/6/8 18:49
 **/
public class Demo1 {
    public static void main(String[] args) {
        String info1 = "Q: 能不能我写一封简短的情话，使用诗经的语言风格?A:";
        info(info1);
    }

    public static void info(String promptInfo) {
//注意：参数2用于设置超时时间
        OpenAiService service = new OpenAiService(Constants.OPENAPI_TOKEN, 5000);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003") //使用的模型
                .prompt(promptInfo) //生成提示
                .temperature(0D) //创新采样
                .maxTokens(1000) //Token大小设置
                .topP(1D) //情绪采样。[0,1]:从悲观到乐观
                .frequencyPenalty(0D) //频率处罚系数。用来设置文本中出现重复词汇时的处罚参数
                .presencePenalty(0D) //重复处罚系数
                .build();
        service.createCompletion(completionRequest)
                .getChoices()
                .forEach(System.out::println);
    }
}
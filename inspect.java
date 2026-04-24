import java.lang.reflect.*;
public class Inspect {
  public static void main(String[] args) throws Exception {
    Class<?> c = Class.forName("org.springframework.ai.chat.memory.ChatMemory");
    for (Method m : c.getDeclaredMethods()) System.out.println(m.toString());
    Class<?> c2 = Class.forName("org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor");
    for (Method m : c2.getDeclaredMethods()) System.out.println("ADVISOR " + m.toString());
  }
}

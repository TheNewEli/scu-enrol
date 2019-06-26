package wechat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.converter.json.GsonBuilderUtils;


import javax.json.JsonArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static String getAccessToken() {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try {
            HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx989faa59743dae99&secret=5b64da2101cd29f3c8b3f18f78094d2c");
            client = HttpClients.createDefault();
            response = client.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String entity = "";
        try {
            entity = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return entity.split("\"")[3];
    }

    public static boolean verifyTeacher(String userName, String password) {
        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "verifyTeacher";

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        //请求
        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
            httpPost.setEntity(new StringEntity("{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}"));
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取结果
        String result = "";
        try {
            result = EntityUtils.toString(response.getEntity(), "UTF-8").split("\"")[9];
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if ("true".equals(result))
            return true;
        else
            return false;
    }

    public static int getTotalQuestionCount() {
        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "getTotalQuestionNumber";

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;


        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
            httpPost.setEntity(new StringEntity("{}"));
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String entity = "";
        try {
            entity = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(entity.split(":")[4].split(",")[0]);
    }

    public static List<Question> getQuestionList(int skip, int limit) {
        List<Question> questionList = new ArrayList<Question>();


        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "getQuestionPage";

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        //请求
        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
            httpPost.setEntity(new StringEntity("{\"skip\":\"" + skip + "\",\"limit\":\"" + limit + "\"}"));
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取结果
        String result = "";
        try {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(result);

        //转换对象
        JSONObject jsonResult = JSON.parseObject(result);
        questionList = JSONObject.parseArray(jsonResult.getJSONObject("resp_data").getJSONArray("data").toJSONString(), Question.class);
        questionList = completeQuestionList(questionList);


        return questionList;
    }

    private static List<Question> completeQuestionList(List<Question> list) {
        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "getUserInfo";

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        for (Question q : list
        ) {

            //请求
            try {
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
                httpPost.setEntity(new StringEntity("{\"openid\":\"" + q.get_openid() + "\"}"));
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //获取结果
            String result = "";
            try {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //关闭资源
            try {
                client.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("asdf" + result);
            JSONArray jsonValues = JSON.parseObject(result).getJSONObject("resp_data").getJSONArray("data");
            JSONObject studentInfo = new JSONObject();
            if (jsonValues.size() > 0) {
                studentInfo = jsonValues.getJSONObject(0);
                q.setSutdent_id(studentInfo.getString("_id"));
                q.setArt_n_sicence(studentInfo.getString("art_n_sicence"));
                q.setDistrict(studentInfo.getString("district"));
                q.setGender(studentInfo.getString("gender"));
                q.setName(studentInfo.getString("name"));
                q.setPhone_number(studentInfo.getString("phone_number"));
                q.setRanking(studentInfo.getString("ranking"));
                q.setSchool(studentInfo.getString("school"));
                q.setScore(studentInfo.getString("score"));
            }
        }
        return list;
    }

    public static void reply(String reply, String question_id) {
        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "reply";


        System.out.println(reply + "^^^^^^^^" + question_id);
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        //请求
        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
            httpPost.setEntity(new StringEntity("{\"reply\":\"" + reply + "\",\"question_id\":\"" + question_id + "\"}", "UTF-8"));
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取结果
        String result = "";
        try {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        templetMesg(reply, question_id);
    }

    private static void templetMesg(String reply, String question_id) {
        String access_token = getAccessToken();
        String env = "scu-undergraduate-tu0da";
        String functionName = "openapi";
        String action = "sendTemplateMessage";


        System.out.println(reply);
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        //请求
        try {
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/tcb/invokecloudfunction?access_token=" + access_token + "&env=" + env + "&name=" + functionName);
            httpPost.setEntity(new StringEntity("{\"reply\":\"" + reply + "\",\"action\":\"" + action + "\",\"question_id\":\"" + question_id + "\"}", "UTF-8"));
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取结果
        String result = "";
        try {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭资源
        try {
            client.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

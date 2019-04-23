import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientCodeGenerator {
    private static final String AUTHOR = "ClientCodeGenerator";//@author
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date
    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template/";//模板位置
    private static final String CONFIG_FILE_PATH = PROJECT_PATH + "/src/test/resources/config/";//模板位置

    private static final String WEB_APP_PATH = PROJECT_PATH + "/src/web/";//模板位置

    public static void main(String[] args){
        System.out.println(PROJECT_PATH);
        System.out.println(TEMPLATE_FILE_PATH);

        JSONObject jsonData = parseJson("ball.json");
        System.out.println(jsonData.get("fields"));
        generateVue(jsonData);
    }

    private static void generateVue(JSONObject jsonData) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, String> jsonMap = processJsonData(jsonData);

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("name", jsonData.get("name"));
            data.put("columns", jsonMap.get("columnsBuilder"));
            data.put("EditForm", jsonMap.get("formBuiler"));
            data.put("editFormData", jsonMap.get("editFormDataBuilder"));
            data.put("editFormDataRule", jsonMap.get("editFormDataRuleBuilder"));

            File file = new File(WEB_APP_PATH +"view/"+
                    jsonData.get("module") + "/" +
                    jsonData.get("name") + ".vue");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("template-vue.ftl").process(data, new FileWriter(file));

            System.out.println(file.getAbsolutePath() + "生成成功");

        } catch (Exception e) {
            System.err.println(">>> generate Vue failed...");
            e.printStackTrace();
        }

    }

    private static Map processJsonData(JSONObject jsonData) {
        Map result = new HashMap();
        String name = (String)jsonData.get("name");

        StringBuilder columnsBuilder = new StringBuilder();
        columnsBuilder.append("[ { type: 'index', title: '序号', width: 60, align: 'center' }");


        StringBuilder formBuiler = new StringBuilder();
        formBuiler.append("<Form ref='"+name+"Form' :model='"+name+"Form' :rules='"+name+"FormRule' :label-width='80'>");

        StringBuilder editFormDataBuilder = new StringBuilder();
        editFormDataBuilder.append("{");

        StringBuilder editFormDataRuleBuilder = new StringBuilder();
        editFormDataRuleBuilder.append("{");

        JSONArray fields = jsonData.getJSONArray("fields");

        String fieldType = "";
        String fieldName = "";
        int fieldMaxlength = -1;
        Iterator iter = fields.iterator();
        while (iter.hasNext()){
            JSONObject jsonObj = (JSONObject)iter.next();
            fieldType = (String) jsonObj.get("type");
            fieldName = (String) jsonObj.get("name");
            if (jsonObj.containsKey("maxlength")) {
                fieldMaxlength =(Integer)jsonObj.get("maxlength");
            }


            columnsBuilder.append(", { title: '"+ jsonObj.get("label") +"', key: '"+ fieldName +"' }");

            formBuiler.append("<FormItem label='"+jsonObj.get("label")+"' prop='"+fieldName+"' >");
            if ("text".equals(fieldType) || StringUtils.isEmpty(fieldType)) {
              formBuiler.append("<Input v-model='"+name+"Form."+fieldName+"' :maxlength="+fieldMaxlength+" placeholder='请输入"+fieldName+"' />");
            } else if ("textarea".equals(fieldType)) {
              formBuiler.append(" <Input v-model='"+name+"Form."+fieldName+"' type='textarea' :maxlength="+fieldMaxlength+" :autosize='{minRows: 2,maxRows: 5}' placeholder='请输入...' />");
            } else if ("number".equals(fieldType)) {
                formBuiler.append("<InputNumber v-model='"+name+"Form."+fieldName+"' :max='"+jsonObj.get("max")+"' />");
            } else if ("date".equals(fieldType)) {
                formBuiler.append("<DatePicker type='date' v-model='"+name+"Form."+fieldName+"' placeholder='选择日期' style='width: 200px' />");
            } else if ("datetime".equals(fieldType)) {
                formBuiler.append("<DatePicker type='datetime' v-model='"+name+"Form."+fieldName+"' placeholder='选择日期和时间' style='width: 200px' />");
            } else if ("date".equals(fieldType)) {
                //TODO
            }
            formBuiler.append("</FormItem>");

            editFormDataBuilder.append(fieldName+": undefined, ");

            editFormDataRuleBuilder.append(fieldName+": [");
            JSONArray fieldRules = jsonObj.getJSONArray("rules");
            Iterator ruleIter = fieldRules.iterator();
            while (ruleIter.hasNext()) {
                String rule = (String) ruleIter.next();
                if ("required".equals(rule) && "number".equals(fieldType)) {
                    editFormDataRuleBuilder.append("{ type: 'number', required: true, message: '"+fieldName+"不能为空.', trigger: 'blur' }, ");
                } if ("required".equals(rule) && fieldType.startsWith("date")) {
                    editFormDataRuleBuilder.append("{ type: 'date', required: true, message: '"+fieldName+"不能为空.', trigger: 'blur' }, ");
                } else if ("required".equals(rule) ) {
                    editFormDataRuleBuilder.append("{ required: true, message: '"+fieldName+"不能为空.', trigger: 'blur' }, ");
                } else if ("email".equals(rule)) {
                    editFormDataRuleBuilder.append("{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }, ");
                } else if ("maxlength".equals(rule)) {
                    editFormDataRuleBuilder.append("{ type: 'string', max: "+fieldMaxlength+", message: '"+fieldName+"最多"+fieldMaxlength+"字符', trigger: 'blur' }, ");
                } else if ("minlength".equals(rule)) {
                    editFormDataRuleBuilder.append("{ type: 'string', min: "+jsonObj.get("minlength")+", message: '"+fieldName+"至少"+jsonObj.get("minlength")+"字符', trigger: 'blur' }, ");
//                } else if ("max".equals(rule)) {
//                    editFormDataRuleBuilder.append("{ type: 'string', max: "+jsonObj.get("max")+", message: '"+fieldName+"最大"+jsonObj.get("max")+"', trigger: 'blur' }, ");
//                } else if ("min".equals(rule)) {
//                    editFormDataRuleBuilder.append("{ type: 'string', min: "+jsonObj.get("min")+", message: '"+fieldName+"最小"+jsonObj.get("min")+"字符', trigger: 'blur' }, ");
                } else if ("xx".equals(rule)) {
                    //TODO
                }
            }
            editFormDataRuleBuilder.substring(0, editFormDataBuilder.lastIndexOf(","));
            editFormDataRuleBuilder.append("], ");

        }
        columnsBuilder.append(" , { title: '操作', align: 'center', key: 'handle', render: (h, params) => { return this.$common.render(this,h,params); } } ]");
        System.out.println(">>> table columns: " + columnsBuilder.toString());


        formBuiler.append("</Form>");
        formBuiler.append("<div slot=\"footer\">\n" +
                "            <Button type=\"ghost\" @click=\"handleReset()\" style=\"margin-left: 8px\">取消</Button>\n" +
                "            <Button type=\"primary\" :loading=\"isSaving\" @click=\"handleSubmit()\">保存</Button>\n" +
                "        </div>");
        System.out.println(">>> EditForm: " + formBuiler.toString());

        editFormDataBuilder.substring(0, editFormDataBuilder.lastIndexOf(","));
        editFormDataBuilder.append("}");
        System.out.println(">>> editFormDataBuilder: " + editFormDataBuilder.toString());

        editFormDataRuleBuilder.substring(0, editFormDataBuilder.lastIndexOf(","));
        editFormDataRuleBuilder.append("}");
        System.out.println(">>> editFormDataRuleBuilder: " + editFormDataRuleBuilder.toString());

        result.put("columnsBuilder", columnsBuilder.toString());
        result.put("formBuiler", formBuiler.toString());
        result.put("editFormDataBuilder", editFormDataBuilder.toString());
        result.put("editFormDataRuleBuilder", editFormDataRuleBuilder.toString());

        return result;
    }

    private static JSONObject parseJson(String fileName){
        JSONObject jsonData=null;
        try {
            File file = new File(CONFIG_FILE_PATH+fileName);
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            jsonData= JSON.parseObject(stringBuilder.toString());//得到JSONobject对象
        }catch (Exception e){
            System.err.println(">>>> Parse template failed");
            e.printStackTrace();
        }
        return jsonData;
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }


}

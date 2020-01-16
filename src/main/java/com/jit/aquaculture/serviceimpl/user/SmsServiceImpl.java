package com.jit.aquaculture.serviceimpl.user;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.jit.aquaculture.commons.CodeProperties;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.user.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CodeProperties codeProperties;

    public SendSmsResponse sendSms(String mobile, String brand, HttpServletRequest httpServletRequest) throws ClientException{

        //产品名称:云通信短信API产品,开发者无需替换
        final String product = "Dysmsapi";
        //产品域名,开发者无需替换
        final String domain = "dysmsapi.aliyuncs.com";

//        final String accessKeyId = codeProperties.getAccessKeyId();
//        final String accessKeySecret = codeProperties.getAccessKeySecret();

        final String accessKeyId = "LTAI4Fbit4dX2j3AhRCwHrCM";
        final String accessKeySecret = "BrLHDXbENYeHozXDH2BWwTJu4EOxlz";

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,
        // 验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(codeProperties.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(codeProperties.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");

        request.setTemplateParam("{name:'" + brand + "'}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            return sendSmsResponse;
        } else {
            throw new BusinessException(sendSmsResponse.getMessage());
        }
//        return sendSmsResponse;
    }
}

package hello;

import java.lang.Exception;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.factory.CallFactory;


@RestController
public class GreetingController {
    @RequestMapping(value = "/greeting", produces = "text/xml")
    public String greeting() {
        TwiMLResponse twiml = new TwiMLResponse();
        Message message = new Message("Hello, Mobile Monkey");
        try{
            twiml.append(message);
        }catch(Exception e){
            e.printStackTrace();
        }

        return twiml.toXML();
    }

    @RequestMapping(value = "/callback")
    public void callBack() throws TwilioRestException{
        TwilioRestClient client = new TwilioRestClient(ST.ACCOUNT_SID, ST.AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", "+447590566866"); // Replace with your phone number
        callParams.put("From", "+441212852673"); // Replace with a Twilio number
        callParams.put("Url", "http://demo.twilio.com/welcome/voice/");
        // Make the call
        Call call = callFactory.create(callParams);
        System.out.println(call.getSid());
    }
}
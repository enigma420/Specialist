package pl.specialist.searchexpert.exceptions;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        /*handle the error first, we will modify later*/
        Map<String,Object> errorAttributes = super.getErrorAttributes(webRequest,includeStackTrace);

        /*Format & update timestamp*/
        Object timestamp = errorAttributes.get("timestamp");
        if(timestamp == null) errorAttributes.put("timestamp", dateFormat.format(new Date()));
        else errorAttributes.put("timestamp", dateFormat.format((Date) timestamp));

        errorAttributes.put("version","0.1");

        return errorAttributes;
    }
}

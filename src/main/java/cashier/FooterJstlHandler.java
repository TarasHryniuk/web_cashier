package cashier;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * @author Taras Hryniuk, created on  12.10.2020
 * email : hryniuk.t@gmail.com
 */
public class FooterJstlHandler extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("© Copyright 2020 final.project");
        } catch (IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }

}

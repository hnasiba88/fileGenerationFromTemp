import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

public class GeneratePDF {

	
	private static final String REKVIZIT_AZN = "REKVIZIT_AZN";
	private static final String ERIZE2 = "ERIZE2";
	private static final String Contract_card_account = "Contract_card_account";
	private static final String UTF_8 = "UTF-8";

	public static void main(String[] args) throws Exception {
		generatePdf();
	}

	public static void generatePdf() throws Exception {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
        Data data = setValues();
        Context context = new Context();
        context.setVariable("data", data);
        ITextRenderer renderer = new ITextRenderer();
		renderer.getFontResolver().addFont("Code39.ttf", IDENTITY_H, EMBEDDED);
        generateFile(templateEngine, context, renderer, FileSystems
                .getDefault()
                .getPath("src", "rekvizit", "resources")
                .toUri()
                .toURL()
                .toString(),REKVIZIT_AZN);
        generateFile(templateEngine, context, renderer, FileSystems
                .getDefault()
                .getPath("src", "erize2", "resources")
                .toUri()
                .toURL()
                .toString(),ERIZE2);
        generateFile(templateEngine, context, renderer, FileSystems
                .getDefault()
                .getPath("src", "contractCardAccount", "resources")
                .toUri()
                .toURL()
                .toString(),Contract_card_account);
    }

	private static void generateFile(TemplateEngine templateEngine, Context context, ITextRenderer renderer,
			String baseUrlErize, String output)
			throws UnsupportedEncodingException, DocumentException, IOException, FileNotFoundException {
		String fileName = output+".pdf";
		renderer.setDocumentFromString(convertToXhtml(templateEngine.process(output, context)), baseUrlErize);
        renderer.layout();
		renderer.createPDF(new FileOutputStream(fileName));
	}

    private static Data setValues() {
        Data data = new Data();
        data.setFirstname("John");
        data.setLastname("Doe");
        data.setStreet("Example Street 1");
        data.setZipCode("12345");
        data.setCity("Example City");
        data.setAccount("fdgfd");
        data.setBankAddress("sdfsdfsd");
        data.setBranchName("ddsfsd");
        data.setCity("dsfsdf");
        data.setCorrespondentAcc1("fdsfsd");
        data.setCustomerName("dfsdfsd");
        data.setDetails("fsdfsd");
        data.setFirstname("grgrgr");
        data.setLastname("fdsfsd");
        data.setStreet("dafdfd");
        data.setSWIFTcode("fdfsdfsd");
        data.setVoen("efgesfg");
        data.setUserName("sdsad");
        data.setCurrency("AZN");
        data.setAmount1("45");
        data.setCardExpired("dasdasd");
        data.setAmountInWriting1("5445");
        data.setRefNo("refNo");
        data.setBranchCity("fdfsdfsd");
        data.setBranchName("fsdfsdgsd");
        data.setOpenDate("fsdfsdgsd");
        data.setBranchChief("fsdfsdgsd");
        data.setCustomerName("fsdfsdgsd");
        data.setBankAddress("fsdfsdgsd");
        data.setBankCode("fsdfsdgsd");
        return data;
    }

    private static String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setCharEncoding(Configuration.UTF8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }
	
}